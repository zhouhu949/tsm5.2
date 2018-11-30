package com.qftx.tsm.sys.dto;

import com.qftx.tsm.sys.bean.HookSmsConfig;

/**
 * 挂机短信模板 DTO
 */
public class HookSmsConfigDto extends HookSmsConfig {
	private static final long serialVersionUID = 1L;

	private String queryText; // 列表模糊查询文本
	
	private Integer dDateType;
	
	private String stateName; // 呼叫类型列表显示 名称
	
	private String sendTypeName; // 发送对象列表显示 名称
	
	private String sendCondiName; // 发送条件列表显示 名称

	public String getQueryText() {
		return queryText;
	}

	public void setQueryText(String queryText) {
		this.queryText = queryText;
	}

	public Integer getdDateType() {
		return dDateType;
	}

	public void setdDateType(Integer dDateType) {
		this.dDateType = dDateType;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getSendTypeName() {
		return sendTypeName;
	}

	public void setSendTypeName(String sendTypeName) {
		this.sendTypeName = sendTypeName;
	}

	public String getSendCondiName() {
		return sendCondiName;
	}

	public void setSendCondiName(String sendCondiName) {
		this.sendCondiName = sendCondiName;
	}

}