package com.qftx.tsm.cust.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;

public class CustOptorBean extends BaseObject {
	private static final long serialVersionUID = -1575323035138134084L;

	private String custOptorId;        //客户操作ID
    private String custId;             //客户ID
    private Short type;  			   //操作类型：10-客户：淘到客户，11-客户：系统到期，12-客户：主动放弃，13-客户：再分配，14-客户：交接，15-客户：签约
    private String transferAcc;        //转入至
    private String transferedAcc;      //转出自
    private String saleProcessId;      //销售进程选项ID
    private Date optorResDate;         //操作时间
    private String optoerAcc;          //操作者
    private String ownerAcc;           //所有者
    private String ownerName;           //所有者
    private String reason;				//记录客户交接原因
    private String orgId;              //机构编号
    private String company;            //客户的单位名称
    private String defined1;
    private String defined2;
    private String defined3;
    private String defined4;
    private String defined5;
    private String groupId;
	public String getCustOptorId() {
		return custOptorId;
	}
	public void setCustOptorId(String custOptorId) {
		this.custOptorId = custOptorId;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public Short getType() {
		return type;
	}
	public void setType(Short type) {
		this.type = type;
	}
	public String getTransferAcc() {
		return transferAcc;
	}
	public void setTransferAcc(String transferAcc) {
		this.transferAcc = transferAcc;
	}
	public String getTransferedAcc() {
		return transferedAcc;
	}
	public void setTransferedAcc(String transferedAcc) {
		this.transferedAcc = transferedAcc;
	}
	public String getSaleProcessId() {
		return saleProcessId;
	}
	public void setSaleProcessId(String saleProcessId) {
		this.saleProcessId = saleProcessId;
	}
	public Date getOptorResDate() {
		return optorResDate;
	}
	public void setOptorResDate(Date optorResDate) {
		this.optorResDate = optorResDate;
	}
	public String getOptoerAcc() {
		return optoerAcc;
	}
	public void setOptoerAcc(String optoerAcc) {
		this.optoerAcc = optoerAcc;
	}
	public String getOwnerAcc() {
		return ownerAcc;
	}
	public void setOwnerAcc(String ownerAcc) {
		this.ownerAcc = ownerAcc;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getDefined1() {
		return defined1;
	}
	public void setDefined1(String defined1) {
		this.defined1 = defined1;
	}
	public String getDefined2() {
		return defined2;
	}
	public void setDefined2(String defined2) {
		this.defined2 = defined2;
	}
	public String getDefined3() {
		return defined3;
	}
	public void setDefined3(String defined3) {
		this.defined3 = defined3;
	}
	public String getDefined4() {
		return defined4;
	}
	public void setDefined4(String defined4) {
		this.defined4 = defined4;
	}
	public String getDefined5() {
		return defined5;
	}
	public void setDefined5(String defined5) {
		this.defined5 = defined5;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
    
}
