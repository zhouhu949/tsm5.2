package com.qftx.tsm.report.bean;

import com.qftx.common.domain.BaseObject;

import java.math.BigDecimal;
import java.util.Date;

public class RankingReportBean extends BaseObject {

	private static final long serialVersionUID = -7318739393769311038L;

	private String id;//id
	private String orgId;//单位id
	private String account;//帐号
	private String year;//年份
	private String month;//月份
	private BigDecimal saleAmounts;//销售金额
	private Integer signNums;//新增签约客户数
	private Integer intNums;//新增意向客户数
	private Date inputTime;//创建时间
	private Date updateTime;//修改时间
	
	public RankingReportBean(String orgId,String account,String year,String month) {
		this.orgId = orgId;
		this.account = account;
		this.year = year;
		this.month = month;
	}
	
	public RankingReportBean() {}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public BigDecimal getSaleAmounts() {
		return saleAmounts;
	}
	public void setSaleAmounts(BigDecimal saleAmounts) {
		this.saleAmounts = saleAmounts;
	}
	public Integer getSignNums() {
		return signNums;
	}
	public void setSignNums(Integer signNums) {
		this.signNums = signNums;
	}
	public Integer getIntNums() {
		return intNums;
	}
	public void setIntNums(Integer intNums) {
		this.intNums = intNums;
	}
	public Date getInputTime() {
		return inputTime;
	}
	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
