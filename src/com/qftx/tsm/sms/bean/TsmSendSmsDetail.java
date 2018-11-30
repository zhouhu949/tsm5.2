package com.qftx.tsm.sms.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;


 /** 
 *短信发送详细记录
 */
public class TsmSendSmsDetail extends BaseObject {

	private static final long serialVersionUID = -4482751966092003127L;

	private String	smsId;			//短信发送主ID
	private String	smsDetailId;	//短信发送明细ID
	private String	receiptStatus;	//回执状态：1-回执成功，2-回执失败
	private Date 	sendDate;		//发送时间
	private String	name;			//客户姓名
	private String	mobilePhone;	//客户号码
	private Integer	smsNumber;		//短信条数
	private String batchId;			// 批次ID
	private String submitStatus;	// 提交状态
	private String orgId;                //机构ID
    private String smsContent; // 短信内容
	private Integer type; // 1:挂机短信。
	private String inputAcc;
	private String signAture; // 挂机短信签名
	 
	public String getSmsId() {
		return smsId;
	}
	public void setSmsId(String smsId) {
		this.smsId = smsId;
	}
	public String getSmsDetailId() {
		return smsDetailId;
	}
	public void setSmsDetailId(String smsDetailId) {
		this.smsDetailId = smsDetailId;
	}
	public String getReceiptStatus() {
		return receiptStatus;
	}
	public void setReceiptStatus(String receiptStatus) {
		this.receiptStatus = receiptStatus;
	}
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public Integer getSmsNumber() {
		return smsNumber;
	}
	public void setSmsNumber(Integer smsNumber) {
		this.smsNumber = smsNumber;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getSubmitStatus() {
		return submitStatus;
	}
	public void setSubmitStatus(String submitStatus) {
		this.submitStatus = submitStatus;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getSmsContent() {
		return smsContent;
	}
	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getInputAcc() {
		return inputAcc;
	}
	public void setInputAcc(String inputAcc) {
		this.inputAcc = inputAcc;
	}
	public String getSignAture() {
		return signAture;
	}
	public void setSignAture(String signAture) {
		this.signAture = signAture;
	}
	
}
