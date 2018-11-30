package com.qftx.tsm.cust.service;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.team.service.TsmGroupShareinfoService;
import com.qftx.base.util.DateUtil;
import com.qftx.base.util.GuidUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.common.util.OperateEnum;
import com.qftx.common.util.Page;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.cust.dao.CustDefinedDataMapper;
import com.qftx.tsm.cust.dao.ResCustInfoDetailMapper;
import com.qftx.tsm.cust.dto.ResCustInfoDto;
import com.qftx.tsm.cust.dto.StaffDto;
import com.qftx.log.tablestore.bean.LogBean;
import com.qftx.tsm.log.service.LogBatchInfoService;
import com.qftx.tsm.log.service.LogCustInfoService;
import com.qftx.tsm.log.service.LogUserOperateService;
import com.qftx.tsm.sys.enums.SysEnum;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

@Service
public class HighSeasCustPoolService {
	private Log logger = LogFactory.getLog(HighSeasCustPoolService.class);
	
	private static final String CLEAR_ALL_CUST_SQL = "UPDATE TSM_RES_CUST_INFO SET IS_DEL = 1,UPDATE_ACC = ?, UPDATE_DATE = now() WHERE ORG_ID = ? AND IS_DEL = 0 AND RES_CUST_ID = ?";
	private static final String RECYLE_SOURCE_SQL = "UPDATE TSM_RES_CUST_INFO SET OWNER_ACTION_DATE=null,UPDATE_ACC = ?, UPDATE_DATE = now(),OWNER_ACC=NULL,OWNER_START_DATE=NULL, TYPE = 1, STATUS = 1, OPREATE_TYPE = 2, RES_GROUP_ID = ?,IMPORT_DEPT_ID=? WHERE ORG_ID = ? AND IS_DEL = 0 AND RES_CUST_ID = ?";
	private static final String RECYLE_SOURCE_SQL_CLEAN = "UPDATE TSM_RES_CUST_INFO SET OWNER_ACTION_DATE=null,UPDATE_ACC = ?, UPDATE_DATE = now(),OWNER_ACC=NULL,OWNER_START_DATE=NULL, TYPE = 1, STATUS = 1, OPREATE_TYPE = 2, RES_GROUP_ID = ?,LAST_CUST_FOLLOW_ID=NULL,LAST_OPTION_ID=NULL,LAST_CUST_TYPE_ID=NULL,LAST_LOG_ID=NULL,IMPORT_DEPT_ID=?  WHERE ORG_ID = ? AND IS_DEL = 0 AND RES_CUST_ID = ?";
	
	private static final String RECYLE_SOURCE_SQL_OLD_GROUP = "UPDATE TSM_RES_CUST_INFO SET OWNER_ACTION_DATE=null,UPDATE_ACC = ?, UPDATE_DATE = now(),OWNER_ACC=NULL,OWNER_START_DATE=NULL, TYPE = 1, STATUS = 1, OPREATE_TYPE = 2,IMPORT_DEPT_ID=? WHERE ORG_ID = ? AND IS_DEL = 0 AND RES_CUST_ID = ?";
	private static final String RECYLE_SOURCE_SQL_CLEAN_OLD_GROUP = "UPDATE TSM_RES_CUST_INFO SET OWNER_ACTION_DATE=null,UPDATE_ACC = ?, UPDATE_DATE = now(),OWNER_ACC=NULL,OWNER_START_DATE=NULL, TYPE = 1, STATUS = 1, OPREATE_TYPE = 2,LAST_CUST_FOLLOW_ID=NULL,LAST_OPTION_ID=NULL,LAST_CUST_TYPE_ID=NULL,LAST_LOG_ID=NULL,IMPORT_DEPT_ID=?  WHERE ORG_ID = ? AND IS_DEL = 0 AND RES_CUST_ID = ?";
	
	private static final String CLEAR_FOLLOW_SQL = "DELETE FROM TSM_CUST_FOLLOW WHERE ORG_ID = ? AND CUST_ID = ?";
	private static final String CLEAR_LOG_SQL = "DELETE FROM TSM_RES_CUSTINFO_LOG WHERE ORG_ID = ? AND SES_ID = ?";
	private static final String CLEAR_GUIDE_SQL = "DELETE FROM TSM_CUST_GUIDE WHERE ORG_ID = ? AND CUST_ID = ?";
	private static final String CLEAR_OPTOR_SQL = "DELETE FROM TSM_CUST_OPTOR WHERE ORG_ID = ? AND CUST_ID = ?";
	private static final String CLEAR_EVENT_SQL = "UPDATE TSM_RES_CUST_EVENT SET IS_DEL=1 WHERE ORG_ID = ? AND CUST_ID = ? AND TYPE = '1'";
//	private static final String INSERT_LOG_SQL = "INSERT INTO LOG_CUST_INFO (ID,ORG_ID,USER_ID,CUST_ID,NAME,TYPE,DATA,INPUTTIME,IS_DEL) VALUES (?,?,?,?,?,?,?,NOW(),0)";
	
//	private static final ExecutorService threadPool = Executors.newFixedThreadPool(4);
	
	@Resource 
	JdbcTemplate jdbcTemplate;
	@Autowired
	private CachedService cachedService;
	@Autowired
	private ResCustInfoService resCustInfoService;
	@Autowired
	private StaffResourceMgService staffResourceMgService;
	@Autowired
	private LogBatchInfoService logBatchInfoService;
	@Autowired
	private LogCustInfoService logCustInfoService;
	@Resource
    private ThreadPoolTaskExecutor taskExecutor;
	@Resource
	private TsmGroupShareinfoService tsmGroupShareinfoService;
	@Autowired
	private LogUserOperateService logUserOperateService;
	@Autowired
	private CustDefinedDataMapper custDefinedDataMapper;
	@Autowired
	private ResCustInfoDetailMapper resCustInfoDetailMapper;
	/* 进度条
	 * @Resource
	private ProgressBarService progressBarService;*/
	
//	private String write_log_to_db = ConfigInfoUtils.getStringValue("write_log_to_db");
	/** 
	 * 公海客户池 清空
	 * @param custInfoDto
	 * @param request
	 * @return
	 * @throws Exception 
	 * @create  2016年4月19日 下午2:55:25 lixing
	 * @history  
	 */
	public String clearHighSeas(ResCustInfoDto custInfoDto,HttpServletRequest request,ShiroUser user,String sessionCode,String taskId) throws Exception{
		ResCustInfoDto tmpDto = null;
		Page tmppage = null;
		int i = 0;
		if(!sessionCode.toUpperCase().equals(custInfoDto.getCheckCode().toUpperCase())){
			return "10";
		}
		logger.debug("****【清空公海资源】  开始");
		custInfoDto.setOrgId(user.getOrgId());
		custInfoDto.setReason(StringUtils.toLikeStr(custInfoDto.getReason()));
		custInfoDto.setOsType(StringUtils.isBlank(custInfoDto.getOsType()) ? "1" : custInfoDto.getOsType());
		if(custInfoDto.getOsType().equals("1") && StringUtils.isBlank(custInfoDto.getOwnerAccsStr())){
//			custInfoDto.setAdminAcc(user.getAccount());
    	}else if(custInfoDto.getOsType().equals("2") && StringUtils.isBlank(custInfoDto.getOwnerAccsStr())){
    		custInfoDto.setOwnerAcc(user.getAccount());
    	}else if(StringUtils.isNotBlank(custInfoDto.getOwnerAccsStr())){
    		custInfoDto.setOwnerAccs(Arrays.asList(custInfoDto.getOwnerAccsStr().split(",")));
		}
		
		if(StringUtils.isBlank(custInfoDto.getIsShareRes())){
			custInfoDto.setIsShareRes("0");
		}
		
		//权限判断
		List<String> authGroupIds = cachedService.getPoolAuthGroupIds(user.getOrgId(),user.getGroupId());
		if(authGroupIds != null && authGroupIds.size() > 0){
			custInfoDto.setAuthGroupIds(authGroupIds);
		}
		
		//处理时间查询
		if(custInfoDto.getnDateType() != null && custInfoDto.getnDateType() != 0 && custInfoDto.getnDateType() != 5){
			custInfoDto.setStartDate(getStartDateStr(custInfoDto.getnDateType()));
			custInfoDto.setEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
		}
		//处理时间查询
		if(custInfoDto.getlDateType() != null && custInfoDto.getlDateType() != 0 && custInfoDto.getlDateType() != 5){
			custInfoDto.setStartActionDate(getStartDateStr(custInfoDto.getlDateType()));
			custInfoDto.setEndActionDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
		}
		//处理放弃人
		custInfoDto.setUsType(StringUtils.isBlank(custInfoDto.getUsType()) ? "1" : custInfoDto.getUsType());
    	if(custInfoDto.getUsType().equals("1") && StringUtils.isBlank(custInfoDto.getUpdateAccsStr())){
    	}else if(custInfoDto.getUsType().equals("2") && StringUtils.isBlank(custInfoDto.getUpdateAccsStr())){
    		custInfoDto.setUpdateAcc(user.getAccount());
    	}else if(StringUtils.isNotBlank(custInfoDto.getUpdateAccsStr())){
    		custInfoDto.setUpdateAccs(Arrays.asList(custInfoDto.getUpdateAccsStr().split(",")));
		}
    	
		//处理标签
        if(StringUtils.isNotBlank(custInfoDto.getAllLabels())){
        	custInfoDto.setLabels(Arrays.asList(custInfoDto.getAllLabels().split(",")));
        }
        
        if(StringUtils.isNotBlank(custInfoDto.getResGroupIdsStr())){
        	custInfoDto.setGroupIds(Arrays.asList(custInfoDto.getResGroupIdsStr().split(",")));
        }
        
		//模糊查询处理
        if(StringUtils.isNotBlank(custInfoDto.getQueryText())){
        	String queryText = custInfoDto.getQueryText().trim();
        	custInfoDto.setQueryText(queryText);
        	if(queryText.matches("[0-9]+")){
        		custInfoDto.setQueryPhone(true);
        	}else{
        		custInfoDto.setQueryPhone(false);
        	}
        }
        
        List<String> multiSearchList = cachedService.getMultiSelectDefinedSearchFiled(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_8.getState());
        multiSearchList.add(AppConstant.SEARCH_LABEL);
        List<String> cids = new ArrayList<String>();
    	if(custInfoDto.getState() != null && 
    			custInfoDto.getState() == 1 && 
    			StringUtils.isNotBlank(custInfoDto.getQueryText()) && 
    			(custInfoDto.getQueryType().equals("mobilephone") || custInfoDto.getQueryType().equals("mainLinkman"))){
    		if(custInfoDto.getQueryType().equals("mobilephone")){
    			cids = resCustInfoDetailMapper.findLinkmanIdsByPhone(custInfoDto.getOrgId(), custInfoDto.getQueryText());
    		}else{
    			cids = resCustInfoDetailMapper.findLinkmanIds(custInfoDto.getOrgId(), custInfoDto.getQueryText());
    		}
    		if(cids.size() == 0) return "9";
    		custInfoDto.setCustIds(cids);
    		custInfoDto.setQueryText(null);
    	}
        
        if(multiSearchList != null && multiSearchList.size() > 0){
			Map<String, Object> paramMap = resCustInfoService.getMultiDefinedSearchParam(custInfoDto, multiSearchList);
			if(paramMap.size() > 0){
				if(cids.size() > 0) paramMap.put("custIds", cids);
				List<String> custIds = custDefinedDataMapper.findCustIdsByDefinedData(paramMap);
				if(custIds.size() > 0){
					custInfoDto.setCustIds(custIds);
				}else{
					return "9";
				}
			}
		}
		List<ResCustInfoDto> arrs = resCustInfoService.getClearPoolListPage(custInfoDto);
		if(arrs == null || arrs.size() == 0){
			logger.debug("****【清空公海资源】  没有找到要清除的公海资源");
			return "9";
		}
		logger.debug("****【清空公海资源】  资源SIZE:"+custInfoDto.getPage().getTotalResult());
		List<ResCustInfoDto> arrs2 = null;
		StaffDto staffDto = new StaffDto();
		staffDto.setOptAcc(user.getAccount());
		staffDto.setType("03");
		staffDto.setOrgId(user.getOrgId());
		staffDto.setOptCount(0L);
		staffDto.setStaffId(SysBaseModelUtil.getModelId());
		staffDto.setOptCount(new Long(custInfoDto.getPage().getTotalResult()));
		
		List<List<ResCustInfoDto>> res_id_list = null;
		int page = custInfoDto.getPage().getTotalResult() / 20000;
		if(custInfoDto.getPage().getTotalResult() % 20000 != 0){
			page++;
		}
		tmpDto = (ResCustInfoDto)custInfoDto.clone();
		tmppage = new Page();
		tmppage.setShowCount(20000);
		tmppage.setPageStr(null);
		tmppage.setPageSubStr(null);
		tmppage.setTotalResult(custInfoDto.getPage().getTotalResult());
		tmppage.setTotalPage(page);
		tmppage.setCurrentPage(page);
		tmpDto.setPage(tmppage);
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("total", custInfoDto.getPage().getTotalResult());
		map.put("finished", 0);
		map.put("status", true);
		cachedService.setProgress(custInfoDto.getOrgId(), taskId, map);
		
		while(tmppage.getCurrentPage() >= 1) {
			arrs2 = resCustInfoService.getClearPoolListPage(tmpDto);
			if(arrs2 != null && arrs2.size() > 0){
				res_id_list = splitList(arrs2, 4000);
				for(final List<ResCustInfoDto> subList : res_id_list){
					i++;
					batchClearCust(subList, user,custInfoDto.getIsShareRes());
					
					map.put("finished", (Integer)map.get("finished") + subList.size());
					cachedService.setProgress(custInfoDto.getOrgId(), taskId, map);
				}
				if (tmppage.getCurrentPage() == 1) {
					staffResourceMgService.createOption(staffDto);
				}
			}
			if (tmppage.getCurrentPage() == 1) {break;}
			tmppage.setCurrentPage(tmppage.getCurrentPage() - 1);
		}
		
			logger.debug("****【清空公海资源】  结束"+i);
			return "1";
	}
	
	public void batchClearCust(final List<ResCustInfoDto> sublist, final ShiroUser user,String isShareRes) {
		long timerBegin = System.currentTimeMillis();
		jdbcTemplate.batchUpdate(CLEAR_ALL_CUST_SQL, new BatchPreparedStatementSetter() {
			//为prepared statement设置参数。这个方法将在整个过程中被调用的次数
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, user.getAccount());
				ps.setString(2, user.getOrgId());
				ps.setString(3, sublist.get(i).getResCustId());
			}
			//返回更新的结果集条数
			public int getBatchSize() {
				return sublist.size();
			}
		});
		//插入日志
//		jdbcTemplate.batchUpdate(INSERT_LOG_SQL, new BatchPreparedStatementSetter() {
//			
//			public void setValues(PreparedStatement ps, int i) throws SQLException {
//				ps.setString(1, SysBaseModelUtil.getModelId());
//				ps.setString(2, user.getOrgId());
//				ps.setString(3, user.getAccount());
//				ps.setString(4, sublist.get(i).getResCustId());
//				ps.setString(5, OperateEnum.LOG_DELETE.getDesc());
//				ps.setString(6, OperateEnum.LOG_DELETE.getCode());
//				ps.setString(7, JSONObject.fromObject(sublist.get(i)).toString());
//			}
//			
//			public int getBatchSize() {
//				return sublist.size();
//			}
//		});
		List<String> ids = new ArrayList<String>();
		for(ResCustInfoDto custInfo : sublist){
			ids.add(custInfo.getResCustId());
		}
		if(isShareRes.equals("0")){
			logBatchInfoService.saveLog(user.getOrgId(), user.getAccount(),user.getName(), OperateEnum.LOG_DELETE, AppConstant.Module_id1004,AppConstant.Module_Name1004, AppConstant.Operate_id21, AppConstant.Operate_Name21, ids.size()+"条", "", ids, "", SysBaseModelUtil.getModelId(), null);
		}else{
			logBatchInfoService.saveLog(user.getOrgId(), user.getAccount(),user.getName(), OperateEnum.LOG_DELETE, AppConstant.Module_id1005,AppConstant.Module_Name1005, AppConstant.Operate_id21, AppConstant.Operate_Name21, ids.size()+"条", "", ids, "", SysBaseModelUtil.getModelId(), null);
		}
		long min = (System.currentTimeMillis() - timerBegin) ;
		logger.info("****【清空公海资源】  耗时(毫秒)："+min);
	}
	
	/** 
	 * 流失客户回收到待分配资源数
	 * @param custInfoDto
	 * @param request
	 * @return
	 * @throws Exception 
	 * @create  2016年4月19日 下午2:58:25 lixing
	 * @history  
	 */
	public Integer losingCustRecyleNum(ResCustInfoDto custInfoDto,HttpServletRequest request,ShiroUser user) throws Exception{
		logger.debug("****【流失客户回收到待分配资源数】  开始");
		custInfoDto.setPage(new Page());
		custInfoDto.setState(user.getIsState());
		custInfoDto.setOrgId(user.getOrgId());
		if(user.getIssys() != null && user.getIssys() == 1){
			custInfoDto.setOsType(StringUtils.isBlank(custInfoDto.getOsType()) ? "1" : custInfoDto.getOsType());
			custInfoDto.setAdminAcc(user.getAccount());
			if(custInfoDto.getOsType().equals("1") && StringUtils.isBlank(custInfoDto.getOwnerAccsStr())){
				List<String> accs = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(), user.getAccount());
				if(!accs.contains(user.getAccount())) accs.add(user.getAccount());
				custInfoDto.setOwnerAccs(accs);
        	}else {
        		// 处理拥有者
                if (StringUtils.isNotBlank(custInfoDto.getOwnerAccsStr())) {
                	custInfoDto.setOwnerAccs(Arrays.asList(custInfoDto.getOwnerAccsStr().split(",")));
                } else {
                    custInfoDto.setOwnerAccs(Arrays.asList(user.getAccount()));
                }
        	}
		}else{
			custInfoDto.setOwnerAcc(user.getAccount());
		}
		if((custInfoDto.getNoteType() == null || custInfoDto.getNoteType().equals("")) && custInfoDto.getDays() == null){
			custInfoDto.setNoteType("1");
		}
		if(custInfoDto.getDays() != null){
			custInfoDto.setNoteType("");
		}
		//处理 合同截止时间
		if(custInfoDto.getoDateType() != null && custInfoDto.getoDateType() != 0 && custInfoDto.getoDateType() != 5){
			if(custInfoDto.getoDateType() == 4){
				custInfoDto.setPstartDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
				custInfoDto.setPendDate(getEndDateStr(custInfoDto.getoDateType()));
			}else{
				custInfoDto.setPstartDate(getStartDateStr(custInfoDto.getoDateType()));
				custInfoDto.setPendDate(getEndDateStr(custInfoDto.getoDateType()));
			}
		}
		//处理 最近联系时间
		if(custInfoDto.getlDateType() != null && custInfoDto.getlDateType() != 0 && custInfoDto.getlDateType() != 5){
			custInfoDto.setStartActionDate(getStartDateStr(custInfoDto.getlDateType()));
			custInfoDto.setEndActionDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
		}
		//模糊查询处理
        if(StringUtils.isNotBlank(custInfoDto.getQueryText())){
        	String queryText = custInfoDto.getQueryText().trim();
        	custInfoDto.setQueryText(queryText);
        	if(queryText.matches("[0-9]+")){
        		custInfoDto.setQueryPhone(true);
        	}else{
        		custInfoDto.setQueryPhone(false);
        	}
        }
        //处理标签
        if(StringUtils.isNotBlank(custInfoDto.getAllLabels())){
        	custInfoDto.setLabels(Arrays.asList(custInfoDto.getAllLabels().split(",")));
        }
        List<String> multiSearchList = cachedService.getMultiSelectDefinedSearchFiled(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_3.getState());
        multiSearchList.add(AppConstant.SEARCH_LABEL);
        List<ResCustInfoDto> arrs = resCustInfoService.getManageLosingCustListPage(custInfoDto,multiSearchList);
		return custInfoDto.getPage().getTotalResult();
	}
	
	/** 
	 * 流失客户回收到待分配资源
	 * @param custInfoDto
	 * @param request
	 * @return
	 * @throws Exception 
	 * @create  2016年4月19日 下午2:58:25 lixing
	 * @history  
	 */
	public String losingCustRecyle(ResCustInfoDto custInfoDto,HttpServletRequest request,ShiroUser user) throws Exception{
		ResCustInfoDto tmpDto = null;
		Page tmppage = null;
		int i = 0;
		logger.debug("****【流失客户回收到待分配资源】  开始");
		custInfoDto.setPage(new Page());
		custInfoDto.setState(user.getIsState());
		custInfoDto.setOrgId(user.getOrgId());
		if(user.getIssys() != null && user.getIssys() == 1){
			custInfoDto.setOsType(StringUtils.isBlank(custInfoDto.getOsType()) ? "1" : custInfoDto.getOsType());
			custInfoDto.setAdminAcc(user.getAccount());
			if(custInfoDto.getOsType().equals("1") && StringUtils.isBlank(custInfoDto.getOwnerAccsStr())){
				List<String> accs = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(), user.getAccount());
				if(!accs.contains(user.getAccount())) accs.add(user.getAccount());
				custInfoDto.setOwnerAccs(accs);
        	}else {
        		// 处理拥有者
                if (StringUtils.isNotBlank(custInfoDto.getOwnerAccsStr())) {
                	custInfoDto.setOwnerAccs(Arrays.asList(custInfoDto.getOwnerAccsStr().split(",")));
                } else {
                    custInfoDto.setOwnerAccs(Arrays.asList(user.getAccount()));
                }
        	}
		}else{
			custInfoDto.setOwnerAcc(user.getAccount());
		}
		if((custInfoDto.getNoteType() == null || custInfoDto.getNoteType().equals("")) && custInfoDto.getDays() == null){
			custInfoDto.setNoteType("1");
		}
		if(custInfoDto.getDays() != null){
			custInfoDto.setNoteType("");
		}
		//处理 合同截止时间
		if(custInfoDto.getoDateType() != null && custInfoDto.getoDateType() != 0 && custInfoDto.getoDateType() != 5){
			if(custInfoDto.getoDateType() == 4){
				custInfoDto.setPstartDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
				custInfoDto.setPendDate(getEndDateStr(custInfoDto.getoDateType()));
			}else{
				custInfoDto.setPstartDate(getStartDateStr(custInfoDto.getoDateType()));
				custInfoDto.setPendDate(getEndDateStr(custInfoDto.getoDateType()));
			}
		}
		//处理 最近联系时间
		if(custInfoDto.getlDateType() != null && custInfoDto.getlDateType() != 0 && custInfoDto.getlDateType() != 5){
			custInfoDto.setStartActionDate(getStartDateStr(custInfoDto.getlDateType()));
			custInfoDto.setEndActionDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
		}
		//模糊查询处理
        if(StringUtils.isNotBlank(custInfoDto.getQueryText())){
        	String queryText = custInfoDto.getQueryText().trim();
        	custInfoDto.setQueryText(queryText);
        	if(queryText.matches("[0-9]+")){
        		custInfoDto.setQueryPhone(true);
        	}else{
        		custInfoDto.setQueryPhone(false);
        	}
        }
        //处理标签
        if(StringUtils.isNotBlank(custInfoDto.getAllLabels())){
        	custInfoDto.setLabels(Arrays.asList(custInfoDto.getAllLabels().split(",")));
        }
        List<String> multiSearchList = cachedService.getMultiSelectDefinedSearchFiled(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_3.getState());
        multiSearchList.add(AppConstant.SEARCH_LABEL);
        List<ResCustInfoDto> arrs = resCustInfoService.getManageLosingCustListPage(custInfoDto,multiSearchList);
		if(arrs == null || arrs.size() == 0){
			logger.debug("****【流失客户回收到待分配资源】  没有找到要清除的公海资源");
			return "9";
		}
		logger.debug("****【流失客户回收到待分配资源】  资源SIZE:"+custInfoDto.getPage().getTotalResult());
		List<ResCustInfoDto> arrs2 = null;
		List<List<ResCustInfoDto>> res_id_list = null;
		String progressId = GuidUtil.getId();
		//progressBarService.insertProgress(user.getOrgId(), user.getAccount(), ProgressType.LOSING_CUST_RECYLE.getValue(), progressId, custInfoDto.getPage().getTotalResult());
		int page = custInfoDto.getPage().getTotalResult() / 20000;
		if(custInfoDto.getPage().getTotalResult() % 20000 != 0){
			page++;
		}
		tmpDto = (ResCustInfoDto)custInfoDto.clone();
		tmppage = new Page();
		tmppage.setShowCount(20000);
		tmppage.setPageStr(null);
		tmppage.setPageSubStr(null);
		tmppage.setTotalResult(custInfoDto.getPage().getTotalResult());
		tmppage.setTotalPage(page);
		tmppage.setCurrentPage(page);
		tmpDto.setPage(tmppage);
//		String batchId = SysBaseModelUtil.getModelId();
		while(tmppage.getCurrentPage() >= 1) {
			arrs2 = resCustInfoService.getManageLosingCustListPage(tmpDto,multiSearchList);
			if(arrs2 != null && arrs2.size() > 0){
				res_id_list = splitList(arrs2, 4000);
				for(final List<ResCustInfoDto> subList : res_id_list){
					i++;
					String batchId = SysBaseModelUtil.getModelId();
					batchRecyleSource(subList, user, tmpDto,batchId,2);
					//progressBarService.updateProgress(user.getOrgId(), user.getAccount(), progressId, ProgressType.LOSING_CUST_RECYLE.getValue(), subList.size());
				}
			}
			if (tmppage.getCurrentPage() == 1) {break;}
			tmppage.setCurrentPage(tmppage.getCurrentPage() - 1);
		}
		logger.debug("****【流失客户回收到待分配资源】  结束"+i);
			return "1";
	}
	
	/** 
	 * 公海客户回收到待分配获取资源数
	 * @param custInfoDto
	 * @param request
	 * @return
	 * @throws Exception 
	 * @create  2016年4月19日 下午3:02:34 lixing
	 * @history  
	 */
	public Integer highSeasRecyleNum(ResCustInfoDto custInfoDto,HttpServletRequest request,ShiroUser user) throws Exception{
		logger.debug("****【回收到待分配资源数量】  开始");
		custInfoDto.setPage(new Page());
		custInfoDto.setState(user.getIsState());
		custInfoDto.setOrgId(user.getOrgId());
		custInfoDto.setReason(StringUtils.toLikeStr(custInfoDto.getReason()));
		custInfoDto.setOsType(StringUtils.isBlank(custInfoDto.getOsType()) ? "1" : custInfoDto.getOsType());
		if(custInfoDto.getOsType().equals("1")  && StringUtils.isBlank(custInfoDto.getOwnerAccsStr())){
//			custInfoDto.setAdminAcc(user.getAccount());
    	}else if(custInfoDto.getOsType().equals("2")  && StringUtils.isBlank(custInfoDto.getOwnerAccsStr())){
    		custInfoDto.setOwnerAcc(user.getAccount());
    	}else if(StringUtils.isNotBlank(custInfoDto.getOwnerAccsStr())){
    		custInfoDto.setOwnerAccs(Arrays.asList(custInfoDto.getOwnerAccsStr().split(",")));
		}
		//处理时间查询
		if(custInfoDto.getnDateType() != null && custInfoDto.getnDateType() != 0 && custInfoDto.getnDateType() != 5){
			custInfoDto.setStartDate(getStartDateStr(custInfoDto.getnDateType()));
			custInfoDto.setEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
		}
		
		//权限判断
		List<String> authGroupIds = cachedService.getPoolAuthGroupIds(user.getOrgId(),user.getGroupId());
		if(authGroupIds != null && authGroupIds.size() > 0){
			custInfoDto.setAuthGroupIds(authGroupIds);
		}
		//处理时间查询
		if(custInfoDto.getlDateType() != null && custInfoDto.getlDateType() != 0 && custInfoDto.getlDateType() != 5){
			custInfoDto.setStartActionDate(getStartDateStr(custInfoDto.getlDateType()));
			custInfoDto.setEndActionDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
		}
		//处理放弃人
		custInfoDto.setUsType(StringUtils.isBlank(custInfoDto.getUsType()) ? "1" : custInfoDto.getUsType());
    	if(custInfoDto.getUsType().equals("1")  && StringUtils.isBlank(custInfoDto.getUpdateAccsStr())){
    	}else if(custInfoDto.getUsType().equals("2")  && StringUtils.isBlank(custInfoDto.getUpdateAccsStr())){
    		custInfoDto.setUpdateAcc(user.getAccount());
    	}else if(StringUtils.isNotBlank(custInfoDto.getUpdateAccsStr())){
    		custInfoDto.setUpdateAccs(Arrays.asList(custInfoDto.getUpdateAccsStr().split(",")));
		}
		//模糊查询处理
        if(StringUtils.isNotBlank(custInfoDto.getQueryText())){
        	String queryText = custInfoDto.getQueryText().trim();
        	custInfoDto.setQueryText(queryText);
        	if(queryText.matches("[0-9]+")){
        		custInfoDto.setQueryPhone(true);
        	}else{
        		custInfoDto.setQueryPhone(false);
        	}
        }
        //处理标签
        if(StringUtils.isNotBlank(custInfoDto.getAllLabels())){
        	custInfoDto.setLabels(Arrays.asList(custInfoDto.getAllLabels().split(",")));
        }
        
        if(StringUtils.isNotBlank(custInfoDto.getResGroupIdsStr())){
        	custInfoDto.setGroupIds(Arrays.asList(custInfoDto.getResGroupIdsStr().split(",")));
        }
        
        List<String> cids = new ArrayList<String>();
    	if(custInfoDto.getState() != null && 
    			custInfoDto.getState() == 1 && 
    			StringUtils.isNotBlank(custInfoDto.getQueryText()) && 
    			(custInfoDto.getQueryType().equals("mobilephone") || custInfoDto.getQueryType().equals("mainLinkman"))){
    		if(custInfoDto.getQueryType().equals("mobilephone")){
    			cids = resCustInfoDetailMapper.findLinkmanIdsByPhone(custInfoDto.getOrgId(), custInfoDto.getQueryText());
    		}else{
    			cids = resCustInfoDetailMapper.findLinkmanIds(custInfoDto.getOrgId(), custInfoDto.getQueryText());
    		}
    		if(cids.size() == 0) return 0;
    		custInfoDto.setCustIds(cids);
    		custInfoDto.setQueryText(null);
    	}
        
        List<String> multiSearchList = cachedService.getMultiSelectDefinedSearchFiled(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_8.getState());
        multiSearchList.add(AppConstant.SEARCH_LABEL);
        if(multiSearchList != null && multiSearchList.size() > 0){
			Map<String, Object> paramMap = resCustInfoService.getMultiDefinedSearchParam(custInfoDto, multiSearchList);
			if(paramMap.size() > 0){
				if(cids.size() > 0) paramMap.put("custIds", cids);
				List<String> custIds = custDefinedDataMapper.findCustIdsByDefinedData(paramMap);
				if(custIds.size() > 0){
					custInfoDto.setCustIds(custIds);
				}else{
					return 0;
				}
			}
		}
		List<ResCustInfoDto> arrs = resCustInfoService.getClearPoolListPage(custInfoDto);
		return custInfoDto.getPage().getTotalResult();
	}
	
	/** 
	 * 公海客户回收到待分配
	 * @param custInfoDto
	 * @param request
	 * @return
	 * @throws Exception 
	 * @create  2016年4月19日 下午3:02:34 lixing
	 * @history  
	 */
	public String highSeasRecyle(ResCustInfoDto custInfoDto,HttpServletRequest request,ShiroUser user,String taskId) throws Exception{
		ResCustInfoDto tmpDto = null;
		Page tmppage = null;
		int i = 0;
		logger.debug("****【回收到待分配资源】  开始");
		custInfoDto.setPage(new Page());
		custInfoDto.setState(user.getIsState());
		custInfoDto.setOrgId(user.getOrgId());
		custInfoDto.setState(user.getIsState());
		custInfoDto.setOsType(StringUtils.isBlank(custInfoDto.getOsType()) ? "1" : custInfoDto.getOsType());
		custInfoDto.setReason(StringUtils.toLikeStr(custInfoDto.getReason()));
		if(custInfoDto.getOsType().equals("1") && StringUtils.isBlank(custInfoDto.getOwnerAccsStr())){
//			custInfoDto.setAdminAcc(user.getAccount());
    	}else if(custInfoDto.getOsType().equals("2") && StringUtils.isBlank(custInfoDto.getOwnerAccsStr())){
    		custInfoDto.setOwnerAcc(user.getAccount());
    	}else if(StringUtils.isNotBlank(custInfoDto.getOwnerAccsStr())){
    		custInfoDto.setOwnerAccs(Arrays.asList(custInfoDto.getOwnerAccsStr().split(",")));
		}
		//处理时间查询
		if(custInfoDto.getnDateType() != null && custInfoDto.getnDateType() != 0 && custInfoDto.getnDateType() != 5){
			custInfoDto.setStartDate(getStartDateStr(custInfoDto.getnDateType()));
			custInfoDto.setEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
		}
		
		//权限判断
		List<String> authGroupIds = cachedService.getPoolAuthGroupIds(user.getOrgId(),user.getGroupId());
		if(authGroupIds != null && authGroupIds.size() > 0){
			custInfoDto.setAuthGroupIds(authGroupIds);
		}
		//处理时间查询
		if(custInfoDto.getlDateType() != null && custInfoDto.getlDateType() != 0 && custInfoDto.getlDateType() != 5){
			custInfoDto.setStartActionDate(getStartDateStr(custInfoDto.getlDateType()));
			custInfoDto.setEndActionDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
		}
		//处理放弃人
		custInfoDto.setUsType(StringUtils.isBlank(custInfoDto.getUsType()) ? "1" : custInfoDto.getUsType());
    	if(custInfoDto.getUsType().equals("1") && StringUtils.isBlank(custInfoDto.getUpdateAccsStr())){
    	}else if(custInfoDto.getUsType().equals("2") && StringUtils.isBlank(custInfoDto.getUpdateAccsStr())){
    		custInfoDto.setUpdateAcc(user.getAccount());
    	}else if(StringUtils.isNotBlank(custInfoDto.getUpdateAccsStr())){
    		custInfoDto.setUpdateAccs(Arrays.asList(custInfoDto.getUpdateAccsStr().split(",")));
		}
		//模糊查询处理
        if(StringUtils.isNotBlank(custInfoDto.getQueryText())){
        	String queryText = custInfoDto.getQueryText().trim();
        	custInfoDto.setQueryText(queryText);
        	if(queryText.matches("[0-9]+")){
        		custInfoDto.setQueryPhone(true);
        	}else{
        		custInfoDto.setQueryPhone(false);
        	}
        }
        //处理标签
        if(StringUtils.isNotBlank(custInfoDto.getAllLabels())){
        	custInfoDto.setLabels(Arrays.asList(custInfoDto.getAllLabels().split(",")));
        }
        
        if(StringUtils.isNotBlank(custInfoDto.getResGroupIdsStr())){
        	custInfoDto.setGroupIds(Arrays.asList(custInfoDto.getResGroupIdsStr().split(",")));
        }
        
        List<String> multiSearchList = cachedService.getMultiSelectDefinedSearchFiled(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_8.getState());
        multiSearchList.add(AppConstant.SEARCH_LABEL);
        List<String> cids = new ArrayList<String>();
    	if(custInfoDto.getState() != null && 
    			custInfoDto.getState() == 1 && 
    			StringUtils.isNotBlank(custInfoDto.getQueryText()) && 
    			(custInfoDto.getQueryType().equals("mobilephone") || custInfoDto.getQueryType().equals("mainLinkman"))){
    		if(custInfoDto.getQueryType().equals("mobilephone")){
    			cids = resCustInfoDetailMapper.findLinkmanIdsByPhone(custInfoDto.getOrgId(), custInfoDto.getQueryText());
    		}else{
    			cids = resCustInfoDetailMapper.findLinkmanIds(custInfoDto.getOrgId(), custInfoDto.getQueryText());
    		}
    		if(cids.size() == 0) return "9";
    		custInfoDto.setCustIds(cids);
    		custInfoDto.setQueryText(null);
    	}
        
        if(multiSearchList != null && multiSearchList.size() > 0){
			Map<String, Object> paramMap = resCustInfoService.getMultiDefinedSearchParam(custInfoDto, multiSearchList);
			if(paramMap.size() > 0){
				if(cids.size() > 0) paramMap.put("custIds", cids);
				List<String> custIds = custDefinedDataMapper.findCustIdsByDefinedData(paramMap);
				if(custIds.size() > 0){
					custInfoDto.setCustIds(custIds);
				}else{
					return "9";
				}
			}
		}
		List<ResCustInfoDto> arrs = resCustInfoService.getClearPoolListPage(custInfoDto);
		if(arrs == null || arrs.size() == 0){
			logger.debug("****【回收到待分配资源】  没有找到要清除的公海资源");
			return "9";
		}
		logger.debug("****【回收到待分配资源】  资源SIZE:"+custInfoDto.getPage().getTotalResult());
		List<ResCustInfoDto> arrs2 = null;
		List<List<ResCustInfoDto>> res_id_list = null;
//		String progressId = GuidUtil.getId();
		//progressBarService.insertProgress(user.getOrgId(), user.getAccount(), ProgressType.HIGH_SEA_PROGRESS.getValue(), progressId, custInfoDto.getPage().getTotalResult());
		int page = custInfoDto.getPage().getTotalResult() / 20000;
		if(custInfoDto.getPage().getTotalResult() % 20000 != 0){
			page++;
		}
		tmpDto = (ResCustInfoDto)custInfoDto.clone();
		tmppage = new Page();
		tmppage.setShowCount(20000);
		tmppage.setPageStr(null);
		tmppage.setPageSubStr(null);
		tmppage.setTotalResult(custInfoDto.getPage().getTotalResult());
		tmppage.setTotalPage(page);
		tmppage.setCurrentPage(page);
		tmpDto.setPage(tmppage);
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("total", custInfoDto.getPage().getTotalResult());
		map.put("finished", 0);
		map.put("status", true);
		cachedService.setProgress(custInfoDto.getOrgId(), taskId, map);
//		String batchId = SysBaseModelUtil.getModelId();
		while(tmppage.getCurrentPage() >= 1) {
			arrs2 = resCustInfoService.getClearPoolListPage(tmpDto);
			if(arrs2 != null && arrs2.size() > 0){
				res_id_list = splitList(arrs2, 4000);
				for(final List<ResCustInfoDto> subList : res_id_list){
					i++;
					String batchId = SysBaseModelUtil.getModelId();
					batchRecyleSource(subList, user, tmpDto,batchId,1);
					//progressBarService.updateProgress(user.getOrgId(), user.getAccount(), progressId, ProgressType.HIGH_SEA_PROGRESS.getValue(), subList.size());
					map.put("finished", (Integer)map.get("finished") + subList.size());
					cachedService.setProgress(custInfoDto.getOrgId(), taskId, map);
				}
			}
			if (tmppage.getCurrentPage() == 1) {break;}
			tmppage.setCurrentPage(tmppage.getCurrentPage() - 1);
		}
		logger.debug("****【回收到待分配资源】  结束"+i);
		return "1";
	}
	
	public void batchRecyleSource(final List<ResCustInfoDto> sublist, final ShiroUser user, final ResCustInfoDto recyleRDto,String batchId,int fromModule) {
		long timerBegin = System.currentTimeMillis();
		if(recyleRDto.getOldResGroup()==1){
			jdbcTemplate.batchUpdate(recyleRDto.getIsClean() == 1 ? RECYLE_SOURCE_SQL_CLEAN_OLD_GROUP : RECYLE_SOURCE_SQL_OLD_GROUP, new BatchPreparedStatementSetter() {
				//为prepared statement设置参数。这个方法将在整个过程中被调用的次数
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setString(1, user.getAccount());
					ps.setString(2, user.getGroupId());
					ps.setString(3, user.getOrgId());
					ps.setString(4, sublist.get(i).getResCustId());
				}
				//返回更新的结果集条数
				public int getBatchSize() {
					return sublist.size();
				}
			});
		}else{
			jdbcTemplate.batchUpdate(recyleRDto.getIsClean() == 1 ? RECYLE_SOURCE_SQL_CLEAN : RECYLE_SOURCE_SQL, new BatchPreparedStatementSetter() {
				//为prepared statement设置参数。这个方法将在整个过程中被调用的次数
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setString(1, user.getAccount());
					ps.setString(2, recyleRDto.getRegroupId());
					ps.setString(3, user.getGroupId());
					ps.setString(4, user.getOrgId());
					ps.setString(5, sublist.get(i).getResCustId());
				}
				//返回更新的结果集条数
				public int getBatchSize() {
					return sublist.size();
				}
			});
		}
		
		//插入日志
//		if(write_log_to_db.equals("1")){
//			jdbcTemplate.batchUpdate(INSERT_LOG_SQL, new BatchPreparedStatementSetter() {
//				
//				public void setValues(PreparedStatement ps, int i) throws SQLException {
//					ps.setString(1, SysBaseModelUtil.getModelId());
//					ps.setString(2, user.getOrgId());
//					ps.setString(3, user.getAccount());
//					ps.setString(4, sublist.get(i).getResCustId());
//					ps.setString(5, OperateEnum.LOG_RECYLE.getDesc());
//					ps.setString(6, OperateEnum.LOG_RECYLE.getCode());
//					ps.setString(7, JSONObject.fromObject(sublist.get(i)).toString());
//				}
//				
//				public int getBatchSize() {
//					return sublist.size();
//				}
//			});
//		}
		
		
		
		if (recyleRDto.getIsClean() == 1) { // 同时清空资源的历史跟进记录等信息
			taskExecutor.submit(new Runnable() {
				public void run() {
					clearCustInfo(CLEAR_FOLLOW_SQL, sublist,user);
				}
			});
			taskExecutor.submit(new Runnable() {
				public void run() {
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						logger.error(e.getMessage(),e);
					}
					clearCustInfo(CLEAR_LOG_SQL, sublist, user);
				}
			});
			taskExecutor.submit(new Runnable() {
				public void run() {
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						logger.error(e.getMessage(),e);
					}
					clearCustInfo(CLEAR_GUIDE_SQL, sublist,user);
				}
			});
			taskExecutor.submit(new Runnable() {
				public void run() {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						logger.error(e.getMessage(),e);
					}
					clearCustInfo(CLEAR_OPTOR_SQL, sublist,user);
				}
			});
			taskExecutor.submit(new Runnable() {
				public void run() {
					try {
						Thread.sleep(500);
					} catch (Exception e) {
						logger.error(e.getMessage(),e);
					}
					clearCustInfo(CLEAR_EVENT_SQL, sublist, user);
				}
			});
		}
		//List<String> ids = new ArrayList<String>();
		Map<String, Object> logMap = new HashMap<String, Object>();
		for(ResCustInfoDto custInfo : sublist){
			//ids.add(custInfo.getResCustId());
			//logCustInfoService.addLog(user.getOrgId(), user.getAccount(), custInfo.getResCustId(), null, OperateEnum.LOG_RECYLE);
			logMap.put(custInfo.getResCustId(), "");
		}
		LogBean logBean = new LogBean(user.getOrgId(), user.getAccount(), user.getName(), OperateEnum.LOG_RECYLE.getCode(), OperateEnum.LOG_RECYLE.getDesc(), sublist.size(), batchId);
		logBean.setContent(sublist.size()+"条");
		logBean.setOperateId(AppConstant.Operate_id19);
		logBean.setOperateName(AppConstant.Operate_Name19);
		if(fromModule == 1){
			logBean.setModuleId(AppConstant.Module_id1004);
			logBean.setModuleName(AppConstant.Module_Name1004);
		}else{
			logBean.setModuleId(AppConstant.Module_id101);
			logBean.setModuleName(AppConstant.Module_Name101);
		}
		logCustInfoService.addTableStoreLog(logBean, logMap);
//		logBatchInfoService.saveLogInfo(user.getOrgId(), user.getAccount(),user.getName(), OperateEnum.LOG_RECYLE, ids,"");
		long min = (System.currentTimeMillis() - timerBegin) ;
		logger.info("【回收到待分配资源】  耗时(毫秒)："+min);
	}
	private void clearCustInfo(String sql, final List<ResCustInfoDto> sublist,final ShiroUser user){
		jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
			//为prepared statement设置参数。这个方法将在整个过程中被调用的次数
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setString(1, user.getOrgId());
				ps.setString(2, sublist.get(i).getResCustId());
			}
			//返回更新的结果集条数
			public int getBatchSize() {
				return sublist.size();
			}
			});
	}
	/** 
	 * 分割
	 * @param idsList
	 * @param spSize
	 * @return 
	 * @create  2015年11月18日 下午1:47:05 lixing
	 * @history  
	 */
	public static List<List<ResCustInfoDto>> splitList(List<ResCustInfoDto> idsList,int spSize){
		List<List<ResCustInfoDto>> rtnList = new ArrayList<List<ResCustInfoDto>>();
		int i = 0, start = 0,end = 0;
		int size = idsList.size();
		while(size > end){
			start = (i++) * spSize;
			end = (size > start + spSize) ? start + spSize : size;
			rtnList.add(idsList.subList(start, end));
		}
		return rtnList;
	}
	
	/**
	 * 获取第一天
	 * 
	 * @param type
	 *            1-当天 2-本周 3-本月 4-半年
	 * @return
	 * @create 2015年12月14日 下午3:48:05 lixing
	 * @history
	 */
	public String getStartDateStr(Integer type) {
		String str = "";
		if (type == 1) {
			str = DateUtil.formatDate(new Date(), DateUtil.DATE_DAY);
		} else if (type == 2) {
			str = DateUtil.getWeekFirstDay(new Date());
		} else if (type == 3) {
			str = DateUtil.getMonthFirstDay(new Date());
		} else if (type == 4) {
			str = DateUtil.formatDate(DateUtil.getAddDate(new Date(), -180), DateUtil.DATE_DAY);
		}
		return str;
	}
	
	/**
	 * 获取最后一天
	 *
	 * @param type
	 *            1-当天 2-本周 3-本月 4-半年
	 * @return
	 * @create 2015年12月14日 下午3:48:05 lixing
	 * @history
	 */
	public String getEndDateStr(Integer type) {
		String str = "";
		if (type == 1) {
			str = DateUtil.formatDate(new Date(), DateUtil.DATE_DAY);
		} else if (type == 2) {
			str = DateUtil.getWeekLastDay(new Date());
		} else if (type == 3) {
			str = DateUtil.getMonthLastDay(new Date());
		} else if (type == 4) {
			str = DateUtil.formatDate(DateUtil.getAddDate(new Date(), 180), DateUtil.DATE_DAY);
		}
		return str;
	}
	
}
