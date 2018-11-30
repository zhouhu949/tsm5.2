package com.qftx.tsm.report.bean;

import com.qftx.common.domain.BaseObject;

import java.util.List;

public class NewWillBean extends BaseObject{
	private String lastOptionId;
	private String forTheCustomerTime;
	private String num;
	private String orgId;
	private String imporTdeptId;
	private String owenrAcc;
	private String groupId;
	private String memberName;
	private String groupName;
	private List<String> groupIds;
	
	
	public String getLastOptionId() {
		return lastOptionId;
	}
	public void setLastOptionId(String lastOptionId) {
		this.lastOptionId = lastOptionId;
	}

	public String getForTheCustomerTime() {
		return forTheCustomerTime;
	}
	public void setForTheCustomerTime(String forTheCustomerTime) {
		this.forTheCustomerTime = forTheCustomerTime;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getImporTdeptId() {
		return imporTdeptId;
	}
	public void setImporTdeptId(String imporTdeptId) {
		this.imporTdeptId = imporTdeptId;
	}
	public String getOwenrAcc() {
		return owenrAcc;
	}
	public void setOwenrAcc(String owenrAcc) {
		this.owenrAcc = owenrAcc;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public List<String> getGroupIds() {
		return groupIds;
	}
	public void setGroupIds(List<String> groupIds) {
		this.groupIds = groupIds;
	}
	
	
	

}
