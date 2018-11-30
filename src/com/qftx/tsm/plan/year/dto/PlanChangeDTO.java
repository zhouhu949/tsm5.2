package com.qftx.tsm.plan.year.dto;

public class PlanChangeDTO {
	private String id;
	private String groupId;
	private String groupName;
	private String month;
	private Double money;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Double getMoney() {
		if(money == null){
			money=0.0D;
		}
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
}
