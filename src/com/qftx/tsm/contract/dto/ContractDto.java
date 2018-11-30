package com.qftx.tsm.contract.dto;

import com.qftx.tsm.contract.bean.ContractBean;

import java.util.List;

public class ContractDto extends ContractBean {

	private static final long serialVersionUID = -5247763305319663713L;

	private String groupName;//销售团队
	private String startSignDate; //签约 开始时间
	private String endSignDate;//签约 结束时间
	private Integer sDateType;
	private String startEffecDate;
	private String endEffecDate;
	private Integer eDateType;
	private String startInvalidDate;
	private String endInvalidDate;
	private Integer iDateType;
	private String ownerAccsStr;
	private List<String> ownerAccs;
	private String groupIdsStr;
	private List<String> groupIds;
	private String ownerAcc;
	private String adminAcc;
	private String queryText;
	private String mobilephone;
	private String telphone;
	private String mainLinkman;
	private String queryType;
	private String osType;
	private String signAccsStr;
	private List<String> signAccs;
	private String ownerName;
	private String commonAcc; //共有者
	private String noteType;//客户类型 1-我的客户 2-共有客户
	
	private String newCustName;
	
	private String newCompany;
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getStartSignDate() {
		return startSignDate;
	}
	public void setStartSignDate(String startSignDate) {
		this.startSignDate = startSignDate;
	}
	public String getEndSignDate() {
		return endSignDate;
	}
	public void setEndSignDate(String endSignDate) {
		this.endSignDate = endSignDate;
	}
	public String getStartEffecDate() {
		return startEffecDate;
	}
	public void setStartEffecDate(String startEffecDate) {
		this.startEffecDate = startEffecDate;
	}
	public String getEndEffecDate() {
		return endEffecDate;
	}
	public void setEndEffecDate(String endEffecDate) {
		this.endEffecDate = endEffecDate;
	}
	public String getStartInvalidDate() {
		return startInvalidDate;
	}
	public void setStartInvalidDate(String startInvalidDate) {
		this.startInvalidDate = startInvalidDate;
	}
	public String getEndInvalidDate() {
		return endInvalidDate;
	}
	public void setEndInvalidDate(String endInvalidDate) {
		this.endInvalidDate = endInvalidDate;
	}
	public String getOwnerAccsStr() {
		return ownerAccsStr;
	}
	public void setOwnerAccsStr(String ownerAccsStr) {
		this.ownerAccsStr = ownerAccsStr;
	}
	public List<String> getOwnerAccs() {
		return ownerAccs;
	}
	public void setOwnerAccs(List<String> ownerAccs) {
		this.ownerAccs = ownerAccs;
	}
	public String getGroupIdsStr() {
		return groupIdsStr;
	}
	public void setGroupIdsStr(String groupIdsStr) {
		this.groupIdsStr = groupIdsStr;
	}
	public List<String> getGroupIds() {
		return groupIds;
	}
	public void setGroupIds(List<String> groupIds) {
		this.groupIds = groupIds;
	}
	public String getOwnerAcc() {
		return ownerAcc;
	}
	public void setOwnerAcc(String ownerAcc) {
		this.ownerAcc = ownerAcc;
	}
	public String getAdminAcc() {
		return adminAcc;
	}
	public void setAdminAcc(String adminAcc) {
		this.adminAcc = adminAcc;
	}
	public Integer getsDateType() {
		return sDateType;
	}
	public void setsDateType(Integer sDateType) {
		this.sDateType = sDateType;
	}
	public Integer geteDateType() {
		return eDateType;
	}
	public void seteDateType(Integer eDateType) {
		this.eDateType = eDateType;
	}
	public Integer getiDateType() {
		return iDateType;
	}
	public void setiDateType(Integer iDateType) {
		this.iDateType = iDateType;
	}
	public String getQueryText() {
		return queryText;
	}
	public void setQueryText(String queryText) {
		this.queryText = queryText;
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
	public String getMainLinkman() {
		return mainLinkman;
	}
	public void setMainLinkman(String mainLinkman) {
		this.mainLinkman = mainLinkman;
	}
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public String getOsType() {
		return osType;
	}
	public void setOsType(String osType) {
		this.osType = osType;
	}
	public String getSignAccsStr() {
		return signAccsStr;
	}
	public void setSignAccsStr(String signAccsStr) {
		this.signAccsStr = signAccsStr;
	}
	public List<String> getSignAccs() {
		return signAccs;
	}
	public void setSignAccs(List<String> signAccs) {
		this.signAccs = signAccs;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
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
	public String getNewCustName() {
		return newCustName;
	}
	public void setNewCustName(String newCustName) {
		this.newCustName = newCustName;
	}
	public String getNewCompany() {
		return newCompany;
	}
	public void setNewCompany(String newCompany) {
		this.newCompany = newCompany;
	}
}
