package com.qftx.tsm.log.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;

public class LogSysOperateBean extends BaseObject {
	private static final long serialVersionUID = -3706421145695303864L;
	private String id;//ID
	private String orgId;//单位ID
	private String userId;//用户ID
	private String name;// 名称
	private String type;//操作类型 0删除、1增、2修改
	private String context;//描述
	private String jsonData; // json 数据
	private Date inputtime;//输入时间
	private Date updatetime;//修改时间
	private Integer isDel;  // 册除状态1-删除，0-未删除
	
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
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public Date getInputtime() {
		return inputtime;
	}
	public void setInputtime(Date inputtime) {
		this.inputtime = inputtime;
	}
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
	public String getJsonData() {
		return jsonData;
	}
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
		
}
