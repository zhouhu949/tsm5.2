package com.qftx.tsm.email.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;

public class MailboxCheckBean extends BaseObject {
	private static final long serialVersionUID = 8102074147719039716L;
	
	private String id;//主键
	private String account;//帐号
	private String orgId;//单位ID
	private Integer type;//类型（0-邮箱绑定，1-更换邮箱）
	private String code;//验证码
	private String email;//邮箱
	private String password;//密码
	private Date inputtime;//创建时间
	private Integer isDel;//是否删除（0-否，1-是）
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getAccount() {
		return account;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}
	
	public String getOrgId() {
		return orgId;
	}
	
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	public Integer getType() {
		return type;
	}
	
	public void setType(Integer type) {
		this.type = type;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Date getInputtime() {
		return inputtime;
	}
	
	public void setInputtime(Date inputtime) {
		this.inputtime = inputtime;
	}
	
	public Integer getIsDel() {
		return isDel;
	}
	
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	
}
