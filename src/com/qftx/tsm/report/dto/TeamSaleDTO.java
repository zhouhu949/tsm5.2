package com.qftx.tsm.report.dto;

import com.qftx.tsm.plan.group.month.bean.PlanGroupmonthBean;

import java.util.List;
import java.util.Map;

public class TeamSaleDTO {
	private List<FullYearGroupPlanDTO> groupList;
	private Map<String, PlanGroupmonthBean> totalMap;
	
	public List<FullYearGroupPlanDTO> getGroupList() {
		return groupList;
	}
	public void setGroupList(List<FullYearGroupPlanDTO> groupList) {
		this.groupList = groupList;
	}
	public Map<String, PlanGroupmonthBean> getTotalMap() {
		return totalMap;
	}
	public void setTotalMap(Map<String, PlanGroupmonthBean> totalMap) {
		this.totalMap = totalMap;
	}
}
