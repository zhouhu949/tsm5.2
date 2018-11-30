package com.qftx.tsm.report.dto;

import com.qftx.tsm.report.bean.RankingReportBean;

public class RankingReportDto extends RankingReportBean {
	private static final long serialVersionUID = -2394078316430646035L;
	
	private String userName;

	private Integer calloutTotal;
	
	private String imgUrl;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getCalloutTotal() {
		return calloutTotal;
	}

	public void setCalloutTotal(Integer calloutTotal) {
		this.calloutTotal = calloutTotal;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	
}
