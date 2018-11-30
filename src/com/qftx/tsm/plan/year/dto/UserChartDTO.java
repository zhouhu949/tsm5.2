package com.qftx.tsm.plan.year.dto;

import com.qftx.base.team.bean.TsmTeamGroupMemberBean;
import com.qftx.tsm.plan.user.month.bean.PlanUsermonthBean;

import java.util.List;

public class UserChartDTO {

	private List<TsmTeamGroupMemberBean> users;
	private List<PlanUsermonthBean> plans;
	
	public List<TsmTeamGroupMemberBean> getUsers() {
		return users;
	}
	public void setUsers(List<TsmTeamGroupMemberBean> users) {
		this.users = users;
	}
	public List<PlanUsermonthBean> getPlans() {
		return plans;
	}
	public void setPlans(List<PlanUsermonthBean> plans) {
		this.plans = plans;
	}
}
