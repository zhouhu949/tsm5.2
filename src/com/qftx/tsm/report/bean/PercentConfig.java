package com.qftx.tsm.report.bean;
/*行动完成率配置*/
public class PercentConfig {
	private boolean open = false;
	private boolean callInTime = true; //呼入时长
	private boolean callOutTime = true;//呼出时长
	private int basicNum = 100;		//通话次数
	private int basicTime = 200;	//通话时长
	
	private double timeXishu =1;	//时长系数
	private double numXishu =1;		//次数系数
	public boolean isOpen() {
		return open;
	}
	public int getBasicNum() {
		return basicNum;
	}
	public int getBasicTime() {
		return basicTime;
	}
	public double getTimeXishu() {
		return timeXishu;
	}
	public double getNumXishu() {
		return numXishu;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	public void setBasicNum(int basicNum) {
		this.basicNum = basicNum;
	}
	public void setBasicTime(int basicTime) {
		this.basicTime = basicTime;
	}
	public void setTimeXishu(double timeXishu) {
		this.timeXishu = timeXishu;
	}
	public void setNumXishu(double numXishu) {
		this.numXishu = numXishu;
	}
	public boolean isCallInTime() {
		return callInTime;
	}
	public boolean isCallOutTime() {
		return callOutTime;
	}
	public void setCallInTime(boolean callInTime) {
		this.callInTime = callInTime;
	}
	public void setCallOutTime(boolean callOutTime) {
		this.callOutTime = callOutTime;
	}
	
}
