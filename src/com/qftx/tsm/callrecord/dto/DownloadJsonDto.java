package com.qftx.tsm.callrecord.dto;

import java.io.Serializable;


/**
 * 录音批量下载拓展类
 *
 */
public class DownloadJsonDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name; // 字段名称

	private String url;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
