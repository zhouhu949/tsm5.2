package com.qftx.tsm.sys.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;

/**
 * 服务回访实体类
 */
public class ServiceVisit extends BaseObject {
	private static final long serialVersionUID = 8633899852390902136L;

	private String id;

	private Date visitingDate;  // 回访时间

	private String visitingAcc; // 回访账号

	private String visitingName; // 回访人姓名

	private String custId; // 回访客户ID

	private String visitingType; // 回访方式：

	private String serviceLevel; // 服务评级

	private Short effectiveness; // 联系有效性

	private String labelCode; // 服务标签代码
	
	private String labelName; // 服务标签名称

	private String remark; // 联系详情

	private Short isDel;

	private String inputerAcc;

	private Date inputdate;

	private String modifierAcc;

	private Date modifydate;

	private String orgId;

	private Date nextVisitingDate; // 下次回访时间
	
	public ServiceVisit() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getVisitingDate() {
		return visitingDate;
	}

	public void setVisitingDate(Date visitingDate) {
		this.visitingDate = visitingDate;
	}

	public String getVisitingAcc() {
		return visitingAcc;
	}

	public void setVisitingAcc(String visitingAcc) {
		this.visitingAcc = visitingAcc;
	}

	public String getVisitingName() {
		return visitingName;
	}

	public void setVisitingName(String visitingName) {
		this.visitingName = visitingName;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getVisitingType() {
		return visitingType;
	}

	public void setVisitingType(String visitingType) {
		this.visitingType = visitingType;
	}

	public String getServiceLevel() {
		return serviceLevel;
	}

	public void setServiceLevel(String serviceLevel) {
		this.serviceLevel = serviceLevel;
	}

	public Short getEffectiveness() {
		return effectiveness;
	}

	public void setEffectiveness(Short effectiveness) {
		this.effectiveness = effectiveness;
	}

	public String getLabelCode() {
		return labelCode;
	}

	public void setLabelCode(String labelCode) {
		this.labelCode = labelCode;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Short getIsDel() {
		return isDel;
	}

	public void setIsDel(Short isDel) {
		this.isDel = isDel;
	}

	public String getInputerAcc() {
		return inputerAcc;
	}

	public void setInputerAcc(String inputerAcc) {
		this.inputerAcc = inputerAcc;
	}

	public Date getInputdate() {
		return inputdate;
	}

	public void setInputdate(Date inputdate) {
		this.inputdate = inputdate;
	}

	public String getModifierAcc() {
		return modifierAcc;
	}

	public void setModifierAcc(String modifierAcc) {
		this.modifierAcc = modifierAcc;
	}

	public Date getModifydate() {
		return modifydate;
	}

	public void setModifydate(Date modifydate) {
		this.modifydate = modifydate;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public Date getNextVisitingDate() {
		return nextVisitingDate;
	}

	public void setNextVisitingDate(Date nextVisitingDate) {
		this.nextVisitingDate = nextVisitingDate;
	}

}