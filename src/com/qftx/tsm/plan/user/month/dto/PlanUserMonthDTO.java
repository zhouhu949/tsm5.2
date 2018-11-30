package com.qftx.tsm.plan.user.month.dto;

import com.qftx.tsm.plan.user.month.bean.PlanUsermonthBean;
import com.qftx.tsm.plan.user.month.bean.PlanUsermonthCommontBean;
import com.qftx.tsm.plan.user.month.bean.PlanUsermonthCustBean;
import com.qftx.tsm.plan.user.month.bean.PlanUsermonthPointBean;

import java.util.List;

public class PlanUserMonthDTO {
	//个人月计划
	private PlanUsermonthBean planMonth;
	//个人月计划_重点客户
	private List<PlanUsermonthCustBean> custs;
	//个人月计划_重点客户_客户数&签约金额
	private PlanUsermonthCustBean custCount;
	//个人月计划_积分
	private List<PlanUsermonthPointBean> points;
	//个人月计划_评论
	private List<PlanUsermonthCommontBean> userPlanCommonts;
	
	public PlanUsermonthBean getPlanMonth() {
		return planMonth;
	}
	public void setPlanMonth(PlanUsermonthBean planMonth) {
		this.planMonth = planMonth;
	}
	public List<PlanUsermonthCustBean> getCusts() {
		return custs;
	}
	public void setCusts(List<PlanUsermonthCustBean> custs) {
		this.custs = custs;
	}
	public List<PlanUsermonthPointBean> getPoints() {
		return points;
	}
	public void setPoints(List<PlanUsermonthPointBean> points) {
		this.points = points;
	}
	public PlanUsermonthCustBean getCustCount() {
		return custCount;
	}
	public void setCustCount(PlanUsermonthCustBean custCount) {
		this.custCount = custCount;
	}
	public List<PlanUsermonthCommontBean> getUserPlanCommonts() {
		return userPlanCommonts;
	}
	public void setUserPlanCommonts(List<PlanUsermonthCommontBean> userPlanCommonts) {
		this.userPlanCommonts = userPlanCommonts;
	}
	
}
