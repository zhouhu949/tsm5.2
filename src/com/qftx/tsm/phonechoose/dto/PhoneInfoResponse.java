package com.qftx.tsm.phonechoose.dto;

/**
 * 号码状态查询接口 响应对象
 * 
 * @author chenhm
 *
 */
public class PhoneInfoResponse extends PhoneInfoRequest {
	
	// 号码状态
	private String status;
	
	// 状态描述
	private String statusDesc;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

}
