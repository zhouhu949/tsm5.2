package com.qftx.tsm.sms.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;

 /** 
 *短信发送记录
 */
public class TsmSendSms extends BaseObject {

	private static final long serialVersionUID = -4794653631151509910L;

	private String smsId;                //短信ID
	private String smsComment;           //短信内容
	private Integer smsNumber;           //短信条数
	private Date sendDate;               //发送时间 
	private Short isDel;                 //是否删除：1-删除，0-未删除
	private String orgId;                //机构ID
	private String submitStatus;         //提交状态
	private String account;				 //账号
	private String signature; // 签名
	private String resendId; // 服务端重发Id
	private String oldSmsId; // 原smsId
 	private Integer type;  // 4.0新增 短信类型（0普通短信，1挂机短信）
 	private String hookTempId; // 挂机短信模板Id (主要用于 之后挂机短信统计)
 	
	public String getSmsId() {
		return smsId;
	}
	public void setSmsId(String smsId) {
		this.smsId = smsId;
	}
	public String getSmsComment() {
		return smsComment;
	}
	public void setSmsComment(String smsComment) {
		this.smsComment = smsComment;
	}
	public Integer getSmsNumber() {
		return smsNumber;
	}
	public void setSmsNumber(Integer smsNumber) {
		this.smsNumber = smsNumber;
	}
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public Short getIsDel() {
		return isDel;
	}
	public void setIsDel(Short isDel) {
		this.isDel = isDel;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId == null ? null : orgId.trim();
	}
	public void setSubmitStatus(String submitStatus) {
		this.submitStatus = submitStatus;
	}
	public String getSubmitStatus() {
		return submitStatus;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getResendId() {
		return resendId;
	}
	public void setResendId(String resendId) {
		this.resendId = resendId;
	}
	public String getOldSmsId() {
		return oldSmsId;
	}
	public void setOldSmsId(String oldSmsId) {
		this.oldSmsId = oldSmsId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getHookTempId() {
		return hookTempId;
	}
	public void setHookTempId(String hookTempId) {
		this.hookTempId = hookTempId;
	}

}
