package com.qftx.tsm.report.dto;

import com.qftx.tsm.report.bean.CallReportBean;

import java.io.Serializable;
import java.util.List;


public class CallReportQueryDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8537265818422471623L;
	
	private String orgId;
	
	private List<String> accounts;

	private List<CallReportBean> beans;
	
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public List<String> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<String> accounts) {
		this.accounts = accounts;
	}

	public List<CallReportBean> getBeans() {
		return beans;
	}

	public void setBeans(List<CallReportBean> beans) {
		this.beans = beans;
	}
	
}
