package com.qftx.tsm.log.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;

public class LogUserOperateBean extends BaseObject{
	private static final long serialVersionUID = -1014851173802452224L;

	private String id;//id
	private String orgId;//机构名
	private String userAccount;//用户账号
	private String userName;//用户名称
	private Date   inputTime;//操作时间
    private String moduleId; //模块id
    private String moduleName;//模块名称
    private String operateId; //操作类型id
    private String operateName; //操作名称
    private String content;//描述
	private String systemOperateId;//系统操作id，销售管理记录id,先修改为jsondata
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
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getInputTime() {
		return inputTime;
	}
	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getOperateId() {
		return operateId;
	}
	public void setOperateId(String operateId) {
		this.operateId = operateId;
	}
	public String getOperateName() {
		return operateName;
	}
	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSystemOperateId() {
		return systemOperateId;
	}
	public void setSystemOperateId(String systemOperateId) {
		this.systemOperateId = systemOperateId;
	}



}
