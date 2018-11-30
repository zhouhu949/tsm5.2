package com.qftx.tsm.plan.user.day.dto;

import com.qftx.base.util.DateUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class PlanIndexDTO{
	private int resourceCount;
	private int resourceContactCount;
	private int signcustCount;
	private int signcustContactCount;
	private int willcustCount;
	private int willcustContactCount;
	private List<WillCustIndex> willCustIndexlist;
	
	private String	id;
	private Date planDate;
	private String planDateStr;
	private String planDateWeekStr;
	private int todayFlag;
	private boolean planFlag;
	private boolean mineFlag;
	public int getResourceCount() {
		return resourceCount;
	}
	public void setResourceCount(int resourceCount) {
		this.resourceCount = resourceCount;
	}
	public int getResourceContactCount() {
		return resourceContactCount;
	}
	public void setResourceContactCount(int resourceContactCount) {
		this.resourceContactCount = resourceContactCount;
	}
	public int getSigncustCount() {
		return signcustCount;
	}
	public void setSigncustCount(int signcustCount) {
		this.signcustCount = signcustCount;
	}
	public int getSigncustContactCount() {
		return signcustContactCount;
	}
	public void setSigncustContactCount(int signcustContactCount) {
		this.signcustContactCount = signcustContactCount;
	}
	public int getWillcustCount() {
		return willcustCount;
	}
	public void setWillcustCount(int willcustCount) {
		this.willcustCount = willcustCount;
	}
	public int getWillcustContactCount() {
		return willcustContactCount;
	}
	public void setWillcustContactCount(int willcustContactCount) {
		this.willcustContactCount = willcustContactCount;
	}
	public List<WillCustIndex> getWillCustIndexlist() {
		return willCustIndexlist;
	}
	public void setWillCustIndexlist(List<WillCustIndex> willCustIndexlist) {
		this.willCustIndexlist = willCustIndexlist;
	}
	public Date getPlanDate() {
		return planDate;
	}
	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}
	public String getPlanDateStr() {
		return DateUtil.formatDate(planDate,DateUtil.DATE_DAY);
	}
	public void setPlanDateStr(String planDateStr) {
		this.planDateStr = planDateStr;
	}
	public String getPlanDateWeekStr() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(planDate);
		String[] weeks = {"","日","一","二","三","四","五","六"};
		return weeks[calendar.get(Calendar.DAY_OF_WEEK)];
	}
	public void setPlanDateWeekStr(String planDateWeekStr) {
		this.planDateWeekStr = planDateWeekStr;
	}
	public int getTodayFlag() {
		return DateUtil.dateBegin(planDate).compareTo(DateUtil.dateBegin(new Date()));
	}
	public void setTodayFlag(int todayFlag) {
		this.todayFlag = todayFlag;
	}
	public boolean isPlanFlag() {
		return planFlag;
	}
	public void setPlanFlag(boolean planFlag) {
		this.planFlag = planFlag;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isMineFlag() {
		return mineFlag;
	}
	public void setMineFlag(boolean mineFlag) {
		this.mineFlag = mineFlag;
	}
	
}
