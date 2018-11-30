package com.qftx.tsm.cust.bean;

import com.qftx.common.domain.BaseObject;

public class LinkmanDefinedDataBean extends BaseObject{
    
	private static final long serialVersionUID = -8042720152803441599L;
	
	private String id;

    private String orgId;

    private String custId;

    private String linkmanId;

    private String fieldCode;

    private String fieldData;

    public String getId() {
        return id;
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

	public String getLinkmanId() {
		return linkmanId;
	}

	public void setLinkmanId(String linkmanId) {
		this.linkmanId = linkmanId;
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

	public void setId(String id) {
		this.id = id;
	}

    
}