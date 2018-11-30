package com.qftx.tsm.report.dto;

import com.qftx.common.domain.BaseObject;

import java.util.Date;

public class LossCustDto extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4802276019688282829L;
	private Date currDate;
	private int signTotal;
	private int lossTotal;
	private int expireCustTotal;
	private int addLossTotal;
	private double lossRate;
	private String account;
	private String deptId;
	private String deptName;

	public Date getCurrDate() {
		return currDate;
	}

	public void setCurrDate(Date currDate) {
		this.currDate = currDate;
	}

	public int getSignTotal() {
		return signTotal;
	}

	public void setSignTotal(int signTotal) {
		this.signTotal = signTotal;
	}

	public int getLossTotal() {
		return lossTotal;
	}

	public void setLossTotal(int lossTotal) {
		this.lossTotal = lossTotal;
	}

	public int getExpireCustTotal() {
		return expireCustTotal;
	}

	public void setExpireCustTotal(int expireCustTotal) {
		this.expireCustTotal = expireCustTotal;
	}

	public int getAddLossTotal() {
		return addLossTotal;
	}

	public void setAddLossTotal(int addLossTotal) {
		this.addLossTotal = addLossTotal;
	}

	public double getLossRate() {
		return lossRate;
	}

	public void setLossRate(double lossRate) {
		this.lossRate = lossRate;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

}
