package com.qftx.tsm.sys.dto;

import com.qftx.tsm.sys.bean.Knowlege;


public class KnowlegeDto extends Knowlege {

	private static final long serialVersionUID = 1664924563070863186L;
	private String titleOrContent;
	private String isUnGroup;
	private String qKeyWordId;
	public String getIsUnGroup() {
		return isUnGroup;
	}

	public void setIsUnGroup(String isUnGroup) {
		this.isUnGroup = isUnGroup;
	}

	public String getTitleOrContent() {
		return titleOrContent;
	}

	public void setTitleOrContent(String titleOrContent) {
		this.titleOrContent = titleOrContent;
	}

	public String getqKeyWordId() {
		return qKeyWordId;
	}

	public void setqKeyWordId(String qKeyWordId) {
		this.qKeyWordId = qKeyWordId;
	}
}
