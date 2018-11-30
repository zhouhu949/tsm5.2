package com.qftx.tsm.callrecord.dto;

public class HistoryCallQueryDto {

	    private Integer pageSize = 20; // =	每页大小(默认值为20)
	    private Boolean sync = false; // =	查询同步数据时为true，否则为false
	    private String id; // =	通话记录id
	    private String orgId; // =	单位id
	    private String inputAcc; // =	录入人
	    private String custId; // =	客户id
	    private String followId; // =	跟进id
	    private String callState; // =	1呼入，2呼出
	    private String callerNum; // =	通信主叫(长号)
	    private String calledNum; // =	通信被叫(长号)
	    private String startTimeBegin; // >=	开始通话时间起始值,格式:yyyy-MM-ddHH:mm:ss
	    private String startTimeEnd; // <=	开始通话时间结束值,格式:yyyy-MM-ddHH:mm:ss
	    private Integer timeLengthBegin; // >=	通话时长起始值(秒)
	    private Integer timeLengthEnd; // <=	通话时长结束值(秒)
	    
		public Integer getPageSize() {
			return pageSize;
		}
		public void setPageSize(Integer pageSize) {
			this.pageSize = pageSize;
		}
		public Boolean getSync() {
			return sync;
		}
		public void setSync(Boolean sync) {
			this.sync = sync;
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
		public String getInputAcc() {
			return inputAcc;
		}
		public void setInputAcc(String inputAcc) {
			this.inputAcc = inputAcc;
		}
		public String getCustId() {
			return custId;
		}
		public void setCustId(String custId) {
			this.custId = custId;
		}
		public String getFollowId() {
			return followId;
		}
		public void setFollowId(String followId) {
			this.followId = followId;
		}
		public String getCallState() {
			return callState;
		}
		public void setCallState(String callState) {
			this.callState = callState;
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
	    	    
}
