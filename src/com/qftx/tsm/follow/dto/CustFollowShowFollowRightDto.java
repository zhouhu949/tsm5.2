package com.qftx.tsm.follow.dto;

import java.io.Serializable;
import java.util.List;

/** 跟进详情页面 前端所需 跟进记录数据 */
public class CustFollowShowFollowRightDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<CustFollowDto> custFollows; //跟进记录
	private String playUrl; // 录音地址
	private String project_path; // 服务地址
	public List<CustFollowDto> getCustFollows() {
		return custFollows;
	}
	public void setCustFollows(List<CustFollowDto> custFollows) {
		this.custFollows = custFollows;
	}
	public String getPlayUrl() {
		return playUrl;
	}
	public void setPlayUrl(String playUrl) {
		this.playUrl = playUrl;
	}
	public String getProject_path() {
		return project_path;
	}
	public void setProject_path(String project_path) {
		this.project_path = project_path;
	}
}
