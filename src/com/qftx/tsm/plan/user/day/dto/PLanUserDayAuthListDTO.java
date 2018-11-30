package com.qftx.tsm.plan.user.day.dto;

import com.qftx.tsm.plan.user.day.bean.PlanUserDayBean;

import java.util.List;

public class PLanUserDayAuthListDTO extends PlanUserDayBean{
	private List<String> groupIds;
	private List<String> userIds;
	
	private Integer oDateType;
	private String pstartDate;
	private String pendDate;

	public List<String> getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(List<String> groupIds) {
		this.groupIds = groupIds;
	}

	public List<String> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<String> userIds) {
		this.userIds = userIds;
	}

	public Integer getoDateType() {
		return oDateType;
	}

	public void setoDateType(Integer oDateType) {
		this.oDateType = oDateType;
	}

	public String getPstartDate() {
		return pstartDate;
	}

	public void setPstartDate(String pstartDate) {
		this.pstartDate = pstartDate;
	}

	public String getPendDate() {
		return pendDate;
	}

	public void setPendDate(String pendDate) {
		this.pendDate = pendDate;
	}
	
}
