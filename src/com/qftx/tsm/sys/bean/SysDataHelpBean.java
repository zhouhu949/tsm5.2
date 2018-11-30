package com.qftx.tsm.sys.bean;

import com.qftx.common.domain.BaseObject;

/** 帮助视频 */
public class SysDataHelpBean extends BaseObject {
	
	private static final long serialVersionUID = 1L;
	
	private String id;//id
	private String dataModule;// 模块CODE
	private String url;//视频地址
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDataModule() {
		return dataModule;
	}
	public void setDataModule(String dataModule) {
		this.dataModule = dataModule;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
		
}
