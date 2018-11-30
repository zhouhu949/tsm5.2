package com.qftx.tsm.follow.dto;

import com.qftx.tsm.follow.bean.CustFollowBean;

import java.util.Date;
import java.util.List;

public class CustFollowDto extends CustFollowBean implements Cloneable {

	private static final long serialVersionUID = -2884076881504922543L;
	
	private String optionName;
	private String optionlistId;
    private String recordUrl;
    private Integer timeLength;
    private String callId;
    private Integer callType;
    private String callerNum;
    private String calledNum;
    private Date lastActionDate; // 最近联系时间
    private String showLastActionDate; // 显示最近联系时间
    private Date nextActionDate; // 下次联系时间
    private String showNextActionDate; // 显示下次联系时间
	private Date minActionDate; // 最初联系时间
	private String showMinActionDate; // 显示最初联系时间
    private List<CustFollowCallDto> urlList;
    private String followCustCation; // 数据分类
  	private String resCustId; // RES_CUST_ID 客户ID
  	private String custName; // NAME 客户姓名
  	private String company; // 单位名称
  	private String custMobilephone; // MOBILEPHONE 客户手机
  	private String telphone;// TELPHONE 客户座机
  	private String ownerAcc;// 客户所有者
	private String custTypeName;    // 客户类型名称	
	private String daysAfterDateCount; //DAYS_AFTER_DATE_COUNT 到期天数
	private String[] labels; // 标签查询集合
	private List<String> ownerAccs; // 所有者集合
	private List<String> followAccs; // 跟进者集合
	private Integer roleType; // 角色类别：0--销售，1--管理者
	private String changeType ; // 进程变化 2:有变化，3:无变化 ,4:新增
	private String lastSaleProcess ; // 上次销售进程
	private Short statusExtended;   //审核状态
	private String groupName; // 分组名称
	private String groupId; // 分组ID
	
	private Integer status; // 客户状态 1:全部，2:意向客户，3:签约客户
	private Integer type;
	private String ownerName; // 拥有者名称	
	private String isMajor; //是否关注
	//下一次开始结束更新时间
	private String nextStartActionDate;
	private String nextEndActionDate;
	//上一次开始结束更新时间
	private String lastStartActionDate;
	private String lastEndActionDate;
	//上一次所有者开始结束更新时间
	private String lastStartOwnerActionDate;
	private String lastEndOwnerActionDate;
	
	private String accs; //用于查询 所有者账号 字符串,号分割
	private String faccs; //用于查询 跟进者 字符串,号分割
	private String comaccs; // 共有者账号
	private String queryText; // 用于模糊查询
	
	private Integer state; // 0：个人客户,1:企业客户
	private Integer contactStatus; // 1:今日待联系
	private List<String>resCustIds; // 在计划中的资源ID/资源ID集合
	private Integer dataDictionVal; // 销售管理设置 客户跟进设置->允许完成客户签单的最长期限 XX天
	private Integer dataDictionVal1; // 跟进警报提前时间
	private String queryType;// 查询类型 
	private String osType;//所有者查询方式 1-全部 2-只看自己 3-选中查询
	private String fosType;//跟进者查询方式 1-全部 2-只看自己 3-选中查询
	private String comosType;//共有者查询方式 1-全部 2-只看自己 3-选中查询
	
	
	
	private String initStartFollowDate; // 开始联系时间-开始
	private String initEndFollowDate; // 开始联系时间-结束
	private String statusName; // 客户状态名称
	private String commAcc; // 共有者
	private String linkName; // 联系人
	private String area; // 所在地区
	private String companyMoney; // 注册资本
	private String companyTrade; // 所属行业
	private String keyWord; // 关键字
	private String fax; // 传真
	private String companyUser; // 法人代表
	private String mobliePhone; // 联系号码
	private String resGroup;
	private String orderType;
	private List<String> custIds;
	private Integer provinceId;
	private Integer cityId;
	private Integer countyId;
	private List<String>ids;
	private String name;//客户名称
	
	public String getOptionName() {
		return optionName;
	}
	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}
	public String getOptionlistId() {
		return optionlistId;
	}
	public void setOptionlistId(String optionlistId) {
		this.optionlistId = optionlistId;
	}
	public String getRecordUrl() {
		return recordUrl;
	}
	public void setRecordUrl(String recordUrl) {
		this.recordUrl = recordUrl;
	}
	public Integer getTimeLength() {
		return timeLength;
	}
	public void setTimeLength(Integer timeLength) {
		this.timeLength = timeLength;
	}
	public String getCallId() {
		return callId;
	}
	public void setCallId(String callId) {
		this.callId = callId;
	}
	public Integer getCallType() {
		return callType;
	}
	public void setCallType(Integer callType) {
		this.callType = callType;
	}
	public String getCallerNum() {
		return callerNum;
	}
	public void setCallerNum(String callerNum) {
		this.callerNum = callerNum;
	}
	public String getCalledNum() {
		return calledNum;
	}
	public void setCalledNum(String calledNum) {
		this.calledNum = calledNum;
	}
	public Date getLastActionDate() {
		return lastActionDate;
	}
	public void setLastActionDate(Date lastActionDate) {
		this.lastActionDate = lastActionDate;
	}
	public Date getNextActionDate() {
		return nextActionDate;
	}
	public void setNextActionDate(Date nextActionDate) {
		this.nextActionDate = nextActionDate;
	}
		
	public List<CustFollowCallDto> getUrlList() {
		return urlList;
	}
	public void setUrlList(List<CustFollowCallDto> urlList) {
		this.urlList = urlList;
	}
	public String getFollowCustCation() {
		return followCustCation;
	}
	public void setFollowCustCation(String followCustCation) {
		this.followCustCation = followCustCation;
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
	public String getCustMobilephone() {
		return custMobilephone;
	}
	public void setCustMobilephone(String custMobilephone) {
		this.custMobilephone = custMobilephone;
	}
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	public String getOwnerAcc() {
		return ownerAcc;
	}
	public void setOwnerAcc(String ownerAcc) {
		this.ownerAcc = ownerAcc;
	}
	public String getCustTypeName() {
		return custTypeName;
	}
	public void setCustTypeName(String custTypeName) {
		this.custTypeName = custTypeName;
	}
	public Date getMinActionDate() {
		return minActionDate;
	}
	public void setMinActionDate(Date minActionDate) {
		this.minActionDate = minActionDate;
	}
	public String getDaysAfterDateCount() {
		return daysAfterDateCount;
	}
	public void setDaysAfterDateCount(String daysAfterDateCount) {
		this.daysAfterDateCount = daysAfterDateCount;
	}
	public String[] getLabels() {
		return labels;
	}
	public void setLabels(String[] labels) {
		this.labels = labels;
	}
	public List<String> getOwnerAccs() {
		return ownerAccs;
	}
	public void setOwnerAccs(List<String> ownerAccs) {
		this.ownerAccs = ownerAccs;
	}
	public Integer getRoleType() {
		return roleType;
	}
	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}
	public String getChangeType() {
		return changeType;
	}
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	public String getLastSaleProcess() {
		return lastSaleProcess;
	}
	public void setLastSaleProcess(String lastSaleProcess) {
		this.lastSaleProcess = lastSaleProcess;
	}
	public Short getStatusExtended() {
		return statusExtended;
	}
	public void setStatusExtended(Short statusExtended) {
		this.statusExtended = statusExtended;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
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
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	public String getIsMajor() {
		return isMajor;
	}
	public void setIsMajor(String isMajor) {
		this.isMajor = isMajor;
	}
	
	public Integer getContactStatus() {
		return contactStatus;
	}
	public void setContactStatus(Integer contactStatus) {
		this.contactStatus = contactStatus;
	}
	
	public List<String> getResCustIds() {
		return resCustIds;
	}
	public void setResCustIds(List<String> resCustIds) {
		this.resCustIds = resCustIds;
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
		
	public String getShowLastActionDate() {
		return showLastActionDate;
	}
	public void setShowLastActionDate(String showLastActionDate) {
		this.showLastActionDate = showLastActionDate;
	}
	public String getShowNextActionDate() {
		return showNextActionDate;
	}
	public void setShowNextActionDate(String showNextActionDate) {
		this.showNextActionDate = showNextActionDate;
	}
	public String getShowMinActionDate() {
		return showMinActionDate;
	}
	public void setShowMinActionDate(String showMinActionDate) {
		this.showMinActionDate = showMinActionDate;
	}

	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public String getOsType() {
		return osType;
	}
	public void setOsType(String osType) {
		this.osType = osType;
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
	public List<String> getFollowAccs() {
		return followAccs;
	}
	public void setFollowAccs(List<String> followAccs) {
		this.followAccs = followAccs;
	}
	
	
	public String getFaccs() {
		return faccs;
	}
	public void setFaccs(String faccs) {
		this.faccs = faccs;
	}
	public String getFosType() {
		return fosType;
	}
	public void setFosType(String fosType) {
		this.fosType = fosType;
	}
	
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getCommAcc() {
		return commAcc;
	}
	public void setCommAcc(String commAcc) {
		this.commAcc = commAcc;
	}
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getCompanyMoney() {
		return companyMoney;
	}
	public void setCompanyMoney(String companyMoney) {
		this.companyMoney = companyMoney;
	}
	public String getCompanyTrade() {
		return companyTrade;
	}
	public void setCompanyTrade(String companyTrade) {
		this.companyTrade = companyTrade;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getCompanyUser() {
		return companyUser;
	}
	public void setCompanyUser(String companyUser) {
		this.companyUser = companyUser;
	}	
	public String getMobliePhone() {
		return mobliePhone;
	}
	public void setMobliePhone(String mobliePhone) {
		this.mobliePhone = mobliePhone;
	}
	
	public String getComaccs() {
		return comaccs;
	}
	public void setComaccs(String comaccs) {
		this.comaccs = comaccs;
	}
	
	public String getComosType() {
		return comosType;
	}
	public void setComosType(String comosType) {
		this.comosType = comosType;
	}
	
	public String getResGroup() {
		return resGroup;
	}
	public void setResGroup(String resGroup) {
		this.resGroup = resGroup;
	}
	
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	public List<String> getCustIds() {
		return custIds;
	}
	public void setCustIds(List<String> custIds) {
		this.custIds = custIds;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
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
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	/**
     * @return 创建并返回此对象的一个副本。
     * @throws CloneNotSupportedException
     */
    public Object clone() throws CloneNotSupportedException {
        //直接调用父类的clone()方法,返回克隆副本
        return super.clone();
    }
}
