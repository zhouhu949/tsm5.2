package com.qftx.tsm.credit.bean;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qftx.common.domain.BaseObject;
/* import_lead_result 放款信息导入_分析结果*/
public class ImportLeadResultBean extends BaseObject {
	private static final long serialVersionUID = -6199846326855512338L;
	
	private String id;	//分析结果ID
	private String orgId;	//机构ID
	private String resId;	//临时资源ID
	private String fileId;	//导入批次ID
	private Integer totalNum;	//总共条数
	private Integer successNum;	//导入成功条数
	private Integer failNum;	//导入失败条数
	private Integer phoneRepeat;	//电话号码重复
	private Integer phoneFormart;	//电话号码格式错误
	private Integer leadCodeRepeat;	//放款编号重复
	private Integer defectRequired; // 缺少必填项
	private Integer ownIllegalChar; // 拥有非法字符
	private Integer formartError; // 格式错误
	private String status;	//状态 0:正在导入，1：导入成功
	private Date startTime;	//分析开始时间
	private Date endTime;	//分析结束时间
	private Date updatetime;	//修改时间
	private Date inputtime;	//录入时间(计划日期)
	private Integer isDel;	//册除状态1-删除，0-未删除
	private String inputAcc; // 录入账号
	private String mark; // 1：资源库，2：意向库，3：签约库
	private String processId; // 销售进程ID
	private Integer custNameNotExist; // 客户名称不存在
	private Integer isDetail; // 1:联系人导入
	
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
	public Integer getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
	public Integer getSuccessNum() {
		return successNum;
	}
	public void setSuccessNum(Integer successNum) {
		this.successNum = successNum;
	}
	public Integer getFailNum() {
		return failNum;
	}
	public void setFailNum(Integer failNum) {
		this.failNum = failNum;
	}
	public Integer getPhoneRepeat() {
		return phoneRepeat;
	}
	public void setPhoneRepeat(Integer phoneRepeat) {
		this.phoneRepeat = phoneRepeat;
	}
	public Integer getPhoneFormart() {
		return phoneFormart;
	}
	public void setPhoneFormart(Integer phoneFormart) {
		this.phoneFormart = phoneFormart;
	}
	public Integer getLeadCodeRepeat() {
		return leadCodeRepeat;
	}
	public void setLeadCodeRepeat(Integer leadCodeRepeat) {
		this.leadCodeRepeat = leadCodeRepeat;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
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
	public Integer getDefectRequired() {
		return defectRequired;
	}
	public void setDefectRequired(Integer defectRequired) {
		this.defectRequired = defectRequired;
	}
	public Integer getOwnIllegalChar() {
		return ownIllegalChar;
	}
	public void setOwnIllegalChar(Integer ownIllegalChar) {
		this.ownIllegalChar = ownIllegalChar;
	}
	public Integer getFormartError() {
		return formartError;
	}
	public void setFormartError(Integer formartError) {
		this.formartError = formartError;
	}
	public String getInputAcc() {
		return inputAcc;
	}
	public void setInputAcc(String inputAcc) {
		this.inputAcc = inputAcc;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public Integer getCustNameNotExist() {
		return custNameNotExist;
	}
	public void setCustNameNotExist(Integer custNameNotExist) {
		this.custNameNotExist = custNameNotExist;
	}
	public Integer getIsDetail() {
		return isDetail;
	}
	public void setIsDetail(Integer isDetail) {
		this.isDetail = isDetail;
	}
	
}