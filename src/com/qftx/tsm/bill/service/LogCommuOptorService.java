package com.qftx.tsm.bill.service;

import com.qftx.base.auth.bean.User;
import com.qftx.base.auth.dao.UserMapper;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.common.cached.CachedService;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.bill.bean.LogCommuOptorBean;
import com.qftx.tsm.bill.bean.LogFdOptorBean;
import com.qftx.tsm.bill.dao.LogCommuOptorMapper;
import com.qftx.tsm.bill.dao.LogFdOptorMapper;
import com.qftx.tsm.bill.dto.LogCommuOptorDto;
import com.qftx.tsm.bill.dto.LogFdOptorDto;
import com.qftx.tsm.log.service.LogUserOperateService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

@Service
public class LogCommuOptorService {
	Logger logger = Logger.getLogger(LogCommuOptorService.class);
	private static final String COMMUNICATION_LENS_UPDATE = "UPDATE AUTH_USER SET COMMUNICATIONS_TIMES = ? WHERE USER_ACCOUNT = ?";
	private static final String COMMUNICATION_TD_LENS_UPDATE = "UPDATE AUTH_USER SET COMMUNICATIONS_TIMES = ? , TD_DISTRIBUTION_TIMES = ? WHERE USER_ACCOUNT = ?";
	public static final String UPDATE_MESSAGE_NUM_SQL = "UPDATE AUTH_USER SET MESSAGES_NUMBER = ? WHERE USER_ACCOUNT = ?";
	@Autowired
	private LogCommuOptorMapper logCommuOptorMapper;
	@Autowired
	private LogFdOptorMapper logFdOptorMapper;
	@Autowired
	private UserMapper userMapper;
	@Resource transient private JdbcTemplate jdbcTemplate;
	@Autowired
	private CachedService cachedService;
	@Autowired
	private LogUserOperateService logUserOperateService;
	/** 
	 * 分配通信时长
	 * @param responeStr
	 * @param user
	 * @param memberAccs
	 * @param realTimes
	 * @param orgId
	 * @param isTdDisTimes 
	 * @throws Exception 
	 * @create  2016年3月1日 下午1:26:06 lixing
	 * @history  
	 */
	public void rechargeCommuTimes(List<Map<String, Object>> returnMaps,ShiroUser user,String memberAccs,Double realTimes,String orgId,Long isTdDisTimes,String module) throws Exception{
		User adminUser = userMapper.getAdmin(user.getOrgId());
		User tempUser = null;
		List<User> users = new ArrayList<User>();
		List<String> accounts = new ArrayList<String>();
		Map<String, User> um = new HashMap<String, User>();
		String[] accs = memberAccs.split(",");
		for (String acc : accs) {
			accounts.add(acc);
		}
		List<User> uList = userMapper.findUsersByAccs(accounts);
		if (uList != null && uList.size() > 0) {
			for (User u : uList) {
				um.put(u.getUserAccount(), u);
			}
		}
		
		for(Map<String, Object> reMap : returnMaps){
			tempUser = new User();
			tempUser.setUserAccount(reMap.get("account").toString());
			tempUser.setCommunicationsTimes(new Double(reMap.get("balance").toString()));
			// true:定时分配时长，增加每天定时分配时长的值
			if(isTdDisTimes != null){
				if (um.containsKey(reMap.get("account").toString())) {
					if (um.get(reMap.get("account").toString()).getTdDistributionTimes() == null) {
						tempUser.setTdDistributionTimes(isTdDisTimes);
					} else {
						tempUser.setTdDistributionTimes(um.get(reMap.get("account").toString()).getTdDistributionTimes() + isTdDisTimes);
					}
				}
			}
			users.add(tempUser);
		}
		if (isTdDisTimes == null) { // 无当天分配时长
			updateCommunicationLens(COMMUNICATION_LENS_UPDATE, users, false);
		} else { // 有当天分配时长
			updateCommunicationLens(COMMUNICATION_TD_LENS_UPDATE, users, true);
		}
		
		// 批量新增通信包操作日志记录(包括管理员与普通成员)
		List<LogFdOptorBean> commuOptors = new ArrayList<LogFdOptorBean>();
		LogFdOptorBean commuOptor = null;		
		Date optTime = new Date();
		
		for (int i = 0; i < returnMaps.size(); i++) {
			Map<String, Object> tempMap = returnMaps.get(i);
			String account = "";
			String remainTime = "";
			account = tempMap.get("account").toString();
			remainTime = tempMap.get("balance").toString();
			commuOptor = new LogFdOptorBean();
			commuOptor.setAllocationId(SysBaseModelUtil.getModelId());
			// true:定时分配时长，增加每天定时分配时长的值		
			commuOptor.setOperateAcc(user.getAccount());
			commuOptor.setAllocationAcc(account);
			commuOptor.setOperateTimelength(new Double(realTimes));
				
			//这个是设置管理员的操作时长
			if(account.equals(adminUser.getUserAccount()) || (StringUtils.isNotBlank(module) && account.equals(user.getAccount()))){
				commuOptor.setOperateType(AppConstant.COMMUNI_OPREATE_TYPE4);//单位管理员增加
			}else{
				if(isTdDisTimes == null){						
				commuOptor.setOperateType(AppConstant.COMMUNI_OPREATE_TYPE1);
				}else{
					commuOptor.setOperateType(AppConstant.COMMUNI_OPREATE_TYPE6);
				}
			}
			commuOptor.setOperateTime(optTime);
			commuOptor.setOrgId(orgId);
			commuOptor.setAfterOperateTimelength(new Double(remainTime));
			
			commuOptors.add(commuOptor);
			
		}
		logFdOptorMapper.insertBatch(commuOptors);
//		Map<String, String> nameMap = cachedService.getOrgUserNames(orgId);
//		for(LogFdOptorBean lob : commuOptors){
//			logUserOperateService.setUserOperateLog( AppConstant.Module_id109,AppConstant.Module_Name109, AppConstant.Operate_id45, AppConstant.Operate_Name45, lob.getOperateTimelength().toString()+"分钟，分配对象："+nameMap.get(lob.getAllocationAcc()),"");
//		}
	}
	
	
	/** 
	 * 回收通信时长
	 * @param responeStr
	 * @param user
	 * @param memberAccs
	 * @param timesStr
	 * @param orgId
	 * @param real_times 
	 * @throws Exception 
	 * @create  2016年3月1日 下午1:25:44 lixing
	 * @history  
	 */
	public void recoverCommuTimes(List<Map<String, Object>> returnMaps,ShiroUser user,String module) throws Exception{
		User adminUser = userMapper.getAdmin(user.getOrgId());
		List<User> users = new ArrayList<User>();
		for(Map<String, Object> tempMap : returnMaps){
			User tempUser = new User();
			tempUser.setUserAccount(tempMap.get("account").toString());
			tempUser.setCommunicationsTimes(new Double(tempMap.get("balance").toString()));
			users.add(tempUser);
		}
		updateCommunicationLens(COMMUNICATION_LENS_UPDATE, users, false);
		List<LogFdOptorBean> commuOptors = new ArrayList<LogFdOptorBean>();
		LogFdOptorBean commuOptor = null;
		Date optTime = new Date();
		for (int i = 0; i < returnMaps.size(); i++) {
			Map<String, Object> tempMap = returnMaps.get(i);
			String account = "";
			String realTime = "";
			String remainTime = "";
			account = tempMap.get("account").toString();
			realTime = "-"+tempMap.get("realOperCount").toString();
			remainTime = tempMap.get("balance").toString();
			commuOptor = new LogFdOptorBean();
			commuOptor.setAllocationId(SysBaseModelUtil.getModelId());
			commuOptor.setAllocationAcc(account);
			commuOptor.setOperateTimelength(new Double(realTime));
			commuOptor.setOperateAcc(user.getAccount());
			//这个是设置管理员的操作时长
			if(account.equals(adminUser.getUserAccount()) || (StringUtils.isNotBlank(module) && account.equals(user.getAccount()))){
				commuOptor.setOperateType(AppConstant.COMMUNI_OPREATE_TYPE5);//单位管理员增加
			}else{
				commuOptor.setOperateType(AppConstant.COMMUNI_OPREATE_TYPE2);
			}
			commuOptor.setOperateTime(optTime);
			commuOptor.setOrgId(user.getOrgId());
			commuOptor.setAfterOperateTimelength(new Double(remainTime));
			
			commuOptors.add(commuOptor);
		}
		logFdOptorMapper.insertBatch(commuOptors);
//		Map<String, String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
//		for(LogFdOptorBean lob : commuOptors){
//			logUserOperateService.setUserOperateLog( AppConstant.Module_id109,AppConstant.Module_Name109, AppConstant.Operate_id46, AppConstant.Operate_Name46, lob.getOperateTimelength().toString()+"分钟，分配对象："+nameMap.get(lob.getAllocationAcc()),"");
//		}
	}
	
	
	/** 
	 * 分配短信
	 * @param user
	 * @param memberAccs
	 * @param realTimes
	 * @param orgId 
	 * @throws Exception 
	 * @create  2016年3月1日 下午1:26:35 lixing
	 * @history  
	 */
	public void smsRecharge(ShiroUser user,User adminUser,final List<Map<String, Object>> maps,Integer realTimes,String orgId) throws Exception{
		jdbcTemplate.batchUpdate(UPDATE_MESSAGE_NUM_SQL, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setInt(1, Integer.parseInt(maps.get(i).get("balance") ==null ? "0" : maps.get(i).get("balance").toString()));
				ps.setString(2, maps.get(i).get("account").toString());
			}
			public int getBatchSize() {
				return maps.size();
			}
		});
		List<String> list = new ArrayList<String>();
		int totalSms = 0 ;
		for(Map<String, Object> tempMap : maps){
			totalSms = totalSms + realTimes;
			list.add(tempMap.get("account").toString());
		}
		adminUser.setMessagesNumber(-totalSms);//计算管理员的短信数量
		list.add(adminUser.getUserAccount());
		
		List<LogCommuOptorBean> commuOptors = new ArrayList<LogCommuOptorBean>();
		LogCommuOptorBean commuOptor = null;
		List<User> userList = new ArrayList<User>();
		userList =  this.userMapper.findUsersByAccs(list);
		Date optTime = new Date();
		for (int i = 0; i < userList.size(); i++) {
			User tempUser = userList.get(i);
			String account = "";
			String remainTime = "";
			account = tempUser.getUserAccount();
			remainTime = tempUser.getMessagesNumber()+"";
			commuOptor = new LogCommuOptorBean();
			commuOptor.setAllocationId(SysBaseModelUtil.getModelId());
			commuOptor.setOperateAcc(user.getAccount());
			commuOptor.setAllocationAcc(account);
				
			//这个是设置管理员的操作时长
			if(account.equals(adminUser.getUserAccount())){
				commuOptor.setOperateTimelength(new BigDecimal(-totalSms));
				commuOptor.setOperateType(AppConstant.COMMUNI_OPREATE_TYPE4);//单位管理员增加
			}else{
				commuOptor.setOperateTimelength(new BigDecimal(realTimes));
				commuOptor.setOperateType(AppConstant.SMS_OPREATE_TYPE7);
			}
			commuOptor.setOperateTime(optTime);
			commuOptor.setOrgId(orgId);
			commuOptor.setAfterOperateTimelength(new Long(remainTime));
			
			commuOptors.add(commuOptor);
		}
		logCommuOptorMapper.insertBatch(commuOptors);
//		Map<String, String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
//		for(LogCommuOptorBean lob : commuOptors){
//			logUserOperateService.setUserOperateLog( AppConstant.Module_id109,AppConstant.Module_Name109, AppConstant.Operate_id47, AppConstant.Operate_Name47, lob.getOperateTimelength().toString()+"条，分配对象："+nameMap.get(lob.getAllocationAcc()),"");
//		}
	}
	
	
	/** 
	 * 回收短信
	 * @param returnMaps
	 * @param user
	 * @param adminUser
	 * @throws Exception 
	 * @create  2016年3月1日 下午1:55:05 lixing
	 * @history  
	 */
	public void smsRecoverCommuTimes(final List<Map<String, Object>> returnMaps,ShiroUser user,User adminUser) throws Exception{
		jdbcTemplate.batchUpdate(UPDATE_MESSAGE_NUM_SQL, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setInt(1, Integer.parseInt(returnMaps.get(i).get("balance") ==null ? "0" : returnMaps.get(i).get("balance").toString()));
				ps.setString(2, returnMaps.get(i).get("account").toString());
			}
			public int getBatchSize() {
				return returnMaps.size();
			}
		});
//		List<String> list = new ArrayList<String>();
		int totalSms = 0;
		for(Map<String, Object> tempMap : returnMaps){
			totalSms = totalSms + Integer.parseInt(tempMap.get("operCount").toString());
		}
		adminUser.setMessagesNumber(totalSms);//计算管理员的短信数量
//		list.add(adminUser.getUserAccount());
		
		List<LogCommuOptorBean> commuOptors = new ArrayList<LogCommuOptorBean>();
		LogCommuOptorBean commuOptor = null;
		
//		List<User> userList = new ArrayList<User>();
//		userList =  this.userMapper.findUsersByAccs(list);
		
		Date optTime = new Date();
		
		for (int i = 0; i < returnMaps.size(); i++) {
			Map<String, Object> tempMap = returnMaps.get(i);
			String realTime = tempMap.get("operCount").toString();
			String account = tempMap.get("account").toString();
			String remainTime = tempMap.get("balance").toString();
			commuOptor = new LogCommuOptorBean();
			commuOptor.setAllocationId(SysBaseModelUtil.getModelId());
			commuOptor.setAllocationAcc(account);
			commuOptor.setOperateAcc(user.getAccount());
			//这个是设置管理员的操作时长
			if(account.equals(adminUser.getUserAccount())){
				commuOptor.setOperateTimelength(new BigDecimal(totalSms));
				commuOptor.setOperateType(AppConstant.COMMUNI_OPREATE_TYPE5);//单位管理员增加
			}else{
				commuOptor.setOperateTimelength(new BigDecimal("-"+realTime));
				commuOptor.setOperateType(AppConstant.SMS_OPREATE_TYPE8);
			}
			commuOptor.setOperateTime(optTime);
			commuOptor.setOrgId(user.getOrgId());
			commuOptor.setAfterOperateTimelength(new Long(remainTime));
			
			commuOptors.add(commuOptor);
		}
		
		logCommuOptorMapper.insertBatch(commuOptors);
//		Map<String, String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
//		for(LogCommuOptorBean lob : commuOptors){
//			logUserOperateService.setUserOperateLog( AppConstant.Module_id109,AppConstant.Module_Name109, AppConstant.Operate_id48, AppConstant.Operate_Name48, lob.getOperateTimelength().toString()+"条，分配对象："+nameMap.get(lob.getAllocationAcc()),"");
//		}
	}
	
	/**
	 * 批量更新通话时长
	 *
	 * @param sql
	 * @param inlist
	 * @param hasTdLens 是否有“当天分配时长”
	 * @create  2015-4-15 下午01:15:00 Administrator
	 * @history
	 */
	private void updateCommunicationLens(String sql, final List<User> inlist, final boolean hasTdLens) {
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			//为prepared statement设置参数。这个方法将在整个过程中被调用的次数
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setDouble(1, inlist.get(i).getCommunicationsTimes());
				if (hasTdLens) { // 有当天分配时长
					ps.setLong(2, inlist.get(i).getTdDistributionTimes());
					ps.setString(3, inlist.get(i).getUserAccount());
				} else { // 无当天分配时长
					ps.setString(2, inlist.get(i).getUserAccount());
				}
			}
			//返回更新的结果集条数
			public int getBatchSize() {
				return inlist.size();
			}
		});
	}
	
	public List<LogCommuOptorDto> getLogCommuOptorListPage(LogCommuOptorDto optorDto){
		return logCommuOptorMapper.findLogCommuOptorListPage(optorDto);
	}
	
	public List<LogFdOptorDto> getLogFdOptorListPage(LogFdOptorDto optorDto){
		return logFdOptorMapper.findLogFdOptorListPage(optorDto);
	}
}
