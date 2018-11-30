package com.qftx.tsm.sms.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;

 /** 
 * 来电短信发送记录
 */
public class TsmCallSendSms extends BaseObject {

	private static final long serialVersionUID = 8304667994166148963L;
	private String smsId;                //短信ID
	private String smsComment;           //短信内容
	private Integer smsNumber;           //短信条数
	private Date sendDate;               //发送时间 
	private Short isDel;                 //是否删除：1-删除，0-未删除
	private String orgId;                //机构ID
	private String submitStatus;         //提交状态
	private String account;				 //账号
 	private Integer type;  // 4.0新增 短信类型（0普通短信，1挂机短信）
    private Date callDate; // 来电时间
    private String name; // 接收人姓名
    private String callerNum; // 主叫号码
    private String calledNum; // 被叫号码
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
		this.orgId = orgId;
	}
	public String getSubmitStatus() {
		return submitStatus;
	}
	public void setSubmitStatus(String submitStatus) {
		this.submitStatus = submitStatus;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Date getCallDate() {
		return callDate;
	}
	public void setCallDate(Date callDate) {
		this.callDate = callDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCallerNum() {
		return callerNum;
	}
	public void setCallerNum(String callerNum) {
		this.callerNum = callerNum;
	}
	public String getCalledNum() {
		return calledNum;
	}
	public void setCalledNum(String calledNum) {
		this.calledNum = calledNum;
	}
 		
}
