package com.qftx.tsm.main.bean;

import com.qftx.common.domain.BaseObject;

public class AuthUserResourceQuickBean extends BaseObject {

	private static final long serialVersionUID = -723352464263501485L;

	private String id;//id
	private String userId;//用户ID
	private String resourceId;//资源ID
	private String orgId;//单位ID
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
}
