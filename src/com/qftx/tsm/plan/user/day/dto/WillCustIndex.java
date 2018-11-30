package com.qftx.tsm.plan.user.day.dto;

public class WillCustIndex implements Comparable<WillCustIndex>{
	private String custStatusId;
	private String custStatus;
	private int willcustCount;
	private int willcustContactCount;
	private int sort;

	public int getWillcustCount() {
		return willcustCount;
	}

	public void setWillcustCount(int willcustCount) {
		this.willcustCount = willcustCount;
	}

	public int getWillcustContactCount() {
		return willcustContactCount;
	}

	public void setWillcustContactCount(int willcustContactCount) {
		this.willcustContactCount = willcustContactCount;
	}

	public String getCustStatusId() {
		return custStatusId;
	}

	public void setCustStatusId(String custStatusId) {
		this.custStatusId = custStatusId;
	}

	public String getCustStatus() {
		return custStatus;
	}

	public void setCustStatus(String custStatus) {
		this.custStatus = custStatus;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	@Override
	public int compareTo(WillCustIndex o) {
        int i = this.getSort() - o.getSort(); 
        return i;  
	}
}
