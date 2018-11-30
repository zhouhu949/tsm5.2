package com.qftx.tsm.plan.year.dto;

import com.qftx.base.auth.bean.OrgGroup;
import com.qftx.tsm.plan.year.bean.PlanSaleYearBean;

import java.util.List;

public class GroupChartDTO {
	List<OrgGroup> groups;
	List<PlanSaleYearBean> plans;
	
	public List<OrgGroup> getGroups() {
		return groups;
	}
	public void setGroups(List<OrgGroup> groups) {
		this.groups = groups;
	}
	public List<PlanSaleYearBean> getPlans() {
		return plans;
	}
	public void setPlans(List<PlanSaleYearBean> plans) {
		this.plans = plans;
	}

}
