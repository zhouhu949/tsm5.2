package com.qftx.tsm.report.bean;

import java.util.List;

import com.qftx.common.domain.BaseObject;

public class TsmReportWillSumBean extends BaseObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userAccount;
	private String custId;
	private String company;//公司名称
	private String name; //名称
	private String mainLinkman;//主要联系人
	private String currProcessId;
	private String currProcessName;
	private String initProcessId;
	private String initProcessName;
	private String initStatus;
	private String initType;
	private String status;
	private String type;
	
	private String userName;
	private String currDate;//日期
	private String groupName;//所属组
	private String ownerAcc;//资源所有者
	
	private String toDateInitType;//今天初始状态：资源、意向、公海
	private String endType;//本日结束状态，意向
	private String comFrom;
	private String custName;
	
	private Boolean showCard;
	
	//查询参数
	private String orgId;
	private List<String> userAccounts;
	private String optionlistId;
	
	private Integer nowType;
	private Integer nowStatus;


	public Integer getNowType() {
		return nowType;
	}
	public void setNowType(Integer nowType) {
		this.nowType = nowType;
	}
	public Integer getNowStatus() {
		return nowStatus;
	}
	public void setNowStatus(Integer nowStatus) {
		this.nowStatus = nowStatus;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public List<String> getUserAccounts() {
		return userAccounts;
	}
	public void setUserAccounts(List<String> userAccounts) {
		this.userAccounts = userAccounts;
	}
	public String getOptionlistId() {
		return optionlistId;
	}
	public void setOptionlistId(String optionlistId) {
		this.optionlistId = optionlistId;
	}
	public Boolean getShowCard() {
		return showCard;
	}
	public void setShowCard(Boolean showCard) {
		this.showCard = showCard;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCurrDate() {
		return currDate;
	}
	public void setCurrDate(String currDate) {
		this.currDate = currDate;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getOwnerAcc() {
		return ownerAcc;
	}
	public void setOwnerAcc(String ownerAcc) {
		this.ownerAcc = ownerAcc;
	}
	public String getEndType() {
		return endType;
	}
	public void setEndType(String endType) {
		this.endType = endType;
	}
	public String getToDateInitType() {
		return toDateInitType;
	}
	public void setToDateInitType(String toDateInitType) {
		this.toDateInitType = toDateInitType;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getComFrom() {
		return comFrom;
	}
	public void setComFrom(String comFrom) {
		this.comFrom = comFrom;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMainLinkman() {
		return mainLinkman;
	}
	public void setMainLinkman(String mainLinkman) {
		this.mainLinkman = mainLinkman;
	}
	public String getCurrProcessId() {
		return currProcessId;
	}
	public void setCurrProcessId(String currProcessId) {
		this.currProcessId = currProcessId;
	}
	public String getCurrProcessName() {
		return currProcessName;
	}
	public void setCurrProcessName(String currProcessName) {
		this.currProcessName = currProcessName;
	}
	public String getInitProcessId() {
		return initProcessId;
	}
	public void setInitProcessId(String initProcessId) {
		this.initProcessId = initProcessId;
	}
	public String getInitProcessName() {
		return initProcessName;
	}
	public void setInitProcessName(String initProcessName) {
		this.initProcessName = initProcessName;
	}
	public String getInitStatus() {
		return initStatus;
	}
	public void setInitStatus(String initStatus) {
		this.initStatus = initStatus;
	}
	public String getInitType() {
		return initType;
	}
	public void setInitType(String initType) {
		this.initType = initType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	

}
