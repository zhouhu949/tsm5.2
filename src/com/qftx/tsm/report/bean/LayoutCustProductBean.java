package com.qftx.tsm.report.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;

public class LayoutCustProductBean extends BaseObject{
	private static final long serialVersionUID = -2038712247357961677L;
	
	private String id;//id
	private String orgId;//单位ID
	private String groupId;//分组ID
	private String groupName;//分组名称
	private String userAccount;//帐号
	private String userName;//姓名
	private Integer type;//类型：1-意向客户，2-签约客户
	private String productId;//产品ID
	private Integer custNums;//客户数量
	private Date inputtime;//录入时间
	
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
	public Integer getType() {
		return type;
	}
	public String getProductId() {
		return productId;
	}
	public Integer getCustNums() {
		return custNums;
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
	public void setType(Integer type) {
		this.type = type;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public void setCustNums(Integer custNums) {
		this.custNums = custNums;
	}
	public void setInputtime(Date inputtime) {
		this.inputtime = inputtime;
	}
	
}
