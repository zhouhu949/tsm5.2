package com.qftx.tsm.cust.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;

public class ResCustinfoLogBean extends BaseObject {

	private static final long serialVersionUID = -3395815652273257133L;

	private String trclId;//trcl_id
	private String sesId;//资源ID
	private String orgId;//机构ID
	private String userId;//用户ID
	private String name;//姓名
	private String context;//备注内容
	private Date inputtime;//输入时间
	private Date updatetime;//修改时间
	private String inputName;//录入人姓名
	private Date nextConcatTime;//下次联系时间
	private Integer isDel;
	
	public String getTrclId() {
		return trclId;
	}
	public void setTrclId(String trclId) {
		this.trclId = trclId;
	}
	public String getSesId() {
		return sesId;
	}
	public void setSesId(String sesId) {
		this.sesId = sesId;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public Date getInputtime() {
		return inputtime;
	}
	public void setInputtime(Date inputtime) {
		this.inputtime = inputtime;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public String getInputName() {
		return inputName;
	}
	public void setInputName(String inputName) {
		this.inputName = inputName;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}//是否删除：0-否，1-是
	public Date getNextConcatTime() {
		return nextConcatTime;
	}
	public void setNextConcatTime(Date nextConcatTime) {
		this.nextConcatTime = nextConcatTime;
	}
}
