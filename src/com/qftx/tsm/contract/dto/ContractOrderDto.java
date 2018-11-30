package com.qftx.tsm.contract.dto;

import com.qftx.tsm.contract.bean.ContractOrderBean;

import java.math.BigDecimal;
import java.util.List;


public class ContractOrderDto extends ContractOrderBean {
	
	private static final long serialVersionUID = 3047354578155112584L;

	private String custName;
	private String userName;
	private String adminAcc;
	private String startEffecDate;
	private String endEffecDate;
	private Integer eDateType;
	private String startInvalidDate;
	private String endInvalidDate;
	private Integer iDateType;
	private String userIdsStr;
	private List<String> userIds;
	private List<String> ids;
	private String queryText;
	private Integer sDateType;
	private List<String> caIds;
	private Integer orderNum;
	private Integer signNum;
	private String groupName;
	private String showCheck;//1-全部 2-审核
	private List<String> groupIds;
	private String company;
	private String queryType;
	private String companyAddr;
	private String telphone;
	private String mobilephone;
	private String osType;
	private String contractCode;
	private String signUserAcc;
	private String signUserId;
	List<ContractOrderDetailDto> detailDtos;
	private String groupId;
	private String orderColumn;
	
	private String orderType;
	
	private List<String> ownerAccs;
	private String ownerAcc;
	private String ownerAccStr;
	
	private String commonAcc;//共有者
	private String noteType;
	
	private BigDecimal signMoney;//签单金额
	
	private BigDecimal willSignMoney;//预期签单金额
	
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
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
	public Integer geteDateType() {
		return eDateType;
	}
	public void seteDateType(Integer eDateType) {
		this.eDateType = eDateType;
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
	public Integer getiDateType() {
		return iDateType;
	}
	public void setiDateType(Integer iDateType) {
		this.iDateType = iDateType;
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
	public List<String> getIds() {
		return ids;
	}
	public void setIds(List<String> ids) {
		this.ids = ids;
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
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public List<String> getCaIds() {
		return caIds;
	}
	public String getCompanyAddr() {
		return companyAddr;
	}
	public String getTelphone() {
		return telphone;
	}
	public String getMobilephone() {
		return mobilephone;
	}
	public void setCompanyAddr(String companyAddr) {
		this.companyAddr = companyAddr;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}
	public void setCaIds(List<String> caIds) {
		this.caIds = caIds;
	}
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	public Integer getSignNum() {
		return signNum;
	}
	public void setSignNum(Integer signNum) {
		this.signNum = signNum;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getShowCheck() {
		return showCheck;
	}
	public void setShowCheck(String showCheck) {
		this.showCheck = showCheck;
	}
	public List<String> getGroupIds() {
		return groupIds;
	}
	public void setGroupIds(List<String> groupIds) {
		this.groupIds = groupIds;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
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
	public String getSignUserAcc() {
		return signUserAcc;
	}
	public void setSignUserAcc(String signUserAcc) {
		this.signUserAcc = signUserAcc;
	}
	public String getSignUserId() {
		return signUserId;
	}
	public void setSignUserId(String signUserId) {
		this.signUserId = signUserId;
	}
	public List<ContractOrderDetailDto> getDetailDtos() {
		return detailDtos;
	}
	public void setDetailDtos(List<ContractOrderDetailDto> detailDtos) {
		this.detailDtos = detailDtos;
	}
	public String getOrderColumn() {
		return orderColumn;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public List<String> getOwnerAccs() {
		return ownerAccs;
	}
	public void setOwnerAccs(List<String> ownerAccs) {
		this.ownerAccs = ownerAccs;
	}
	public String getOwnerAcc() {
		return ownerAcc;
	}
	public void setOwnerAcc(String ownerAcc) {
		this.ownerAcc = ownerAcc;
	}
	public String getOwnerAccStr() {
		return ownerAccStr;
	}
	public void setOwnerAccStr(String ownerAccStr) {
		this.ownerAccStr = ownerAccStr;
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
	public BigDecimal getSignMoney() {
		return signMoney;
	}
	public void setSignMoney(BigDecimal signMoney) {
		this.signMoney = signMoney;
	}
	public BigDecimal getWillSignMoney() {
		return willSignMoney;
	}
	public void setWillSignMoney(BigDecimal willSignMoney) {
		this.willSignMoney = willSignMoney;
	}
}
