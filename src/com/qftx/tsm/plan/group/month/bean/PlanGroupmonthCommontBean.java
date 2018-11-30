package com.qftx.tsm.plan.group.month.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;

public class PlanGroupmonthCommontBean extends BaseObject {
	
	private static final long serialVersionUID = 7121956455538223871L;
	private String id; // null
	private String orgId; // 机构ID
	private String groupId; // 部门
	private String commontUserId; // 点评用户id
	private String planGroupmonthId; // 部门月计划id
	private String context; // 点评内容
	private Date updatetime; // 修改时间
	private Date inputtime; // 录入时间(计划日期)
	private int isDel; // 册除状态1-删除，0-未删除
	
	private String commontUserName;
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
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getCommontUserId() {
		return commontUserId;
	}
	public void setCommontUserId(String commontUserId) {
		this.commontUserId = commontUserId;
	}
	public String getPlanGroupmonthId() {
		return planGroupmonthId;
	}
	public void setPlanGroupmonthId(String planGroupmonthId) {
		this.planGroupmonthId = planGroupmonthId;
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
	public String getCommontUserName() {
		return commontUserName;
	}
	public void setCommontUserName(String commontUserName) {
		this.commontUserName = commontUserName;
	}
}