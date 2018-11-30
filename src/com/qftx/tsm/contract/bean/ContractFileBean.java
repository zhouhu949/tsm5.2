package com.qftx.tsm.contract.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;

public class ContractFileBean extends BaseObject {

	private static final long serialVersionUID = -132389535533715575L;
	
	private String id;//id
	private String orgId;//机构ID
	private String caId;//合同ID
	private String fileId;//附件表ID(sys_file的ID)
	private Integer type;//1-合同 2-订单
	private Date updatetime;//修改时间
	private Date inputtime;//录入时间
	private Integer isDel;//册除状态1-删除，0-未删除
	
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
	public String getCaId() {
		return caId;
	}
	public void setCaId(String caId) {
		this.caId = caId;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
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
	
}
