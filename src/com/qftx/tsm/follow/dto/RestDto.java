package com.qftx.tsm.follow.dto;

import com.qftx.common.domain.BaseObject;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 跟进页面显示 其余待跟进资源 dto
 * <p>    company：浙江中恩通信技术有限公司
 * @author zwj
 */
public class RestDto extends BaseObject implements Serializable{
	private static final long serialVersionUID = 6839235707256215954L;
	private String resCustId;  	// 资源ID
	private String custName;	// 客户姓名
	private String telphone;		// 联系电话
	private Integer startPage; 	// 开始索引
	private Integer endPage;  // 结束索引
	private Date nextActionDate;// 下次跟进时间
	private Date actionDate;// 最近
	private Date amoyToCustomrDate;// 淘到时间
	private String sudId; // 今日计划ID
	private String orgId; // 机构ID
	private String ownerAcc; // 资源拥有者
	private List<String>resCustIds; // 在计划中的资源ID
	private Integer custListType;//1-全部客户 2-意向客户 3-签约客户 4-沉默客户 5-流失客户 6-客户跟进
	private String custType; // 2-意向客户 3-签约客户 4-沉默客户 5-流失客户
	//下一次开始结束更新时间
	private String nextStartActionDate;
	private String nextEndActionDate;
			
	//上一次开始结束更新时间
	private String lastStartActionDate;
	private String lastEndActionDate;
	//上一次所有者开始结束更新时间
	private String lastStartOwnerActionDate;
	private String lastEndOwnerActionDate;
	private String pstartDate;//添加分配时间 开始
	private String pendDate;//添加分配时间 结束
	
	/*private String startDate;
	private String endDate;*/
	private String startActionDate;
	private String endActionDate;
	
	private String startContractEndDate;//合同到期时间开始
	private String endContractEndDate;//合同到期时间结束
	private Integer state; // 0:个人客户，1：企业客户
	private Integer status;
	private String optionlistId;
	
	private Integer dataDictionVal; // 销售管理设置 客户跟进设置->允许完成客户签单的最长期限 XX天
	private Integer dataDictionVal1; // // 跟进警报提前时间
	
	private String lastCustType; // 最近跟进客户类型ID
	private String lastOptionId; // 最近跟进销售进程ID
	
	private String custCation; // 数据分类
	private String noteType;//查询标签
	private String allLabels;//标签字符串
	private String[] labels; // 标签查询集合
	private boolean queryContract;//查询合同失效日期
	
	private String accs; // 【客户跟进】用于查询 所有者账号 字符串,号分割
	private List<String> ownerAccs; // 所有者集合
	private String queryText; // 【客户跟进】用于模糊查询
	private String queryType;// 【客户跟进】查询类型 
	private Integer provinceId;
	private Integer cityId;
	private Integer countyId;
	
	private String dDateType; // 最近联系时间类型
	private String nDateType; // 下次联系时间类型
	private String osType;//所有者查询方式 1-全部 2-只看自己 3-选中查询
	private Integer contactStatus; // 1:今日待联系
	private String labelCode; //跟进标签code
	private String isMajor; //是否关注
	private List<String> ownerUserIds;
	
	private String custTypeId;
	private String saleProcessId;
	private String lDateType;
	private String oDateType;
	
	private String ownerAccsStr;
	private String ownerUserIdsStr;
	private String losingId;// 流失原因（4.0新增）
	private String resGroupId;
	private Date nextFollowDate; // 下次跟进时间
	private Integer days;
	private String startNextContactDate;//下次联系时间 开始
	private String endNextContactDate;//下次联系时间 结束
	
	private Integer tDateType;
	private String taoStartDate;
	private String taoEndDate;
	private String orderType;
	
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
	private List<String> resIds;
	//客户跟进页面的查询项
	private String resGroup;
	private String initStartFollowDate; // 开始联系时间-开始
	private String initEndFollowDate; // 开始联系时间-结束
	private String mDateType;
	private Integer roleType;
	//意向客户
	private String commonAccsStr;
	private String mainLinkman;
	private String mobilephone;
	private String name;
	private List<String> commonAccs;
	private String rejectIds;
	private String groupId;
	private String commonAcc;
	private String companyTrade;
	private Integer dateIsNull; //下次联系时间是否为空,0:为空,1:不为空
	private String isContact;
	private List<String> ids;
	private Integer source;
	public String getCompanyTrade() {
		return companyTrade;
	}
	public void setCompanyTrade(String companyTrade) {
		this.companyTrade = companyTrade;
	}
	public String getResCustId() {
		return resCustId;
	}
	public void setResCustId(String resCustId) {
		this.resCustId = resCustId;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	public Integer getStartPage() {
		return startPage;
	}
	public void setStartPage(Integer startPage) {
		this.startPage = startPage;
	}
	
	public Integer getEndPage() {
		return endPage;
	}
	public void setEndPage(Integer endPage) {
		this.endPage = endPage;
	}
	public Date getNextActionDate() {
		return nextActionDate;
	}
	public void setNextActionDate(Date nextActionDate) {
		this.nextActionDate = nextActionDate;
	}
	public String getSudId() {
		return sudId;
	}
	public void setSudId(String sudId) {
		this.sudId = sudId;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getOwnerAcc() {
		return ownerAcc;
	}
	public void setOwnerAcc(String ownerAcc) {
		this.ownerAcc = ownerAcc;
	}
	public List<String> getResCustIds() {
		return resCustIds;
	}
	public void setResCustIds(List<String> resCustIds) {
		this.resCustIds = resCustIds;
	}

	public String getNextStartActionDate() {
		return nextStartActionDate;
	}
	public void setNextStartActionDate(String nextStartActionDate) {
		this.nextStartActionDate = nextStartActionDate;
	}
	public String getNextEndActionDate() {
		return nextEndActionDate;
	}
	public void setNextEndActionDate(String nextEndActionDate) {
		this.nextEndActionDate = nextEndActionDate;
	}
	public String getLastStartActionDate() {
		return lastStartActionDate;
	}
	public void setLastStartActionDate(String lastStartActionDate) {
		this.lastStartActionDate = lastStartActionDate;
	}
	public String getLastEndActionDate() {
		return lastEndActionDate;
	}
	public void setLastEndActionDate(String lastEndActionDate) {
		this.lastEndActionDate = lastEndActionDate;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getDataDictionVal() {
		return dataDictionVal;
	}
	public void setDataDictionVal(Integer dataDictionVal) {
		this.dataDictionVal = dataDictionVal;
	}
	public Integer getDataDictionVal1() {
		return dataDictionVal1;
	}
	public void setDataDictionVal1(Integer dataDictionVal1) {
		this.dataDictionVal1 = dataDictionVal1;
	}
	public String getLastCustType() {
		return lastCustType;
	}
	public void setLastCustType(String lastCustType) {
		this.lastCustType = lastCustType;
	}
	public String getLastOptionId() {
		return lastOptionId;
	}
	public void setLastOptionId(String lastOptionId) {
		this.lastOptionId = lastOptionId;
	}
	public String getPstartDate() {
		return pstartDate;
	}
	public void setPstartDate(String pstartDate) {
		this.pstartDate = pstartDate;
	}
	public String getPendDate() {
		return pendDate;
	}
	public void setPendDate(String pendDate) {
		this.pendDate = pendDate;
	}
	public String getStartContractEndDate() {
		return startContractEndDate;
	}
	public void setStartContractEndDate(String startContractEndDate) {
		this.startContractEndDate = startContractEndDate;
	}
	public String getEndContractEndDate() {
		return endContractEndDate;
	}
	public void setEndContractEndDate(String endContractEndDate) {
		this.endContractEndDate = endContractEndDate;
	}
	public String getCustCation() {
		return custCation;
	}
	public void setCustCation(String custCation) {
		this.custCation = custCation;
	}
	public String getAccs() {
		return accs;
	}
	public void setAccs(String accs) {
		this.accs = accs;
	}
	public String getQueryText() {
		return queryText;
	}
	public void setQueryText(String queryText) {
		this.queryText = queryText;
	}
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public String getNoteType() {
		return noteType;
	}
	public void setNoteType(String noteType) {
		this.noteType = noteType;
	}
	public String getAllLabels() {
		return allLabels;
	}
	public void setAllLabels(String allLabels) {
		this.allLabels = allLabels;
	}
	public String[] getLabels() {
		return labels;
	}
	public void setLabels(String[] labels) {
		this.labels = labels;
	}
	public String getdDateType() {
		return dDateType;
	}
	public void setdDateType(String dDateType) {
		this.dDateType = dDateType;
	}
	public String getnDateType() {
		return nDateType;
	}
	public void setnDateType(String nDateType) {
		this.nDateType = nDateType;
	}
	public String getOsType() {
		return osType;
	}
	public void setOsType(String osType) {
		this.osType = osType;
	}
	public List<String> getOwnerAccs() {
		return ownerAccs;
	}
	public void setOwnerAccs(List<String> ownerAccs) {
		this.ownerAccs = ownerAccs;
	}
	public Integer getContactStatus() {
		return contactStatus;
	}
	public void setContactStatus(Integer contactStatus) {
		this.contactStatus = contactStatus;
	}
	public List<String> getOwnerUserIds() {
		return ownerUserIds;
	}
	public void setOwnerUserIds(List<String> ownerUserIds) {
		this.ownerUserIds = ownerUserIds;
	}
	public String getIsMajor() {
		return isMajor;
	}
	public void setIsMajor(String isMajor) {
		this.isMajor = isMajor;
	}
	/*public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}*/
	public String getStartActionDate() {
		return startActionDate;
	}
	public void setStartActionDate(String startActionDate) {
		this.startActionDate = startActionDate;
	}
	public String getEndActionDate() {
		return endActionDate;
	}
	public void setEndActionDate(String endActionDate) {
		this.endActionDate = endActionDate;
	}
	public String getCustTypeId() {
		return custTypeId;
	}
	public void setCustTypeId(String custTypeId) {
		this.custTypeId = custTypeId;
	}
	public String getSaleProcessId() {
		return saleProcessId;
	}
	public void setSaleProcessId(String saleProcessId) {
		this.saleProcessId = saleProcessId;
	}
	public String getlDateType() {
		return lDateType;
	}
	public void setlDateType(String lDateType) {
		this.lDateType = lDateType;
	}
	public String getoDateType() {
		return oDateType;
	}
	public void setoDateType(String oDateType) {
		this.oDateType = oDateType;
	}
	public String getOwnerAccsStr() {
		return ownerAccsStr;
	}
	public void setOwnerAccsStr(String ownerAccsStr) {
		this.ownerAccsStr = ownerAccsStr;
	}
	public String getOwnerUserIdsStr() {
		return ownerUserIdsStr;
	}
	public void setOwnerUserIdsStr(String ownerUserIdsStr) {
		this.ownerUserIdsStr = ownerUserIdsStr;
	}
	public String getLosingId() {
		return losingId;
	}
	public void setLosingId(String losingId) {
		this.losingId = losingId;
	}
	public String getResGroupId() {
		return resGroupId;
	}
	public void setResGroupId(String resGroupId) {
		this.resGroupId = resGroupId;
	}
	public Date getNextFollowDate() {
		return nextFollowDate;
	}
	public void setNextFollowDate(Date nextFollowDate) {
		this.nextFollowDate = nextFollowDate;
	}
	public Integer getDays() {
		return days;
	}
	public void setDays(Integer days) {
		this.days = days;
	}
	public Integer getCustListType() {
		return custListType;
	}
	public void setCustListType(Integer custListType) {
		this.custListType = custListType;
	}
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getOptionlistId() {
		return optionlistId;
	}
	public void setOptionlistId(String optionlistId) {
		this.optionlistId = optionlistId;
	}
	public boolean isQueryContract() {
		return queryContract;
	}
	public void setQueryContract(boolean queryContract) {
		this.queryContract = queryContract;
	}
	public String getStartNextContactDate() {
		return startNextContactDate;
	}
	public void setStartNextContactDate(String startNextContactDate) {
		this.startNextContactDate = startNextContactDate;
	}
	public String getEndNextContactDate() {
		return endNextContactDate;
	}
	public void setEndNextContactDate(String endNextContactDate) {
		this.endNextContactDate = endNextContactDate;
	}
	public Integer gettDateType() {
		return tDateType;
	}
	public void settDateType(Integer tDateType) {
		this.tDateType = tDateType;
	}
	public String getTaoStartDate() {
		return taoStartDate;
	}
	public void setTaoStartDate(String taoStartDate) {
		this.taoStartDate = taoStartDate;
	}
	public String getTaoEndDate() {
		return taoEndDate;
	}
	public void setTaoEndDate(String taoEndDate) {
		this.taoEndDate = taoEndDate;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public Date getActionDate() {
		return actionDate;
	}
	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}
	public Date getAmoyToCustomrDate() {
		return amoyToCustomrDate;
	}
	public void setAmoyToCustomrDate(Date amoyToCustomrDate) {
		this.amoyToCustomrDate = amoyToCustomrDate;
	}
	public String getResGroup() {
		return resGroup;
	}
	public void setResGroup(String resGroup) {
		this.resGroup = resGroup;
	}
	public String getInitStartFollowDate() {
		return initStartFollowDate;
	}
	public void setInitStartFollowDate(String initStartFollowDate) {
		this.initStartFollowDate = initStartFollowDate;
	}
	public String getInitEndFollowDate() {
		return initEndFollowDate;
	}
	public void setInitEndFollowDate(String initEndFollowDate) {
		this.initEndFollowDate = initEndFollowDate;
	}
	public String getCommonAccsStr() {
		return commonAccsStr;
	}
	public void setCommonAccsStr(String commonAccsStr) {
		this.commonAccsStr = commonAccsStr;
	}
	public String getMainLinkman() {
		return mainLinkman;
	}
	public void setMainLinkman(String mainLinkman) {
		this.mainLinkman = mainLinkman;
	}
	public String getMobilephone() {
		return mobilephone;
	}
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public List<String> getCommonAccs() {
		return commonAccs;
	}
	public void setCommonAccs(List<String> commonAccs) {
		this.commonAccs = commonAccs;
	}
	public String getRejectIds() {
		return rejectIds;
	}
	public void setRejectIds(String rejectIds) {
		this.rejectIds = rejectIds;
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
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
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
	public List<String> getResIds() {
		return resIds;
	}
	public void setResIds(List<String> resIds) {
		this.resIds = resIds;
	}
	public String getCommonAcc() {
		return commonAcc;
	}
	public void setCommonAcc(String commonAcc) {
		this.commonAcc = commonAcc;
	}
	public String getmDateType() {
		return mDateType;
	}
	public void setmDateType(String mDateType) {
		this.mDateType = mDateType;
	}
	public Integer getRoleType() {
		return roleType;
	}
	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}
	public String getLabelCode() {
		return labelCode;
	}
	public void setLabelCode(String labelCode) {
		this.labelCode = labelCode;
	}
	public Integer getDateIsNull() {
		return dateIsNull;
	}
	public void setDateIsNull(Integer dateIsNull) {
		this.dateIsNull = dateIsNull;
	}
	public String getIsContact() {
		return isContact;
	}
	public void setIsContact(String isContact) {
		this.isContact = isContact;
	}
	public String getLastStartOwnerActionDate() {
		return lastStartOwnerActionDate;
	}
	public void setLastStartOwnerActionDate(String lastStartOwnerActionDate) {
		this.lastStartOwnerActionDate = lastStartOwnerActionDate;
	}
	public String getLastEndOwnerActionDate() {
		return lastEndOwnerActionDate;
	}
	public void setLastEndOwnerActionDate(String lastEndOwnerActionDate) {
		this.lastEndOwnerActionDate = lastEndOwnerActionDate;
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
	public List<String> getIds() {
		return ids;
	}
	public void setIds(List<String> ids) {
		this.ids = ids;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
