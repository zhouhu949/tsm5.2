package com.qftx.tsm.plan.year.bean;

public class PlanSaleTrendBean {
	private Double planMoney;//截止目前规划金额
	private Double factMoney;//截止目前完成金额
	private Double planMoneyAll;//本年度全年计划金额
	private String planYear;//规划年份
	 
	public String getPlanYear() {
		return planYear;
	}
	public void setPlanYear(String planYear) {
		this.planYear = planYear;
	}
	public Double getPlanMoney() {
		return planMoney;
	}
	public void setPlanMoney(Double planMoney) {
		this.planMoney = planMoney;
	}
	public Double getFactMoney() {
		return factMoney;
	}
	public void setFactMoney(Double factMoney) {
		this.factMoney = factMoney;
	}
	public Double getPlanMoneyAll() {
		return planMoneyAll;
	}
	public void setPlanMoneyAll(Double planMoneyAll) {
		this.planMoneyAll = planMoneyAll;
	}
	
}
