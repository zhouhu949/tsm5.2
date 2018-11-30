package com.qftx.tsm.callrecord.dto;

import java.io.Serializable;

import com.qftx.base.shiro.ShiroUtil;


public class CallRecordPageDto implements Serializable{
	
	private static final long serialVersionUID = -5471059609918720143L;
	private int showCount=ShiroUtil.getPageSize(); // 每页显示记录数
	private int totalPage; // 总页数
	private int totalResult; // 总记录数
	private int currentPage = 1; // 当前页
	
	public int getShowCount() {
		return showCount;
	}
	public void setShowCount(int showCount) {
		this.showCount = showCount;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getTotalResult() {
		return totalResult;
	}
	public void setTotalResult(int totalResult) {
		this.totalResult = totalResult;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
}
