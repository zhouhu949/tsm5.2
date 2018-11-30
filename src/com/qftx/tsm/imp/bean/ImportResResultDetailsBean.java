package com.qftx.tsm.imp.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;
/* import_res_result_details 资源导入_分析结果_详情*/
public class ImportResResultDetailsBean extends BaseObject {
	private static final long serialVersionUID = -901882882189551883L;
	
	private String id;	//分析详情ID
	private String orgId;	//机构ID
	private String resId;	//临时资源ID
	private String fileId;	//导入批次ID
	private String erroCode;	//错误类型
	private String status;	//状态
	private Date updatetime;	//修改时间
	private Date inputtime;	//录入时间(计划日期)
	private Integer isDel;	//册除状态1-删除，0-未删除
	private String errorMsg; // 错误详细信息
	
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
	public String getResId() {
		return resId;
	}
	public void setResId(String resId) {
		this.resId = resId;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getErroCode() {
		return erroCode;
	}
	public void setErroCode(String erroCode) {
		this.erroCode = erroCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}