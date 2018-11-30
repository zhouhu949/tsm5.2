package com.qftx.tsm.cust.bean;

import com.qftx.common.domain.BaseObject;

/**
 * 保存淘客户页面的分组和排序的标识
 * 
 * @author: wuwei
 * @since: 2015-2-9 下午04:48:35
 * @history:
 */
public class TaoTagBean extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3682753036267845369L;
	private String account;
	private String groupId;
	private String orderType;
	private String resourceId;
	private Integer isConcat;
	private String orgId;
	private String willType;
	private String pool;
	public Integer getIsConcat() {
		return isConcat;
	}

	public void setIsConcat(Integer isConcat) {
		this.isConcat = isConcat;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getWillType() {
		return willType;
	}

	public void setWillType(String willType) {
		this.willType = willType;
	}

	public String getPool() {
		return pool;
	}

	public void setPool(String pool) {
		this.pool = pool;
	}

}
