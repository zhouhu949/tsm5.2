package com.qftx.tsm.report.dto;

import java.util.Map;

public class PlanUserDto {
	private String groupName;
	private String userName;
	private String account;
	
	private Map<String, double[]> userMap;
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Map<String, double[]> getuserMap() {
		return userMap;
	}
	public void setuserMap(Map<String, double[]> userMap) {
		this.userMap = userMap;
	}
}
