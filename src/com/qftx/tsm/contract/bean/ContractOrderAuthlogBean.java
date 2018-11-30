package com.qftx.tsm.contract.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;

public class ContractOrderAuthlogBean extends BaseObject {
	private static final long serialVersionUID = 7841284171968915092L;
	
	private String id;//id
	private String orgId;//机构ID
	private String userAcc;//审核人帐号
	private String userName;//审核人
	private String orderId;//订单ID
	private String authState;//审核状态(2通过，3拒绝)
	private String context;//审核备注
	private Date inputtime;//录入时间
	private Integer isDel;//册除状态1-删除，0-未删除
	
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
	public String getUserAcc() {
		return userAcc;
	}
	public void setUserAcc(String userAcc) {
		this.userAcc = userAcc;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

}
