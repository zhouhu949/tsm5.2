package com.qftx.tsm.worklog.service;

import com.qftx.base.auth.bean.User;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.team.bean.TeamGroupBean;
import com.qftx.base.team.bean.TsmTeamGroupMemberBean;
import com.qftx.base.team.service.TsmTeamGroupMemberService;
import com.qftx.base.team.service.TsmTeamGroupService;
import com.qftx.base.util.DateUtil;
import com.qftx.base.util.GuidUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.common.domain.BaseJsonResult;
import com.qftx.tsm.workAll.bean.WorkAllIndexBean;
import com.qftx.tsm.workAll.service.WorkAllIndexService;
import com.qftx.tsm.worklog.bean.WorkLogInfoBean;
import com.qftx.tsm.worklog.dao.WorkLogInfoMapper;
import com.qftx.tsm.worklog.dto.WorkLogDto;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class WorkLogInfoService {
	@Autowired
	private WorkLogInfoMapper workLogInfoMapper;
	@Autowired
	private TsmTeamGroupService tsmTeamGroupService;
	@Autowired
	private TsmTeamGroupMemberService tsmTeamGroupMemberService;
	@Autowired
	private WorkLogBbsUpService workLogBbsUpService;
	@Autowired
	private CachedService cachedService;
	@Autowired
	private WorkAllIndexService workAllIndexService;
	private Logger logger=  Logger.getLogger(WorkLogInfoService.class);
	/* 查询日历状态*/
	public List<Date> getCalendarState(String orgId,String userId,long dateTime){
		Date date = new Date(dateTime);
		return  queryWorkLogByMonth(orgId,userId, date);
	}
	
	/* 查询管理者日历状态*/
	public List<Date> getManagerCalendarState(ShiroUser user,long dateTime){
		String orgId = user.getOrgId();
		String account= user.getAccount();
		String myId= user.getId();
		
		Date date = new Date(dateTime);
		List<TeamGroupBean> groups = tsmTeamGroupService.queryManageGroupList(orgId, account);
		String[] groupIds = tsmTeamGroupService.getGroupIds(groups);
		
		List<TsmTeamGroupMemberBean> users = tsmTeamGroupMemberService.findByGroupIds(orgId, groupIds);
		
		if(users!=null && users.size()>0){
			List<String> userIds = new ArrayList<String>();
			for (int i =0;i<users.size();i++) {
				if(myId.equals(users.get(i).getUserId())) continue;
				userIds.add(users.get(i).getUserId());
			}
			
			WorkLogInfoBean entity = new WorkLogInfoBean();
			entity.setFromDate(DateUtil.monthBegin(date));
			entity.setToDate(DateUtil.monthEnd(date));
			entity.setIsDel(0);
			entity.setStatus(1);
			entity.setOrgId(orgId);
			entity.setUserIds(userIds.toArray(new String[userIds.size()]));
			
			return workLogInfoMapper.queryManagerCalendarState(entity);
		}else{
			return new ArrayList<Date>();
		}
	}
	/* 查询管理者日历状态*/
	public List<Map<String, String>> findNoLogUsers(ShiroUser user,long dateTime){
		String orgId = user.getOrgId();
		String account= user.getAccount();
		String myId= user.getId();
		
		Date date = new Date(dateTime);
		List<TeamGroupBean> groups = tsmTeamGroupService.queryManageGroupList(orgId, account);
		String[] groupIds = tsmTeamGroupService.getGroupIds(groups);
		List<TsmTeamGroupMemberBean> users = tsmTeamGroupMemberService.findByGroupIds(orgId, groupIds);
		
		if(users!=null && users.size()>0){
			List<String> userIds = new ArrayList<String>();
			for (int i =0;i<users.size();i++) {
				if(myId.equals(users.get(i).getUserId())) continue;
				userIds.add(users.get(i).getUserId());
			}
			
			WorkLogInfoBean entity = new WorkLogInfoBean();
			entity.setFromDate(DateUtil.dateBegin(date));
			entity.setToDate(DateUtil.dateEnd(date));
			entity.setIsDel(0);
			entity.setStatus(1);
			entity.setOrgId(orgId);
			entity.setUserIds(userIds.toArray(new String[userIds.size()]));
			List<String> haveLogsUserList = workLogInfoMapper.findHaveLogUsers(entity);
			
			List<Map<String, String>> temp=new ArrayList<Map<String, String>>();
			Map<String, String> map = cachedService.getOrgUserNamesByID(orgId);
			for (String userId : userIds) {
				if(!haveLogsUserList.contains(userId)) {
					HashMap<String, String> uMap = new HashMap<>();
					uMap.put("userId", userId);
					uMap.put("userName", map.get(userId));
					temp.add(uMap);
				}
			}
			return temp;
		}else{
			return new ArrayList<Map<String, String>>();
		}
	}
	
	
	/*根据用户ID查询所有日志*/
	public List<WorkLogInfoBean> findWorkLogByUserId(String orgId,WorkLogInfoBean entity,String type){
		entity.setOrgId(orgId);
		entity.setIsDel(0);
		if("first".equals(type)){
			entity.setSymbol(">");
			entity.setOrderKey("log_date asc");
		}else{
			entity.setSymbol("<");
			if(type==null && entity.getLogDate()!=null)  entity.setLogDate(DateUtil.dateEnd(entity.getLogDate()));
			entity.setOrderKey("log_date desc");
		}
		return workLogInfoMapper.findByPage(entity);
	}
	
	/*根据日志ID查询日志*/
	public WorkLogInfoBean getByCondtion(WorkLogInfoBean entity){
		entity.setIsDel(0);
		return workLogInfoMapper.getByCondtion(entity);
	}
	
	public List<WorkLogInfoBean> findByCondtion(WorkLogInfoBean entity){
		entity.setIsDel(0);
		return workLogInfoMapper.findByCondtion(entity);
	}
	
	/*插入日志*/
	public BaseJsonResult insertWorkLog(ShiroUser user,WorkLogInfoBean workLogInfo){
		workLogInfo.setWliId(GuidUtil.getUUID());
		workLogInfo.setOrgId(user.getOrgId());
		workLogInfo.setGroupId(user.getGroupId());
		workLogInfo.setUserId(user.getId());
		
		workLogInfo.setInputdate(new Date());
		if(StringUtils.isBlank(workLogInfo.getDevType())) workLogInfo.setDevType("PC客户端");
		if(workLogInfo.getStatus()==null) workLogInfo.setStatus(0);
		workLogInfo.setIsDel(0);
		workLogInfo.setCommentNum(0);
		workLogInfo.setShareNum(0);
		workLogInfo.setFavourNum(0);
		workLogInfoMapper.insert(workLogInfo);
		
		
		User user_u=new User();
		user_u.setOrgId(user.getOrgId());
		user_u.setUserAccount(user.getAccount());
		user_u.setUserId(user.getId());
		workAllIndexService.insert(workLogInfo.getWliId(),workLogInfo.getInputdate(),user_u,2);
		return BaseJsonResult.success();
		
	}
	/*编辑更新日志*/
	public void updateWorkLog(WorkLogInfoBean bean){
		bean.setUpdatedate(new Date());
		workLogInfoMapper.updateTrends(bean);
	}
	
	public void updateNum(String orgId,String wliId,String colName,int num){
		//2、评论数+1
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("orgId", orgId);
		params.put("wliId", wliId);
		params.put("colName", colName);
		params.put("num", num);
		workLogInfoMapper.updateNum(params);
	}
	
	/*编辑更新日志*/
	public BaseJsonResult delWorkLog(ShiroUser user,WorkLogInfoBean bean){
		WorkLogInfoBean dbWorkLog = getByCondtion(bean);
		if(dbWorkLog!=null){
			Date date = new Date();
			if(!user.getId().equals(dbWorkLog.getUserId())){
				return BaseJsonResult.error("只能删除自己发布的日志！");
			}
			Date inputDate = dbWorkLog.getInputdate();
			
			if(!DateUtil.dateBegin(inputDate).equals(DateUtil.dateBegin(date))
					){
				return BaseJsonResult.error("只能删除当天发布的日志！");
			}else{
				WorkAllIndexBean index= new WorkAllIndexBean();
				index.setOrgId(bean.getOrgId());
				index.setWorkId(bean.getWliId());
				index.setIsDel(1);
				workAllIndexService.updateTrends(index);
				
				bean.setUpdatedate(new Date());
				bean.setIsDel(1);
				workLogInfoMapper.updateTrends(bean);
				return BaseJsonResult.success();
			}
		}else{
			return BaseJsonResult.error("工作日志不存在！");
		}
	}
	
	/* 查询是当前日期否存在日志*/
	public boolean queryIfWorkLogExist(String orgId,String userId,Date date){
		Map<String, Object> params=new HashMap<String, Object>(3);
		params.put("orgId", orgId);
		params.put("userId", userId);
		params.put("date", date);
		int row = workLogInfoMapper.queryCountByUserAndDate(params);
		return row == 0?false:true;
	}
	
	/* 查询是当月日志分布时间*/
	public List<Date> queryWorkLogByMonth(String orgId,String userId,Date date){
		Map<String, Object> params=new HashMap<String, Object>(3);
		params.put("orgId", orgId);
		params.put("userId", userId);
		params.put("monthBegin", DateUtil.monthBegin(date));
		params.put("monthEnd", DateUtil.monthEnd(date));
		
		List<Date> dates = workLogInfoMapper.queryWorkLogByMonth(params);
		/* 剔除重复的日志*/
		List<Date> temps = new ArrayList<Date>();
		for (Date dt : dates) {
			dt = DateUtil.dateBegin(dt);
			if(!temps.contains(dt)){
				temps.add(dt);
			}
		}
		return temps;
	}
	
	public List<WorkLogDto> processLog(ShiroUser user,List<WorkLogInfoBean> logs){
		workLogBbsUpService.processFavour(logs, user);
		Map<String, List<WorkLogInfoBean>> map = new LinkedHashMap<String, List<WorkLogInfoBean>>();
		if(logs!=null &&logs.size()>0){
			for (WorkLogInfoBean log : logs) {
				List<WorkLogInfoBean> workLogs =null;
				String logDate = DateUtil.getDateDay(log.getLogDate());
				if(map.containsKey(logDate)){
					workLogs = map.get(logDate);
				}else{
					workLogs = new ArrayList<WorkLogInfoBean>();
					map.put(logDate, workLogs);
				}
				workLogs.add(log);
			}
		}
		Set<String> keys = map.keySet();
		List<WorkLogDto> dtos = new ArrayList<WorkLogDto>();
		
		for (String key : keys) {
			WorkLogDto dto = new WorkLogDto();
			dto.setLogDate(key);
			dto.setLogs(map.get(key));
			dtos.add(dto);
		}
		return dtos;
	} 
	
	/**
	 * 处理是否显示删除逻辑*/
	public void processDelable(List<WorkLogInfoBean> logs){
		Date date = new Date();
		
		for (WorkLogInfoBean workLogInfoBean : logs) {
			Date inputDate = workLogInfoBean.getInputdate();
			if(inputDate!=null && DateUtil.dateBegin(inputDate).equals(DateUtil.dateBegin(date))){
				workLogInfoBean.setDelable(true);
				}
			}
		}
	
}
