package com.qftx.tsm.callrecord.dto;

import java.util.List;

import com.qftx.common.domain.BaseObject;

/**
 * 邮件发送中 异步查询资源信息dto
 * <p>    company：浙江中恩通信技术有限公司
 * @author zwj
 */
public class CustResInfoDto extends BaseObject{
	private static final long serialVersionUID = 6839235707256215954L;
	private String custId; // 资源ID
	private String custName;	// 客户姓名
	private String telphone;		// 联系电话
	private String company;	// 企业名称
	private String ownerAcc; // 资源拥有者
	private Integer state; // 0:个人客户，1：企业客户	
	private String orgId; // 单位ID
	private String queryText; // 查询条件
	private String email; // 邮箱
	private List<String> ownerAccs;
	
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getOwnerAcc() {
		return ownerAcc;
	}
	public void setOwnerAcc(String ownerAcc) {
		this.ownerAcc = ownerAcc;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getQueryText() {
		return queryText;
	}
	public void setQueryText(String queryText) {
		this.queryText = queryText;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public List<String> getOwnerAccs() {
		return ownerAccs;
	}
	public void setOwnerAccs(List<String> ownerAccs) {
		this.ownerAccs = ownerAccs;
	}		
	
}
