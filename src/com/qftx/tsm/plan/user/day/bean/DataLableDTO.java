package com.qftx.tsm.plan.user.day.bean;

public class DataLableDTO {
	private int dataLableSelect;
	private int planCount;//计划总数
	private int tempPlanCount;//计划临时数
	private int signCount;//转签约
	private int willCount;//转意向
	private int toHighSeas;//转公海
	private int noChange;//无进展
	private int noContact;//未联系
	private int silenceCount;//沉默数
	private int alreadyContact;
	
	public int getDataLableSelect() {
		return dataLableSelect;
	}

	public void setDataLableSelect(int dataLableSelect) {
		this.dataLableSelect = dataLableSelect;
	}

	public int getPlanCount() {
		return planCount;
	}

	public void setPlanCount(int planCount) {
		this.planCount = planCount;
	}

	public int getTempPlanCount() {
		return tempPlanCount;
	}

	public void setTempPlanCount(int tempPlanCount) {
		this.tempPlanCount = tempPlanCount;
	}

	public int getSignCount() {
		return signCount;
	}

	public void setSignCount(int signCount) {
		this.signCount = signCount;
	}

	public int getWillCount() {
		return willCount;
	}

	public void setWillCount(int willCount) {
		this.willCount = willCount;
	}

	public int getToHighSeas() {
		return toHighSeas;
	}

	public void setToHighSeas(int toHighSeas) {
		this.toHighSeas = toHighSeas;
	}

	public int getNoChange() {
		return noChange;
	}

	public void setNoChange(int noChange) {
		this.noChange = noChange;
	}

	public int getNoContact() {
		return noContact;
	}

	public void setNoContact(int noContact) {
		this.noContact = noContact;
	}

	public int getSilenceCount() {
		return silenceCount;
	}

	public void setSilenceCount(int silenceCount) {
		this.silenceCount = silenceCount;
	}

	public int getAlreadyContact() {
		return alreadyContact;
	}

	public void setAlreadyContact(int alreadyContact) {
		this.alreadyContact = alreadyContact;
	}
	
}
