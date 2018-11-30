package com.qftx.tsm.area.bean;

import com.qftx.common.domain.BaseObject;

// 市
public class ChinaCityBean extends BaseObject {
	private static final long serialVersionUID = 1L;
	
	private Integer cid;
	private String cname;
	private Integer pid;
	
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	
}
