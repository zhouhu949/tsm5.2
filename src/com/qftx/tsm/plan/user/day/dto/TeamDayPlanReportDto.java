package com.qftx.tsm.plan.user.day.dto;

import java.io.Serializable;

public class TeamDayPlanReportDto implements Serializable {

	private static final long serialVersionUID = 3072694082268616208L;

	private String groupId;
	private String groupName;
	private Integer notReport;
	private Integer checkedPlan;
	private Integer uncheckedPlan;
	
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Integer getNotReport() {
		return notReport;
	}
	public void setNotReport(Integer notReport) {
		this.notReport = notReport;
	}
	public Integer getCheckedPlan() {
		return checkedPlan;
	}
	public void setCheckedPlan(Integer checkedPlan) {
		this.checkedPlan = checkedPlan;
	}
	public Integer getUncheckedPlan() {
		return uncheckedPlan;
	}
	public void setUncheckedPlan(Integer uncheckedPlan) {
		this.uncheckedPlan = uncheckedPlan;
	}
}
