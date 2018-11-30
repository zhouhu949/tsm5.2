package com.qftx.tsm.plan.year.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AllYearPlanDTO {
	private String [] months={"1","2","3","4","5","6","7","8","9","10","11","12","total"};
	private List<GroupYearPlanDTO> list;
	private Map<String,Double> totalByMonth;
	private boolean ifInit;
	
	public String[] getMonths() {
		return months;
	}
	public void setMonths(String[] months) {
		this.months = months;
	}
	public List<GroupYearPlanDTO> getList() {
		return list;
	}
	public void setList(List<GroupYearPlanDTO> list) {
		this.list = list;
	}
	public Map<String, Double> getTotalByMonth() {
		return totalByMonth;
	}
	public void setTotalByMonth(Map<String, Double> totalByMonth) {
		this.totalByMonth = totalByMonth;
	}
	public void add(GroupYearPlanDTO gyDto){
		if(list==null){
			list= new ArrayList<GroupYearPlanDTO>();
		}
		list.add(gyDto);
	}
	public boolean isIfInit() {
		return ifInit;
	}
	public void setIfInit(boolean ifInit) {
		this.ifInit = ifInit;
	}
	
}
