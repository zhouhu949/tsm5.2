package com.qftx.tsm.cust.dto;

import com.qftx.tsm.log.bean.LogBatchInfoBean;

import java.util.List;

public class LogBatchInfoDto extends LogBatchInfoBean{
	private static final long serialVersionUID = 3706421145695303865L; 
	private Integer dDateType;
	private String accountsStr;
    private List<String> accounts;
    private String queryText;//关键字查询
    private String ownerName;
    private List<String> shareAccList;
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
	public String getQueryText() {
		return queryText;
	}
	public void setQueryText(String queryText) {
		this.queryText = queryText;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public List<String> getShareAccList() {
		return shareAccList;
	}
	public void setShareAccList(List<String> shareAccList) {
		this.shareAccList = shareAccList;
	}
    

}
