package com.qftx.tsm.phonechoose.dto;

import java.util.List;

/**
 * 号码状态查询接口 请求对象
 * 
 * @author chenhm
 *
 */
public class QueryPhoneStatusRequest {
	private String taskId;
	private List<PhoneInfoRequest> phones;
	
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public List<PhoneInfoRequest> getPhones() {
		return phones;
	}
	public void setPhones(List<PhoneInfoRequest> phones) {
		this.phones = phones;
	}

}
