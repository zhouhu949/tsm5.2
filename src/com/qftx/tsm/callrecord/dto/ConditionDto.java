package com.qftx.tsm.callrecord.dto;

import java.io.Serializable;
import java.util.List;

public class ConditionDto implements Serializable{

	private static final long serialVersionUID = -3737094074604416852L;
	private String orgId;  // =	单位id
	private List<String>inputAccs; //=	账号列表
	private List<String>custIds;
	private String custName;
	private String queryKey; // like	客户名称、主叫号码或被叫号码
	private String custType; // =	资源类型
	private String custState; // =	资源状态
	private String saleProcessId; // =	销售进程id
	private Integer callType; // =	呼叫类型 
	private String callState; // =	呼叫状态
	private Integer timeLengthBegin; //>=	通话时长起始值(秒)
	private Integer timeLengthEnd; // <=	通话时长结束值(秒)
	private String startTimeBegin; // >=开始通话时间起始值,格式:yyyy-MM-ddHH:mm:ss
	private String startTimeEnd; // <=开始通话时间结束值,格式: yyyy-MM-ddHH:mm:ss
	private String define1; // like	自定义字段1
	private String define2; // like	自定义字段2
	private String define3; // like	自定义字段3
	private String define10; // like	自定义字段10
	private String callerNum; // 主叫
	private String calledNum; // 被叫
	private CallRecordPageDto page = new CallRecordPageDto();
	
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public List<String> getInputAccs() {
		return inputAccs;
	}
	public void setInputAccs(List<String> inputAccs) {
		this.inputAccs = inputAccs;
	}
	public List<String> getCustIds() {
		return custIds;
	}
	public void setCustIds(List<String> custIds) {
		this.custIds = custIds;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getQueryKey() {
		return queryKey;
	}
	public void setQueryKey(String queryKey) {
		this.queryKey = queryKey;
	}
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
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
	public Integer getCallType() {
		return callType;
	}
	public void setCallType(Integer callType) {
		this.callType = callType;
	}
	public String getCallState() {
		return callState;
	}
	public void setCallState(String callState) {
		this.callState = callState;
	}
	public Integer getTimeLengthBegin() {
		return timeLengthBegin;
	}
	public void setTimeLengthBegin(Integer timeLengthBegin) {
		this.timeLengthBegin = timeLengthBegin;
	}
	public Integer getTimeLengthEnd() {
		return timeLengthEnd;
	}
	public void setTimeLengthEnd(Integer timeLengthEnd) {
		this.timeLengthEnd = timeLengthEnd;
	}

	public String getStartTimeBegin() {
		return startTimeBegin;
	}
	public void setStartTimeBegin(String startTimeBegin) {
		this.startTimeBegin = startTimeBegin;
	}
	public String getStartTimeEnd() {
		return startTimeEnd;
	}
	public void setStartTimeEnd(String startTimeEnd) {
		this.startTimeEnd = startTimeEnd;
	}
	public String getDefine1() {
		return define1;
	}
	public void setDefine1(String define1) {
		this.define1 = define1;
	}
	public String getDefine2() {
		return define2;
	}
	public void setDefine2(String define2) {
		this.define2 = define2;
	}
	public String getDefine3() {
		return define3;
	}
	public void setDefine3(String define3) {
		this.define3 = define3;
	}
	public String getDefine10() {
		return define10;
	}
	public void setDefine10(String define10) {
		this.define10 = define10;
	}
	public CallRecordPageDto getPage() {
		return page;
	}
	public void setPage(CallRecordPageDto page) {
		this.page = page;
	}
	public String getCallerNum() {
		return callerNum;
	}
	public void setCallerNum(String callerNum) {
		this.callerNum = callerNum;
	}
	public String getCalledNum() {
		return calledNum;
	}
	public void setCalledNum(String calledNum) {
		this.calledNum = calledNum;
	}
	

}
