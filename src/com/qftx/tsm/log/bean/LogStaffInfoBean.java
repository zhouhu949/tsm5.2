package com.qftx.tsm.log.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;

public class LogStaffInfoBean extends BaseObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5977610696800419608L;
	private String id;//ID
	private String orgId;//单位ID
	private String type;//操作类型
	private String operateAcc;//操作人
	private Date operateDate;//操作时间
	private String data;//数据
	private String context;//描述
	private String reqId;
	public String getId() {
		return id;
	}
	public String getOrgId() {
		return orgId;
	}
	public String getType() {
		return type;
	}
	public String getOperateAcc() {
		return operateAcc;
	}
	public Date getOperateDate() {
		return operateDate;
	}
	public String getData() {
		return data;
	}
	public String getContext() {
		return context;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setOperateAcc(String operateAcc) {
		this.operateAcc = operateAcc;
	}
	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}
	public void setData(String data) {
		this.data = data;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getReqId() {
		return reqId;
	}
	public void setReqId(String reqId) {
		this.reqId = reqId;
	}
}
