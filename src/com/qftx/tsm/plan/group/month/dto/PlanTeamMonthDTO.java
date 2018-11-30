package com.qftx.tsm.plan.group.month.dto;

import com.qftx.tsm.plan.group.month.bean.PlanGroupmonthBean;
import com.qftx.tsm.plan.group.month.bean.PlanGroupmonthCommontBean;
import com.qftx.tsm.plan.group.month.bean.PlanStatus;
import com.qftx.tsm.plan.user.month.bean.PlanUsermonthBean;
import com.qftx.tsm.plan.year.bean.PlanSaleYearBean;

import java.util.List;

public class PlanTeamMonthDTO {
	//团队月计划
	private PlanGroupmonthBean teamPlan;
	//年规划
	private PlanSaleYearBean planSaleYearBean;
	//团队成员计划和
	private PlanUsermonthBean userPlansSum;
	
	private PlanStatus planStatus;
	//团队月计划_用户
	private List<PlanUsermonthBean> userPlans;
	//团队月计划_评论
	private List<PlanGroupmonthCommontBean> teamPlanCommonts;
	
	
	public PlanGroupmonthBean getTeamPlan() {
		return teamPlan;
	}
	public void setTeamPlan(PlanGroupmonthBean teamPlan) {
		this.teamPlan = teamPlan;
	}
	public List<PlanUsermonthBean> getUserPlans() {
		return userPlans;
	}
	public void setUserPlans(List<PlanUsermonthBean> userPlans) {
		this.userPlans = userPlans;
	}
	public List<PlanGroupmonthCommontBean> getTeamPlanCommonts() {
		return teamPlanCommonts;
	}
	public void setTeamPlanCommonts(List<PlanGroupmonthCommontBean> teamPlanCommonts) {
		this.teamPlanCommonts = teamPlanCommonts;
	}
	public PlanUsermonthBean getUserPlansSum() {
		return userPlansSum;
	}
	public void setUserPlansSum(PlanUsermonthBean userPlansSum) {
		this.userPlansSum = userPlansSum;
	}
	public PlanSaleYearBean getPlanSaleYearBean() {
		return planSaleYearBean;
	}
	public void setPlanSaleYearBean(PlanSaleYearBean planSaleYearBean) {
		this.planSaleYearBean = planSaleYearBean;
	}
	public PlanStatus getPlanStatus() {
		return planStatus;
	}
	public void setPlanStatus(PlanStatus planStatus) {
		this.planStatus = planStatus;
	}
	
}
