package com.qftx.tsm.callrecord.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CallRecordListDto implements Serializable{

	private static final long serialVersionUID = 3257145294451122118L;
	
	private ConditionDto condition = new ConditionDto();
	private List<TsmRecordCallBean>beans = new ArrayList<TsmRecordCallBean>();
	
	
	
	
	public ConditionDto getCondition() {
		return condition;
	}
	public void setCondition(ConditionDto condition) {
		this.condition = condition;
	}
	public List<TsmRecordCallBean> getBeans() {
		return beans;
	}
	public void setBeans(List<TsmRecordCallBean> beans) {
		this.beans = beans;
	}
	
	
}
