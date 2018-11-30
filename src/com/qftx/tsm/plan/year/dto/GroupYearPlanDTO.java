package com.qftx.tsm.plan.year.dto;

import com.qftx.base.auth.bean.OrgGroup;

import java.util.Map;

public class GroupYearPlanDTO {
	private OrgGroup group;
	private Map<String, Double> map;
	private Map<String, String> idMap;
	
	public OrgGroup getGroup() {
		return group;
	}
	public void setGroup(OrgGroup group) {
		this.group = group;
	}
	public Map<String, Double> getMap() {
		return map;
	}
	public void setMap(Map<String, Double> map) {
		this.map = map;
	}
	public Map<String, String> getIdMap() {
		return idMap;
	}
	public void setIdMap(Map<String, String> idMap) {
		this.idMap = idMap;
	}
}
