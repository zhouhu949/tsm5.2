package com.qftx.tsm.report.dto;

import com.qftx.common.domain.BaseObject;

public class CustInfoNalysisDto extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3912526247244539666L;

	private String resGroupId;
	private String groupName;
	private Integer resTotal;
	private Integer willTotal;
	private Integer signTotal;
	private double willRate;
	private double signRate;
	private Integer calledTotal;
	private double calledRate;
	private Integer effectCallTotal;
	private double effectCallRate;
	private String resId;
	private Integer callTotal;
	private String orgId;
	public String getResGroupId() {
		return resGroupId;
	}
	public void setResGroupId(String resGroupId) {
		this.resGroupId = resGroupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Integer getResTotal() {
		return resTotal;
	}
	public void setResTotal(Integer resTotal) {
		this.resTotal = resTotal;
	}
	public Integer getWillTotal() {
		return willTotal;
	}
	public void setWillTotal(Integer willTotal) {
		this.willTotal = willTotal;
	}
	public Integer getSignTotal() {
		return signTotal;
	}
	public void setSignTotal(Integer signTotal) {
		this.signTotal = signTotal;
	}
	public double getWillRate() {
		return willRate;
	}
	public void setWillRate(double willRate) {
		this.willRate = willRate;
	}
	public double getSignRate() {
		return signRate;
	}
	public void setSignRate(double signRate) {
		this.signRate = signRate;
	}
	public Integer getCalledTotal() {
		return calledTotal;
	}
	public void setCalledTotal(Integer calledTotal) {
		this.calledTotal = calledTotal;
	}
	public double getCalledRate() {
		return calledRate;
	}
	public void setCalledRate(double calledRate) {
		this.calledRate = calledRate;
	}
	public Integer getEffectCallTotal() {
		return effectCallTotal;
	}
	public void setEffectCallTotal(Integer effectCallTotal) {
		this.effectCallTotal = effectCallTotal;
	}
	public double getEffectCallRate() {
		return effectCallRate;
	}
	public void setEffectCallRate(double effectCallRate) {
		this.effectCallRate = effectCallRate;
	}
	public String getResId() {
		return resId;
	}
	public void setResId(String resId) {
		this.resId = resId;
	}
	public Integer getCallTotal() {
		return callTotal;
	}
	public void setCallTotal(Integer callTotal) {
		this.callTotal = callTotal;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	

}
