package com.qftx.tsm.contract.bean;

import com.qftx.common.domain.BaseObject;

import java.math.BigDecimal;
import java.util.Date;

public class ContractOrderDetailBean extends BaseObject {

	private static final long serialVersionUID = 8751645037597912396L;

	private String id;//id
	private String orgId;//机构ID
	private String orderId;//订单ID(contract_order的ID)
	private String code;//订单编号
	private String productName;//产品名称
	private String productModel;//产品型号
	private String productType;//产品规格
	private String productId;//产品ID
	private BigDecimal productPrice;//产品原价
	private BigDecimal buyPrice;//购买交易价
	private BigDecimal buyNum;//购买数量
	private BigDecimal buyMoney;//购买总价
	private String context;//备注
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
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductModel() {
		return productModel;
	}
	public void setProductModel(String productModel) {
		this.productModel = productModel;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public BigDecimal getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}
	public BigDecimal getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(BigDecimal buyPrice) {
		this.buyPrice = buyPrice;
	}
	public BigDecimal getBuyNum() {
		return buyNum;
	}
	public void setBuyNum(BigDecimal buyNum) {
		this.buyNum = buyNum;
	}
	public BigDecimal getBuyMoney() {
		return buyMoney;
	}
	public void setBuyMoney(BigDecimal buyMoney) {
		this.buyMoney = buyMoney;
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
}
