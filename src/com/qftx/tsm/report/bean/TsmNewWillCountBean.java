package com.qftx.tsm.report.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;

public class TsmNewWillCountBean extends BaseObject{
	private static final long serialVersionUID = -6719357093233607298L;
	
	private String tsmCountId;//id
	private String orgId;     //单位id
	private String account;   //用户账号
	private String name;      //用户名
	private Date inputDate; //日期
	private int count;     //新增资源数
	public String getTsmCountId() {
		return tsmCountId;
	}
	public void setTsmCountId(String tsmCountId) {
		this.tsmCountId = tsmCountId;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Date getInputDate() {
		return inputDate;
	}
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}

	
	
	
	

}
