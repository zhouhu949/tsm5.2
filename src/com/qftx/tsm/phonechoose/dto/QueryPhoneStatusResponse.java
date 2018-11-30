package com.qftx.tsm.phonechoose.dto;

import java.util.List;

/**
 * 号码状态查询接口 响应对象
 * 
 * @author chenhm
 *
 */
public class QueryPhoneStatusResponse {
	private String taskId;
	private List<PhoneInfoResponse> phones;
	
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public List<PhoneInfoResponse> getPhones() {
		return phones;
	}
	public void setPhones(List<PhoneInfoResponse> phones) {
		this.phones = phones;
	}

}
