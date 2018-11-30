package com.qftx.tsm.sys.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;

/**
 * 挂机短信发送规则
 * */
public class HookSmsConfig extends BaseObject{
	
	private static final long serialVersionUID = 372037701981504186L;
	private String id;
	private String orgId; 			// 机构ID
	private String content;         // 短信内容	
	private String sendType;		// 发送对象 1：资源，2：签约客户，3：沉默客户，4：流失客户 ,5：意向客户（会有多个，逗号分割）
	private String status;			// 呼叫类型 0：呼入，1：呼出（会有多个，逗号分割）
	private String sendCondi;		// 发送条件 1：接通，0：未接通（会有多个，逗号分割 )
	private Integer enable;			// 1:启用，0：不启用
	private Date updateTime;
	private Date inputTime;
	private String inputAcc;
	private String updateAcc;	
	private Integer isDel;			// 册除状态1-删除，0-未删除
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSendType() {
		return sendType;
	}
	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSendCondi() {
		return sendCondi;
	}
	public void setSendCondi(String sendCondi) {
		this.sendCondi = sendCondi;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getInputTime() {
		return inputTime;
	}
	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}
	public String getInputAcc() {
		return inputAcc;
	}
	public void setInputAcc(String inputAcc) {
		this.inputAcc = inputAcc;
	}
	public String getUpdateAcc() {
		return updateAcc;
	}
	public void setUpdateAcc(String updateAcc) {
		this.updateAcc = updateAcc;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	public Integer getEnable() {
		return enable;
	}
	public void setEnable(Integer enable) {
		this.enable = enable;
	}

}
