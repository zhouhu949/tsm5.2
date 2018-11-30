package com.qftx.tsm.report.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;
import java.util.List;

public class TsmReportWillBean extends BaseObject{
	private String id;
	private Date inputeDate;
	private Integer newWillSum;
	private String optionlistId;
	private String optionName;
	private Integer optionSum;
	private String userAccount;
	private String userName;
	private String orgId;
	private String groupId;
	private String groupName;
	private Integer type;
	
	private List<String> groupIds;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getNewWillSum() {
		return newWillSum;
	}
	public void setNewWillSum(Integer newWillSum) {
		this.newWillSum = newWillSum;
	}
	public Integer getOptionSum() {
		return optionSum;
	}
	public void setOptionSum(Integer optionSum) {
		this.optionSum = optionSum;
	}
	public String getOptionlistId() {
		return optionlistId;
	}
	public void setOptionlistId(String optionlistId) {
		this.optionlistId = optionlistId;
	}
	public String getOptionName() {
		return optionName;
	}
	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
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
	public Date getInputeDate() {
		return inputeDate;
	}
	public void setInputeDate(Date inputeDate) {
		this.inputeDate = inputeDate;
	}
	public List<String> getGroupIds() {
		return groupIds;
	}
	public void setGroupIds(List<String> groupIds) {
		this.groupIds = groupIds;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	


}
