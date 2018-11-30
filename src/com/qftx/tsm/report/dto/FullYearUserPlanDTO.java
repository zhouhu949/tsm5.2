package com.qftx.tsm.report.dto;

import com.qftx.tsm.plan.user.month.bean.PlanUsermonthBean;

import java.util.Map;

public class FullYearUserPlanDTO {
	private Map<String, PlanUsermonthBean> monthMap;
	private String userName;
	private String account;
	private String userId;
	
	public Map<String, PlanUsermonthBean> getMonthMap() {
		return monthMap;
	}
	public void setMonthMap(Map<String, PlanUsermonthBean> monthMap) {
		this.monthMap = monthMap;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	
}
