package com.qftx.tsm.progress.dto;

import com.qftx.base.util.DateUtil;

import java.io.Serializable;
import java.util.Date;

public class ProgressBarDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8782568241327750790L;
	private String orgId;	//单位ID
	private String id;		//进度ID
	private String account;	//用户账号
	private String type;	//进度类型
	private int total;		//任务总量
	private int current;	//当前完成
	private int percent;	//完成百分比
	private Date startTime;	//开始时间
	private String name;
	private String startaTimeStr;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
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
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getCurrent() {
		return current;
	}
	public void setCurrent(int current) {
		this.current = current;
	}
	public int getPercent() {
		if(total!=0) return (int) (current*100d/total);
		return percent;
	}
	public void setPercent(int percent) {
		this.percent = percent;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public String getStartaTimeStr() {
		return DateUtil.formatDate(startTime);
	}
	public void setStartaTimeStr(String startaTimeStr) {
		this.startaTimeStr = startaTimeStr;
	}
	public String getName() {
		if(ProgressType.HIGH_SEA_PROGRESS.getValue().equals(type)){
			return "公海客户回收";
		}else if(ProgressType.LOSING_CUST_RECYLE.getValue().equals(type)){
			return "流失客户回收";
		}else if(ProgressType.CUST_MOVE.getValue().equals(type)){
			return "销售交接";
		} 
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
