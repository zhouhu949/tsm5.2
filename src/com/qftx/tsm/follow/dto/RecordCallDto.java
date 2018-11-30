package com.qftx.tsm.follow.dto;


import com.qftx.tsm.callrecord.dto.TsmRecordCallBean;


public class RecordCallDto extends TsmRecordCallBean {
    private static final long serialVersionUID = 7802748441153980914L;
   
    private String  timeShow;
	public String getTimeShow() {
		return timeShow;
	}
	public void setTimeShow(String timeShow) {
		this.timeShow = timeShow;
	}
    
    
}
