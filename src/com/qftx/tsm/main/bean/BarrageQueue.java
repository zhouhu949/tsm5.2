package com.qftx.tsm.main.bean;

import com.qftx.common.domain.BaseObject;

public class BarrageQueue extends BaseObject{
	private static final long serialVersionUID = -723352464263501485L;
	private String barrType; //消息类型
	private String content;  //弹幕内容
	
	private String imgUrl;   //头像地址
	private String userAccount;   //
	private String userName;   //
	
	private Integer showType;//默认为0,1为生日,2为荣誉榜
	
	private String id;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getShowType() {
		return showType;
	}
	public void setShowType(Integer showType) {
		this.showType = showType;
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
	public String getBarrType() {
		return barrType;
	}
	public void setBarrType(String barrType) {
		this.barrType = barrType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	

}
