package com.qftx.tsm.message.bean;

import com.qftx.common.domain.BaseObject;
/*
 * 发送消息
 * */
public class SendMessageBean extends BaseObject{
	private String ogrId;  
	private String account;
	private String title;   //标题
	private String content; //内容
	private String type;    //24-系统维护消息，25-钱包消息 ，26-其他消息，27-系统版本说明
	private String retCode; //返回数据类型：100-消息发送成功。101-消息发送失败
	private String retMessage;//返回消息内容
	
	
	
	public String getOgrId() {
		return ogrId;
	}
	public void setOgrId(String ogrId) {
		this.ogrId = ogrId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRetCode() {
		return retCode;
	}
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}
	public String getRetMessage() {
		return retMessage;
	}
	public void setRetMessage(String retMessage) {
		this.retMessage = retMessage;
	}
	
}
