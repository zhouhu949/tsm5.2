package com.qftx.tsm.log.dto;

import java.util.List;

public class DtoPModuleResourceBean extends DtoModuleResourceBean{
	private static final long serialVersionUID = -1014851173802452224L;
	
	private List<DtoModuleResourceBean> PModuleResourceList; //子模块

	public List<DtoModuleResourceBean> getPModuleResourceList() {
		return PModuleResourceList;
	}

	public void setPModuleResourceList(
			List<DtoModuleResourceBean> pModuleResourceList) {
		PModuleResourceList = pModuleResourceList;
	}
	

}
