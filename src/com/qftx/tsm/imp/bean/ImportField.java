package com.qftx.tsm.imp.bean;

public class ImportField {
	private String fieldCode;//字段名
	private String fieldName;//中文字段名
	private Integer type;//字段类型
	private String length;//字段长度
	private Integer isState; // 0:个人资源，1：企业资源,2:联系人
	
	public ImportField() {
		super();
	}
	
	public ImportField(String fieldCode, String fieldName, Integer type, String length, Integer isState) {
		super();
		this.fieldCode = fieldCode;
		this.fieldName = fieldName;
		this.type = type;
		this.length = length;
		this.isState = isState;
	}

	
	public String getFieldCode() {
		return fieldCode;
	}
	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public Integer getIsState() {
		return isState;
	}

	public void setIsState(Integer isState) {
		this.isState = isState;
	}
	
}
