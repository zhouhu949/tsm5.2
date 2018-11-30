package com.qftx.tsm.cust.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;

//绑定微信bean
public class WxBindBean extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5281596305617596623L;
	private String id;
	private String account;
	private String orgId;
	private String custId;
	private String linkNameId;
	private String linkName;
	private String wxLoginId;
	private String wxId;
	private String wxName;
	private Date inputDate;
	private String inputAcc;
	private Date updateDate;
	private String updateAcc;
	private Integer type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getLinkNameId() {
		return linkNameId;
	}

	public void setLinkNameId(String linkNameId) {
		this.linkNameId = linkNameId;
	}

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public String getWxLoginId() {
		return wxLoginId;
	}

	public void setWxLoginId(String wxLoginId) {
		this.wxLoginId = wxLoginId;
	}

	public String getWxId() {
		return wxId;
	}

	public void setWxId(String wxId) {
		this.wxId = wxId;
	}

	public String getWxName() {
		return wxName;
	}

	public void setWxName(String wxName) {
		this.wxName = wxName;
	}

	public String getInputAcc() {
		return inputAcc;
	}

	public void setInputAcc(String inputAcc) {
		this.inputAcc = inputAcc;
	}

	public String getUpdateAcc() {
		return updateAcc;
	}

	public void setUpdateAcc(String updateAcc) {
		this.updateAcc = updateAcc;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

}
