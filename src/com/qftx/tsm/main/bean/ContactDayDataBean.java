package com.qftx.tsm.main.bean;

import com.qftx.common.domain.BaseObject;

public class ContactDayDataBean extends BaseObject {
	private static final long serialVersionUID = 2864688902414090468L;
	
	private String id;//id
	private String resId;//客户id
	private String account;//账号
	private String orgId;//单位id
	private String currDate;//当天日期
	private Integer type;//类型（1-资源；2-客户）
	private Integer status;//状态（1-转意向；2-转签约；3-无变化；4-转公海）
	private String initProcessId;//初始销售进程
	private String currProcessId;//当前销售进程
	private Integer lastStatus;//上次状态
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getResId() {
		return resId;
	}
	public void setResId(String resId) {
		this.resId = resId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getCurrDate() {
		return currDate;
	}
	public void setCurrDate(String currDate) {
		this.currDate = currDate;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getInitProcessId() {
		return initProcessId;
	}
	public void setInitProcessId(String initProcessId) {
		this.initProcessId = initProcessId;
	}
	public String getCurrProcessId() {
		return currProcessId;
	}
	public void setCurrProcessId(String currProcessId) {
		this.currProcessId = currProcessId;
	}
	public Integer getLastStatus() {
		return lastStatus;
	}
	public void setLastStatus(Integer lastStatus) {
		this.lastStatus = lastStatus;
	}
	
}
