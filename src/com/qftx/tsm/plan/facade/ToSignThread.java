package com.qftx.tsm.plan.facade;

import com.qftx.base.util.DateUtil;
import com.qftx.common.filter.AppContextUtil;
import com.qftx.tsm.plan.group.month.service.PlanGroupmonthService;
import com.qftx.tsm.plan.user.month.bean.PlanUsermonthBean;
import com.qftx.tsm.plan.user.month.bean.PlanUsermonthCustBean;
import com.qftx.tsm.plan.user.month.service.PlanUserMonthService;
import com.qftx.tsm.plan.user.month.service.PlanUsermonthCustService;
import com.qftx.tsm.plan.user.month.service.PlanUsermonthToSignLogService;
import com.qftx.tsm.plan.year.service.PlanSaleYearService;
import com.qftx.tsm.report.service.TsmReportPlanService;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.Date;

public class ToSignThread implements Runnable{
	private String orgId;
	private String groupId;
	private String userId;
	private String custId;
	private int signNum;
	private double factMoney;
	private Date date;
	private int type;
	
	private Logger logger = Logger.getLogger(ToSignThread.class);
	private PlanUserMonthService userMonthService = (PlanUserMonthService) AppContextUtil.getBean("planUserMonthService");
	private PlanUsermonthCustService usermonthCustService= (PlanUsermonthCustService) AppContextUtil.getBean("planUsermonthCustService");
	private PlanGroupmonthService groupService= (PlanGroupmonthService) AppContextUtil.getBean("planGroupmonthService");
	private PlanSaleYearService planSaleYearService= (PlanSaleYearService) AppContextUtil.getBean("planSaleYearService");
	private PlanUsermonthToSignLogService planUsermonthToSignLogService =  (PlanUsermonthToSignLogService) AppContextUtil.getBean("planUsermonthToSignLogService");
	private TsmReportPlanService tsmReportPlanService =  (TsmReportPlanService) AppContextUtil.getBean("tsmReportPlanService");
	
	public ToSignThread(String orgId, String groupId, String userId,
			String custId, int signNum, double factMoney,Date date) {
		super();
		this.orgId = orgId;
		this.groupId = groupId;
		this.userId = userId;
		this.custId = custId;
		this.signNum = signNum;
		this.factMoney = factMoney;
		this.date = date;
	}

	public void setType(int type) {
		this.type = type;
	}


	@Override
	public void run() {
		try {
			if(orgId==null ||groupId==null ||userId==null||custId==null){
				logger.error("param is null:"+orgId==null?"orgId":groupId==null?"groupId":userId==null?"userId":custId==null?"custId":"");
			}else{
				toSign();
				if(signNum<0 || factMoney<0){
					//统计分析修改 签约数和签约金额
					tsmReportPlanService.cancelSign(orgId, userId, date, type, signNum, factMoney);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}
	
	public void toSign(){
		planUsermonthToSignLogService.insert(orgId, userId, groupId, signNum, factMoney,date);
		
		if(factMoney!=0){
			BigDecimal bigDecimal =new BigDecimal(factMoney/10000);  
			factMoney = bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();  
		}
		//1、更新年规划
		planSaleYearService.updatePlanFactNumFacade(orgId, groupId, factMoney,date);
		
		//1、更新用户月计划
		PlanUsermonthBean userMonthPlan = userMonthService.queryByUserAndMonth(orgId, userId, date);
		if(userMonthPlan!=null){
			//是否重点客户
			PlanUsermonthCustBean cust = usermonthCustService.getByPlanIdAndCustId(orgId,userMonthPlan.getId(), custId);
			if(cust!=null){
				//重点客户
				usermonthCustService.updateFactNum(orgId,signNum,factMoney,cust);
			}else{
				userMonthService.updateFactAddNum(userMonthPlan, orgId, 0, signNum, factMoney);
				//待确定客户
			}
			userMonthService.updateFactNum(userMonthPlan, orgId, 0, signNum, factMoney);
		}else{
			logger.warn("无月计划！userId:"+userId+" date:"+DateUtil.formatDate(date));
		}
		//2、更新团队和部门月计划
		groupService.updateFactNum(orgId, groupId, 0, signNum, factMoney, date,0);
		logger.debug("success!");
	}
}
