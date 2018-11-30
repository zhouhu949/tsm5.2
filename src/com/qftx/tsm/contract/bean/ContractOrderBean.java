package com.qftx.tsm.contract.bean;

import com.qftx.common.domain.BaseObject;

import java.math.BigDecimal;
import java.util.Date;

public class ContractOrderBean extends BaseObject {

	private static final long serialVersionUID = 7910403171184857366L;

	private String id;//id
	private String orgId;//机构ID
	private String saleAcc;//订单所有者帐号
	private String userId;//提交人
	private String groupId;//部门
	private String caId;//合同ID
	private String custName;//客户名称
	private String custId;//客户ID
	private String code;//订单编号
	private String payType;//支付方式[0、1、2、3]
	private BigDecimal money;//订单金额
	private Date tradeDate;//交易日期
	private Date effectiveDate;//生效时间
	private Date invalidDate;//失效时间(服务类型的有，单产品没有)
	private String status;//订单状态（1未执行，2执行中，0执行完毕）
	private String context;//订单说明
	private Date updatetime;//修改时间
	private Date inputtime;//录入时间
	private Integer isDel;//册除状态1-删除，0-未删除
	private Integer authState;//审核状态(0未上报，1未审核，2通过，3驳回)
	private String authDesc;//审核意见
	
	/**5.1新增字段*/
	private String linkName;//联系人 5.1新增
	private String company;//单位名称 5.1新增
	private String linkPhone;//联系电话 5.1新增
	private String linkAddress;//联系地址 5.1新增
	private String courierNumber;//物流单号 5.1新增
	private Integer courierStatus;//物流状态 1 暂无记录 2 在途中 3 派送中 4 已签收 5 用户拒签 6 疑难件 7 无效单8 超时单 9 签收失败 10 退回
	private String salesOppId;//销售机会ID 5.1新增
	private String productNames;//订单包含产品名称#分割 5.1新增 用于订单导出
	private String productIds;//订单包含产品ID#分割 5.1新增 用于订单导出
	private Date reportDate;//上报日期
	
	public Integer getAuthState() {
		return authState;
	}
	public void setAuthState(Integer authState) {
		this.authState = authState;
	}
	public String getAuthDesc() {
		return authDesc;
	}
	public void setAuthDesc(String authDesc) {
		this.authDesc = authDesc;
	}
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
	public String getSaleAcc() {
		return saleAcc;
	}
	public void setSaleAcc(String saleAcc) {
		this.saleAcc = saleAcc;
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
	public String getCaId() {
		return caId;
	}
	public void setCaId(String caId) {
		this.caId = caId;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public Date getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public Date getInvalidDate() {
		return invalidDate;
	}
	public void setInvalidDate(Date invalidDate) {
		this.invalidDate = invalidDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
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
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getLinkPhone() {
		return linkPhone;
	}
	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}
	public String getLinkAddress() {
		return linkAddress;
	}
	public void setLinkAddress(String linkAddress) {
		this.linkAddress = linkAddress;
	}
	public String getCourierNumber() {
		return courierNumber;
	}
	public void setCourierNumber(String courierNumber) {
		this.courierNumber = courierNumber;
	}
	public Integer getCourierStatus() {
		return courierStatus;
	}
	public void setCourierStatus(Integer courierStatus) {
		this.courierStatus = courierStatus;
	}
	public String getSalesOppId() {
		return salesOppId;
	}
	public void setSalesOppId(String salesOppId) {
		this.salesOppId = salesOppId;
	}
	public String getProductNames() {
		return productNames;
	}
	public void setProductNames(String productNames) {
		this.productNames = productNames;
	}
	public String getProductIds() {
		return productIds;
	}
	public void setProductIds(String productIds) {
		this.productIds = productIds;
	}
	public Date getReportDate() {
		return reportDate;
	}
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	
}
