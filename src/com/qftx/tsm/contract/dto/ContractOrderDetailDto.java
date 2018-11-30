package com.qftx.tsm.contract.dto;

import com.qftx.tsm.contract.bean.ContractOrderDetailBean;

import java.util.Date;
import java.util.List;

public class ContractOrderDetailDto extends ContractOrderDetailBean {

	private static final long serialVersionUID = -8583660057280498937L;
	
	private String custName;
	private String orderCode;
	private Date effectiveDate;
	private Date invalidDate;
	private String userName;
	private String adminAcc;
	private String userId;
	private Date tradeDate;
	private String queryText;
	private Integer sDateType;
	private String userIdsStr;
	private String custId;
	private List<String> userIds;
	private String queryType;
	private String company;
	private String osType;
	private String isTP;//1-来弹屏
	private String ownerAcc;
	private List<String> ownerAccs;
	private String ownerAccsStr;
	private String commonAcc;
	private String noteType;
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public Date getInvalidDate() {
		return invalidDate;
	}
	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAdminAcc() {
		return adminAcc;
	}
	public void setAdminAcc(String adminAcc) {
		this.adminAcc = adminAcc;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}
	public String getQueryText() {
		return queryText;
	}
	public void setQueryText(String queryText) {
		this.queryText = queryText;
	}
	public Integer getsDateType() {
		return sDateType;
	}
	public void setsDateType(Integer sDateType) {
		this.sDateType = sDateType;
	}
	public String getUserIdsStr() {
		return userIdsStr;
	}
	public void setUserIdsStr(String userIdsStr) {
		this.userIdsStr = userIdsStr;
	}
	public List<String> getUserIds() {
		return userIds;
	}
	public void setUserIds(List<String> userIds) {
		this.userIds = userIds;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getOsType() {
		return osType;
	}
	public void setOsType(String osType) {
		this.osType = osType;
	}
	public String getIsTP() {
		return isTP;
	}
	public void setIsTP(String isTP) {
		this.isTP = isTP;
	}
	public String getOwnerAcc() {
		return ownerAcc;
	}
	public void setOwnerAcc(String ownerAcc) {
		this.ownerAcc = ownerAcc;
	}
	public List<String> getOwnerAccs() {
		return ownerAccs;
	}
	public void setOwnerAccs(List<String> ownerAccs) {
		this.ownerAccs = ownerAccs;
	}
	public String getOwnerAccsStr() {
		return ownerAccsStr;
	}
	public void setOwnerAccsStr(String ownerAccsStr) {
		this.ownerAccsStr = ownerAccsStr;
	}
	public String getCommonAcc() {
		return commonAcc;
	}
	public void setCommonAcc(String commonAcc) {
		this.commonAcc = commonAcc;
	}
	public String getNoteType() {
		return noteType;
	}
	public void setNoteType(String noteType) {
		this.noteType = noteType;
	}
}
