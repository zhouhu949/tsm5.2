package com.qftx.tsm.cust.dto;

import com.qftx.tsm.cust.bean.ResCustInfoBean;

import java.util.Date;
import java.util.List;


/**
 * 客户表
 * @author xiangli
 *
 */
public class CustDto extends ResCustInfoBean implements Cloneable {
	private static final long serialVersionUID = 1L;
	private String ownerName;    //所有者
	private String loginAcc;     //当前登录帐号
	private String phone;        //手机/固话
	private String major;        //重点关注
	private String optionName;   //销售进程名称
	private String itemCode;     //销售进程类别
	private String optionId;     //销售进程id
	private String resultsStr;   //结果拼接   所有者+'&'+姓名+'&'+手机+'&'+固话 (查询结果用)
	private short isCust;		//设置为1查找Type为2
	private String groupName;
	private String custToday;    //今日新增客户
	private String addCust;    
	private Date maxActionDate;  //最近跟进记录
	private String resGroupName;   //资源分组名称
	private String startActionDate;  //开始跟进时间查询
	private String endActionDate;    //结束跟进时间查询
	private Integer rowNum;
	private Integer rowId;
	private String labelName;
	private String feedbackComment;
	private Date followDate;      //计划跟进时间
	private String pstartDate;  //开始跟进时间查询
	private String pendDate;    //结束跟进时间查询
	private String vstartDate;  //最近访问时间
	private String vendDate;    //最近访问时间
	private String nstartDate;  //下次访问时间
	private String nendDate;    //下次访问时间
	private String serviceLabelCode;//服务标签
	
	private String[] labels; // 标签查询集合
	private String labelCode; // 行动标签
	private String serviceLevelName;     //服务评级名称
	
	private String saleProcessId;     //销售进程ID
	private String saleProcess;       //销售
	private Date lastActionDate;	  //最后跟进时间
	private Integer isSetService;
	private String followCustCation; // 数据分类
	private String todayAddDate;//用于今日新增分类查询
	private String weekAddDate;//用于本周新增分类查询
	private String custType;//类型：1资源 2意向客户 3签约客户
	private String forWord;
	private List<String> ownerAccs;
	private String custTypeId;
	private Date nextActionDate;
	private String custTypeName;
	public String getCustTypeName() {
		return custTypeName;
	}

	public void setCustTypeName(String custTypeName) {
		this.custTypeName = custTypeName;
	}

	public String getCustTypeId() {
		return custTypeId;
	}

	public void setCustTypeId(String custTypeId) {
		this.custTypeId = custTypeId;
	}

	public List<String> getOwnerAccs() {
		return ownerAccs;
	}

	public void setOwnerAccs(List<String> ownerAccs) {
		this.ownerAccs = ownerAccs;
	}

	public String getForWord() {
		return forWord;
	}

	public void setForWord(String forWord) {
		this.forWord = forWord;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	public String getTodayAddDate() {
		return todayAddDate;
	}

	public void setTodayAddDate(String todayAddDate) {
		this.todayAddDate = todayAddDate;
	}

	public String getWeekAddDate() {
		return weekAddDate;
	}

	public void setWeekAddDate(String weekAddDate) {
		this.weekAddDate = weekAddDate;
	}

	public String getFollowCustCation() {
		return followCustCation;
	}

	public void setFollowCustCation(String followCustCation) {
		this.followCustCation = followCustCation;
	}

	public Integer getIsSetService() {
		return isSetService;
	}

	public void setIsSetService(Integer isSetService) {
		this.isSetService = isSetService;
	}

	public String getSaleProcessId() {
		return saleProcessId;
	}

	public void setSaleProcessId(String saleProcessId) {
		this.saleProcessId = saleProcessId;
	}

	public String getSaleProcess() {
		return saleProcess;
	}

	public void setSaleProcess(String saleProcess) {
		this.saleProcess = saleProcess;
	}

	public Date getLastActionDate() {
		return lastActionDate;
	}

	public void setLastActionDate(Date lastActionDate) {
		this.lastActionDate = lastActionDate;
	}

	public String[] getLabels() {
		return labels;
	}

	public void setLabels(String[] labels) {
		this.labels = labels;
	}

	public String getLabelCode() {
		return labelCode;
	}

	public void setLabelCode(String labelCode) {
		this.labelCode = labelCode;
	}

	public short getIsCust() {
		return isCust;
	}

	public void setIsCust(short isCust) {
		this.isCust = isCust;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getLoginAcc() {
		return loginAcc;
	}

	public void setLoginAcc(String loginAcc) {
		this.loginAcc = loginAcc;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getOptionName() {
		return optionName;
	}

	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getOptionId() {
		return optionId;
	}

	public void setOptionId(String optionId) {
		this.optionId = optionId;
	}

	public String getResultsStr() {
		return resultsStr;
	}

	public void setResultsStr(String resultsStr) {
		this.resultsStr = resultsStr;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupName() {
		return groupName;
	}

	public String getCustToday() {
		return custToday;
	}

	public void setCustToday(String custToday) {
		this.custToday = custToday;
	}

	public void setAddCust(String addCust) {
		this.addCust = addCust;
	}

	public String getAddCust() {
		return addCust;
	}

	public Date getMaxActionDate() {
		return maxActionDate;
	}

	public void setMaxActionDate(Date maxActionDate) {
		this.maxActionDate = maxActionDate;
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

	public String getResGroupName() {
		return resGroupName;
	}

	public void setResGroupName(String resGroupName) {
		this.resGroupName = resGroupName;
	}

	public Integer getRowNum() {
		return rowNum;
	}

	public void setRowNum(Integer rowNum) {
		this.rowNum = rowNum;
	}

	public Integer getRowId() {
		return rowId;
	}

	public void setRowId(Integer rowId) {
		this.rowId = rowId;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public String getFeedbackComment() {
		return feedbackComment;
	}

	public void setFeedbackComment(String feedbackComment) {
		this.feedbackComment = feedbackComment;
	}

	public Date getFollowDate() {
		return followDate;
	}

	public void setFollowDate(Date followDate) {
		this.followDate = followDate;
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

	public String getVstartDate() {
		return vstartDate;
	}

	public void setVstartDate(String vstartDate) {
		this.vstartDate = vstartDate;
	}

	public String getVendDate() {
		return vendDate;
	}

	public void setVendDate(String vendDate) {
		this.vendDate = vendDate;
	}

	public String getNstartDate() {
		return nstartDate;
	}

	public void setNstartDate(String nstartDate) {
		this.nstartDate = nstartDate;
	}

	public String getNendDate() {
		return nendDate;
	}

	public void setNendDate(String nendDate) {
		this.nendDate = nendDate;
	}

	public void setServiceLevelName(String serviceLevelName) {
		this.serviceLevelName = serviceLevelName;
	}

	public String getServiceLevelName() {
		return serviceLevelName;
	}

	public void setServiceLabelCode(String serviceLabelCode) {
		this.serviceLabelCode = serviceLabelCode;
	}

	public String getServiceLabelCode() {
		return serviceLabelCode;
	}
	
	 public Date getNextActionDate() {
		return nextActionDate;
	}

	public void setNextActionDate(Date nextActionDate) {
		this.nextActionDate = nextActionDate;
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
