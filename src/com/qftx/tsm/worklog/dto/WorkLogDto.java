package com.qftx.tsm.worklog.dto;

import com.qftx.tsm.worklog.bean.WorkLogInfoBean;

import java.util.List;

public class WorkLogDto{
	private String logDate;
	private List<WorkLogInfoBean> logs;
	private String formatDay;
	private String formatMonth;
	
	private String[] months={"一","二","三","四","五","六","七","八","九","十","十一","十二"};
	public String getLogDate() {
		return logDate;
	}
	public void setLogDate(String logDate) {
		this.logDate = logDate;
	}
	public List<WorkLogInfoBean> getLogs() {
		return logs;
	}
	public void setLogs(List<WorkLogInfoBean> logs) {
		this.logs = logs;
	}
	public String getFormatDay() {
		return logDate.substring(8);
	}
	public void setFormatDay(String formatDay) {
		this.formatDay = formatDay;
	}
	public String getFormatMonth() {
		//"2012-08-14"
		try {
			return String.valueOf(months[Integer.parseInt(logDate.substring(5,7))-1]+"月");
		} catch (Exception e) {
			return null;
		}
	}
	public void setFormatMonth(String formatMonth) {
		this.formatMonth = formatMonth;
	}
	
}
