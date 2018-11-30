package com.qftx.tsm.cust.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;

/** 资源对外同步接口临时表实体 */
public class TsmCustSynTempDataBean extends BaseObject {

	private static final long serialVersionUID = 1L;

	private String id;
	
	private String jsonData;
	
	private String orgId;
	
	private Date inputtime;

	private Integer type; // 同步类型 1：跟进信息，2：签约客户，3：订单信息

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public Date getInputtime() {
		return inputtime;
	}

	public void setInputtime(Date inputtime) {
		this.inputtime = inputtime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
		
}
