package com.qftx.tsm.plan.user.month.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;

public class PlanUsermonthPointBean extends BaseObject{
	    
	private static final long serialVersionUID = -688802171662157699L;
	private String id;	//null
	private String orgId;	//机构ID
	private String suId;	//个人月计划ID
	private String type;	//计划类型[0意向客户，1签约客户，2金额，当完成了有记录，没有完成不插入数据
	private String status;	//执行状态(0未领取，1已领取)
	private String context;	//说明
	private Date updatetime;	//修改时间
	private Date inputtime;	//录入时间(计划日期)
	private int isDel;	//册除状态1-删除，0-未删除
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getSuId() {
		return suId;
	}
	public void setSuId(String suId) {
		this.suId = suId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public Date getInputtime() {
		return inputtime;
	}
	public void setInputtime(Date inputtime) {
		this.inputtime = inputtime;
	}
	public int getIsDel() {
		return isDel;
	}
	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}

}

