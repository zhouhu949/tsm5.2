package com.qftx.tsm.plan.group.month.dto;

import com.qftx.tsm.plan.group.month.bean.PlanGroupmonthAnalyBean;

import java.text.DecimalFormat;

public class AnalyDTO1 {
	DecimalFormat df = new DecimalFormat(".##");
	
	private PlanGroupmonthAnalyBean lastMonthAnaly;
	private PlanGroupmonthAnalyBean currentMonthAnaly;
	private String silenceCustPercent;
	private String lostCustPercent;
	private String signMoneyPercent;
	
	public PlanGroupmonthAnalyBean getLastMonthAnaly() {
		return lastMonthAnaly;
	}
	public void setLastMonthAnaly(PlanGroupmonthAnalyBean lastMonthAnaly) {
		this.lastMonthAnaly = lastMonthAnaly;
	}
	public PlanGroupmonthAnalyBean getCurrentMonthAnaly() {
		return currentMonthAnaly;
	}
	public void setCurrentMonthAnaly(PlanGroupmonthAnalyBean currentMonthAnaly) {
		this.currentMonthAnaly = currentMonthAnaly;
	}
	public String getSilenceCustPercent() {
		if(lastMonthAnaly.getSilenceCustNum()!=0){
			double temp = (currentMonthAnaly.getSilenceCustNum() - lastMonthAnaly.getSilenceCustNum())*100.0/lastMonthAnaly.getSilenceCustNum();
			silenceCustPercent = df.format(temp)+"%";
		}else{
			silenceCustPercent = "无数据";
		}
		
		return silenceCustPercent;
	}
	public void setSilenceCustPercent(String silenceCustPercent) {
		this.silenceCustPercent = silenceCustPercent;
	}
	public String getLostCustPercent() {
		if(lastMonthAnaly.getLostCustNum()!=0){
			double temp = (currentMonthAnaly.getLostCustNum() - lastMonthAnaly.getLostCustNum())*100.0/lastMonthAnaly.getLostCustNum();
			lostCustPercent = df.format(temp)+"%";
		}else{
			lostCustPercent = "无数据";
		}
		return lostCustPercent;
	}
	public void setLostCustPercent(String lostCustPercent) {
		this.lostCustPercent = lostCustPercent;
	}
	public String getSignMoneyPercent() {
		if(lastMonthAnaly.getSignMoney()!=0){
			double temp = (currentMonthAnaly.getSignMoney()- lastMonthAnaly.getSignMoney())*100.0/lastMonthAnaly.getSignMoney();
			signMoneyPercent = df.format(temp)+"%";
		}else{
			signMoneyPercent="无数据";
		}
		
		return signMoneyPercent;
	}
	public void setSignMoneyPercent(String signMoneyPercent) {
		this.signMoneyPercent = signMoneyPercent;
	}
	
	
	
}
