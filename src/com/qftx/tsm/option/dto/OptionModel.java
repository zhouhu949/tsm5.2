package com.qftx.tsm.option.dto;

import com.qftx.tsm.option.bean.OptionBean;

import java.util.List;

/**
 * option list表单对象组装
 */
public class OptionModel {
	
	private List<OptionBean>options;

	public List<OptionBean> getOptions() {
		return options;
	}
	public void setOptions(List<OptionBean> options) {
		this.options = options;
	}
	
	
}
