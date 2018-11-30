package com.qftx.tsm.cust.bean;

import com.qftx.common.domain.BaseObject;

public class CustDefinedDataBean extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8042720152801441599L;

	private String id;//id

	private String orgId;//机构ID

	private String custId;//资源ID

	private String fieldCode;//自定义字段code

	private String fieldData;//自定义字段值

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

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getFieldCode() {
		return fieldCode;
	}

	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}

	public String getFieldData() {
		return fieldData;
	}

	public void setFieldData(String fieldData) {
		this.fieldData = fieldData;
	}

}
