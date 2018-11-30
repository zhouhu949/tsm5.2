package com.qftx.tsm.report.dto;

import com.qftx.tsm.report.bean.TsmReportCallInfoBean;

import java.util.Date;

public class TsmReportCallInfoDTO extends TsmReportCallInfoBean{
	
	private Date fromDate;
	private Date toDate;
	private String [] groupIds;
	private String [] userIds;
	private String dateFmtStr;
	
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public String[] getGroupIds() {
		return groupIds;
	}
	public void setGroupIds(String[] groupIds) {
		this.groupIds = groupIds;
	}
	public String getDateFmtStr() {
		return dateFmtStr;
	}
	public void setDateFmtStr(String dateFmtStr) {
		this.dateFmtStr = dateFmtStr;
	}
	public String[] getUserIds() {
		return userIds;
	}
	public void setUserIds(String[] userIds) {
		this.userIds = userIds;
	}
	
}
