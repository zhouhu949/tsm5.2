package com.qftx.tsm.worklog.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;
/* work_log_attention 工作日志_关注用户表*/
public class WorkLogAttentionBean extends BaseObject {
	private String id;	//ID
	private String userId;	//用户ID
	private String userAcc;	//用户账号
	private String orgId;	//机构ID
	private String attentionUserId;	//关注用户ID
	private String attentionUserAcc;	//关注用户账号
	private Date inputTime;	//插入时间
	private Date updateTime;	//更新时间
	private Integer isDel;	//是否删除（0：否 1：是）
	
	private String attentionUseName;//关注用户姓名
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserAcc() {
		return userAcc;
	}
	public void setUserAcc(String userAcc) {
		this.userAcc = userAcc;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getAttentionUserId() {
		return attentionUserId;
	}
	public void setAttentionUserId(String attentionUserId) {
		this.attentionUserId = attentionUserId;
	}
	public String getAttentionUserAcc() {
		return attentionUserAcc;
	}
	public void setAttentionUserAcc(String attentionUserAcc) {
		this.attentionUserAcc = attentionUserAcc;
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
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	public String getAttentionUseName() {
		return attentionUseName;
	}
	public void setAttentionUseName(String attentionUseName) {
		this.attentionUseName = attentionUseName;
	}
	
}