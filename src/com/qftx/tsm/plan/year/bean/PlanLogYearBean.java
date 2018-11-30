package com.qftx.tsm.plan.year.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qftx.common.domain.BaseObject;

import java.util.Date;
/* plan_log_year 计划_年度计划变更日志总信息*/
public class PlanLogYearBean extends BaseObject {
	private String id;	//ID
	private String orgId;	//机构ID
	private String userId;	//修改人
	private Double planMoney;	//计划金额
	private String planYear;	//年份
	private String data;	//存JSON数据，修改明细说明
	private String dcontext;	//修改说明（部门里的修改与个人修改+说明）
	private String reason;	//变更说明
	private Date updatetime;	//修改时间
	private Date inputtime;	//录入时间
	private Integer isDel;	//册除状态1-删除，0-未删除
	
	private Date inputtimeBegin;
	private Date inputtimeEnd;
	
	private String inputtimeStr;
	private String inputtimeMonthDayStr;
	private String inputtimeYearStr;
	private Integer isHaveDcontext;
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Double getPlanMoney() {
		return planMoney;
	}
	public void setPlanMoney(Double planMoney) {
		this.planMoney = planMoney;
	}
	public String getPlanYear() {
		return planYear;
	}
	public void setPlanYear(String planYear) {
		this.planYear = planYear;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getDcontext() {
		return dcontext;
	}
	public void setDcontext(String dcontext) {
		this.dcontext = dcontext;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getInputtime() {
		return inputtime;
	}
	public void setInputtime(Date inputtime) {
		this.inputtime = inputtime;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	public Integer getIsHaveDcontext() {
		return isHaveDcontext;
	}
	public void setIsHaveDcontext(Integer isHaveDcontext) {
		this.isHaveDcontext = isHaveDcontext;
	}
	public Date getInputtimeBegin() {
		return inputtimeBegin;
	}
	public void setInputtimeBegin(Date inputtimeBegin) {
		this.inputtimeBegin = inputtimeBegin;
	}
	public Date getInputtimeEnd() {
		return inputtimeEnd;
	}
	public void setInputtimeEnd(Date inputtimeEnd) {
		this.inputtimeEnd = inputtimeEnd;
	}
	public String getInputtimeStr() {
		return inputtimeStr;
	}
	public void setInputtimeStr(String inputtimeStr) {
		this.inputtimeStr = inputtimeStr;
	}
	public String getInputtimeMonthDayStr() {
		return inputtimeMonthDayStr;
	}
	public void setInputtimeMonthDayStr(String inputtimeMonthDayStr) {
		this.inputtimeMonthDayStr = inputtimeMonthDayStr;
	}
	public String getInputtimeYearStr() {
		return inputtimeYearStr;
	}
	public void setInputtimeYearStr(String inputtimeYearStr) {
		this.inputtimeYearStr = inputtimeYearStr;
	}
	
}