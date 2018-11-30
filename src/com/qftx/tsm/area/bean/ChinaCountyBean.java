package com.qftx.tsm.area.bean;

import com.qftx.common.domain.BaseObject;

// 区
public class ChinaCountyBean extends BaseObject {
	private static final long serialVersionUID = 1L;
	
	private Integer oid;
	private String oname;
	private Integer cid;
	
	public Integer getOid() {
		return oid;
	}
	public void setOid(Integer oid) {
		this.oid = oid;
	}
	public String getOname() {
		return oname;
	}
	public void setOname(String oname) {
		this.oname = oname;
	}
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	
}
