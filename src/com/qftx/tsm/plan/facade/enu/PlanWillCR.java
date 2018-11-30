package com.qftx.tsm.plan.facade.enu;

public enum PlanWillCR {
	TURN_SIGN("转签约", 0),
	TURN_WILL("意向增进", 1), 
	TURN_HIGH_SEA("转公海", 2), 
	NO_CHANGE("意向无变化", 3),
	NO_CONTACT("未联系", 4);
	
    private String result;
    private int number;
	private PlanWillCR(String result, int number) {
		this.result = result;
		this.number = number;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
}
