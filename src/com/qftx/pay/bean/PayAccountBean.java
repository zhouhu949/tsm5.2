package com.qftx.pay.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qftx.common.domain.BaseObject;

import java.util.Date;
/* pay_account 支付账户*/
public class PayAccountBean extends BaseObject {
	private String id;	//主ID
	private String orgId;	//单位ID
	private String account;	//用户账号
	private String userName;	//用户名(设置交易密码时候的用户名)
	private String tradePwd;	//支付密码
	private String idNumber;	//身份证号码
	private Integer balance;	//余额,单位为分
	private Integer status;	//启用状态:0未启用,1:启用
	private Date inputtime;	//录入时间
	private Date updatetime;	//修改时间
	private Integer isDel;	//册除状态1-删除，0-未删除
	
	private String imgUrl;//用户头像
	private String mobil;//手机号
	private String yuan;//用户余额元
	private String fen;//用户余额分
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTradePwd() {
		return tradePwd;
	}
	public void setTradePwd(String tradePwd) {
		this.tradePwd = tradePwd;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public Integer getBalance() {
		return balance;
	}
	public void setBalance(Integer balance) {
		this.balance = balance;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getInputtime() {
		return inputtime;
	}
	public void setInputtime(Date inputtime) {
		this.inputtime = inputtime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	
	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getMobil() {
		return mobil;
	}

	public void setMobil(String mobil) {
		this.mobil = mobil;
	}

	public String getYuan() {
		if(this.balance!=null){
			yuan = String.valueOf(this.balance.intValue()/100);
		}else{
			yuan = "0";
		}
		return yuan;
	}

	public String getFen() {
		if(this.balance!=null){
			fen = String.valueOf(this.balance.intValue()%100);
			if(fen.length()==1) fen = "0"+fen;
		}else{
			fen = "0";
		}
		return fen;
	}

	public void setYuan(String yuan) {
		this.yuan = yuan;
	}

	public void setFen(String fen) {
		this.fen = fen;
	}
}