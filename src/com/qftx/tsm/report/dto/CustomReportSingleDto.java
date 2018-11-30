package com.qftx.tsm.report.dto;

import java.io.Serializable;

public class CustomReportSingleDto implements Serializable{
	
	private String singleName;
	private Integer count;
	public String getSingleName() {
		return singleName;
	}
	public void setSingleName(String singleName) {
		this.singleName = singleName;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
}
