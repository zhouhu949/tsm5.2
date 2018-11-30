package com.qftx.tsm.plan.group.month.bean;

public class PlanStatus {
	private int noUpNum;
	private int noAuthNum;
	private int noAuthPassNum;
	
	private StringBuilder noUpSb = new StringBuilder();
	private StringBuilder noAuthPassSb = new StringBuilder();
	
	public int getNoUpNum() {
		return noUpNum;
	}
	public void setNoUpNum(int noUpNum) {
		this.noUpNum = noUpNum;
	}
	public int getNoAuthNum() {
		return noAuthNum;
	}
	public void setNoAuthNum(int noAuthNum) {
		this.noAuthNum = noAuthNum;
	}
	public int getNoAuthPassNum() {
		return noAuthPassNum;
	}
	public void setNoAuthPassNum(int noAuthPassNum) {
		this.noAuthPassNum = noAuthPassNum;
	}
	public StringBuilder getNoUpSb() {
		return noUpSb;
	}
	public void setNoUpSb(StringBuilder noUpSb) {
		this.noUpSb = noUpSb;
	}
	public StringBuilder getNoAuthPassSb() {
		return noAuthPassSb;
	}
	public void setNoAuthPassSb(StringBuilder noAuthPassSb) {
		this.noAuthPassSb = noAuthPassSb;
	}
	public void addNoUpSb(String str){
		if(str !=null){
			if(noUpSb.length()>0) noUpSb.append(",");
			
			noUpSb.append(str);
		}
	}
	
	public void addNoAuthPass(String str){
		if(str != null){
			if(noAuthPassSb.length()>0) noAuthPassSb.append(",");
			
			noAuthPassSb.append(str);
		}
	}
	
	
}
