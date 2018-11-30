package com.qftx.tsm.follow.dto;

import com.qftx.tsm.follow.bean.TsmCustExtension;

import java.util.List;


/**
 * 客户延期审核列表DTO
 *
 */
public class TsmCustExtensionDto extends TsmCustExtension{
	private static final long serialVersionUID = 1L;
	
	private String custId;     //客户编号
	private String custName;   //客户姓名
	private String company;    //单位名称
	private String optionId;   //销售进程编号
	private String optionName; //销售进程 
	private String isMajor;    //是否重点关注
	private String itemCode;   //销售进程编号
	private String orgId;      //机构id
	private String ownerAcc;
	
	private String auditStartDate;   //审核开始时间
	private String auditEndDate;     //审核结束时间
	private String queryText; // 模糊查询
	
	private String showApplicationTimeExtension; // 显示延期申请时间
	private String showReviewerTime; // 显示审核时间
	private String queryType; // 查询类型
	private List<String> custIds;
	
	private Integer state;
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getOptionName() {
		return optionName;
	}
	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getOptionId() {
		return optionId;
	}
	public void setOptionId(String optionId) {
		this.optionId = optionId;
	}
	public String getIsMajor() {
		return isMajor;
	}
	public void setIsMajor(String isMajor) {
		this.isMajor = isMajor;
	}
	public String getAuditStartDate() {
		return auditStartDate;
	}
	public void setAuditStartDate(String auditStartDate) {
		this.auditStartDate = auditStartDate;
	}
	public String getAuditEndDate() {
		return auditEndDate;
	}
	public void setAuditEndDate(String auditEndDate) {
		this.auditEndDate = auditEndDate;
	}
	public String getOwnerAcc() {
		return ownerAcc;
	}
	public void setOwnerAcc(String ownerAcc) {
		this.ownerAcc = ownerAcc;
	}
	public String getQueryText() {
		return queryText;
	}
	public void setQueryText(String queryText) {
		this.queryText = queryText;
	}
	public String getShowApplicationTimeExtension() {
		return showApplicationTimeExtension;
	}
	public void setShowApplicationTimeExtension(String showApplicationTimeExtension) {
		this.showApplicationTimeExtension = showApplicationTimeExtension;
	}
	public String getShowReviewerTime() {
		return showReviewerTime;
	}
	public void setShowReviewerTime(String showReviewerTime) {
		this.showReviewerTime = showReviewerTime;
	}
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public List<String> getCustIds() {
		return custIds;
	}
	public void setCustIds(List<String> custIds) {
		this.custIds = custIds;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	
	
}

  
