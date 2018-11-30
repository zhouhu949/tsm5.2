package com.qftx.tsm.tao.dto;

import com.qftx.common.domain.BaseObject;

public class OptionDto extends BaseObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String optionId;
	private String optionName;
	private String isDefault; // 是否默认值0-否；1-是
    private Integer sort;            //排序
	public String getOptionId() {
		return optionId;
	}
	public void setOptionId(String optionId) {
		this.optionId = optionId;
	}
	public String getOptionName() {
		return optionName;
	}
	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}
	public String getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
