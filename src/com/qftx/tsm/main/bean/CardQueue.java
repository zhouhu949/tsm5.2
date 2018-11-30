package com.qftx.tsm.main.bean;

import com.qftx.common.domain.BaseObject;

public class CardQueue extends BaseObject{
	private static final long serialVersionUID = -9134522569934724303L;
	private String cardType;  //类型： 1：生日 ，2：排行榜，3：签约
	private String content; //内容
	private String imgUrl;   //头像地址
	private String userAccount;   //
	private String userName;   //
	
	private Integer showType;//默认为0,1为生日,2为荣誉榜
	
	private Integer ifShow;//是否展示
	
	private String money ;
	
	private Integer moneyIfShow;
	
	private String id;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public Integer getMoneyIfShow() {
		return moneyIfShow;
	}
	public void setMoneyIfShow(Integer moneyIfShow) {
		this.moneyIfShow = moneyIfShow;
	}
	public Integer getIfShow() {
		return ifShow;
	}
	public void setIfShow(Integer ifShow) {
		this.ifShow = ifShow;
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
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getShowType() {
		return showType;
	}
	public void setShowType(Integer showType) {
		this.showType = showType;
	}


}
