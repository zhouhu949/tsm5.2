package com.qftx.tsm.card.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qftx.log.tablestore.bean.LogBean;

public class LogReturnDataDto extends LogBean {
	
	private String context;
	
	private String custId;

	private long inputTime;
	
	private Date inputDate;
	
	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public long getInputTime() {
		return inputTime;
	}

	public void setInputTime(long inputTime) {
		this.inputTime = inputTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}
}
