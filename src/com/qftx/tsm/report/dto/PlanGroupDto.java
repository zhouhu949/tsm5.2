package com.qftx.tsm.report.dto;

import java.util.Map;

public class PlanGroupDto {
	private String groupName;
	private Map<String, double[]> planMap;
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Map<String, double[]> getPlanMap() {
		return planMap;
	}
	public void setPlanMap(Map<String, double[]> planMap) {
		this.planMap = planMap;
	}
}
