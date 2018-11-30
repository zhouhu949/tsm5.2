package com.qftx.tsm.report.dto;

import com.qftx.tsm.main.bean.MainInfoBean;

import java.util.List;

public class TeamTodayContractDto extends MainInfoBean {
	private static final long serialVersionUID = 4214325792479522329L;
	
	private String userName;
	
	private String groupName;
	
	private String groupId;
	
	private Integer contactCount;
	
	private Integer noContactCount;
	
	private Integer planNum;

	private Integer addResCount;
	
	private List<String> groupIds;
	
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

	public Integer getContactCount() {
		return contactCount;
	}

	public void setContactCount(Integer contactCount) {
		this.contactCount = contactCount;
	}

	public Integer getNoContactCount() {
		return noContactCount;
	}

	public void setNoContactCount(Integer noContactCount) {
		this.noContactCount = noContactCount;
	}

	public Integer getPlanNum() {
		return planNum;
	}

	public void setPlanNum(Integer planNum) {
		this.planNum = planNum;
	}
	
	public Integer getAddResCount() {
		return addResCount;
	}

	public void setAddResCount(Integer addResCount) {
		this.addResCount = addResCount;
	}

	public List<String> getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(List<String> groupIds) {
		this.groupIds = groupIds;
	}
	
}
