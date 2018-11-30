package com.qftx.tsm.main.dto;

public class BrankingDto extends RankReportDto{
	
	private static final long serialVersionUID = 3017447882634883124L;
	
	private String imgUrl; //头像地址
	private String cardType; //弹幕类型：1：生日弹屏 ，2：排行榜 ，3：签约
	private String content; //弹幕内容
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	

	
	
	

}
