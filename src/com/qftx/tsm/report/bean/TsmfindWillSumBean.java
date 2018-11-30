package com.qftx.tsm.report.bean;

import com.qftx.common.domain.BaseObject;

import java.util.List;

public class TsmfindWillSumBean extends BaseObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String orgId;
	private List<String> userAccounts;
	private String optionlistId;
	private String currDate;
	
	private String userAccount;
	
	
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public List<String> getUserAccounts() {
		return userAccounts;
	}
	public void setUserAccounts(List<String> userAccounts) {
		this.userAccounts = userAccounts;
	}
	public String getOptionlistId() {
		return optionlistId;
	}
	public void setOptionlistId(String optionlistId) {
		this.optionlistId = optionlistId;
	}
	public String getCurrDate() {
		return currDate;
	}
	public void setCurrDate(String currDate) {
		this.currDate = currDate;
	}
	
	

}
