package com.qftx.tsm.plan.user.month.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qftx.common.domain.BaseObject;

import java.util.Date;

public class PlanUsermonthCommontBean extends BaseObject {
	private static final long serialVersionUID = 8530683153896254545L;
	private String id; // null
	private String orgId; // 机构ID
	private String groupId; // 部门
	private String commontUserId; // 点评用户id
	private String planUsermonthId; // 月计划id
	private String context; // 点评内容
	private Date updatetime; // 修改时间
	private Date inputtime; // 录入时间(计划日期)
	private Integer isDel;	//册除状态1-删除，0-未删除
	
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
	public String getPlanUsermonthId() {
		return planUsermonthId;
	}
	public void setPlanUsermonthId(String planUsermonthId) {
		this.planUsermonthId = planUsermonthId;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
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
	public String getCommontUserName() {
		return commontUserName;
	}
	public void setCommontUserName(String commontUserName) {
		this.commontUserName = commontUserName;
	}
	
}
