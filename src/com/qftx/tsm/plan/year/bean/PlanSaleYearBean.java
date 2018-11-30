package com.qftx.tsm.plan.year.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qftx.common.domain.BaseObject;

import java.util.Date;
/* plan_sale_year 计划_年度规划表*/
public class PlanSaleYearBean extends BaseObject {
	private String id;	//ID
	private String orgId;	//机构ID
	private String userId;	//修改人
	private Double planMoney;	//计划金额
	private Double factMoney;	//实际金额
	private String planMonth;	//月份
	private String planYear;	//年份
	private String groupId;	//部门
	private Date updatetime;	//修改时间
	private Date inputtime;	//录入时间
	private Integer isDel;	//册除状态1-删除，0-未删除
	
	private Integer init;	//初始化（0：第一次编辑，1：非第一次编辑）
	private String[] groupIds;	//部门
	private String groupName;
	
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
	public Double getFactMoney() {
		return factMoney;
	}
	public void setFactMoney(Double factMoney) {
		this.factMoney = factMoney;
	}
	public String getPlanMonth() {
		return planMonth;
	}
	public void setPlanMonth(String planMonth) {
		this.planMonth = planMonth;
	}
	public String getPlanYear() {
		return planYear;
	}
	public void setPlanYear(String planYear) {
		this.planYear = planYear;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
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
	public Integer getInit() {
		return init;
	}
	public void setInit(Integer init) {
		this.init = init;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String[] getGroupIds() {
		return groupIds;
	}
	public void setGroupIds(String[] groupIds) {
		this.groupIds = groupIds;
	}
	
}