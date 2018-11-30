package com.qftx.tsm.plan.group.month.dto;


public class ResAnaly {
	private Double contracAmount; //新增合同金额
	private int callTime; //通话时长
	
	private int resNum;//联系资源
	private int willNum;//转意向
	private int signNum;//转签约
	
	public Double getContracAmount() {
		return contracAmount;
	}
	public void setContracAmount(Double contracAmount) {
		this.contracAmount = contracAmount;
	}
	public int getCallTime() {
		return callTime;
	}
	public void setCallTime(int callTime) {
		this.callTime = callTime;
	}
	public int getResNum() {
		return resNum;
	}
	public void setResNum(int resNum) {
		this.resNum = resNum;
	}
	public int getWillNum() {
		return willNum;
	}
	public void setWillNum(int willNum) {
		this.willNum = willNum;
	}
	public int getSignNum() {
		return signNum;
	}
	public void setSignNum(int signNum) {
		this.signNum = signNum;
	}
}
