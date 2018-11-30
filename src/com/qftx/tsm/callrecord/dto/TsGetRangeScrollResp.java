package com.qftx.tsm.callrecord.dto;


import java.io.Serializable;
import java.util.List;


public class TsGetRangeScrollResp implements Serializable{
	private static final long serialVersionUID = 1172157512582372490L;
	
	private Object scroll;
    private List<TsmRecordCallBean> beans;
	
	public Object getScroll() {
		return scroll;
	}
	public void setScroll(Object scroll) {
		this.scroll = scroll;
	}
	public List<TsmRecordCallBean> getBeans() {
		return beans;
	}
	public void setBeans(List<TsmRecordCallBean> beans) {
		this.beans = beans;
	}

	  
	  
}
