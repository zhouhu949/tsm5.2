package com.qftx.tsm.plan.group.month.dto;

import com.qftx.tsm.plan.group.month.bean.PlanGroupmonthBean;

public class PlanGroupmonthReportDto extends PlanGroupmonthBean {
	private static final long serialVersionUID = -848959730682148871L;
	private String groupName;
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}
