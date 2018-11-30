package com.qftx.export.dto;

import java.io.Serializable;
import java.util.List;

import com.qftx.tsm.cust.dto.ResCustInfoDto;

public class CustDataExportDto implements Serializable {
	
	private static final long serialVersionUID = 7475934138774895081L;

	private ResCustInfoDto custInfo;//过滤条件
	
	private Integer exportType;//导出库类型 1-资源 2-意向 3-签约 4-沉默 5-流失
	
	private List<String> columns;//导出字段

	public ResCustInfoDto getCustInfo() {
		return custInfo;
	}

	public void setCustInfo(ResCustInfoDto custInfo) {
		this.custInfo = custInfo;
	}

	public Integer getExportType() {
		return exportType;
	}

	public void setExportType(Integer exportType) {
		this.exportType = exportType;
	}

	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}
	
}
