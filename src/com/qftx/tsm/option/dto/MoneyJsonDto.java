package com.qftx.tsm.option.dto;

/***
 * 销售管理设置中 回款金额弹幕 
 */
public class MoneyJsonDto {

	private String minM; //  最小金额 【订单金额不小于....使用这个变量】
	private String maxM; // 最大金额
	private String redPacket; // 红包额度设置值 如：0,8,18.......
	
	public String getMinM() {
		return minM;
	}
	public void setMinM(String minM) {
		this.minM = minM;
	}
	public String getMaxM() {
		return maxM;
	}
	public void setMaxM(String maxM) {
		this.maxM = maxM;
	}
	public String getRedPacket() {
		return redPacket;
	}
	public void setRedPacket(String redPacket) {
		this.redPacket = redPacket;
	}
	
}
