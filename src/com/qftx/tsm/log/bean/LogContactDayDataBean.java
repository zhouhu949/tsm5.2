package com.qftx.tsm.log.bean;

import com.qftx.common.domain.BaseObject;

import java.sql.Timestamp;
import java.util.Date;

public class LogContactDayDataBean extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3601237956386457478L;

	private String id;//id
	
	private String orgId;//单位ID
	
	private String custId;//客户ID
	
	private String ownerAcc;//所有者帐号
	
	private Integer initType;//初始化类型
	
	private Integer initStatus;//初始化状态
	
	private String initProcessId;//初始化销售进程
	
	private Integer type;//类型
	
	private Integer status;//状态
	
	private String currProcessId;//销售进程
	
	private Date currDate;//日期
	
	private Timestamp contactTime;//联系时间 5.1新增
	
	public LogContactDayDataBean(){} 
	
	public LogContactDayDataBean(String id,String orgId,String custId,String ownerAcc,Integer initType,Integer initStatus,
			String initProcessId,Integer type,Integer status,String currProcessId,Date currDate,Timestamp contactTime){
		this.id = id;
		this.orgId = orgId;
		this.custId = custId;
		this.ownerAcc = ownerAcc;
		this.initType = initType;
		this.initStatus = initStatus;
		this.initProcessId = initProcessId;
		this.type = type;
		this.status = status;
		this.currProcessId = currProcessId;
		this.currDate = currDate;
		this.contactTime = contactTime;
	}
	
	public LogContactDayDataBean(Integer initType,Integer initStatus,Integer type,Integer status){
		this.initType = initType;
		this.initStatus = initStatus;
		this.type = type;
		this.status = status;
	}
	
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

	public String getOwnerAcc() {
		return ownerAcc;
	}

	public void setOwnerAcc(String ownerAcc) {
		this.ownerAcc = ownerAcc;
	}

	public Integer getInitType() {
		return initType;
	}

	public void setInitType(Integer initType) {
		this.initType = initType;
	}

	public Integer getInitStatus() {
		return initStatus;
	}

	public void setInitStatus(Integer initStatus) {
		this.initStatus = initStatus;
	}

	public String getInitProcessId() {
		return initProcessId;
	}

	public void setInitProcessId(String initProcessId) {
		this.initProcessId = initProcessId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCurrProcessId() {
		return currProcessId;
	}

	public void setCurrProcessId(String currProcessId) {
		this.currProcessId = currProcessId;
	}

	public Date getCurrDate() {
		return currDate;
	}

	public void setCurrDate(Date currDate) {
		this.currDate = currDate;
	}

	public Timestamp getContactTime() {
		return contactTime;
	}

	public void setContactTime(Timestamp contactTime) {
		this.contactTime = contactTime;
	}
	
}
