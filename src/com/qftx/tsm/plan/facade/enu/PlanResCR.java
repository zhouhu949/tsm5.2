package com.qftx.tsm.plan.facade.enu;

public enum PlanResCR {
	TURN_SIGN("转签约", 0),
	TURN_WILL("转意向", 1), 
	TURN_HIGH_SEA("转公海", 2), 
	NO_CHANGE("联系无变化", 3),
	NO_CONTACT("未联系", 4);

	private String result;
    private int number;
    
	private PlanResCR(String result, int number) {
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
