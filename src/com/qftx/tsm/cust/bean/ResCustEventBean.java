package com.qftx.tsm.cust.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;

public class ResCustEventBean extends BaseObject {
	private static final long serialVersionUID = -3710233885484443640L;
	private String id;//id
	private String orgId;//机构名
	private String userId;//用户名
	private String custId;//资源ID
	private String type;//操作类型(1行动记录、2服务记录、3通话记录、4评论记录、5合同记录、6订单记录)
	private String state;//事件触发节点，与type合用(1-资源 2-意向 3-签约客户 4-沉默客户 5-流失客户)
	private String data;//资源JSON数据，根据不同的数据触发节点不，数据参数不同
	private String context;//描述
	private Date inputtime;//输入时间
	private Date updatetime;//修改时间
	private Integer isDel;//册除状态1-删除，0-未删除
	private String lifeCode;//生命周期编码
	
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
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
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	public String getLifeCode() {
		return lifeCode;
	}
	public void setLifeCode(String lifeCode) {
		this.lifeCode = lifeCode;
	}
}
