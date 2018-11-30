package com.qftx.tsm.plan.facade;

import com.qftx.base.util.DateUtil;
import com.qftx.common.filter.AppContextUtil;
import com.qftx.tsm.plan.group.month.bean.PlanGroupmonthAnalyBean;
import com.qftx.tsm.plan.group.month.service.PlanGroupmonthAnalyService;
import com.qftx.tsm.plan.group.month.service.PlanGroupmonthService;
import com.qftx.tsm.plan.user.month.bean.PlanUsermonthBean;
import com.qftx.tsm.plan.user.month.service.PlanUserMonthService;
import com.qftx.tsm.plan.user.month.service.PlanUsermonthToWillLogService;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.List;

public class ToWillThread implements Runnable{
	private String orgId;
	private String groupId;
	private String userId;
	
	private Logger logger = Logger.getLogger(ToWillThread.class);
	
	private PlanUserMonthService userMonthService = (PlanUserMonthService) AppContextUtil.getBean("planUserMonthService");
	private PlanGroupmonthService groupService = (PlanGroupmonthService) AppContextUtil.getBean("planGroupmonthService");
	private PlanGroupmonthAnalyService analyService = (PlanGroupmonthAnalyService) AppContextUtil.getBean("planGroupmonthAnalyService");
	private PlanUsermonthToWillLogService planUsermonthToWillLogService =  (PlanUsermonthToWillLogService) AppContextUtil.getBean("planUsermonthToWillLogService");
	
	public ToWillThread(String orgId, String groupId, String userId) {
		super();
		this.orgId = orgId;
		this.groupId = groupId;
		this.userId = userId;
	}

	@Override
	public void run() {
		try {
			if(orgId==null ||groupId==null ||userId==null){
				logger.error("param is null:"+orgId==null?"orgId":groupId==null?"groupId":userId==null?"userId":"");
			}else{
				toWill();
			}
		} catch (Exception e) {
			logger.error("",e);
		}
		
	}
	/*转意向*/
	public void toWill(){
		planUsermonthToWillLogService.insert(orgId, groupId, userId, 1);
		
		Date date = new Date();
		//部门月计划分析 更新转意向数
		List<PlanGroupmonthAnalyBean> analys = analyService.findFatherByDate(orgId, groupId, date);
		for (PlanGroupmonthAnalyBean analy : analys) {
			if(analy!=null && analy.getId()!=null) analyService.updateWill(orgId, analy.getId(), 1);
		}
		
		//1、更新用户月计划
		PlanUsermonthBean userMonthPlan = userMonthService.queryByUserAndMonth(orgId, userId, date);
		if(userMonthPlan!=null){
			//更新待确定客户执行值
			userMonthService.updateFactAddNum(userMonthPlan, orgId, 1, 0, 0);
			//更新月计划总执行值
			userMonthService.updateFactNum(userMonthPlan, orgId, 1, 0, 0);
		}else{
			logger.warn("无月计划！userId:"+userId+" date:"+DateUtil.formatDate(date));
		}
		groupService.updateFactNum(orgId, groupId, 1, 0, 0, date,0);
		logger.debug("success!");
	}
}
