package com.qftx.pay.api;

public class PayApiRequest {
	private String orgId;	//单位ID
	private String userAcc;	//用户账号
	private PayApiTypeEnum payApiEnum;	//交易类型枚举
	private String desc;	//desc详情描述 回款金额到达XXX元
	private Double money;	//奖励金额
	private String data;    //奖励依据Json数据
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getUserAcc() {
		return userAcc;
	}
	public void setUserAcc(String userAcc) {
		this.userAcc = userAcc;
	}
	public PayApiTypeEnum getPayApiEnum() {
		return payApiEnum;
	}
	public void setPayApiEnum(PayApiTypeEnum payApiEnum) {
		this.payApiEnum = payApiEnum;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
}
