package com.qftx.tsm.report.dto;

import java.io.Serializable;

public class CustomReportDoubleDto implements Serializable{
	
	private String singleName1;
	private String singleName2;
	private Integer count;
	
	public String getSingleName1() {
		return singleName1;
	}
	public void setSingleName1(String singleName1) {
		this.singleName1 = singleName1;
	}
	public String getSingleName2() {
		return singleName2;
	}
	public void setSingleName2(String singleName2) {
		this.singleName2 = singleName2;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
}
