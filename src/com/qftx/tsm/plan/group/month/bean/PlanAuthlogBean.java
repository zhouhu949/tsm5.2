package com.qftx.tsm.plan.group.month.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;

public class PlanAuthlogBean extends BaseObject {
	private static final long serialVersionUID = -5273226468324825456L;
	private String id; // null
	private String orgId; // 机构ID
	private String userId; // 审核人
	private String planId; // 计划ID
	private String planType; // 计划类型0：用户日计划 1：用户约计划 2：团队月计划
	private String authState; // 审核状态(1通过，2驳回)
	private String context; // 审核备注
	private Date updatetime; // 修改时间
	private Date inputtime; // 录入时间
	private int isDel; // 册除状态1-删除，0-未删除
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
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public String getPlanType() {
		return planType;
	}
	public void setPlanType(String planType) {
		this.planType = planType;
	}
	public String getAuthState() {
		return authState;
	}
	public void setAuthState(String authState) {
		this.authState = authState;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public Date getInputtime() {
		return inputtime;
	}
	public void setInputtime(Date inputtime) {
		this.inputtime = inputtime;
	}
	public int getIsDel() {
		return isDel;
	}
	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}
}