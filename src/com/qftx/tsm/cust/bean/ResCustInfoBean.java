package com.qftx.tsm.cust.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;
import java.util.List;

/**
 * 资源-客户表 实体类
 * 
 * @author: lixing
 * @since: 2015年11月11日 上午9:23:43
 * @history: 4.0
 */
public class ResCustInfoBean extends BaseObject {
	private static final long serialVersionUID = -1227249200327056217L;

	private String resCustId; // 资源客户ID
	private String name; // 姓名
	private String mobilephone; // 手机
	private String telphone; // 固话
	private String company; // 单位
	private String duty; // 职务
	private String sex; // 性别：1-男， 2-女
	private String companyAddr; // 单位地址
	private String companyOrg; // 部门
	private String remark; // 备注
	private Integer type; // 类型:1-资源，2客户
	private Integer isDel; // 是否删除：0-未删除，1-删除
	private Date resLastCallDate; // 最近呼叫时间
	private Date inputDate; // 录入时间
	private String inputAcc; // 录入人
	private Date updateDate; // 修改时间
	private String updateAcc; // 修改人
	private Integer isMajor; // 是否关注：0-否，1-是
	private String resGroupId; // 资源分组ID
	private String ownerAcc; // 所有者帐号
	private String commonAcc;//共有者帐号
	private String ownerName; // 所有者名称
	private Integer status; // 资源客户状态：1-未分配，2-未跟进，3-跟进中，4-公海客户，5-资源回收站，6-已签约，7-沉默客户，8-流失客户
	private String orgId; // 机构ID
	private Date signDate; // 签约时间
	private Date ownerStartDate; // 开始拥有资源/客户时间
	private Integer inputStatus; // 导入情况1-已经导入，0-未导入
	private Long filterCount; // 筛选次数
	private Integer filterType; // 筛选操作类型：0-未筛选，1-下一条处理，2-延后处理
	private String groupId;

	// 操作类型：1-资源：管理员导入，2-资源：个人新增或导入，3-资源：分配，4-资源：沟通失败，
	// 5-资源：信息错误，6-资源：非目标客户，7-资源：人工回收，
	// 10-客户：淘到客户，11-客户：系统到期，12-客户：主动放弃，
	// 13-客户：再分配，14-客户：交接，15-客户：签约，16-沉默，17-唤醒，18-流失，21-签约客户-流失
	private Integer opreateType;

	private String coordinates; // 地理坐标
	private String fax; // 传真
	private String email; // 邮箱地址
	private String unithome; // 单位主页
	private String unitintroduction; // 单位简介
	private Date amoytocustomerDate; // 淘到客户时间
	private String qq; // qq号码
	private String keyWord; // 关键词

	private String defined1; // 自定义1
	private String defined2; // 自定义2
	private String defined3; // 自定义3
	private String defined4; // 自定义4
	private String defined5; // 自定义5
	private String defined6; // 自定义6
	private String defined7; // 自定义7
	private String defined8; // 自定义8
	private String defined9; // 自定义9
	private String defined10; // 自定义10

	private String defined11; // 自定义11
	private String defined12; // 自定义12
	private String defined13; // 自定义13
	private String defined14; // 自定义14
	private String defined15; // 自定义15
	private Date defined16;
	private Date defined17;
	private Date defined18;
	
	private String showdefined16;
	private String showdefined17;
	private String showdefined18;
	
	private String startdefined16;
	private String enddefined16;
	private String startdefined17;
	private String enddefined17;
	private String startdefined18;
	private String enddefined18;
	
	private String contractNumber; // 合同编号
	private String signAcc; // 签约人帐号
	private String signName; // 签约人名称
	private Date nextFollowDate; // 下次跟进时间
	private Date birthday; // 生日
	private Date callDatePostponed; // 延后呼叫日期
	private String reasonForCalldelay; // 延后呼叫原因
	private String recuclingTypeDetails; // 回收类型详情
	private String alternatePhone2;
	private Integer againwashCount; // 再淘资源的次数
	private Date actionDate; // 本次跟进时间

	private String lastCustFollowID; // 最近跟进记录id
	private String serviceAcc;

	private String serviceName;

	private String serviceLevel;

	private Date visitingDate;

	private Date nextVisitingDate;

	private String serviceLabelCode;

	private String serviceLabelName;
	private Date forTheCustomerTime;
	private Integer isPrecedence;

	private String lastVisitAcc;
	private Integer state;// 0-个人资源，1-企业资源，企业的多些字段(4.0新加，其余是3.1版相同)

	private Date planDate;// 计划时间(表示该资源被加入计划日期), play_userday表在做日计划时写入(4.0新增)
	private String address;// 联系地址(4.0新增)
	private String companyMoney;// 公司注册资金(4.0新增)
	private String companyUser;// 公司法人或注册人(4.0新增)
	private String companyTrade;// 所属行业(4.0新增)
	private String companyFax;// 公司传真(4.0新增)
	private String losingId;// 流失原因（4.0新增）
	private String losingRemark;// 流失备注（4.0新增）
	private Integer provinceId;// 省ID（4.0新增）
	private Integer cityId;// 市ID（4.0新增）
	private Integer countyId;// 区ID（4.0新增）
	private String lastLogId;// 最近资源备注ID（4.0新增）
	private Integer isConcat; // 0-未联系；1-已联系
	private String wangwang;// 阿里旺旺（4.0新增）
	private String scope;// 经营范围（4.0新增）
	private String mainLinkman;// 主要联系人（4.0新增，用于冗余企业资源主要联系人名称）
	private String importDeptId;
	private Integer taoNo;// 自设置号码
	private Integer signBeforeType;// 签约前类型
	private Integer signBeforeStatus;// 签约前状态
	private String lifeCode;// 生命周期编码
	private String lastOptionId; // 销售进程
	private String lastOptionName;
	private Date lastLossDate;// 流失时间
	private String lastCustTypeId;// 最近跟进客户类型id
	private String labelCode;
	private String labelName;
	private Date ownerActionDate;  //所有者联系时间
	private Integer source;//客户来源 1:自有导入 2:分配交接 3:公海取回 null:老数据     4：Ai初筛   5：在线表单 6：数据合作
	private List<String> resGroupIds;

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

	// 企业资源新增字段
	public String getResCustId() {
		return resCustId;
	}

	public void setResCustId(String resCustId) {
		this.resCustId = resCustId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCompanyAddr() {
		return companyAddr;
	}

	public void setCompanyAddr(String companyAddr) {
		this.companyAddr = companyAddr;
	}

	public String getCompanyOrg() {
		return companyOrg;
	}

	public void setCompanyOrg(String companyOrg) {
		this.companyOrg = companyOrg;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public Date getResLastCallDate() {
		return resLastCallDate;
	}

	public void setResLastCallDate(Date resLastCallDate) {
		this.resLastCallDate = resLastCallDate;
	}

	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	public String getInputAcc() {
		return inputAcc;
	}

	public void setInputAcc(String inputAcc) {
		this.inputAcc = inputAcc;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateAcc() {
		return updateAcc;
	}

	public void setUpdateAcc(String updateAcc) {
		this.updateAcc = updateAcc;
	}

	public Integer getIsMajor() {
		return isMajor;
	}

	public void setIsMajor(Integer isMajor) {
		this.isMajor = isMajor;
	}

	public String getResGroupId() {
		return resGroupId;
	}

	public void setResGroupId(String resGroupId) {
		this.resGroupId = resGroupId;
	}

	public String getOwnerAcc() {
		return ownerAcc;
	}

	public void setOwnerAcc(String ownerAcc) {
		this.ownerAcc = ownerAcc;
	}

	public String getCommonAcc() {
		return commonAcc;
	}

	public void setCommonAcc(String commonAcc) {
		this.commonAcc = commonAcc;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public Date getOwnerStartDate() {
		return ownerStartDate;
	}

	public void setOwnerStartDate(Date ownerStartDate) {
		this.ownerStartDate = ownerStartDate;
	}

	public Integer getInputStatus() {
		return inputStatus;
	}

	public void setInputStatus(Integer inputStatus) {
		this.inputStatus = inputStatus;
	}

	public Long getFilterCount() {
		return filterCount;
	}

	public void setFilterCount(Long filterCount) {
		this.filterCount = filterCount;
	}

	public Integer getFilterType() {
		return filterType;
	}

	public void setFilterType(Integer filterType) {
		this.filterType = filterType;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public Integer getOpreateType() {
		return opreateType;
	}

	public void setOpreateType(Integer opreateType) {
		this.opreateType = opreateType;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUnithome() {
		return unithome;
	}

	public void setUnithome(String unithome) {
		this.unithome = unithome;
	}

	public String getUnitintroduction() {
		return unitintroduction;
	}

	public void setUnitintroduction(String unitintroduction) {
		this.unitintroduction = unitintroduction;
	}

	public Date getAmoytocustomerDate() {
		return amoytocustomerDate;
	}

	public void setAmoytocustomerDate(Date amoytocustomerDate) {
		this.amoytocustomerDate = amoytocustomerDate;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
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

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getSignAcc() {
		return signAcc;
	}

	public void setSignAcc(String signAcc) {
		this.signAcc = signAcc;
	}

	public String getSignName() {
		return signName;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}

	public Date getNextFollowDate() {
		return nextFollowDate;
	}

	public void setNextFollowDate(Date nextFollowDate) {
		this.nextFollowDate = nextFollowDate;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getCallDatePostponed() {
		return callDatePostponed;
	}

	public void setCallDatePostponed(Date callDatePostponed) {
		this.callDatePostponed = callDatePostponed;
	}

	public String getReasonForCalldelay() {
		return reasonForCalldelay;
	}

	public void setReasonForCalldelay(String reasonForCalldelay) {
		this.reasonForCalldelay = reasonForCalldelay;
	}

	public String getRecuclingTypeDetails() {
		return recuclingTypeDetails;
	}

	public void setRecuclingTypeDetails(String recuclingTypeDetails) {
		this.recuclingTypeDetails = recuclingTypeDetails;
	}

	public String getDefined5() {
		return defined5;
	}

	public void setDefined5(String defined5) {
		this.defined5 = defined5;
	}

	public String getAlternatePhone2() {
		return alternatePhone2;
	}

	public void setAlternatePhone2(String alternatePhone2) {
		this.alternatePhone2 = alternatePhone2;
	}

	public Integer getAgainwashCount() {
		return againwashCount;
	}

	public void setAgainwashCount(Integer againwashCount) {
		this.againwashCount = againwashCount;
	}

	public Date getActionDate() {
		return actionDate;
	}

	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}

	public String getLastCustFollowID() {
		return lastCustFollowID;
	}

	public void setLastCustFollowID(String lastCustFollowID) {
		this.lastCustFollowID = lastCustFollowID;
	}

	public String getServiceAcc() {
		return serviceAcc;
	}

	public void setServiceAcc(String serviceAcc) {
		this.serviceAcc = serviceAcc;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceLevel() {
		return serviceLevel;
	}

	public void setServiceLevel(String serviceLevel) {
		this.serviceLevel = serviceLevel;
	}

	public Date getVisitingDate() {
		return visitingDate;
	}

	public void setVisitingDate(Date visitingDate) {
		this.visitingDate = visitingDate;
	}

	public Date getNextVisitingDate() {
		return nextVisitingDate;
	}

	public void setNextVisitingDate(Date nextVisitingDate) {
		this.nextVisitingDate = nextVisitingDate;
	}

	public String getServiceLabelCode() {
		return serviceLabelCode;
	}

	public void setServiceLabelCode(String serviceLabelCode) {
		this.serviceLabelCode = serviceLabelCode;
	}

	public String getServiceLabelName() {
		return serviceLabelName;
	}

	public void setServiceLabelName(String serviceLabelName) {
		this.serviceLabelName = serviceLabelName;
	}

	public Date getForTheCustomerTime() {
		return forTheCustomerTime;
	}

	public void setForTheCustomerTime(Date forTheCustomerTime) {
		this.forTheCustomerTime = forTheCustomerTime;
	}

	public Integer getIsPrecedence() {
		return isPrecedence;
	}

	public void setIsPrecedence(Integer isPrecedence) {
		this.isPrecedence = isPrecedence;
	}

	public String getLastVisitAcc() {
		return lastVisitAcc;
	}

	public void setLastVisitAcc(String lastVisitAcc) {
		this.lastVisitAcc = lastVisitAcc;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getPlanDate() {
		return planDate;
	}

	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCompanyMoney() {
		return companyMoney;
	}

	public void setCompanyMoney(String companyMoney) {
		this.companyMoney = companyMoney;
	}

	public String getCompanyUser() {
		return companyUser;
	}

	public void setCompanyUser(String companyUser) {
		this.companyUser = companyUser;
	}

	public String getCompanyTrade() {
		return companyTrade;
	}

	public void setCompanyTrade(String companyTrade) {
		this.companyTrade = companyTrade;
	}

	public String getCompanyFax() {
		return companyFax;
	}

	public void setCompanyFax(String companyFax) {
		this.companyFax = companyFax;
	}

	public String getLosingId() {
		return losingId;
	}

	public void setLosingId(String losingId) {
		this.losingId = losingId;
	}

	public String getLosingRemark() {
		return losingRemark;
	}

	public void setLosingRemark(String losingRemark) {
		this.losingRemark = losingRemark;
	}

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getCountyId() {
		return countyId;
	}

	public void setCountyId(Integer countyId) {
		this.countyId = countyId;
	}

	public String getLastLogId() {
		return lastLogId;
	}

	public void setLastLogId(String lastLogId) {
		this.lastLogId = lastLogId;
	}

	public Integer getIsConcat() {
		return isConcat;
	}

	public void setIsConcat(Integer isConcat) {
		this.isConcat = isConcat;
	}

	public String getWangwang() {
		return wangwang;
	}

	public void setWangwang(String wangwang) {
		this.wangwang = wangwang;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getMainLinkman() {
		return mainLinkman;
	}

	public void setMainLinkman(String mainLinkman) {
		this.mainLinkman = mainLinkman;
	}

	public String getImportDeptId() {
		return importDeptId;
	}

	public void setImportDeptId(String importDeptId) {
		this.importDeptId = importDeptId;
	}

	public String getDefined6() {
		return defined6;
	}

	public void setDefined6(String defined6) {
		this.defined6 = defined6;
	}

	public String getDefined7() {
		return defined7;
	}

	public void setDefined7(String defined7) {
		this.defined7 = defined7;
	}

	public String getDefined8() {
		return defined8;
	}

	public void setDefined8(String defined8) {
		this.defined8 = defined8;
	}

	public String getDefined9() {
		return defined9;
	}

	public void setDefined9(String defined9) {
		this.defined9 = defined9;
	}

	public String getDefined10() {
		return defined10;
	}

	public void setDefined10(String defined10) {
		this.defined10 = defined10;
	}

	public Integer getTaoNo() {
		return taoNo;
	}

	public void setTaoNo(Integer taoNo) {
		this.taoNo = taoNo;
	}

	public Integer getSignBeforeType() {
		return signBeforeType;
	}

	public void setSignBeforeType(Integer signBeforeType) {
		this.signBeforeType = signBeforeType;
	}

	public Integer getSignBeforeStatus() {
		return signBeforeStatus;
	}

	public void setSignBeforeStatus(Integer signBeforeStatus) {
		this.signBeforeStatus = signBeforeStatus;
	}

	public String getLifeCode() {
		return lifeCode;
	}

	public void setLifeCode(String lifeCode) {
		this.lifeCode = lifeCode;
	}

	public String getLastOptionId() {
		return lastOptionId;
	}

	public void setLastOptionId(String lastOptionId) {
		this.lastOptionId = lastOptionId;
	}

	public Date getLastLossDate() {
		return lastLossDate;
	}

	public void setLastLossDate(Date lastLossDate) {
		this.lastLossDate = lastLossDate;
	}

	public String getLastCustTypeId() {
		return lastCustTypeId;
	}

	public void setLastCustTypeId(String lastCustTypeId) {
		this.lastCustTypeId = lastCustTypeId;
	}

	public String getDefined11() {
		return defined11;
	}

	public void setDefined11(String defined11) {
		this.defined11 = defined11;
	}

	public String getDefined12() {
		return defined12;
	}

	public void setDefined12(String defined12) {
		this.defined12 = defined12;
	}

	public String getDefined13() {
		return defined13;
	}

	public void setDefined13(String defined13) {
		this.defined13 = defined13;
	}

	public String getDefined14() {
		return defined14;
	}

	public void setDefined14(String defined14) {
		this.defined14 = defined14;
	}

	public String getDefined15() {
		return defined15;
	}

	public void setDefined15(String defined15) {
		this.defined15 = defined15;
	}

	public Date getDefined16() {
		return defined16;
	}

	public void setDefined16(Date defined16) {
		this.defined16 = defined16;
	}

	public Date getDefined17() {
		return defined17;
	}

	public void setDefined17(Date defined17) {
		this.defined17 = defined17;
	}

	public Date getDefined18() {
		return defined18;
	}

	public void setDefined18(Date defined18) {
		this.defined18 = defined18;
	}

	public String getShowdefined16() {
		return showdefined16;
	}

	public void setShowdefined16(String showdefined16) {
		this.showdefined16 = showdefined16;
	}

	public String getShowdefined17() {
		return showdefined17;
	}

	public void setShowdefined17(String showdefined17) {
		this.showdefined17 = showdefined17;
	}

	public String getShowdefined18() {
		return showdefined18;
	}

	public void setShowdefined18(String showdefined18) {
		this.showdefined18 = showdefined18;
	}

	public String getStartdefined16() {
		return startdefined16;
	}

	public void setStartdefined16(String startdefined16) {
		this.startdefined16 = startdefined16;
	}

	public String getEnddefined16() {
		return enddefined16;
	}

	public void setEnddefined16(String enddefined16) {
		this.enddefined16 = enddefined16;
	}

	public String getStartdefined17() {
		return startdefined17;
	}

	public void setStartdefined17(String startdefined17) {
		this.startdefined17 = startdefined17;
	}

	public String getEnddefined17() {
		return enddefined17;
	}

	public void setEnddefined17(String enddefined17) {
		this.enddefined17 = enddefined17;
	}

	public String getStartdefined18() {
		return startdefined18;
	}

	public void setStartdefined18(String startdefined18) {
		this.startdefined18 = startdefined18;
	}

	public String getEnddefined18() {
		return enddefined18;
	}

	public void setEnddefined18(String enddefined18) {
		this.enddefined18 = enddefined18;
	}

	public Date getOwnerActionDate() {
		return ownerActionDate;
	}

	public void setOwnerActionDate(Date ownerActionDate) {
		this.ownerActionDate = ownerActionDate;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public List<String> getResGroupIds() {
		return resGroupIds;
	}

	public void setResGroupIds(List<String> resGroupIds) {
		this.resGroupIds = resGroupIds;
	}

	public String getLastOptionName() {
		return lastOptionName;
	}

	public void setLastOptionName(String lastOptionName) {
		this.lastOptionName = lastOptionName;
	}
	
	

	
	
	
	
	
}
