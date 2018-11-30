package com.qftx.tsm.phonechoose.dto;

import com.qftx.tsm.phonechoose.bean.PhoneChooseTaskBean;

public class PhoneChooseTaskBeanDto extends PhoneChooseTaskBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String symbol;//查询 大于小于符号        <&lt;   >&gt;         
	private String type; // first  向上，last 向下
	private String impTime;
	private String screenReson;    //筛查失败原因
	private String ownerAcc;		//所有者
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getScreenReson() {
		return screenReson;
	}
	public void setScreenReson(String screenReson) {
		this.screenReson = screenReson;
	}
	public String getImpTime() {
		return impTime;
	}
	public void setImpTime(String impTime) {
		this.impTime = impTime;
	}
	public String getOwnerAcc() {
		return ownerAcc;
	}
	public void setOwnerAcc(String ownerAcc) {
		this.ownerAcc = ownerAcc;
	}
	
}
