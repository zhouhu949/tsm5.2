package com.qftx.tsm.main.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;

public class MainInfoBean extends BaseObject {
	private static final long serialVersionUID = -9134522569934724303L;
	
	private String id;//ID编码
	private String orgId;//机构ID
	private String type;//类型(0资源，1意向)
	private String account;//账户
	private Integer totalNum;//总数
	private Integer signNum;//转签约
	private Integer custNum;//转意向
	private Integer poolNum;//公海池
	private Integer noNum;//无进展
	private Integer callNum;//时长
	private Date inputtime;//录入时间
	private Date updateDate;//更新时间
	private Integer isSet;//录音主机设置：0-未设置，1-设置
	
	private String dateFmt;//日期格式化
	
	public MainInfoBean(String orgId,String type,String account) {
		this.orgId = orgId;
		this.type = type;
		this.account = account;
	}
	
	public MainInfoBean() {}
	
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Integer getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
	public Integer getSignNum() {
		return signNum;
	}
	public void setSignNum(Integer signNum) {
		this.signNum = signNum;
	}
	public Integer getCustNum() {
		return custNum;
	}
	public void setCustNum(Integer custNum) {
		this.custNum = custNum;
	}
	public Integer getPoolNum() {
		return poolNum;
	}
	public void setPoolNum(Integer poolNum) {
		this.poolNum = poolNum;
	}
	public Integer getNoNum() {
		return noNum;
	}
	public void setNoNum(Integer noNum) {
		this.noNum = noNum;
	}
	public Integer getCallNum() {
		return callNum;
	}
	public void setCallNum(Integer callNum) {
		this.callNum = callNum;
	}
	public Date getInputtime() {
		return inputtime;
	}
	public void setInputtime(Date inputtime) {
		this.inputtime = inputtime;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Integer getIsSet() {
		return isSet;
	}
	public void setIsSet(Integer isSet) {
		this.isSet = isSet;
	}
	public String getDateFmt() {
		return dateFmt;
	}
	public void setDateFmt(String dateFmt) {
		this.dateFmt = dateFmt;
	}
}
