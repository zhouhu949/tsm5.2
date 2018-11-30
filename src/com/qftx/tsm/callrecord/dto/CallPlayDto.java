package com.qftx.tsm.callrecord.dto;

import java.io.Serializable;

/**
 * 播放器缓冲 列表
 * @author: zwj
 * @since: 2015-5-21  上午11:41:44
 * @history: t.x
 */
public class CallPlayDto implements Serializable {
	private static final long serialVersionUID = 2472983929612143413L;
	private String custName; // 客户姓名
	private String callTimeShow;//播放录音时间
	private String callId; // 主键ID
	private String calledNum; 
	private String recordUrl; // 录音文件HTTP地址
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCallTimeShow() {
		return callTimeShow;
	}
	public void setCallTimeShow(String callTimeShow) {
		this.callTimeShow = callTimeShow;
	}
	public String getCallId() {
		return callId;
	}
	public void setCallId(String callId) {
		this.callId = callId;
	}
	public String getCalledNum() {
		return calledNum;
	}
	public void setCalledNum(String calledNum) {
		this.calledNum = calledNum;
	}
	public String getRecordUrl() {
		return recordUrl;
	}
	public void setRecordUrl(String recordUrl) {
		this.recordUrl = recordUrl;
	}
}
