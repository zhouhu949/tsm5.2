package com.qftx.tsm.plan.user.month.bean;

public class PlanHistoryBean extends PlanUsermonthCustBean{
	private static final long serialVersionUID = -4520450956796815594L;
	
	private String planId;
	private String groupId;
	private String groupName;
	private String userId;
	private String userName;
	private Integer willcustNum;
	
	private String planYear;
	private String planMonth;
	private String[] groupIds;
	private String[] userIds;
	private String queryType;//查询类型 1-客户姓名 2-客户单位 
	private String queryText;
	
	public String getPlanYear() {
		return planYear;
	}
	public void setPlanYear(String planYear) {
		this.planYear = planYear;
	}
	public String getPlanMonth() {
		return planMonth;
	}
	public void setPlanMonth(String planMonth) {
		this.planMonth = planMonth;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getWillcustNum() {
		return willcustNum;
	}
	public void setWillcustNum(Integer willcustNum) {
		this.willcustNum = willcustNum;
	}
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String[] getGroupIds() {
		return groupIds;
	}
	public void setGroupIds(String[] groupIds) {
		this.groupIds = groupIds;
	}
	public String[] getUserIds() {
		return userIds;
	}
	public void setUserIds(String[] userIds) {
		this.userIds = userIds;
	}
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public String getQueryText() {
		return queryText;
	}
	public void setQueryText(String queryText) {
		this.queryText = queryText;
	}
}
