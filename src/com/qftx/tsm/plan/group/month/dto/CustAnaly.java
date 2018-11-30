package com.qftx.tsm.plan.group.month.dto;

import java.text.DecimalFormat;


public class CustAnaly {
	private DecimalFormat df = new DecimalFormat(".##");
	
	private int currentMonthSilenceCust;//本月沉默客户
	private int lastMonthSilenceCust;//上月沉默客户
	private String silenceCustPersent;
	
	private int currentMonthLostCust;//本月流失客户
	private int lastMonthLostCust;//上月流失客户
	private String lostCustPersent;
	
	public int getCurrentMonthSilenceCust() {
		return currentMonthSilenceCust;
	}
	public void setCurrentMonthSilenceCust(int currentMonthSilenceCust) {
		this.currentMonthSilenceCust = currentMonthSilenceCust;
	}
	public int getLastMonthSilenceCust() {
		return lastMonthSilenceCust;
	}
	public void setLastMonthSilenceCust(int lastMonthSilenceCust) {
		this.lastMonthSilenceCust = lastMonthSilenceCust;
	}
	public int getCurrentMonthLostCust() {
		return currentMonthLostCust;
	}
	public void setCurrentMonthLostCust(int currentMonthLostCust) {
		this.currentMonthLostCust = currentMonthLostCust;
	}
	public int getLastMonthLostCust() {
		return lastMonthLostCust;
	}
	public void setLastMonthLostCust(int lastMonthLostCust) {
		this.lastMonthLostCust = lastMonthLostCust;
	}
	public String getSilenceCustPersent() {
		double temp = (currentMonthSilenceCust - lastMonthSilenceCust)*100.0/lastMonthSilenceCust;
		silenceCustPersent = df.format(temp);
		return silenceCustPersent;
	}
	public String getLostCustPersent() {
		double temp = (currentMonthLostCust - lastMonthLostCust)*100.0/lastMonthLostCust;
		lostCustPersent = df.format(temp);
		return lostCustPersent;
	}
	public void setSilenceCustPersent(String silenceCustPersent) {
		this.silenceCustPersent = silenceCustPersent;
	}
	public void setLostCustPersent(String lostCustPersent) {
		this.lostCustPersent = lostCustPersent;
	}
	
}
