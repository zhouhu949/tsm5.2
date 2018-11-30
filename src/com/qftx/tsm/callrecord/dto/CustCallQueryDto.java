package com.qftx.tsm.callrecord.dto;

/***
 *  客户跟进/我的客户 查询通话记录 请求参数
 */
public class CustCallQueryDto{
	private String id;  //  =	通话记录id
    private String orgId; // =	单位id
    private String custId; // =	客户id
    private String startTimeBegin; // >=	开始通话时间起始值,格式:yyyy-MM-ddHH:mm:ss
    private String startTimeEnd; // <=	开始通话时间结束值,格式: yyyy-MM-ddHH:mm:ss
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
    
}
