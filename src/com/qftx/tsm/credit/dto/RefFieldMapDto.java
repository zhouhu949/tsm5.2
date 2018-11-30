package com.qftx.tsm.credit.dto;

import com.qftx.tsm.sys.bean.CustFieldSet;

/**
 * 曲牌 引用字段映射类
 * 
 * @author chenhuamao
 *
 */
public class RefFieldMapDto {
	
	private CustFieldSet qupaiField;
	
	private CustFieldSet resField;

	public CustFieldSet getQupaiField() {
		return qupaiField;
	}

	public void setQupaiField(CustFieldSet qupaiField) {
		this.qupaiField = qupaiField;
	}

	public CustFieldSet getResField() {
		return resField;
	}

	public void setResField(CustFieldSet resField) {
		this.resField = resField;
	}

}
