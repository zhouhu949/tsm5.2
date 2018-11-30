package com.qftx.tsm.report.dto;

import java.util.List;

public class CustomReportDoubleShowDto {
	
	private String groupByName;
	private List<CustomReportSingleDto> list;
	public String getGroupByName() {
		return groupByName;
	}
	public void setGroupByName(String groupByName) {
		this.groupByName = groupByName;
	}
	public List<CustomReportSingleDto> getList() {
		return list;
	}
	public void setList(List<CustomReportSingleDto> list) {
		this.list = list;
	}
	
}
