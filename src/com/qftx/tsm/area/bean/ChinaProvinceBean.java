package com.qftx.tsm.area.bean;

import com.qftx.common.domain.BaseObject;

// 省
public class ChinaProvinceBean extends BaseObject {
	private static final long serialVersionUID = 1L;

	private Integer pid;
	private String pname;
	
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
}
