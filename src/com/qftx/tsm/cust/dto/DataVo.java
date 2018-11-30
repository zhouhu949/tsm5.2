package com.qftx.tsm.cust.dto;

import java.util.List;

public class DataVo {
	private List<CustFieldVo> defList;
	private List<Object> dataList;
	private PageVo pageVo;
	public List<CustFieldVo> getDefList() {
		return defList;
	}

	public void setDefList(List<CustFieldVo> defList) {
		this.defList = defList;
	}

	public PageVo getPageVo() {
		return pageVo;
	}

	public void setPageVo(PageVo pageVo) {
		this.pageVo = pageVo;
	}

	public List<Object> getDataList() {
		return dataList;
	}

	public void setDataList(List<Object> dataList) {
		this.dataList = dataList;
	}

}
