package com.qftx.tsm.follow.dto;

import com.qftx.tsm.cust.bean.TsmCustGuide;
import com.qftx.tsm.follow.bean.CustFollowBean;

public class PageFormDto {
	private CustFollowBean custFollow;
	private TsmCustGuide custGuide;
	
	public CustFollowBean getCustFollow() {
		return custFollow;
	}
	public void setCustFollow(CustFollowBean custFollow) {
		this.custFollow = custFollow;
	}
	public TsmCustGuide getCustGuide() {
		return custGuide;
	}
	public void setCustGuide(TsmCustGuide custGuide) {
		this.custGuide = custGuide;
	}
	

}
