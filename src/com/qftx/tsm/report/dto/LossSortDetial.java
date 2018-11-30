package com.qftx.tsm.report.dto;

import com.qftx.common.domain.BaseObject;

import java.util.Date;

public class LossSortDetial extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8456184575821797389L;
	private String currDate;
	private String name;
	private String linkName;
	private Date signDate;
	private Double money;
	private Integer dayDiff;
	private Date actionDate;
	private Date invalidDate;
	private String ownerAcc;
    private String deptId;
    private String deptName;
	public String getCurrDate() {
		return currDate;
	}

	public void setCurrDate(String currDate) {
		this.currDate = currDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Integer getDayDiff() {
		return dayDiff;
	}

	public void setDayDiff(Integer dayDiff) {
		this.dayDiff = dayDiff;
	}

	public Date getActionDate() {
		return actionDate;
	}

	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}

	public Date getInvalidDate() {
		return invalidDate;
	}

	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
	}

	public String getOwnerAcc() {
		return ownerAcc;
	}

	public void setOwnerAcc(String ownerAcc) {
		this.ownerAcc = ownerAcc;
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
