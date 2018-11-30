package com.qftx.tsm.email.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qftx.common.domain.BaseObject;

import java.util.Date;

/***
 * 邮箱发送记录
 * @author: zwj
 * @since: 2015-12-9  下午5:26:28
 * @history: 4.x
 */
public class TsmEmailSendLog extends BaseObject {
	private static final long serialVersionUID = 5604877776552733407L;

	private String id;
	private String orgId; // 机构ID
	private String emailSendId; // 邮件发送ID
	private Integer status; // 发送状态1，发送成功，0发送失败
	private Date inputTime;
	private Date updateTime;
	private Integer isDel;
	private String emailReviceUser; // 接收人
	
	
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
	public String getEmailSendId() {
		return emailSendId;
	}
	public void setEmailSendId(String emailSendId) {
		this.emailSendId = emailSendId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getInputTime() {
		return inputTime;
	}
	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	public String getEmailReviceUser() {
		return emailReviceUser;
	}
	public void setEmailReviceUser(String emailReviceUser) {
		this.emailReviceUser = emailReviceUser;
	}
		
}