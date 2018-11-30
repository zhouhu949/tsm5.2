package com.qftx.tsm.report.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qftx.common.domain.BaseObject;
/* tsm_report_plan_sale_chance 销售机会月统计(5.1新增)*/
public class TsmReportPlanSaleChanceBean extends BaseObject {
	private String id;	//ID编码
	private String orgId;	//机构ID
	private String account;	//账户
	private String userId;	//用户ID
	private Double saleChanceMoney;	//预期签单金额(5.1新增)
	private Date reportDate;	//报表时间(每月一条)
	private Date inputtime;	//录入时间
	private Date updateDate;	//更新时间
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Double getSaleChanceMoney() {
		return saleChanceMoney;
	}
	public void setSaleChanceMoney(Double saleChanceMoney) {
		this.saleChanceMoney = saleChanceMoney;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getReportDate() {
		return reportDate;
	}
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getInputtime() {
		return inputtime;
	}
	public void setInputtime(Date inputtime) {
		this.inputtime = inputtime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
}