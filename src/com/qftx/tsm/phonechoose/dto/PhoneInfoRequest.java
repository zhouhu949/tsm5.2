package com.qftx.tsm.phonechoose.dto;

/**
 * 号码状态查询接口 请求对象
 * 
 * @author chenhm
 *
 */
public class PhoneInfoRequest {
	
	// 资源ID
	private String detailId;
	
	// 手机号码
	private String phone;
	
	public String getDetailId() {
		return detailId;
	}
	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

}
