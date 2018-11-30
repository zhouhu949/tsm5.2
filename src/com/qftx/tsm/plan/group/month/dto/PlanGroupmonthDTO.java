package com.qftx.tsm.plan.group.month.dto;

import com.qftx.tsm.plan.group.month.bean.PlanGroupmonthBean;
import com.qftx.tsm.plan.group.month.bean.PlanGroupmonthCommontBean;
import com.qftx.tsm.plan.year.bean.PlanSaleYearBean;

import java.util.List;

public class PlanGroupmonthDTO {
	//部门计划
	private PlanGroupmonthBean groupPlan;
	//年规划
	private PlanSaleYearBean planSaleYearBean;
	//下级团队和
	private PlanGroupmonthBean teamPlansSum;
	//下级团队计划
	private List<PlanGroupmonthBean> teamPlans;
	//下级团队计划评论
	private List<PlanGroupmonthCommontBean> teamPlanCommonts;
	
	public PlanGroupmonthBean getGroupPlan() {
		return groupPlan;
	}
	public void setGroupPlan(PlanGroupmonthBean groupPlan) {
		this.groupPlan = groupPlan;
	}
	public List<PlanGroupmonthBean> getTeamPlans() {
		return teamPlans;
	}
	public void setTeamPlans(List<PlanGroupmonthBean> teamPlans) {
		this.teamPlans = teamPlans;
	}
	public List<PlanGroupmonthCommontBean> getTeamPlanCommonts() {
		return teamPlanCommonts;
	}
	public void setTeamPlanCommonts(List<PlanGroupmonthCommontBean> teamPlanCommonts) {
		this.teamPlanCommonts = teamPlanCommonts;
	}
	public PlanGroupmonthBean getTeamPlansSum() {
		return teamPlansSum;
	}
	public void setTeamPlansSum(PlanGroupmonthBean teamPlansSum) {
		this.teamPlansSum = teamPlansSum;
	}
	public PlanSaleYearBean getPlanSaleYearBean() {
		return planSaleYearBean;
	}
	public void setPlanSaleYearBean(PlanSaleYearBean planSaleYearBean) {
		this.planSaleYearBean = planSaleYearBean;
	}
	
}
