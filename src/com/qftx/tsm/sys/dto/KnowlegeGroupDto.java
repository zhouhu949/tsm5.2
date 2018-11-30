package com.qftx.tsm.sys.dto;

import com.qftx.tsm.sys.bean.Knowlege;


public class KnowlegeGroupDto extends Knowlege {

	private static final long serialVersionUID = 1754653721498613713L;
	private String groupName;
	private String knowlegeNum;
	private Integer groupSort;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getKnowlegeNum() {
		return knowlegeNum;
	}

	public void setKnowlegeNum(String knowlegeNum) {
		this.knowlegeNum = knowlegeNum;
	}

	public Integer getGroupSort() {
		return groupSort;
	}

	public void setGroupSort(Integer groupSort) {
		this.groupSort = groupSort;
	}
	
}
