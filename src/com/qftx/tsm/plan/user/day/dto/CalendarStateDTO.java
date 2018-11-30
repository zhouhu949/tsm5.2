package com.qftx.tsm.plan.user.day.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class CalendarStateDTO {
	private List<Date> havePlanDates;
	private List<Date> reportPlanDates;
	private List<Date> authPassPlanDates;
	private List<Date> authNoPassPlanDates;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public List<Date> getHavePlanDates() {
		return havePlanDates;
	}
	public void setHavePlanDates(List<Date> havePlanDates) {
		this.havePlanDates = havePlanDates;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public List<Date> getReportPlanDates() {
		return reportPlanDates;
	}
	public void setReportPlanDates(List<Date> reportPlanDates) {
		this.reportPlanDates = reportPlanDates;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public List<Date> getAuthPassPlanDates() {
		return authPassPlanDates;
	}
	public void setAuthPassPlanDates(List<Date> authPassPlanDates) {
		this.authPassPlanDates = authPassPlanDates;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public List<Date> getAuthNoPassPlanDates() {
		return authNoPassPlanDates;
	}
	public void setAuthNoPassPlanDates(List<Date> authNoPassPlanDates) {
		this.authNoPassPlanDates = authNoPassPlanDates;
	}
	
}
