package com.qftx.tsm.cust.dto;

import java.util.Date;

public class CustVo {
	private String resCustId;
	private Date inputDate;
	private String name;
	private int isState;
	private String company;
	private String mobilephone;
	private String telphone;
	private String groupName;
	private String deptName;

	public String getResCustId() {
		return resCustId;
	}

	public void setResCustId(String resCustId) {
		this.resCustId = resCustId;
	}

	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIsState() {
		return isState;
	}

	public void setIsState(int isState) {
		this.isState = isState;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

}
