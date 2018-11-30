package com.qftx.tsm.plan.user.day.dto;

public class ContactResult {
	//全部数量
	private int totalCount;
	//已经联系数量
	private int contactCount;
	//未联系数量
	private int noContactCount;
	
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getContactCount() {
		return contactCount;
	}
	public void setContactCount(int contactCount) {
		this.contactCount = contactCount;
	}
	public int getNoContactCount() {
		return noContactCount;
	}
	public void setNoContactCount(int noContactCount) {
		this.noContactCount = noContactCount;
	}
}
