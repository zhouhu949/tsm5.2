package com.qftx.tsm.sys.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qftx.common.domain.BaseObject;

import java.util.Date;

/**
 * 点评
 * 
 * @author: WUWEI
 * @since: 2015年11月13日 下午1:21:41
 * @history:
 */
public class TsmCustReview extends BaseObject {
	private static final long serialVersionUID = 1L;

	private String reviewId; // 点评ID
	private String retaId; // 客户跟进ID/录音ID
	private String revComment; // 点评内容
	private Date reviewDate; // 点评时间
	private String reviewAcc; // 点评人
	private String reviewName; // 点评人姓名
	private String recordExampId; // 录音示范类型ID
	private String orgId; // 机构编号
	private Date retaTime; // 行动时间/录音时间
	private String recordUrl; // 录音地址
	private String custId; // 客户ID
	private String ownerAcc; // 所有者账号
	private Integer timeLengh; // 通话时长秒
	private String name; // 客户姓名
	private String company; // 单位
	private String salesProcess; // 销售进程
	private Integer isRead; // 是否已读
	private String phone; // 被叫号码
	private Integer minDate; // 消息中，点评时间为15天内用
	private Integer isDel;
	private Integer type;// 1-本地上传范例；2-通话列表点评范例
	private String callCode;// 通话列表添加范例时播放参数
	private String callD;// 通话列表添加范例时播放参数

	public String getReviewId() {
		return reviewId;
	}

	public String getCallCode() {
		return callCode;
	}

	public void setCallCode(String callCode) {
		this.callCode = callCode;
	}

	public String getCallD() {
		return callD;
	}

	public void setCallD(String callD) {
		this.callD = callD;
	}

	public void setReviewId(String reviewId) {
		this.reviewId = reviewId == null ? null : reviewId.trim();
	}

	public String getRetaId() {
		return retaId;
	}

	public void setRetaId(String retaId) {
		this.retaId = retaId == null ? null : retaId.trim();
	}

	public String getRevComment() {
		return revComment;
	}

	public void setRevComment(String revComment) {
		this.revComment = revComment == null ? null : revComment.trim();
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mi:ss", timezone = "GMT+8")
	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	public String getReviewAcc() {
		return reviewAcc;
	}

	public void setReviewAcc(String reviewAcc) {
		this.reviewAcc = reviewAcc == null ? null : reviewAcc.trim();
	}

	public String getRecordExampId() {
		return recordExampId;
	}

	public void setRecordExampId(String recordExampId) {
		this.recordExampId = recordExampId == null ? null : recordExampId.trim();
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public Date getRetaTime() {
		return retaTime;
	}

	public void setRetaTime(Date retaTime) {
		this.retaTime = retaTime;
	}

	public String getRecordUrl() {
		return recordUrl;
	}

	public void setRecordUrl(String recordUrl) {
		this.recordUrl = recordUrl;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getOwnerAcc() {
		return ownerAcc;
	}

	public void setOwnerAcc(String ownerAcc) {
		this.ownerAcc = ownerAcc;
	}

	public Integer getTimeLengh() {
		return timeLengh;
	}

	public void setTimeLengh(Integer timeLengh) {
		this.timeLengh = timeLengh;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company == null ? null : company.trim();
	}

	public String getSalesProcess() {
		return salesProcess;
	}

	public void setSalesProcess(String salesProcess) {
		this.salesProcess = salesProcess;
	}

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone == null ? null : phone.trim();
	}

	public Integer getMinDate() {
		return minDate;
	}

	public void setMinDate(Integer minDate) {
		this.minDate = minDate;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public String getReviewName() {
		return reviewName;
	}

	public void setReviewName(String reviewName) {
		this.reviewName = reviewName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}