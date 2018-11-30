package com.qftx.tsm.report.dto;

import com.qftx.common.domain.BaseObject;

public class SilenceCustDto extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5227137070725264261L;
	private String id;
	private String currDate;
	private Integer signTotal;
	private Integer silentTotal;
	private Integer expireCustTotal;
	private Integer addSilentTotal;
	private Double silentRate;
	private String account;
	private Integer wakeTotal;
	private Integer addWakeTotal;
	private Double wakeRate;
	private String deptId;
	private String deptName;
	private Integer lossTotal;
	private Integer addLossTotal;
	private Double lossRate;
	private String orgId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getCurrDate() {
		return currDate;
	}

	public void setCurrDate(String currDate) {
		this.currDate = currDate;
	}

	public Integer getSignTotal() {
		return signTotal;
	}

	public void setSignTotal(Integer signTotal) {
		this.signTotal = signTotal;
	}

	public Integer getSilentTotal() {
		return silentTotal;
	}

	public void setSilentTotal(Integer silentTotal) {
		this.silentTotal = silentTotal;
	}

	public Integer getExpireCustTotal() {
		return expireCustTotal;
	}

	public void setExpireCustTotal(Integer expireCustTotal) {
		this.expireCustTotal = expireCustTotal;
	}

	public Integer getAddSilentTotal() {
		return addSilentTotal;
	}

	public void setAddSilentTotal(Integer addSilentTotal) {
		this.addSilentTotal = addSilentTotal;
	}

	public Double getSilentRate() {
		return silentRate;
	}

	public void setSilentRate(Double silentRate) {
		this.silentRate = silentRate;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Integer getWakeTotal() {
		return wakeTotal;
	}

	public void setWakeTotal(Integer wakeTotal) {
		this.wakeTotal = wakeTotal;
	}

	public Integer getAddWakeTotal() {
		return addWakeTotal;
	}

	public void setAddWakeTotal(Integer addWakeTotal) {
		this.addWakeTotal = addWakeTotal;
	}

	public Double getWakeRate() {
		return wakeRate;
	}

	public void setWakeRate(Double wakeRate) {
		this.wakeRate = wakeRate;
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

	public Integer getLossTotal() {
		return lossTotal;
	}

	public void setLossTotal(Integer lossTotal) {
		this.lossTotal = lossTotal;
	}

	public Integer getAddLossTotal() {
		return addLossTotal;
	}

	public void setAddLossTotal(Integer addLossTotal) {
		this.addLossTotal = addLossTotal;
	}

	public Double getLossRate() {
		return lossRate;
	}

	public void setLossRate(Double lossRate) {
		this.lossRate = lossRate;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

}
