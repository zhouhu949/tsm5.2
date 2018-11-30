package com.qftx.tsm.plan.user.month.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qftx.common.domain.BaseObject;

import java.util.Date;
/* plan_usermonth_cust 计划_个人月计划_重点客户*/
public class PlanUsermonthCustBean extends BaseObject {
	private static final long serialVersionUID = -2711548036057857734L;
	
	private String id; // null
	private String orgId; // 机构ID
	private String planId;	//个人月计划ID
	private String custId;	//客户ID
	private String custName;	//客户名称
	private String company;	//客户名称
	private String custStatus;	//销售进程
	private Integer singcustNum;	//计划签约客户数（意向客户为1，签约客户为0）
	private Integer factSingcustNum;	//实际签约客户数
	private Double planMoney;	//计划签约金额
	private Double factMoney;	//实际签约金额
	private Integer status;	//重点客户完成情况（0：未完成1：完成）
	private String context;	//备注说明
	private Date updatetime;	//修改时间
	private Date inputtime;	//录入时间(计划日期)
	private Integer isDel;	//册除状态1-删除，0-未删除
	
	private String name;//客户名称
	private String saleProcessName;//销售进程名称
	private String[] custIds;
	
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
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCustStatus() {
		return custStatus;
	}
	public void setCustStatus(String custStatus) {
		this.custStatus = custStatus;
	}
	public Integer getSingcustNum() {
		return singcustNum;
	}
	public void setSingcustNum(Integer singcustNum) {
		this.singcustNum = singcustNum;
	}
	public Integer getFactSingcustNum() {
		return factSingcustNum;
	}
	public void setFactSingcustNum(Integer factSingcustNum) {
		this.factSingcustNum = factSingcustNum;
	}
	public Double getPlanMoney() {
		return planMoney;
	}
	public void setPlanMoney(Double planMoney) {
		this.planMoney = planMoney;
	}
	public Double getFactMoney() {
		return factMoney;
	}
	public void setFactMoney(Double factMoney) {
		this.factMoney = factMoney;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSaleProcessName() {
		return saleProcessName;
	}
	public void setSaleProcessName(String saleProcessName) {
		this.saleProcessName = saleProcessName;
	}
	public String[] getCustIds() {
		return custIds;
	}
	public void setCustIds(String[] custIds) {
		this.custIds = custIds;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	@Override
	public boolean equals(Object obj){
		if( obj==null){
			return false;
		}else{
			if(obj instanceof PlanUsermonthCustBean){
				PlanUsermonthCustBean bean = (PlanUsermonthCustBean) obj;
				if((this.custId==null?bean.custId==null:this.custId.equals(bean.custId))&&
						(this.singcustNum==null?bean.singcustNum==null:this.singcustNum.equals(bean.singcustNum))&&
						(this.planMoney==null?bean.planMoney==null:this.planMoney.equals(bean.planMoney))&&
						(this.context==null?bean.context==null:this.context.equals(bean.context))){
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
			
		}
	}
}
