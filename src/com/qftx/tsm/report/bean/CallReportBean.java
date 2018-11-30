package com.qftx.tsm.report.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;

public class CallReportBean extends BaseObject {
	private static final long serialVersionUID = -6719357093233607298L;
	
	private String id;//ID
	
	private String orgId;//单位ID
	
	private String account;//账号
	
	private Date reportDate;//统计时间
	
	private Integer calloutTotal = 0;//呼出总数
	
	private Integer calloutAnswer = 0;//呼出已接
	
	private Integer callinTotal = 0;//呼入总数
	
	private Integer callinAnswer = 0;//呼入已接
	
	private Integer validCalls = 0;//有效通话
	
	private Integer validCallIn = 0;//有效呼入
	
	private Integer restCalls = 0;//呼叫资源数
	
	private Integer custCalls = 0;//呼叫意向客户数
	
	private Integer calloutTime = 0;//呼出时长（秒）
	
	private Integer callinTime = 0;//呼入时长（秒）
	
	private Integer billingTime = 0;//计费时长（分）
	
	private Integer visitNum;//上门拜访量
	
	private Date insertTime;//录入时间
	
	private Date updateTime;//修改时间

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

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public Integer getCalloutTotal() {
		return calloutTotal;
	}

	public void setCalloutTotal(Integer calloutTotal) {
		this.calloutTotal = calloutTotal;
	}

	public Integer getCalloutAnswer() {
		return calloutAnswer;
	}

	public void setCalloutAnswer(Integer calloutAnswer) {
		this.calloutAnswer = calloutAnswer;
	}

	public Integer getCallinTotal() {
		return callinTotal;
	}

	public Integer getCallinAnswer() {
		return callinAnswer;
	}

	public void setCallinTotal(Integer callinTotal) {
		this.callinTotal = callinTotal;
	}

	public void setCallinAnswer(Integer callinAnswer) {
		this.callinAnswer = callinAnswer;
	}

	public Integer getValidCalls() {
		return validCalls;
	}

	public void setValidCalls(Integer validCalls) {
		this.validCalls = validCalls;
	}

	public Integer getValidCallIn() {
		return validCallIn;
	}

	public void setValidCallIn(Integer validCallIn) {
		this.validCallIn = validCallIn;
	}

	public Integer getRestCalls() {
		return restCalls;
	}

	public void setRestCalls(Integer restCalls) {
		this.restCalls = restCalls;
	}

	public Integer getCustCalls() {
		return custCalls;
	}

	public void setCustCalls(Integer custCalls) {
		this.custCalls = custCalls;
	}

	public Integer getCalloutTime() {
		return calloutTime;
	}

	public void setCalloutTime(Integer calloutTime) {
		this.calloutTime = calloutTime;
	}

	public Integer getCallinTime() {
		return callinTime;
	}

	public void setCallinTime(Integer callinTime) {
		this.callinTime = callinTime;
	}

	public Integer getBillingTime() {
		return billingTime;
	}

	public void setBillingTime(Integer billingTime) {
		this.billingTime = billingTime;
	}

	public Integer getVisitNum() {
		return visitNum;
	}

	public void setVisitNum(Integer visitNum) {
		this.visitNum = visitNum;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
