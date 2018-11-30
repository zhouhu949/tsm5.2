package com.qftx.tsm.bill.dto;

import com.qftx.tsm.bill.bean.LogCommuOptorBean;

import java.util.List;

public class LogCommuOptorDto extends LogCommuOptorBean {
	private static final long serialVersionUID = -5656663936826095960L;
	
	 private String userName;      //用户名称
	    
     private String operateNname;	//操作人名称
    
     private Long firstOperateTimelength;		//操作前剩余时长
     
     private String queryText;//关键字查询
     
     private Integer dDateType;
     
     private String accountsStr;
     
     private List<String> accounts;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOperateNname() {
		return operateNname;
	}

	public void setOperateNname(String operateNname) {
		this.operateNname = operateNname;
	}

	public Long getFirstOperateTimelength() {
		return firstOperateTimelength;
	}

	public void setFirstOperateTimelength(Long firstOperateTimelength) {
		this.firstOperateTimelength = firstOperateTimelength;
	}

	public String getQueryText() {
		return queryText;
	}

	public void setQueryText(String queryText) {
		this.queryText = queryText;
	}

	public Integer getdDateType() {
		return dDateType;
	}

	public void setdDateType(Integer dDateType) {
		this.dDateType = dDateType;
	}

	public String getAccountsStr() {
		return accountsStr;
	}

	public void setAccountsStr(String accountsStr) {
		this.accountsStr = accountsStr;
	}

	public List<String> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<String> accounts) {
		this.accounts = accounts;
	}
     
}
