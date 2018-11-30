package com.qftx.tsm.plan.year.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;

public class SaleWorkBean extends BaseObject {
	private static final long serialVersionUID = -8978383880820899937L;
	private int id; // null
	private String orgId; // 机构ID
	private String userId; // 用户ID
	private int userState; // 账号状态0不在线，1在线
	private int userOnline; // 在线时长分,显示小时
	private int telState; // 通话状态0不通话，1通话中
	private int telTime; // 通话时长，总的，
	private Date worktime; // 工作时间
	private String context; // 备注内容
	private Date inputtime; // 输入时间
	private Date updatetime; // 修改时间
	private int isDel; // 是否删除：0-否，1-是
	
	private String userName;
	private String userAccount;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public int getUserState() {
		return userState;
	}
	public void setUserState(int userState) {
		this.userState = userState;
	}
	public int getUserOnline() {
		return userOnline;
	}
	public void setUserOnline(int userOnline) {
		this.userOnline = userOnline;
	}
	public int getTelState() {
		return telState;
	}
	public void setTelState(int telState) {
		this.telState = telState;
	}
	public int getTelTime() {
		return telTime;
	}
	public void setTelTime(int telTime) {
		this.telTime = telTime;
	}
	public Date getWorktime() {
		return worktime;
	}
	public void setWorktime(Date worktime) {
		this.worktime = worktime;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public Date getInputtime() {
		return inputtime;
	}
	public void setInputtime(Date inputtime) {
		this.inputtime = inputtime;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public int getIsDel() {
		return isDel;
	}
	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
}