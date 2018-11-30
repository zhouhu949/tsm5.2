package com.qftx.tsm.cust.dto;

import com.qftx.common.domain.BaseObject;
import com.qftx.tsm.callrecord.dto.TsmRecordCallBean;
import com.qftx.tsm.follow.dto.CustFollowCallDto;

import java.util.Date;
import java.util.List;


 /** 
 * 客户卡片 行动记录 实体类
 * @author: lixing
 * @since: 2016年8月11日  上午11:39:25
 * @history:
 */
public class ResCustActionDto extends BaseObject {
	private static final long serialVersionUID = 2020156493134866679L;

	private String custId;
	
	private String orgId;
	
	private Date inputDate;
	
	private String actionType;
	
	private Integer followType;
	
	private String remark;
	
	private Date nextConcatTime;
	
	private String labels;
	
	private String custFollowId;
	
	private String saleProcessId;
	
	private String saleProcessName;
	
	private String inputAcc;
	
	private String inputName;
	
	private String mainLinkman;
	
	private String telphone;

	private List<TsmRecordCallBean> records;

	private List<CustFollowCallDto> urlList;
	
	private Integer effectiveness;

	private String feedbackImg;//跟进图片,用,号隔开

	 public String getFeedbackImg() {
		 return feedbackImg;
	 }

	 public void setFeedbackImg(String feedbackImg) {
		 this.feedbackImg = feedbackImg;
	 }

	 public String getCustId() {
		return custId;
	}

	public String getOrgId() {
		return orgId;
	}

	public Date getInputDate() {
		return inputDate;
	}

	public String getActionType() {
		return actionType;
	}

	public Integer getFollowType() {
		return followType;
	}

	public String getRemark() {
		return remark;
	}

	public Date getNextConcatTime() {
		return nextConcatTime;
	}

	public String getLabels() {
		return labels;
	}

	public String getCustFollowId() {
		return custFollowId;
	}

	public String getSaleProcessId() {
		return saleProcessId;
	}

	public String getSaleProcessName() {
		return saleProcessName;
	}

	public String getInputAcc() {
		return inputAcc;
	}

	public String getInputName() {
		return inputName;
	}

	public String getMainLinkman() {
		return mainLinkman;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public void setFollowType(Integer followType) {
		this.followType = followType;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setNextConcatTime(Date nextConcatTime) {
		this.nextConcatTime = nextConcatTime;
	}

	public void setLabels(String labels) {
		this.labels = labels;
	}

	public void setCustFollowId(String custFollowId) {
		this.custFollowId = custFollowId;
	}

	public void setSaleProcessId(String saleProcessId) {
		this.saleProcessId = saleProcessId;
	}

	public void setSaleProcessName(String saleProcessName) {
		this.saleProcessName = saleProcessName;
	}

	public void setInputAcc(String inputAcc) {
		this.inputAcc = inputAcc;
	}

	public void setInputName(String inputName) {
		this.inputName = inputName;
	}

	public void setMainLinkman(String mainLinkman) {
		this.mainLinkman = mainLinkman;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	
	public List<TsmRecordCallBean> getRecords() {
		return records;
	}

	public void setRecords(List<TsmRecordCallBean> records) {
		this.records = records;
	}

	public List<CustFollowCallDto> getUrlList() {
		return urlList;
	}

	public void setUrlList(List<CustFollowCallDto> urlList) {
		this.urlList = urlList;
	}

	public Integer getEffectiveness() {
		return effectiveness;
	}

	public void setEffectiveness(Integer effectiveness) {
		this.effectiveness = effectiveness;
	}
	
}
