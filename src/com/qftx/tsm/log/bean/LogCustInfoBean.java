package com.qftx.tsm.log.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qftx.common.domain.BaseObject;

import java.util.Date;

public class LogCustInfoBean extends BaseObject {

	private static final long serialVersionUID = -1014851173802452224L;

	private String id;//id
	private String orgId;//机构名
	private String userId;//用户名
	private String custId;//资源ID
	private String name;//日志名称
	private String type;//操作类型1增2修改(2001转为客户、2002转为意向、2003签约、2010转为沉默客户，2011转为流失客户、2021沉默客户转客户、2022流失转客户)0删除
	private String data;//资源JSON数据
	private String context;//描述
	private Date inputtime;//输入时间
	private Date updatetime;//修改时间
	private Integer isDel;//册除状态1-删除，0-未删除
	private String userName;//操作人
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
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getCustId() {
		return custId;
	}
	
	public void setCustId(String custId) {
		this.custId = custId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
	
	public String getContext() {
		return context;
	}
	
	public void setContext(String context) {
		this.context = context;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getInputtime() {
		return inputtime;
	}
	
	public void setInputtime(Date inputtime) {
		this.inputtime = inputtime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getUpdatetime() {
		return updatetime;
	}
	
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
	public Integer getIsDel() {
		return isDel;
	}
	
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
