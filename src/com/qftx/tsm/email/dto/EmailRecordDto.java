package com.qftx.tsm.email.dto;

import com.qftx.tsm.email.bean.TsmEmailSend;

import java.util.List;

/** 邮件记录 */
public class EmailRecordDto extends TsmEmailSend{
	
	private static final long serialVersionUID = 1L;
	
	private String emailReviceUser; // 接收人
	private String sendLogId;	// 发送日志ID
	private List<String> ownerAccs; // 所有者集合
	private String accs;
	private String ownerAcc;
	private Integer roleType; // 0:普通销售，1：管理者
	private String osType;//所有者查询方式 1-全部 2-只看自己 3-选中查询
	
	public String getEmailReviceUser() {
		return emailReviceUser;
	}

	public void setEmailReviceUser(String emailReviceUser) {
		this.emailReviceUser = emailReviceUser;
	}

	public List<String> getOwnerAccs() {
		return ownerAccs;
	}

	public void setOwnerAccs(List<String> ownerAccs) {
		this.ownerAccs = ownerAccs;
	}

	public String getAccs() {
		return accs;
	}

	public void setAccs(String accs) {
		this.accs = accs;
	}

	public String getOwnerAcc() {
		return ownerAcc;
	}

	public void setOwnerAcc(String ownerAcc) {
		this.ownerAcc = ownerAcc;
	}

	public Integer getRoleType() {
		return roleType;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

	public String getSendLogId() {
		return sendLogId;
	}

	public void setSendLogId(String sendLogId) {
		this.sendLogId = sendLogId;
	}

	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}	
	
}
