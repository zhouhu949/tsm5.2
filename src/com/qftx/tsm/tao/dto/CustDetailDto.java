package com.qftx.tsm.tao.dto;

public class CustDetailDto {
    
	private String detailId;
	private String name;// 联系人姓名
	private String telphone;// 电话
	private String telphonebak;// 备用电话
	private String isDefault;// 是否为默认联系人：0-否 1-是
	private String sex;
	private String duty;
	private String email;
	private String wangwang;
	private String qq;
    private String callNum; //拨通次数
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWangwang() {
		return wangwang;
	}

	public void setWangwang(String wangwang) {
		this.wangwang = wangwang;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getTelphonebak() {
		return telphonebak;
	}

	public void setTelphonebak(String telphonebak) {
		this.telphonebak = telphonebak;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public String getDetailId() {
		return detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

	public String getCallNum() {
		return callNum;
	}

	public void setCallNum(String callNum) {
		this.callNum = callNum;
	}

}
