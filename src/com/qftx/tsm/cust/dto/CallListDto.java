package com.qftx.tsm.cust.dto;

import java.util.List;

//呼叫列表dto--组装ajax返回数据
public class CallListDto {
	private String followId; // 跟进id
	private String custId; // 客户id
	private String autoCall;
	private String projectPath;
	private List<TaoResDto> otherList;
	private List<GroupDto> groupList;
	private String isReplaceWord;
	private String signSetting;
	private String dyName;
	private String dyMoblie;
	private String dyTel;
	private String orderType;
	private String resourceGroupId;
	private String isConcat;
	private String pool;
	private String isFromOtherPage;
	private String currResId;

	public String getFollowId() {
		return followId;
	}

	public void setFollowId(String followId) {
		this.followId = followId;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getAutoCall() {
		return autoCall;
	}

	public void setAutoCall(String autoCall) {
		this.autoCall = autoCall;
	}

	public String getProjectPath() {
		return projectPath;
	}

	public void setProjectPath(String projectPath) {
		this.projectPath = projectPath;
	}

	public List<TaoResDto> getOtherList() {
		return otherList;
	}

	public void setOtherList(List<TaoResDto> otherList) {
		this.otherList = otherList;
	}

	public List<GroupDto> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<GroupDto> groupList) {
		this.groupList = groupList;
	}

	public String getIsReplaceWord() {
		return isReplaceWord;
	}

	public void setIsReplaceWord(String isReplaceWord) {
		this.isReplaceWord = isReplaceWord;
	}

	public String getSignSetting() {
		return signSetting;
	}

	public void setSignSetting(String signSetting) {
		this.signSetting = signSetting;
	}

	public String getDyName() {
		return dyName;
	}

	public void setDyName(String dyName) {
		this.dyName = dyName;
	}

	public String getDyMoblie() {
		return dyMoblie;
	}

	public void setDyMoblie(String dyMoblie) {
		this.dyMoblie = dyMoblie;
	}

	public String getDyTel() {
		return dyTel;
	}

	public void setDyTel(String dyTel) {
		this.dyTel = dyTel;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getResourceGroupId() {
		return resourceGroupId;
	}

	public void setResourceGroupId(String resourceGroupId) {
		this.resourceGroupId = resourceGroupId;
	}

	public String getIsConcat() {
		return isConcat;
	}

	public void setIsConcat(String isConcat) {
		this.isConcat = isConcat;
	}

	public String getPool() {
		return pool;
	}

	public void setPool(String pool) {
		this.pool = pool;
	}

	public String getIsFromOtherPage() {
		return isFromOtherPage;
	}

	public void setIsFromOtherPage(String isFromOtherPage) {
		this.isFromOtherPage = isFromOtherPage;
	}

	public String getCurrResId() {
		return currResId;
	}

	public void setCurrResId(String currResId) {
		this.currResId = currResId;
	}

}
