package com.qftx.tsm.log.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;

public class LogBatchInfoBean extends BaseObject {
	private static final long serialVersionUID = -3706421145695303864L;
	private String id;//ID
	private String orgId;//单位ID
	private String type;//操作类型
	private String context;//描述
	private String operateAcc;//操作人賬號
	private String operateName;//操作人姓名
	private Date operateDate;//操作时间
	private String data;//数据
	private String size;//操作資源數量
	private String ownerAcc;//資源持有人賬號
	//private String ownerName;//資源持有人姓名
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
	public String getOwnerAcc() {
		return ownerAcc;
	}
	public void setOwnerAcc(String ownerAcc) {
		this.ownerAcc = ownerAcc;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getOperateName() {
		return operateName;
	}
	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}
	/*public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	*/
	
	
}
