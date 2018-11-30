package com.qftx.tsm.follow.dto;

import java.io.Serializable;
import java.util.List;

/** 跟进详情 右侧待跟进列表信息 */
public class CustFollowShowWaitDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String planParam; // 1：只显示计划中客户，2：不显示计划中客户，3：签约未跟进
	private List<RestDto> restDtos; // 待跟进信息
	private String startPage; // 起始页
	private Integer idReplaceWord; // 0:不隐藏号码，1:隐藏号码
	private RestDto dto;
	public String getPlanParam() {
		return planParam;
	}
	public void setPlanParam(String planParam) {
		this.planParam = planParam;
	}
	public List<RestDto> getRestDtos() {
		return restDtos;
	}
	public void setRestDtos(List<RestDto> restDtos) {
		this.restDtos = restDtos;
	}
	public String getStartPage() {
		return startPage;
	}
	public void setStartPage(String startPage) {
		this.startPage = startPage;
	}
	public Integer getIdReplaceWord() {
		return idReplaceWord;
	}
	public void setIdReplaceWord(Integer idReplaceWord) {
		this.idReplaceWord = idReplaceWord;
	}
	public RestDto getDto() {
		return dto;
	}
	public void setDto(RestDto dto) {
		this.dto = dto;
	}
	
	
}
