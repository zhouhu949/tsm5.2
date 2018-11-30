package com.qftx.tsm.option.dto;

import com.qftx.tsm.option.bean.DataDictionaryBean;

import java.util.List;


/**
 * DataDictionaryBean list表单对象组装
 */
public class DataDictionaryModel {
	
	private List<DataDictionaryBean>dictionaryList;
	private List<DictionaryWatersDto>dictionaryWaterList;
	private List<MoneyJsonDto>dictionaryMoneyList;
	private List<CustFollowProcessJsonDto>dictionaryFollowList;
	
	public List<DataDictionaryBean> getDictionaryList() {
		return dictionaryList;
	}

	public void setDictionaryList(List<DataDictionaryBean> dictionaryList) {
		this.dictionaryList = dictionaryList;
	}

	public List<DictionaryWatersDto> getDictionaryWaterList() {
		return dictionaryWaterList;
	}

	public void setDictionaryWaterList(List<DictionaryWatersDto> dictionaryWaterList) {
		this.dictionaryWaterList = dictionaryWaterList;
	}

	public List<MoneyJsonDto> getDictionaryMoneyList() {
		return dictionaryMoneyList;
	}

	public void setDictionaryMoneyList(List<MoneyJsonDto> dictionaryMoneyList) {
		this.dictionaryMoneyList = dictionaryMoneyList;
	}

	public List<CustFollowProcessJsonDto> getDictionaryFollowList() {
		return dictionaryFollowList;
	}

	public void setDictionaryFollowList(
			List<CustFollowProcessJsonDto> dictionaryFollowList) {
		this.dictionaryFollowList = dictionaryFollowList;
	}
	
}
