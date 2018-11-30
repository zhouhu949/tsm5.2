package com.qftx.tsm.cust.dto;

import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.option.dto.DictionProcessJsonDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
public class ResCustInfoDto extends ResCustInfoBean implements Cloneable {
	private static final long serialVersionUID = -2904301692720746024L;

	private String groupName;//资源分组名称
	private String ownerName;//所属人
	private String pstartDate;//添加分配时间 开始
	private String pendDate;//添加分配时间 结束
	private String startActionDate;//最近联系时间 开始
	private String endActionDate;//最近联系时间 结束
	private String startNextContactDate;//下次联系时间 开始
	private String endNextContactDate;//下次联系时间 结束
	private String startContractEndDate;//合同到期时间开始
	private String endContractEndDate;//合同到期时间结束
	private String saleProcessName;//销售进程名称
	private String saleProcessId;//销售进程ID
	private String custTypeId;//客户类型ID
	private String custTypeName;//客户类型名称
	private Integer isToday;//是否今日新增 0-否 1-是
	private String allLabels;//标签字符串
	private String itemCode;//字典编码
	private List<String> labels;
	private String noteType;//查询标签
	private String adminAcc;//管理者帐号 用于管理者登陆时查询列表
	private BigDecimal orderCountMonty;//订单总金额
	private BigDecimal totalOrderAmount;//回款金额
	private Integer silentDay;//沉默天数
	private Date contractEndDate;//合同到期时间
	private String contractCode;//合同编号
	private String reason;//放弃原因
	private String losingReason;//流失原因
	private String linkMan;//联系人
	private String custType;//1-资源 2-意向客户 3-签约客户 4-沉默客户 5-流失客户
	private String isUnGroup;//未分组
	private List<String> ids;
	private List<String> rejectIds;//查询需要剔除的ID
	private List<String> ownerAccs;
	private String ownerAccsStr;
	private String ownerUserIdsStr;
	private List<String> ownerUserIds;
	private String regroupId;//重新分组ID
	private Integer isClean;//是否清除联系信息
	private Integer oldResGroup;//是否自动回收到原有资源组中
	private String isShareRes;//是否查询共享资源（公海客户池） 1-是 0-否
	private String moveToAcc;//交接人员
	private Integer inputDays;
	private Integer days;
	private Integer oDateType;
	private Integer lDateType;
	private Integer nDateType;
	private Integer isHaveResRemark;//是否有资源备注 1-是 0-否
	private String resRemark;//资源备注
	private String queryText;
	private String checkCode;
	private Integer custNums;
	private String procId;
	private String giveUpName;
	private String isOpenReason;
	private boolean queryResLog;//查询资源备注
	private boolean queryContract;//查询合同失效日期
	private List<String> authGroupIds;//公海权限
	private boolean queryPhone;//是否为电话号码查询
	private String queryType;//查询类型 1-客户姓名 2-联系人 3-联系电话 
	private String osType;//所有者查询方式 1-全部 2-只看自己 3-选中查询
	private String isOrderBy;
	private String commonOwnerName;//共有者
	private String commonAccsStr;
	private List<String> commonAccs;
	private String importDeptName;
	private String importDeptIdsStr;
	private List<String> importDeptIds;
	
	private String serviceAccStr;
	private List<String> serviceAccs;
	
	private String showdefined16;
	private String showdefined17;
	private String showdefined18;
	
	private String startdefined16;
	private String enddefined16;
	private String startdefined17;
	private String enddefined17;
	private String startdefined18;
	private String enddefined18;
	private String area;
	
	private String updateAccsStr;
	private List<String> updateAccs;
	private String usType;//公海-放弃人查询方式 1-全部 2-只看自己 3-选中查询
	
	private String notContactedType;//未联系天数
	
	private List<String> custIds;
	
	private String expireType;
	
	private Integer recyleDay;
	
	private String resGroupIdsStr;
	
	private List<String> groupIds;
	
	private List<DictionProcessJsonDto> expireOptions;
	
	private boolean emptySearch = false;
	
	private Integer min;
	
	private Integer max;
	
	private String inputStatusStr;
	
	private List<String> inputStatusList;

	private List<String> deptIds;

	public List<String> getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(List<String> deptIds) {
		this.deptIds = deptIds;
	}

	public Integer getMin() {
		return min;
	}
	public void setMin(Integer min) {
		this.min = min;
	}
	public Integer getMax() {
		return max;
	}
	public void setMax(Integer max) {
		this.max = max;
	}
	public String getCheckCode() {
		return checkCode;
	}
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
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
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getSaleProcessName() {
		return saleProcessName;
	}
	public void setSaleProcessName(String saleProcessName) {
		this.saleProcessName = saleProcessName;
	}
	
	public String getSaleProcessId() {
		return saleProcessId;
	}
	public void setSaleProcessId(String saleProcessId) {
		this.saleProcessId = saleProcessId;
	}
	public String getNoteType() {
		return noteType;
	}
	public void setNoteType(String noteType) {
		this.noteType = noteType;
	}
	public String getCustTypeId() {
		return custTypeId;
	}
	public void setCustTypeId(String custTypeId) {
		this.custTypeId = custTypeId;
	}
	public String getCustTypeName() {
		return custTypeName;
	}
	public void setCustTypeName(String custTypeName) {
		this.custTypeName = custTypeName;
	}
	public Integer getIsToday() {
		return isToday;
	}
	public void setIsToday(Integer isToday) {
		this.isToday = isToday;
	}
	public String getAllLabels() {
		return allLabels;
	}
	public void setAllLabels(String allLabels) {
		this.allLabels = allLabels;
	}
	public List<String> getLabels() {
		return labels;
	}
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public boolean isQueryPhone() {
		return queryPhone;
	}
	public void setQueryPhone(boolean queryPhone) {
		this.queryPhone = queryPhone;
	}
	public void setLabels(List<String> labels) {
		this.labels = labels;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getAdminAcc() {
		return adminAcc;
	}
	public void setAdminAcc(String adminAcc) {
		this.adminAcc = adminAcc;
	}
	public BigDecimal getTotalOrderAmount() {
		return totalOrderAmount;
	}
	public void setTotalOrderAmount(BigDecimal totalOrderAmount) {
		this.totalOrderAmount = totalOrderAmount;
	}
	public Integer getSilentDay() {
		return silentDay;
	}
	public void setSilentDay(Integer silentDay) {
		this.silentDay = silentDay;
	}
	public Date getContractEndDate() {
		return contractEndDate;
	}
	public void setContractEndDate(Date contractEndDate) {
		this.contractEndDate = contractEndDate;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getLosingReason() {
		return losingReason;
	}
	public void setLosingReason(String losingReason) {
		this.losingReason = losingReason;
	}
	public String getLinkMan() {
		return linkMan;
	}
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}
	public List<String> getIds() {
		return ids;
	}
	public void setIds(List<String> ids) {
		this.ids = ids;
	}
	public List<String> getOwnerAccs() {
		return ownerAccs;
	}
	public void setOwnerAccs(List<String> ownerAccs) {
		this.ownerAccs = ownerAccs;
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
	public List<String> getOwnerUserIds() {
		return ownerUserIds;
	}
	public void setOwnerUserIdsStr(String ownerUserIdsStr) {
		this.ownerUserIdsStr = ownerUserIdsStr;
	}
	public void setOwnerUserIds(List<String> ownerUserIds) {
		this.ownerUserIds = ownerUserIds;
	}
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public String getIsUnGroup() {
		return isUnGroup;
	}
	public void setIsUnGroup(String isUnGroup) {
		this.isUnGroup = isUnGroup;
	}
	
	public String getRegroupId() {
		return regroupId;
	}
	public void setRegroupId(String regroupId) {
		this.regroupId = regroupId;
	}
	public Integer getIsClean() {
		return isClean;
	}
	public void setIsClean(Integer isClean) {
		this.isClean = isClean;
	}
	public String getIsShareRes() {
		return isShareRes;
	}
	public void setIsShareRes(String isShareRes) {
		this.isShareRes = isShareRes;
	}
	public String getMoveToAcc() {
		return moveToAcc;
	}
	public void setMoveToAcc(String moveToAcc) {
		this.moveToAcc = moveToAcc;
	}
	public Integer getInputDays() {
		return inputDays;
	}
	public void setInputDays(Integer inputDays) {
		this.inputDays = inputDays;
	}
	public Integer getDays() {
		return days;
	}
	public void setDays(Integer days) {
		this.days = days;
	}
	public Integer getoDateType() {
		return oDateType;
	}
	public void setoDateType(Integer oDateType) {
		this.oDateType = oDateType;
	}
	public Integer getlDateType() {
		return lDateType;
	}
	public void setlDateType(Integer lDateType) {
		this.lDateType = lDateType;
	}
	public Integer getnDateType() {
		return nDateType;
	}
	public void setnDateType(Integer nDateType) {
		this.nDateType = nDateType;
	}
	public String getQueryText() {
		return queryText;
	}
	public void setQueryText(String queryText) {
		this.queryText = queryText;
	}
	
	public Integer getIsHaveResRemark() {
		return isHaveResRemark;
	}
	public void setIsHaveResRemark(Integer isHaveResRemark) {
		this.isHaveResRemark = isHaveResRemark;
	}
	
	public List<String> getRejectIds() {
		return rejectIds;
	}
	public void setRejectIds(List<String> rejectIds) {
		this.rejectIds = rejectIds;
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
	public Integer getCustNums() {
		return custNums;
	}
	public void setCustNums(Integer custNums) {
		this.custNums = custNums;
	}
	public String getProcId() {
		return procId;
	}
	public void setProcId(String procId) {
		this.procId = procId;
	}
	public String getResRemark() {
		return resRemark;
	}
	public void setResRemark(String resRemark) {
		this.resRemark = resRemark;
	}
	public String getGiveUpName() {
		return giveUpName;
	}
	public void setGiveUpName(String giveUpName) {
		this.giveUpName = giveUpName;
	}
	public String getIsOpenReason() {
		return isOpenReason;
	}
	public void setIsOpenReason(String isOpenReason) {
		this.isOpenReason = isOpenReason;
	}
	public boolean isQueryResLog() {
		return queryResLog;
	}
	public void setQueryResLog(boolean queryResLog) {
		this.queryResLog = queryResLog;
	}
	public boolean isQueryContract() {
		return queryContract;
	}
	public void setQueryContract(boolean queryContract) {
		this.queryContract = queryContract;
	}
	public List<String> getAuthGroupIds() {
		return authGroupIds;
	}
	public void setAuthGroupIds(List<String> authGroupIds) {
		this.authGroupIds = authGroupIds;
	}
	public Integer getOldResGroup() {
		return oldResGroup;
	}
	public void setOldResGroup(Integer oldResGroup) {
		this.oldResGroup = oldResGroup;
	}
	public String getOsType() {
		return osType;
	}
	public void setOsType(String osType) {
		this.osType = osType;
	}
	public String getIsOrderBy() {
		return isOrderBy;
	}
	public void setIsOrderBy(String isOrderBy) {
		this.isOrderBy = isOrderBy;
	}
	public String getCommonOwnerName() {
		return commonOwnerName;
	}
	public void setCommonOwnerName(String commonOwnerName) {
		this.commonOwnerName = commonOwnerName;
	}
	public String getCommonAccsStr() {
		return commonAccsStr;
	}
	public void setCommonAccsStr(String commonAccsStr) {
		this.commonAccsStr = commonAccsStr;
	}
	public List<String> getCommonAccs() {
		return commonAccs;
	}
	public void setCommonAccs(List<String> commonAccs) {
		this.commonAccs = commonAccs;
	}
	public String getImportDeptName() {
		return importDeptName;
	}
	public void setImportDeptName(String importDeptName) {
		this.importDeptName = importDeptName;
	}
	public String getImportDeptIdsStr() {
		return importDeptIdsStr;
	}
	public void setImportDeptIdsStr(String importDeptIdsStr) {
		this.importDeptIdsStr = importDeptIdsStr;
	}
	public List<String> getImportDeptIds() {
		return importDeptIds;
	}
	public void setImportDeptIds(List<String> importDeptIds) {
		this.importDeptIds = importDeptIds;
	}
	public String getServiceAccStr() {
		return serviceAccStr;
	}
	public void setServiceAccStr(String serviceAccStr) {
		this.serviceAccStr = serviceAccStr;
	}
	public List<String> getServiceAccs() {
		return serviceAccs;
	}
	public void setServiceAccs(List<String> serviceAccs) {
		this.serviceAccs = serviceAccs;
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
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getUpdateAccsStr() {
		return updateAccsStr;
	}
	public void setUpdateAccsStr(String updateAccsStr) {
		this.updateAccsStr = updateAccsStr;
	}
	public List<String> getUpdateAccs() {
		return updateAccs;
	}
	public void setUpdateAccs(List<String> updateAccs) {
		this.updateAccs = updateAccs;
	}
	public String getUsType() {
		return usType;
	}
	public void setUsType(String usType) {
		this.usType = usType;
	}
	public String getNotContactedType() {
		return notContactedType;
	}
	public void setNotContactedType(String notContactedType) {
		this.notContactedType = notContactedType;
	}
	public List<String> getCustIds() {
		return custIds;
	}
	public void setCustIds(List<String> custIds) {
		this.custIds = custIds;
	}
	public String getExpireType() {
		return expireType;
	}
	public void setExpireType(String expireType) {
		this.expireType = expireType;
	}
	public Integer getRecyleDay() {
		return recyleDay;
	}
	public void setRecyleDay(Integer recyleDay) {
		this.recyleDay = recyleDay;
	}
	public List<DictionProcessJsonDto> getExpireOptions() {
		return expireOptions;
	}
	public void setExpireOptions(List<DictionProcessJsonDto> expireOptions) {
		this.expireOptions = expireOptions;
	}
	public BigDecimal getOrderCountMonty() {
		return orderCountMonty;
	}
	public void setOrderCountMonty(BigDecimal orderCountMonty) {
		this.orderCountMonty = orderCountMonty;
	}
	public String getResGroupIdsStr() {
		return resGroupIdsStr;
	}
	public void setResGroupIdsStr(String resGroupIdsStr) {
		this.resGroupIdsStr = resGroupIdsStr;
	}
	public List<String> getGroupIds() {
		return groupIds;
	}
	public void setGroupIds(List<String> groupIds) {
		this.groupIds = groupIds;
	}
	
	public boolean getEmptySearch() {
		return emptySearch;
	}
	public void setEmptySearch(boolean emptySearch) {
		this.emptySearch = emptySearch;
	}
	
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	public String getInputStatusStr() {
		return inputStatusStr;
	}
	public void setInputStatusStr(String inputStatusStr) {
		this.inputStatusStr = inputStatusStr;
	}
	public List<String> getInputStatusList() {
		return inputStatusList;
	}
	public void setInputStatusList(List<String> inputStatusList) {
		this.inputStatusList = inputStatusList;
	}
	
	
}
