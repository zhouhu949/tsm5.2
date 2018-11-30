package com.qftx.tsm.contract.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;

public class ContractOrderExpressInfoBean extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 795861950618143827L;

	private String id;
	
	private String orgId;
	
	private String courierNumber;//快递单号
	
	private Integer courierStatus;//快递状态
	
	private Date invalidTime;//失效时间
	
	private String context;//物流信息

	private String expressName;//快递名称
	
	private String expressSpellName;//快递简拼
	
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

	public String getCourierNumber() {
		return courierNumber;
	}

	public void setCourierNumber(String courierNumber) {
		this.courierNumber = courierNumber;
	}

	public Integer getCourierStatus() {
		return courierStatus;
	}

	public void setCourierStatus(Integer courierStatus) {
		this.courierStatus = courierStatus;
	}

	public Date getInvalidTime() {
		return invalidTime;
	}

	public void setInvalidTime(Date invalidTime) {
		this.invalidTime = invalidTime;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getExpressName() {
		return expressName;
	}

	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}

	public String getExpressSpellName() {
		return expressSpellName;
	}

	public void setExpressSpellName(String expressSpellName) {
		this.expressSpellName = expressSpellName;
	}
}
