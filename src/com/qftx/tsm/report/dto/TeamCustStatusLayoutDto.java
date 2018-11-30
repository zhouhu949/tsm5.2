package com.qftx.tsm.report.dto;

import java.io.Serializable;

public class TeamCustStatusLayoutDto implements Serializable {
	private static final long serialVersionUID = -8852481978613654287L;
	
	private String userName;
	
	private String userAccount;
	
	private String groupId;
	
	private String groupName;
	
	private Integer resNum;
	
	private Integer intNum;
	
	private Integer signNum;
	
	private Integer silentNum;
	
	private Integer losingNum;
	
	private Integer allNum;

	private Integer userNum;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
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

	public Integer getResNum() {
		return resNum;
	}

	public void setResNum(Integer resNum) {
		this.resNum = resNum;
	}

	public Integer getIntNum() {
		return intNum;
	}

	public void setIntNum(Integer intNum) {
		this.intNum = intNum;
	}

	public Integer getSignNum() {
		return signNum;
	}

	public void setSignNum(Integer signNum) {
		this.signNum = signNum;
	}

	public Integer getSilentNum() {
		return silentNum;
	}

	public void setSilentNum(Integer silentNum) {
		this.silentNum = silentNum;
	}

	public Integer getLosingNum() {
		return losingNum;
	}

	public void setLosingNum(Integer losingNum) {
		this.losingNum = losingNum;
	}

	public Integer getAllNum() {
		return allNum;
	}

	public void setAllNum(Integer allNum) {
		this.allNum = allNum;
	}

	public Integer getUserNum() {
		return userNum;
	}

	public void setUserNum(Integer userNum) {
		this.userNum = userNum;
	}
	
}
