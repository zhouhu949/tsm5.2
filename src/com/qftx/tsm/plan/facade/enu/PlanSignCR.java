package com.qftx.tsm.plan.facade.enu;

public enum PlanSignCR {
	TURN_SIGN("续签", 0),
	TURN_SILENCE("转沉默", 1),
	NO_CHANGE("联系无变化", 2),
	NO_CONTACT("未联系", 3);
	
    private String result;
    private int number;
	private PlanSignCR(String result, int number) {
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
