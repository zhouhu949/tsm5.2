package com.qftx.tsm.option.dto;

import java.io.Serializable;

/**
 * 公海数据权限设置 json
 */
public class DictionaryWatersDto implements Serializable{

	private static final long serialVersionUID = -2251702115694649348L;
	
	private String groupId; // 部门ID
	private String shareGroupIds; // 权限范围内部门ID集合，','号分割
	private String groupName;
	private String shareGroupNames;
	
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getShareGroupIds() {
		return shareGroupIds;
	}
	public void setShareGroupIds(String shareGroupIds) {
		this.shareGroupIds = shareGroupIds;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getShareGroupNames() {
		return shareGroupNames;
	}
	public void setShareGroupNames(String shareGroupNames) {
		this.shareGroupNames = shareGroupNames;
	}
}
