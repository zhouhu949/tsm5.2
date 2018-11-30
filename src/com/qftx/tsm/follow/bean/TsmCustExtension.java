package com.qftx.tsm.follow.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;

/**
 * 客户跟进延期表
 *
 */
public class TsmCustExtension extends BaseObject{
	private static final long serialVersionUID = 1L;
	
    private String extensionId;                   //延期ID
    private String custId;                         //客户ID
    private Short daysExtension;             //延期天数
    private String reasonsExtension;              //延期原因
    private String applicantExtensionId;         //延期申请人ID
    private Date applicationTimeExtension;       //延期申请时间
    private Short statusExtended;            //申请延期状态：0-未通过;1-已通过;2-待审核
    private Date inputDate;                       //录入时间
    private String inputAcc;                      //录入人
    private Date updateDate;                      //修改时间
    private String updateAcc;                     //修改人
    private String reviewerId;                    //审核人ID
    private String reviewerName;                  //审核人姓名
    private String applicantExtensionName;       //延期申请人姓名
    private Date reviewerTime;                    //审核时间
    private String orgId;								 // 机构ID（4.0新增）
	public String getExtensionId() {
		return extensionId;
	}
	public void setExtensionId(String extensionId) {
		this.extensionId = extensionId;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public Short getDaysExtension() {
		return daysExtension;
	}
	public void setDaysExtension(Short daysExtension) {
		this.daysExtension = daysExtension;
	}
	public String getReasonsExtension() {
		return reasonsExtension;
	}
	public void setReasonsExtension(String reasonsExtension) {
		this.reasonsExtension = reasonsExtension;
	}
	public String getApplicantExtensionId() {
		return applicantExtensionId;
	}
	public void setApplicantExtensionId(String applicantExtensionId) {
		this.applicantExtensionId = applicantExtensionId;
	}
	public Date getApplicationTimeExtension() {
		return applicationTimeExtension;
	}
	public void setApplicationTimeExtension(Date applicationTimeExtension) {
		this.applicationTimeExtension = applicationTimeExtension;
	}
	public Short getStatusExtended() {
		return statusExtended;
	}
	public void setStatusExtended(Short statusExtended) {
		this.statusExtended = statusExtended;
	}
	public Date getInputDate() {
		return inputDate;
	}
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}
	public String getInputAcc() {
		return inputAcc;
	}
	public void setInputAcc(String inputAcc) {
		this.inputAcc = inputAcc;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getUpdateAcc() {
		return updateAcc;
	}
	public void setUpdateAcc(String updateAcc) {
		this.updateAcc = updateAcc;
	}
	public String getReviewerId() {
		return reviewerId;
	}
	public void setReviewerId(String reviewerId) {
		this.reviewerId = reviewerId;
	}
	public String getReviewerName() {
		return reviewerName;
	}
	public void setReviewerName(String reviewerName) {
		this.reviewerName = reviewerName;
	}
	public String getApplicantExtensionName() {
		return applicantExtensionName;
	}
	public void setApplicantExtensionName(String applicantExtensionName) {
		this.applicantExtensionName = applicantExtensionName;
	}
	public Date getReviewerTime() {
		return reviewerTime;
	}
	public void setReviewerTime(Date reviewerTime) {
		this.reviewerTime = reviewerTime;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
}

  
