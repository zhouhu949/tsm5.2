package com.qftx.tsm.credit.bean;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qftx.common.domain.BaseObject;

/*
*工作流程设置表
*/
public class TsmCustWorkflowSetBean extends BaseObject implements Serializable{
	private static final long serialVersionUID = 1L;
	
	// 审核条件
	public static final int TYPE_DISABLE = 0;	//0： 总不
	public static final int TYPE_ENABLE = 1;	//1： 总是
	
	/**
	 * 工作流程id
	 */
	private String workflowId;
	/**
	 * 审核流程名称
	 */
	private String workflowName;
	/**
	 * 审核流程描述
	 */
	private String workflowDescribe;
	/**
	 * 单位id
	 */
	private String orgId;
	/**
	 * 审核条件：0： 总不 ；1： 总是
	 */
	private Integer type;
	/**
	 * 审核次数：1,2,3 次
	 */
	private Integer auditNum;
	/**
	 * 一级 审核人账号
	 */
	private String auditorAcc1;
	/**
	 * 二级 审核人账号
	 */
	private String auditorAcc2;
	/**
	 * 三级 审核人账号
	 */
	private String auditorAcc3;
	
	/**
	 * 一级 审核人名称
	 */
	private String auditorName1;
	/**
	 * 二级 审核人名称
	 */
	private String auditorName2;
	/**
	 * 三级 审核人名称
	 */
	private String auditorName3;
	
	
	/**
	 * 录入人
	 */
	private String inputerAcc;
	/**
	 * 录入时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date inputtime;

	public String getWorkflowId() {
		return workflowId;
	}
	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}
	public String getWorkflowName() {
		return workflowName;
	}
	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}
	public String getWorkflowDescribe() {
		return workflowDescribe;
	}
	public void setWorkflowDescribe(String workflowDescribe) {
		this.workflowDescribe = workflowDescribe;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getAuditNum() {
		return auditNum;
	}
	public void setAuditNum(Integer auditNum) {
		this.auditNum = auditNum;
	}
	public String getAuditorAcc1() {
		return auditorAcc1;
	}
	public void setAuditorAcc1(String auditorAcc1) {
		this.auditorAcc1 = auditorAcc1;
	}
	public String getAuditorAcc2() {
		return auditorAcc2;
	}
	public void setAuditorAcc2(String auditorAcc2) {
		this.auditorAcc2 = auditorAcc2;
	}
	public String getAuditorAcc3() {
		return auditorAcc3;
	}
	public void setAuditorAcc3(String auditorAcc3) {
		this.auditorAcc3 = auditorAcc3;
	}
	public String getInputerAcc() {
		return inputerAcc;
	}
	public void setInputerAcc(String inputerAcc) {
		this.inputerAcc = inputerAcc;
	}
	public Date getInputtime() {
		return inputtime;
	}

	public void setInputtime(Date inputtime) {
		this.inputtime = inputtime;
	}
	public String getAuditorName1() {
		return auditorName1;
	}
	public void setAuditorName1(String auditorName1) {
		this.auditorName1 = auditorName1;
	}
	public String getAuditorName2() {
		return auditorName2;
	}
	public void setAuditorName2(String auditorName2) {
		this.auditorName2 = auditorName2;
	}
	public String getAuditorName3() {
		return auditorName3;
	}
	public void setAuditorName3(String auditorName3) {
		this.auditorName3 = auditorName3;
	}
	
}