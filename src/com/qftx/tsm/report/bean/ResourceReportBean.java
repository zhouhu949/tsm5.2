package com.qftx.tsm.report.bean;

import com.qftx.common.domain.BaseObject;

public class ResourceReportBean extends BaseObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String resCustId;
	private String orgId;
	private Integer informationError;//信息错误(放入公海):1为放入信息错误公海
	private Integer called;//接通数，1为已经有效接通过
	private Integer callLength;//通话时长：保存所有通话中最长通话时长
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getResCustId() {
		return resCustId;
	}
	public void setResCustId(String resCustId) {
		this.resCustId = resCustId;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public Integer getInformationError() {
		return informationError;
	}
	public void setInformationError(Integer informationError) {
		this.informationError = informationError;
	}
	public Integer getCalled() {
		return called;
	}
	public void setCalled(Integer called) {
		this.called = called;
	}
	public Integer getCallLength() {
		return callLength;
	}
	public void setCallLength(Integer callLength) {
		this.callLength = callLength;
	}
	
	

}
