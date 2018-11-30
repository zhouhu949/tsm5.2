package com.qftx.tsm.cust.dto;

import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.cust.bean.ResCustInfoDetailBean;

import java.io.Serializable;

public class CompanyResDto implements Serializable {

	private static final long serialVersionUID = 2345879073148974480L;

	private ResCustInfoBean custInfo;
	private ResCustInfoDetailBean custInfoDetail;
	
	public ResCustInfoBean getCustInfo() {
		return custInfo;
	}
	public void setCustInfo(ResCustInfoBean custInfo) {
		this.custInfo = custInfo;
	}
	public ResCustInfoDetailBean getCustInfoDetail() {
		return custInfoDetail;
	}
	public void setCustInfoDetail(ResCustInfoDetailBean custInfoDetail) {
		this.custInfoDetail = custInfoDetail;
	}
	
}
