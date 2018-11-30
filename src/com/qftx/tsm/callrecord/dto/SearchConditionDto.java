package com.qftx.tsm.callrecord.dto;

import java.io.Serializable;

public class SearchConditionDto implements Serializable{

	private static final long serialVersionUID = -3737094074604416852L;
	private String custState; // =	资源状态
	private String saleProcessId; // =	销售进程id
	private String callState; // =	呼叫类型 
	private Integer timeLength; //通话时长
	private String startTime; // >=开始通话时间起始值,格式:yyyy-MM-ddHH:mm:ss
	private String endTime; // <=开始通话时间结束值,格式: yyyy-MM-ddHH:mm:ss
	private String ownerAccsStr; // 联系人
	private String dDateType;
	
	public String getCustState() {
		return custState;
	}
	public void setCustState(String custState) {
		this.custState = custState;
	}
	public String getSaleProcessId() {
		return saleProcessId;
	}
	public void setSaleProcessId(String saleProcessId) {
		this.saleProcessId = saleProcessId;
	}
	public String getCallState() {
		return callState;
	}
	public void setCallState(String callState) {
		this.callState = callState;
	}
	public Integer getTimeLength() {
		return timeLength;
	}
	public void setTimeLength(Integer timeLength) {
		this.timeLength = timeLength;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getOwnerAccsStr() {
		return ownerAccsStr;
	}
	public void setOwnerAccsStr(String ownerAccsStr) {
		this.ownerAccsStr = ownerAccsStr;
	}
	public String getdDateType() {
		return dDateType;
	}
	public void setdDateType(String dDateType) {
		this.dDateType = dDateType;
	}

}
