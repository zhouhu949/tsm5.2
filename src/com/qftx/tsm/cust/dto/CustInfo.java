package com.qftx.tsm.cust.dto;

/**
 * 客户联想查询实体
 * @author: 徐承恩
 * @since: 2013-11-13  上午10:41:58
 * @history:
 */
public class CustInfo {
	
	private String resCustId;       // 资源客户ID
    private String name;           // 姓名
    private String phone;         //电话信息
	public String getResCustId() {
		return resCustId;
	}
	public void setResCustId(String resCustId) {
		this.resCustId = resCustId == null ? null : resCustId.trim();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}
	public String getPhone() {
		return phone;
	}	
	public void setPhone(String phone) {
		this.phone = phone == null ? null : phone.trim();
	}
	
}
