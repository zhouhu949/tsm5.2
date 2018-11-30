package com.qftx.tsm.credit.dto;

import java.util.List;

import com.qftx.tsm.credit.bean.CreditLeadInfoBean;

public class StatisticInfoDto extends CreditLeadInfoBean implements Cloneable{

	private String orgId; // 机构ID
	private String groupIds; //分组id
	private String noteType;//查询标签  1.本周   2.本月  3.查询按钮
	
	private String osType;// 所有者查询方式 1-全部 2-只看自己 3-选中查询
	private String adminAcc;// 管理者帐号 用于管理者登陆时查询列表
	private String ownerAcc; // 所有者帐号
	
	private List<String> ownerAccs;
	private String ownerAccsStr;

	private String startDate;  //开始时间
	private String endDate;		//结束时间
	
	//页面展示字段
	private String userName;
	private String count;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getNoteType() {
		return noteType;
	}
	public void setNoteType(String noteType) {
		this.noteType = noteType;
	}
	public String getOsType() {
		return osType;
	}
	public void setOsType(String osType) {
		this.osType = osType;
	}
	public String getAdminAcc() {
		return adminAcc;
	}
	public void setAdminAcc(String adminAcc) {
		this.adminAcc = adminAcc;
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
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getGroupIds() {
		return groupIds;
	}
	public void setGroupIds(String groupIds) {
		this.groupIds = groupIds;
	}
	
}
