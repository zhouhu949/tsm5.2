package com.qftx.tsm.callrecord.dto;


import java.util.List;


public class TsGetRecordsDto {

    private List<TsmRecordCallBean> beans;
	
	public List<TsmRecordCallBean> getBeans() {
		return beans;
	}
	public void setBeans(List<TsmRecordCallBean> beans) {
		this.beans = beans;
	}
  	  
}
