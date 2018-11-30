package com.qftx.tsm.plan.facade;

import com.qftx.base.auth.bean.Org;
import com.qftx.base.auth.bean.User;
import com.qftx.base.auth.service.OrgService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.common.cached.CachedService;
import com.qftx.common.filter.AppContextUtil;
import com.qftx.tsm.plan.group.month.bean.PlanGroupmonthAnalyBean;
import com.qftx.tsm.plan.group.month.service.PlanGroupmonthAnalyService;
import com.qftx.tsm.plan.user.day.bean.PlanUserDayBean;
import com.qftx.tsm.plan.user.day.service.PlanUserDayResourceService;
import com.qftx.tsm.plan.user.day.service.PlanUserDayService;
import com.qftx.tsm.plan.user.day.service.PlanUserdaySigncustService;
import com.qftx.tsm.plan.user.day.service.PlanUserdayWillcustService;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class UpdateContactResultThread implements Runnable{
	private String orgId;
	private String groupId;
	private String userId;
	private String custId;
	private String custType;
	private String contactResult;
	private String lastOptionId;
	private boolean nextFollowToday;
	private String changeCustType;
	
	public boolean isNextFollowToday() {
		return nextFollowToday;
	}
	public void setNextFollowToday(boolean nextFollowToday) {
		this.nextFollowToday = nextFollowToday;
	}
	public String getChangeCustType() {
		return changeCustType;
	}
	public void setChangeCustType(String changeCustType) {
		this.changeCustType = changeCustType;
	}
	private Logger logger = Logger.getLogger(UpdateContactResultThread.class);
	
	private PlanGroupmonthAnalyService analyService = (PlanGroupmonthAnalyService) AppContextUtil.getBean("planGroupmonthAnalyService");
	
	private PlanUserDayService userDayService = (PlanUserDayService) AppContextUtil.getBean("planUserDayService");
	private PlanUserDayResourceService resService = (PlanUserDayResourceService) AppContextUtil.getBean("planUserDayResourceService");
	private PlanUserdayWillcustService willcustService = (PlanUserdayWillcustService) AppContextUtil.getBean("planUserdayWillcustService");
	private PlanUserdaySigncustService signcustService = (PlanUserdaySigncustService) AppContextUtil.getBean("planUserdaySigncustService");
	private CachedService cachedService = (CachedService) AppContextUtil.getBean("cachedService");
	private OrgService orgService = (OrgService) AppContextUtil.getBean("orgService");
	
	public UpdateContactResultThread(String orgId, String groupId,String userId, String custId, String custType,
			String contactResult,String lastOptionId) {
		super();
		this.orgId = orgId;
		this.groupId = groupId;
		this.userId = userId;
		this.custId = custId;
		this.custType = custType;
		this.contactResult = contactResult;
		this.lastOptionId = lastOptionId;
	}
	
	public UpdateContactResultThread(String orgId, String groupId,String userId, String custId, String custType) {
		super();
		this.orgId = orgId;
		this.groupId = groupId;
		this.userId = userId;
		this.custId = custId;
		this.custType = custType;
	}
	@Override
	public void run() {
		try {
			if(orgId==null ||userId==null||custId==null){
				logger.error("some param is null orgId:["+orgId+"]\tuserId["+userId+"]\tcustId["+custId+"]");
			}else{
				updateContactResult();
			}
		} catch (Exception e) {
			logger.error("", e);
		}
	}
	/*更新资源联系结果  
	 * custType(
	 * res:资源   contactResult枚举值：  PlanResCR
	 * will:意向  contactResult枚举值：PlanWillCR
	 * sign:签约 contactResult枚举值：  PlanSignCR
	 * )*/
	public void updateContactResult(){
		Date date = new Date();
		
		if("res".equals(custType)&& groupId!=null){
			//部门月计划分析  更新联系资源数
			try {
				List<PlanGroupmonthAnalyBean> analys = analyService.findFatherByDate(orgId, groupId, date);
				for (PlanGroupmonthAnalyBean analy : analys) {
					if(analy!=null && analy.getId()!=null) analyService.updateRes(orgId, analy.getId(), custId);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(!nextFollowToday){
			//1、日计划更改状态
			PlanUserDayBean userDayPlan = userDayService.getPlanUserDay(orgId, userId, date);
			if(userDayPlan == null){
				//logger.warn("无日计划！userId:"+userId+" date:"+DateUtil.formatDate(date));
			}else{
				if("res".equals(custType)){
					resService.updateContactResult(orgId, userDayPlan.getId(), custId, contactResult);
				}else if("will".equals(custType)){
					String lastOptionName = null;
					if(!StringUtils.isBlank(lastOptionId)){
						Map<String, String> map = cachedService.getOrgSaleProcess(orgId);
						if(map!=null && !map.isEmpty()) lastOptionName = map.get(lastOptionId);
					}
					willcustService.updateContactResult(orgId, userDayPlan.getId(), custId, contactResult,lastOptionId,lastOptionName);
				}else if("sign".equals(custType)){
					signcustService.updateContactResult(orgId, userDayPlan.getId(), custId, contactResult);
				}else{
					logger.error("客户类型无法识别："+custType);
				}
			}
		}else{
			Org org = cachedService.getAuthOrgCatch(orgId);
			
			Map<String, User> userMap = cachedService.getOrgUserMapById(orgId);
			ShiroUser user = new ShiroUser();
			if(userMap!=null && userMap.containsKey(userId)){
				user.setAccount(userMap.get(userId).getUserAccount());
			}
			user.setIsState(org.getState());
			user.setOrgId(orgId);
			user.setId(userId);
			user.setGroupId(groupId);
			PlanUserDayBean userDayPlan = userDayService.getPlanUserDay(orgId, userId, date);
			
			if(userDayPlan==null){
				
			}else{
				List<String> ids = userDayService.findCustIdByPlanId(orgId, userDayPlan.getId(), custType);
				if(ids!=null && ids.size()>0 && ids.contains(custId)){
					//当天计划中存在该资源    先删除掉
 					userDayService.delPlanCust(orgId , userDayPlan.getId(), custId, custType);
				}
			}
			
			if("res".equals(changeCustType)){
				resService.insertResources(user, new String[]{custId}, new Date());
			}else if("will".equals(changeCustType)){
				willcustService.insertWillcusts(user, new String[]{custId}, new Date());
			}else if("sign".equals(changeCustType)){
				signcustService.insertSigncusts(user, new String[]{custId}, new Date());
			}
			 
		}
	}
}
