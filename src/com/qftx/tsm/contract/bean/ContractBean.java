package com.qftx.tsm.contract.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qftx.common.domain.BaseObject;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

public class ContractBean extends BaseObject {

	private static final long serialVersionUID = 108962983068266029L;
	private String id;//id
	private String contractName;//合同名称
	private String orgId;//机构ID
	private String userId;//销售人员
	private String groupId;//部门ID
	private String custId;//客户ID
	private String custName;//客户名称(这个名称可能与实际名称不同，显示以这个为主)
	private String company;//单位名称（5.1新增）
	private String code;//合同编号
	private BigDecimal money;//合同金额
	private String body;//合同主体
	private String payContext;//付款计划说明
	private Date effectiveDate;//生效时间
	private String storeIndex;//合同库索引
	private String signUsername;//签约人姓名
	private String signUserid;//签约人
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date signDate;//签约日期
	private String status;//状态（1执行中，0执行完毕）
	private Date invalidDate;//失效时间
	private String authState;//审核状态(0未通过，1未审核，2通过)
	private Date updatetime;//修改时间
	private Date inputtime;//录入时间
	private Integer isDel;
	private String cancleRemark;//取消原因
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContractName() {
		return contractName;
	}
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
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
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getPayContext() {
		return payContext;
	}
	public void setPayContext(String payContext) {
		this.payContext = payContext;
	}
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public String getStoreIndex() {
		return storeIndex;
	}
	public void setStoreIndex(String storeIndex) {
		this.storeIndex = storeIndex;
	}
	public String getSignUsername() {
		return signUsername;
	}
	public void setSignUsername(String signUsername) {
		this.signUsername = signUsername;
	}
	public String getSignUserid() {
		return signUserid;
	}
	public void setSignUserid(String signUserid) {
		this.signUserid = signUserid;
	}
	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
	public Date getSignDate() {
		return signDate;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getInvalidDate() {
		return invalidDate;
	}
	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
	}
	public String getAuthState() {
		return authState;
	}
	public void setAuthState(String authState) {
		this.authState = authState;
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
	}//册除状态1-删除，0-未删除
	public String getCancleRemark() {
		return cancleRemark;
	}
	public void setCancleRemark(String cancleRemark) {
		this.cancleRemark = cancleRemark;
	}
	
}
