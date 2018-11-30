package com.qftx.tsm.cust.dto;

import com.qftx.tsm.cust.bean.CustOptorBean;

import java.util.Date;
import java.util.List;

public class CustOptorDto extends CustOptorBean {

	private static final long serialVersionUID = -5230355143233749037L;

	private String optionName;
	private String userName;
	private String custName;
	private String mobilephone;
	private String telphone;
	private Integer isMajor;
	private Date signDate;
	private Integer transferType;
	private String optoerName;
	private String custTypeId;
	private String custTypeName;
	private String queryText;
	private String mainLinkman;
	private Integer nDateType;
	private String ownerAccsStr;
	private String shareAcc;
	private List<String> ownerAccs;
	private boolean queryPhone;
	private String queryType;
	private String osType;
	private List<String> custIds;
	
	public List<String> getCustIds() {
		return custIds;
	}
	public void setCustIds(List<String> custIds) {
		this.custIds = custIds;
	}
	public String getOptionName() {
		return optionName;
	}
	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getMobilephone() {
		return mobilephone;
	}
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	public Integer getIsMajor() {
		return isMajor;
	}
	public void setIsMajor(Integer isMajor) {
		this.isMajor = isMajor;
	}
	public Date getSignDate() {
		return signDate;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	public Integer getTransferType() {
		return transferType;
	}
	public void setTransferType(Integer transferType) {
		this.transferType = transferType;
	}
	public String getOptoerName() {
		return optoerName;
	}
	public void setOptoerName(String optoerName) {
		this.optoerName = optoerName;
	}
	public String getCustTypeId() {
		return custTypeId;
	}
	public void setCustTypeId(String custTypeId) {
		this.custTypeId = custTypeId;
	}
	public String getCustTypeName() {
		return custTypeName;
	}
	public void setCustTypeName(String custTypeName) {
		this.custTypeName = custTypeName;
	}
	public String getQueryText() {
		return queryText;
	}
	public void setQueryText(String queryText) {
		this.queryText = queryText;
	}
	public String getMainLinkman() {
		return mainLinkman;
	}
	public void setMainLinkman(String mainLinkman) {
		this.mainLinkman = mainLinkman;
	}
	public Integer getnDateType() {
		return nDateType;
	}
	public void setnDateType(Integer nDateType) {
		this.nDateType = nDateType;
	}
	public String getOwnerAccsStr() {
		return ownerAccsStr;
	}
	public boolean isQueryPhone() {
		return queryPhone;
	}
	public void setQueryPhone(boolean queryPhone) {
		this.queryPhone = queryPhone;
	}
	public String getShareAcc() {
		return shareAcc;
	}
	public String getOsType() {
		return osType;
	}
	public void setOsType(String osType) {
		this.osType = osType;
	}
	public void setShareAcc(String shareAcc) {
		this.shareAcc = shareAcc;
	}
	public List<String> getOwnerAccs() {
		return ownerAccs;
	}
	public void setOwnerAccs(List<String> ownerAccs) {
		this.ownerAccs = ownerAccs;
	}
	public void setOwnerAccsStr(String ownerAccsStr) {
		this.ownerAccsStr = ownerAccsStr;
	}
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	
}
