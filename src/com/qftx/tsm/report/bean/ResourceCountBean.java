package com.qftx.tsm.report.bean;

import com.qftx.common.domain.BaseObject;

import java.util.List;

public class ResourceCountBean extends BaseObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pid; //资源分类id
	private String pidGroupName; //资源分类名称
	private String resGroupId;//资源分组id
	private String groupName;//资源分组名称
	private Integer resCount;//资源数
	private Integer willCount;//意向数
	private Integer signCount;//签约数
	private Integer callCount;//接通数
	private Integer vaildCallCount;//有效通话数
	private Integer errorCount;//错误数
	
	private String willCountstr;//意向数/资源数
	private String signCountstr;//签约数/资源数
	private String callCountstr;//接通数/资源数
	private String vaildCallCountstr;//有效通话数/资源数
	private String errorCountstr;//错误数/资源数
	
	private String orgId; //资源分类id
	
	private List<String> groupList;//分组列表
	private Integer errorValue;//系统设置有效通话时间
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getResGroupId() {
		return resGroupId;
	}
	public void setResGroupId(String resGroupId) {
		this.resGroupId = resGroupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Integer getResCount() {
		return resCount;
	}
	public void setResCount(Integer resCount) {
		this.resCount = resCount;
	}
	public Integer getWillCount() {
		return willCount;
	}
	public void setWillCount(Integer willCount) {
		this.willCount = willCount;
	}
	public Integer getSignCount() {
		return signCount;
	}
	public void setSignCount(Integer signCount) {
		this.signCount = signCount;
	}
	public Integer getCallCount() {
		return callCount;
	}
	public void setCallCount(Integer callCount) {
		this.callCount = callCount;
	}
	public Integer getVaildCallCount() {
		return vaildCallCount;
	}
	public void setVaildCallCount(Integer vaildCallCount) {
		this.vaildCallCount = vaildCallCount;
	}
	public Integer getErrorCount() {
		return errorCount;
	}
	public void setErrorCount(Integer errorCount) {
		this.errorCount = errorCount;
	}
	public List<String> getGroupList() {
		return groupList;
	}
	public void setGroupList(List<String> groupList) {
		this.groupList = groupList;
	}
	public Integer getErrorValue() {
		return errorValue;
	}
	public void setErrorValue(Integer errorValue) {
		this.errorValue = errorValue;
	}
	public String getWillCountstr() {
		return willCountstr;
	}
	public void setWillCountstr(String willCountstr) {
		this.willCountstr = willCountstr;
	}
	public String getSignCountstr() {
		return signCountstr;
	}
	public void setSignCountstr(String signCountstr) {
		this.signCountstr = signCountstr;
	}
	public String getCallCountstr() {
		return callCountstr;
	}
	public void setCallCountstr(String callCountstr) {
		this.callCountstr = callCountstr;
	}
	public String getVaildCallCountstr() {
		return vaildCallCountstr;
	}
	public void setVaildCallCountstr(String vaildCallCountstr) {
		this.vaildCallCountstr = vaildCallCountstr;
	}
	public String getErrorCountstr() {
		return errorCountstr;
	}
	public void setErrorCountstr(String errorCountstr) {
		this.errorCountstr = errorCountstr;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	


}
