package com.qftx.tsm.cust.dto;

import com.qftx.tsm.cust.bean.ResCustInfoBean;

import java.util.List;

public class ResCustDto extends ResCustInfoBean implements Cloneable {

	private static final long serialVersionUID = 8999508085922968829L;
	private String groupName;// 客户分组
	private String inputerName;// 录入者
	private String ownerName; // 所有者
	private String groupCount; // 分组用户数
	private String simpleCondition; // 手机或固话合用
	private String resType;// 资源类型 未分配资源 分配类型
	private Integer updownOptType; // 上/下 一条操作类型 1：上一条;2:下一条
	private String followDateStart; // 销售最近跟进start
	private String followDateEnd; // 销售最近跟进end
	private String visitingDateStart; // 最近联系时间start
	private String visitingDateEnd; // 最近联系时间end
	private String nextVisitingDateStart; // 计划联系时间start
	private String nextVisitingDateEnd; // 计划联系时间end
	private List<String> shrAccList; // 服务共享组员acc list
	private String orderType;
	private String resourceGroupId;
	private String isFromOtherRes;
	private Long startNumber;
	private Long endNumber;
	private String optionName;
	private String saleProcessId;
	private String startActionDate;
	private String endActionDate;
	private String custType;
	private String followCustCation;
	private String todayAddDate;// 用于今日新增分类查询
	private String weekAddDate;// 用于本周新增分类查询
	private String custTypeId;
	private String[] lables;

	private List<String> ownerAccs;
	private String qKeyWord;
	private String oDateType;
	private int qStatus;
	private String deptName;
	private List<String> deptIds;
	private String ownerAccsStr;
	private boolean queryPhone;// 是否为电话号码查询
	private String queryType;
	private String osType;// 所有者查询方式 1-全部 2-只看自己 3-选中查询
	private String ownerUserIdsStr;
	private List<String> ownerUserIds;
	private String adminAcc;//管理者帐号 用于管理者登陆时查询列表
	
	private String phone;
	private String locationArea;
	private List<String> resIds;
	
	private String inputStatusStr;
	
	private List<String> inputStatusList;
	
	public String getInputStatusStr() {
		return inputStatusStr;
	}
	public void setInputStatusStr(String inputStatusStr) {
		this.inputStatusStr = inputStatusStr;
	}
	public List<String> getInputStatusList() {
		return inputStatusList;
	}
	public void setInputStatusList(List<String> inputStatusList) {
		this.inputStatusList = inputStatusList;
	}
	
	public List<String> getOwnerAccs() {
		return ownerAccs;
	}

	public void setOwnerAccs(List<String> ownerAccs) {
		this.ownerAccs = ownerAccs;
	}

	public String getCustTypeId() {
		return custTypeId;
	}

	public void setCustTypeId(String custTypeId) {
		this.custTypeId = custTypeId;
	}

	public String getFollowCustCation() {
		return followCustCation;
	}

	public void setFollowCustCation(String followCustCation) {
		this.followCustCation = followCustCation;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getStartActionDate() {
		return startActionDate;
	}

	public void setStartActionDate(String startActionDate) {
		this.startActionDate = startActionDate;
	}

	public String getEndActionDate() {
		return endActionDate;
	}

	public void setEndActionDate(String endActionDate) {
		this.endActionDate = endActionDate;
	}

	public String getOptionName() {
		return optionName;
	}

	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}

	public String getSaleProcessId() {
		return saleProcessId;
	}

	public void setSaleProcessId(String saleProcessId) {
		this.saleProcessId = saleProcessId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getInputerName() {
		return inputerName;
	}

	public void setInputerName(String inputerName) {
		this.inputerName = inputerName;
	}

	public String getGroupCount() {
		return groupCount;
	}

	public void setGroupCount(String groupCount) {
		this.groupCount = groupCount;
	}

	public String getSimpleCondition() {
		return simpleCondition;
	}

	public void setSimpleCondition(String simpleCondition) {
		this.simpleCondition = simpleCondition;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getResType() {
		return resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
	}

	public Integer getUpdownOptType() {
		return updownOptType;
	}

	public void setUpdownOptType(Integer updownOptType) {
		this.updownOptType = updownOptType;
	}

	public String getFollowDateStart() {
		return followDateStart;
	}

	public void setFollowDateStart(String followDateStart) {
		this.followDateStart = followDateStart;
	}

	public String getFollowDateEnd() {
		return followDateEnd;
	}

	public void setFollowDateEnd(String followDateEnd) {
		this.followDateEnd = followDateEnd;
	}

	public String getVisitingDateStart() {
		return visitingDateStart;
	}

	public void setVisitingDateStart(String visitingDateStart) {
		this.visitingDateStart = visitingDateStart;
	}

	public String getVisitingDateEnd() {
		return visitingDateEnd;
	}

	public void setVisitingDateEnd(String visitingDateEnd) {
		this.visitingDateEnd = visitingDateEnd;
	}

	public String getNextVisitingDateStart() {
		return nextVisitingDateStart;
	}

	public void setNextVisitingDateStart(String nextVisitingDateStart) {
		this.nextVisitingDateStart = nextVisitingDateStart;
	}

	public String getNextVisitingDateEnd() {
		return nextVisitingDateEnd;
	}

	public void setNextVisitingDateEnd(String nextVisitingDateEnd) {
		this.nextVisitingDateEnd = nextVisitingDateEnd;
	}

	public List<String> getShrAccList() {
		return shrAccList;
	}

	public void setShrAccList(List<String> shrAccList) {
		this.shrAccList = shrAccList;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setResourceGroupId(String resourceGroupId) {
		this.resourceGroupId = resourceGroupId;
	}

	public String getResourceGroupId() {
		return resourceGroupId;
	}

	public void setIsFromOtherRes(String isFromOtherRes) {
		this.isFromOtherRes = isFromOtherRes;
	}

	public String getIsFromOtherRes() {
		return isFromOtherRes;
	}

	public Long getStartNumber() {
		return startNumber;
	}

	public void setStartNumber(Long startNumber) {
		this.startNumber = startNumber;
	}

	public Long getEndNumber() {
		return endNumber;
	}

	public void setEndNumber(Long endNumber) {
		this.endNumber = endNumber;
	}

	public String[] getLables() {
		return lables;
	}

	public void setLables(String[] lables) {
		this.lables = lables;
	}

	public String getTodayAddDate() {
		return todayAddDate;
	}

	public void setTodayAddDate(String todayAddDate) {
		this.todayAddDate = todayAddDate;
	}

	public String getWeekAddDate() {
		return weekAddDate;
	}

	public void setWeekAddDate(String weekAddDate) {
		this.weekAddDate = weekAddDate;
	}

	/**
	 * @return 创建并返回此对象的一个副本。
	 * @throws CloneNotSupportedException
	 */
	public Object clone() throws CloneNotSupportedException {
		// 直接调用父类的clone()方法,返回克隆副本
		return super.clone();
	}

	public String getqKeyWord() {
		return qKeyWord;
	}

	public void setqKeyWord(String qKeyWord) {
		this.qKeyWord = qKeyWord;
	}

	public String getoDateType() {
		return oDateType;
	}

	public void setoDateType(String oDateType) {
		this.oDateType = oDateType;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public List<String> getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(List<String> deptIds) {
		this.deptIds = deptIds;
	}

	public int getqStatus() {
		return qStatus;
	}

	public void setqStatus(int qStatus) {
		this.qStatus = qStatus;
	}

	public String getOwnerAccsStr() {
		return ownerAccsStr;
	}

	public void setOwnerAccsStr(String ownerAccsStr) {
		this.ownerAccsStr = ownerAccsStr;
	}

	public boolean isQueryPhone() {
		return queryPhone;
	}

	public void setQueryPhone(boolean queryPhone) {
		this.queryPhone = queryPhone;
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

	public String getOwnerUserIdsStr() {
		return ownerUserIdsStr;
	}

	public void setOwnerUserIdsStr(String ownerUserIdsStr) {
		this.ownerUserIdsStr = ownerUserIdsStr;
	}

	public List<String> getOwnerUserIds() {
		return ownerUserIds;
	}

	public void setOwnerUserIds(List<String> ownerUserIds) {
		this.ownerUserIds = ownerUserIds;
	}

	public String getAdminAcc() {
		return adminAcc;
	}

	public void setAdminAcc(String adminAcc) {
		this.adminAcc = adminAcc;
	}


	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLocationArea() {
		return locationArea;
	}

	public void setLocationArea(String locationArea) {
		this.locationArea = locationArea;
	}

	public List<String> getResIds() {
		return resIds;
	}

	public void setResIds(List<String> resIds) {
		this.resIds = resIds;
	}

	
	


	


}