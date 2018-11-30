package com.qftx.tsm.log.dto;

import com.qftx.tsm.log.bean.LogUserOperateBean;

import java.util.List;

public class DtoLogUserOperateBean extends LogUserOperateBean{
	private static final long serialVersionUID = 3706421145695303865L; 
	private Integer dDateType;
	private String accountsStr;
    private List<String> accounts; //账号列表
	private String moduleStr;
    private List<String> modules; //模块列表
    private String queryText;//关键字查询
	private String idStr;
    private List<String> ids; //id列表
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
	public String getModuleStr() {
		return moduleStr;
	}
	public void setModuleStr(String moduleStr) {
		this.moduleStr = moduleStr;
	}
	public List<String> getModules() {
		return modules;
	}
	public void setModules(List<String> modules) {
		this.modules = modules;
	}
	public String getQueryText() {
		return queryText;
	}
	public void setQueryText(String queryText) {
		this.queryText = queryText;
	}
	public String getIdStr() {
		return idStr;
	}
	public void setIdStr(String idStr) {
		this.idStr = idStr;
	}
	public List<String> getIds() {
		return ids;
	}
	public void setIds(List<String> ids) {
		this.ids = ids;
	}
    
    

}
