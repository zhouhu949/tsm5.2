package com.qftx.tsm.callrecord.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;

/** 
 * 通信管理 漏接记录
 * @author: zwj
 * @since: 2015-12-9  上午11:37:09
 * @history: 4.x
 */
public class MissCallRecordBean extends BaseObject {
	private static final long serialVersionUID = 8271591162679687228L;
	
	private String id;
	private String orgId;
	private String name; // 客户名称
	private String phone; // 来电号码
	private String region; // 归属地
	private String inputAcc; // 被呼成员账号
	private String inputName; // 被呼成员名称
	private Date inputDate; // 来电时间
	private Integer missNum; // 未接来电次数
	private Integer status; // 处理状态：0--未处理，1--已处理
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getInputAcc() {
		return inputAcc;
	}
	public void setInputAcc(String inputAcc) {
		this.inputAcc = inputAcc;
	}
	public String getInputName() {
		return inputName;
	}
	public void setInputName(String inputName) {
		this.inputName = inputName;
	}
	public Date getInputDate() {
		return inputDate;
	}
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}
	public Integer getMissNum() {
		return missNum;
	}
	public void setMissNum(Integer missNum) {
		this.missNum = missNum;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}