package com.qftx.tsm.worklog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qftx.tsm.worklog.bean.WorkLogBbsBean;
import com.qftx.tsm.worklog.bean.WorkLogInfoBean;

import java.util.Date;
import java.util.List;

public class WorkLogModel {
	private Date date;
	//当月存在我的日志的日期
	private List<Date> existDates;
	private List<Date> existSubordinateDates;
	private List<WorkLogInfoBean> workLogInfos;
	private List<WorkLogBbsBean> workLogBbss;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public List<WorkLogInfoBean> getWorkLogInfos() {
		return workLogInfos;
	}
	public void setWorkLogInfos(List<WorkLogInfoBean> workLogInfos) {
		this.workLogInfos = workLogInfos;
	}
	public List<WorkLogBbsBean> getWorkLogBbss() {
		return workLogBbss;
	}
	public void setWorkLogBbss(List<WorkLogBbsBean> workLogBbss) {
		this.workLogBbss = workLogBbss;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public List<Date> getExistDates() {
		return existDates;
	}
	public void setExistDates(List<Date> existDates) {
		this.existDates = existDates;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public List<Date> getExistSubordinateDates() {
		return existSubordinateDates;
	}
	public void setExistSubordinateDates(List<Date> existSubordinateDates) {
		this.existSubordinateDates = existSubordinateDates;
	}
}
