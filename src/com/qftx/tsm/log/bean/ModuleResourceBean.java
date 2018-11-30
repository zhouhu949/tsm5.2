package com.qftx.tsm.log.bean;

import com.qftx.common.domain.BaseObject;

public class ModuleResourceBean extends BaseObject{
	private static final long serialVersionUID = -1014851173802452224L;
	
	private String id;     //id
	private String moduleId; //模块id
	private String moduleName; //模块名称
	private String pid;      //父模块id
	private String levelType;//分级：1为第一级，2为第二级，3为第三级
	private Integer type;//0：公共，1：专业版2：标准版
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getLevelType() {
		return levelType;
	}
	public void setLevelType(String levelType) {
		this.levelType = levelType;
	}
	
	
	

}
