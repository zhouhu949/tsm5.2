package com.qftx.tsm.sys.dto;

import com.qftx.tsm.sys.bean.CustFieldSet;

import java.util.List;
/***
 * CustField list表单对象组装
 */
public class CustFieldModel {
	private List<CustFieldSet> custFieldSets;

	public List<CustFieldSet> getCustFieldSets() {
		return custFieldSets;
	}

	public void setCustFieldSets(List<CustFieldSet> custFieldSets) {
		this.custFieldSets = custFieldSets;
	}

	
}
