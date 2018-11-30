package com.qftx.tsm.report.dto;

import com.qftx.tsm.report.bean.CallReportBean;

import java.util.List;

public class CallReportDto extends CallReportBean {
	private static final long serialVersionUID = -7325669889170371730L;
	
	private String userName;
	
	private String groupName;

	private String groupId;
	
	private List<String> groupIds;
	
	private String orderColumn;
	
	private String orderType;
	
	private String actionCompletionRate;
	
	public String getActionCompletionRate() {
		return actionCompletionRate;
	}

	public void setActionCompletionRate(String actionCompletionRate) {
		this.actionCompletionRate = actionCompletionRate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public List<String> getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(List<String> groupIds) {
		this.groupIds = groupIds;
	}

	public String getOrderColumn() {
		return orderColumn;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
}
