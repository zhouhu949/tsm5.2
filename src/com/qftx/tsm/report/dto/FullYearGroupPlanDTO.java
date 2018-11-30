package com.qftx.tsm.report.dto;

import com.qftx.tsm.plan.group.month.bean.PlanGroupmonthBean;

import java.util.Map;

public class FullYearGroupPlanDTO {

	private Map<String, PlanGroupmonthBean> monthMap;
	private String groupName;
	private String groupId;
	
	public Map<String, PlanGroupmonthBean> getMonthMap() {
		return monthMap;
	}
	public void setMonthMap(Map<String, PlanGroupmonthBean> monthMap) {
		this.monthMap = monthMap;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
}
