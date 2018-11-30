package com.qftx.pay.api;

public enum PayApiTypeEnum {
	BIRTHDAY(1,"生日红包"),
	SIGN(2,"签约红包"),
	MONEY(3,"回款红包"),
	TOP_SING_MONEY(4,"月度冠军-签约金额"),
	TOP_WILL_ADD(5,"月度冠军-新增意向"),
	TOP_SIGN_ADD(6,"月度冠军-新增签约"),
	TOP_CALL_NUM(7,"月度冠军-呼出次数"),
	TOP_CALL_LENGTH(8,"月度冠军-呼出时长");
	
	private int type;
	private String desc;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	private PayApiTypeEnum(int type, String desc) {
		this.type = type;
		this.desc = desc;
	}
}
