package com.qftx.tsm.cust.dto;

/**
 * 资源同步 推送跟进信息 json体
 */
public class TsmCustSynFollowDto{

	private String id;// 主键Id
	private String custName; // 客户名称|单位名称
	private String ownerAcc; // 所有人
	private String followAcc; // 跟进人
	private String linkName; // 客户联系人|客户姓名
	private String linkPhone; // 客户联系人电话|客户联系电话
	private String saleProcess; // 销售进程
	private String custStatus; // 客户状态
	private String actionDate; // 最近联系时间 格式：2014-12-13 05:12:15
	private String actionType; // 联系方式 默认值：电话联系
	private String nextActionDate; // 下次联系时间 格式：2014-12-13 05:12:15
	private String nextActionType; // 下次联系方式
	private String custType; // 客户类型
	private String effectiveness; // 联系有效性 默认值：有效联系
	private String product; // 适用产品。多产品之间使用#号分割
	private String labelCode; // 行动标签。多标签之间使用#号分割
	private String actionInfo; // 联系小记。字数不超过2000字
	
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getOwnerAcc() {
		return ownerAcc;
	}
	public void setOwnerAcc(String ownerAcc) {
		this.ownerAcc = ownerAcc;
	}
	public String getFollowAcc() {
		return followAcc;
	}
	public void setFollowAcc(String followAcc) {
		this.followAcc = followAcc;
	}
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	public String getLinkPhone() {
		return linkPhone;
	}
	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}
	public String getSaleProcess() {
		return saleProcess;
	}
	public void setSaleProcess(String saleProcess) {
		this.saleProcess = saleProcess;
	}
	public String getCustStatus() {
		return custStatus;
	}
	public void setCustStatus(String custStatus) {
		this.custStatus = custStatus;
	}
	public String getActionDate() {
		return actionDate;
	}
	public void setActionDate(String actionDate) {
		this.actionDate = actionDate;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getNextActionDate() {
		return nextActionDate;
	}
	public void setNextActionDate(String nextActionDate) {
		this.nextActionDate = nextActionDate;
	}
	public String getNextActionType() {
		return nextActionType;
	}
	public void setNextActionType(String nextActionType) {
		this.nextActionType = nextActionType;
	}
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public String getEffectiveness() {
		return effectiveness;
	}
	public void setEffectiveness(String effectiveness) {
		this.effectiveness = effectiveness;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getLabelCode() {
		return labelCode;
	}
	public void setLabelCode(String labelCode) {
		this.labelCode = labelCode;
	}
	public String getActionInfo() {
		return actionInfo;
	}
	public void setActionInfo(String actionInfo) {
		this.actionInfo = actionInfo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
