package com.qftx.tsm.tao.dto;

import java.util.List;

public class CustDto {

	private String wangWang;
	private String qq;
	private String mobilephone;
	private String telphone;
	private String alternatePhone2;
	private String email;
	private String custId;
	private String sex;
	private String isConcat; //0-未联系；1-已联系
	private List<CustDetailDto> list;

	public String getWangWang() {
		return wangWang;
	}

	public void setWangWang(String wangWang) {
		this.wangWang = wangWang;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getAlternatePhone2() {
		return alternatePhone2;
	}

	public void setAlternatePhone2(String alternatePhone2) {
		this.alternatePhone2 = alternatePhone2;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public List<CustDetailDto> getList() {
		return list;
	}

	public void setList(List<CustDetailDto> list) {
		this.list = list;
	}

	public String getIsConcat() {
		return isConcat;
	}

	public void setIsConcat(String isConcat) {
		this.isConcat = isConcat;
	}

}
