package com.qftx.tsm.plan.group.month.dto;

import java.text.DecimalFormat;

public class SignAnaly {
	private DecimalFormat df = new DecimalFormat(".##");
	
	private Double newCustIncome;// 新客户签约金额
	private Double oldCustIncome;// 老客户签约金额
	private Double currentMonthIncome; // 本月收入

	private int oldCustSignCount;// 老客户签约数
	private int newCustSignCount;// 新客户签约数
	private int currentMonthSignCount;// 签约次数
	
	private Double lastMonthIncome; // 上月收入
	
	private String incomePersent;//环比增幅
	
	public Double getCurrentMonthIncome() {
		return currentMonthIncome;
	}

	public void setCurrentMonthIncome(Double currentMonthIncome) {
		this.currentMonthIncome = currentMonthIncome;
	}

	public Double getNewCustIncome() {
		return newCustIncome;
	}

	public void setNewCustIncome(Double newCustIncome) {
		this.newCustIncome = newCustIncome;
	}

	public Double getOldCustIncome() {
		return oldCustIncome;
	}

	public void setOldCustIncome(Double oldCustIncome) {
		this.oldCustIncome = oldCustIncome;
	}

	public Double getLastMonthIncome() {
		return lastMonthIncome;
	}

	public void setLastMonthIncome(Double lastMonthIncome) {
		this.lastMonthIncome = lastMonthIncome;
	}

	public int getOldCustSignCount() {
		return oldCustSignCount;
	}

	public void setOldCustSignCount(int oldCustSignCount) {
		this.oldCustSignCount = oldCustSignCount;
	}

	public int getNewCustSignCount() {
		return newCustSignCount;
	}

	public void setNewCustSignCount(int newCustSignCount) {
		this.newCustSignCount = newCustSignCount;
	}

	public int getCurrentMonthSignCount() {
		return currentMonthSignCount;
	}

	public void setCurrentMonthSignCount(int currentMonthSignCount) {
		this.currentMonthSignCount = currentMonthSignCount;
	}

	public String getIncomePersent() {
		double temp = (currentMonthIncome - lastMonthIncome)*100.0/lastMonthIncome;
		incomePersent = df.format(temp);
		return incomePersent;
	}

	public void setIncomePersent(String incomePersent) {
		this.incomePersent = incomePersent;
	}
	
	

}
