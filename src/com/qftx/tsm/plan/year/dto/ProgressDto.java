package com.qftx.tsm.plan.year.dto;

import com.qftx.tsm.plan.year.bean.PlanSaleYearBean;

import java.util.Map;

public class ProgressDto {
	private String [] months={"01","02","03","04","05","06","07","08","09","10","11","12"};
	Map<String, PlanSaleYearBean> map;
	public String[] getMonths() {
		return months;
	}
	public void setMonths(String[] months) {
		this.months = months;
	}
	public Map<String, PlanSaleYearBean> getMap() {
		return map;
	}
	public void setMap(Map<String, PlanSaleYearBean> map) {
		this.map = map;
	}
	
}
