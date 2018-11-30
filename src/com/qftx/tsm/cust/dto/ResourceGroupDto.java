package com.qftx.tsm.cust.dto;

import com.qftx.tsm.cust.bean.ResourceGroupBean;


 /** 
 * 资源分组
 * @author: wuwei
 * @since: 2013-10-30  上午10:06:52
 * @history:
 */
public class ResourceGroupDto extends ResourceGroupBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -271621717813880694L;
	private String groupName;// 客户分组
	private String inputerName;// 录入者
	private String groupCount; // 分组用
	private String orgId;
	private String isUnGroup;

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

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getIsUnGroup() {
		return isUnGroup;
	}

	public void setIsUnGroup(String isUnGroup) {
		this.isUnGroup = isUnGroup;
	}

}