package com.qftx.tsm.cust.bean;

import com.qftx.common.domain.BaseObject;

/** 资源对外同步接口配置实体 */
public class TsmCustSynConfigBean extends BaseObject  {

	private static final long serialVersionUID = 1L;

	private String id;
	private String orgId;
	private String synUrl; // 推送地址
	private String synKey; // 密钥
	private String synWay; // 协议
	private Integer synType; // 同步类型(1：跟进信息，2：签约客户，3：订单信息)
	private Integer orderAuth; // 订单条件(1：全部订单，2：审核通过的订单)
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getSynUrl() {
		return synUrl;
	}
	public void setSynUrl(String synUrl) {
		this.synUrl = synUrl;
	}
	public String getSynKey() {
		return synKey;
	}
	public void setSynKey(String synKey) {
		this.synKey = synKey;
	}
	public String getSynWay() {
		return synWay;
	}
	public void setSynWay(String synWay) {
		this.synWay = synWay;
	}
	public Integer getSynType() {
		return synType;
	}
	public void setSynType(Integer synType) {
		this.synType = synType;
	}
	public Integer getOrderAuth() {
		return orderAuth;
	}
	public void setOrderAuth(Integer orderAuth) {
		this.orderAuth = orderAuth;
	}

}
