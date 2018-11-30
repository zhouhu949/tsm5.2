package com.qftx.tsm.callrecord.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;

/** 
 * 黑名单
 * @author: zwj
 * @since: 2015-12-9  上午11:37:09
 * @history: 4.x
 */
public class CallRecordBlack extends BaseObject {
	private static final long serialVersionUID = 8271591162679687228L;

	private String id;
	private String orgId;		// 单位ID
	private String phone;	// 号码
	private String inputAcc;// 添加人
	private Date inputDate; // 添加时间
	private String remark; // 备注
	private Integer reason; // 原因：1-诈骗电话，2-骚扰电话，3-非法业务，4-中介，5-商家，6-其他
	
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getInputAcc() {
		return inputAcc;
	}
	public void setInputAcc(String inputAcc) {
		this.inputAcc = inputAcc;
	}
	
	public Date getInputDate() {
		return inputDate;
	}
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getReason() {
		return reason;
	}
	public void setReason(Integer reason) {
		this.reason = reason;
	}

}