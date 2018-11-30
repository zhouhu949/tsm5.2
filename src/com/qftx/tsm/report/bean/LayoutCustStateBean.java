package com.qftx.tsm.report.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;

public class LayoutCustStateBean extends BaseObject {
	private static final long serialVersionUID = 2260789450664950359L;
	
	private String id;//id
	private String orgId;//单位ID
	private String groupId;//分组ID
	private String groupName;//分组名称
	private String userAccount;//用户账号
	private String userName;//用户姓名
	private Integer resNum;//资源数量
	private Integer custNum;//意向客户数量
	private Integer signNum;//签约客户数量
	private Integer silentNum;//沉默客户数量
	private Integer losingNum;//流失客户数量
	private Integer allNum;//全部数量
	private Date inputtime;//inputtime
	public String getId() {
		return id;
	}
	public String getOrgId() {
		return orgId;
	}
	public String getGroupId() {
		return groupId;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public String getUserName() {
		return userName;
	}
	public Integer getResNum() {
		return resNum;
	}
	public Integer getCustNum() {
		return custNum;
	}
	public Integer getSignNum() {
		return signNum;
	}
	public Integer getSilentNum() {
		return silentNum;
	}
	public Integer getLosingNum() {
		return losingNum;
	}
	public Date getInputtime() {
		return inputtime;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setResNum(Integer resNum) {
		this.resNum = resNum;
	}
	public void setCustNum(Integer custNum) {
		this.custNum = custNum;
	}
	public void setSignNum(Integer signNum) {
		this.signNum = signNum;
	}
	public void setSilentNum(Integer silentNum) {
		this.silentNum = silentNum;
	}
	public void setLosingNum(Integer losingNum) {
		this.losingNum = losingNum;
	}
	public Integer getAllNum() {
		return allNum;
	}
	public void setAllNum(Integer allNum) {
		this.allNum = allNum;
	}
	public void setInputtime(Date inputtime) {
		this.inputtime = inputtime;
	}
	
}
