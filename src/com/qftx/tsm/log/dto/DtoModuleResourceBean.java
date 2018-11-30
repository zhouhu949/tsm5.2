package com.qftx.tsm.log.dto;

import com.qftx.tsm.log.bean.ModuleResourceBean;

import java.util.List;

public class DtoModuleResourceBean extends ModuleResourceBean{
	private static final long serialVersionUID = -1014851173802452224L;
	
	private List<ModuleResourceBean> ModuleResourceList; //子模块

	public List<ModuleResourceBean> getModuleResourceList() {
		return ModuleResourceList;
	}

	public void setModuleResourceList(List<ModuleResourceBean> moduleResourceList) {
		ModuleResourceList = moduleResourceList;
	}
	

}
