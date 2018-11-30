package com.qftx.tsm.report.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qftx.base.util.DateUtil;
import com.qftx.common.domain.BaseObject;

import java.util.Date;
/* tsm_report_plan 首页统计*/
public class TsmReportPlanBean extends BaseObject {
	private String id;	//ID编码
	private String orgId;	//机构ID
	private String type;	//类型(0资源，1意向)
	private String account;	//账户
	private String userId;	//用户ID
	private Integer resAddNum;	//添加资源数
	private Integer resPlanNum;	//计划联系数
	private Integer resNoContact;	//未联系数
	private Integer resTotalNum;	//实际联系总数
	private Integer resSignNum;	//转签约
	private Integer resCustNum;	//转意向
	private Integer resPoolNum;	//公海池
	private Integer resNoNum;	//无进展
	private Integer willPlanNum;	//计划联系数
	private Integer willNoContact;	//未联系数
	private Integer willTotalNum;	//实际联系总数
	private Integer willSignNum;	//转签约
	private Integer willCustNum;	//转意向
	private Integer willPoolNum;	//公海池
	private Integer willNoNum;	//无进展
	private Integer signResNum;	//取消签约   签约转资源
	private Integer signWillNum;	//取消签约   签约转意向
	private Integer tradCustNum;	//交易客户数
	private Integer callOutTime;	//呼出时长
	private Double signMoney;	//签约金额
	private Double saleChanceMoney;	//逾期签单金额(5.1新增)
	private Double saleMoney;	//签单金额(5.1新增)
	private Date reportDate;	//报表时间
	private Date inputtime;	//录入时间
	private Date updateDate;	//更新时间
	
	private String groupId;
	private String groupName;
	private String userName;
	
	private String groupNames;
	private String groupIdsStr;
	private String[] groupIds;
	
	private String userNames;
	private String[] userIds;
	private Date fromDate;
	private Date toDate;
	private String dateFmtStr;
	private String dateFmt;
	
	private String reportDateStr;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getResAddNum() {
		return resAddNum;
	}
	public void setResAddNum(Integer resAddNum) {
		this.resAddNum = resAddNum;
	}
	public Integer getResPlanNum() {
		return resPlanNum;
	}
	public void setResPlanNum(Integer resPlanNum) {
		this.resPlanNum = resPlanNum;
	}
	public Integer getResNoContact() {
		return resNoContact;
	}
	public void setResNoContact(Integer resNoContact) {
		this.resNoContact = resNoContact;
	}
	public Integer getResTotalNum() {
		return resTotalNum;
	}
	public void setResTotalNum(Integer resTotalNum) {
		this.resTotalNum = resTotalNum;
	}
	public Integer getResSignNum() {
		return resSignNum;
	}
	public void setResSignNum(Integer resSignNum) {
		this.resSignNum = resSignNum;
	}
	public Integer getResCustNum() {
		return resCustNum;
	}
	public void setResCustNum(Integer resCustNum) {
		this.resCustNum = resCustNum;
	}
	public Integer getResPoolNum() {
		return resPoolNum;
	}
	public void setResPoolNum(Integer resPoolNum) {
		this.resPoolNum = resPoolNum;
	}
	public Integer getResNoNum() {
		return resNoNum;
	}
	public void setResNoNum(Integer resNoNum) {
		this.resNoNum = resNoNum;
	}
	public Integer getWillPlanNum() {
		return willPlanNum;
	}
	public void setWillPlanNum(Integer willPlanNum) {
		this.willPlanNum = willPlanNum;
	}
	public Integer getWillNoContact() {
		return willNoContact;
	}
	public void setWillNoContact(Integer willNoContact) {
		this.willNoContact = willNoContact;
	}
	public Integer getWillTotalNum() {
		return willTotalNum;
	}
	public void setWillTotalNum(Integer willTotalNum) {
		this.willTotalNum = willTotalNum;
	}
	public Integer getWillSignNum() {
		return willSignNum;
	}
	public void setWillSignNum(Integer willSignNum) {
		this.willSignNum = willSignNum;
	}
	public Integer getWillCustNum() {
		return willCustNum;
	}
	public void setWillCustNum(Integer willCustNum) {
		this.willCustNum = willCustNum;
	}
	public Integer getWillPoolNum() {
		return willPoolNum;
	}
	public void setWillPoolNum(Integer willPoolNum) {
		this.willPoolNum = willPoolNum;
	}
	public Integer getWillNoNum() {
		return willNoNum;
	}
	public void setWillNoNum(Integer willNoNum) {
		this.willNoNum = willNoNum;
	}
	public Integer getSignResNum() {
		return signResNum;
	}
	public void setSignResNum(Integer signResNum) {
		this.signResNum = signResNum;
	}
	public Integer getSignWillNum() {
		return signWillNum;
	}
	public void setSignWillNum(Integer signWillNum) {
		this.signWillNum = signWillNum;
	}
	public Integer getTradCustNum() {
		return tradCustNum;
	}
	public void setTradCustNum(Integer tradCustNum) {
		this.tradCustNum = tradCustNum;
	}
	public Integer getCallOutTime() {
		return callOutTime;
	}
	public void setCallOutTime(Integer callOutTime) {
		this.callOutTime = callOutTime;
	}
	public Double getSignMoney() {
		return signMoney;
	}
	public void setSignMoney(Double signMoney) {
		this.signMoney = signMoney;
	}
	public Double getSaleChanceMoney() {
		return saleChanceMoney;
	}
	public void setSaleChanceMoney(Double saleChanceMoney) {
		this.saleChanceMoney = saleChanceMoney;
	}
	public Double getSaleMoney() {
		return saleMoney;
	}
	public void setSaleMoney(Double saleMoney) {
		this.saleMoney = saleMoney;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getReportDate() {
		return reportDate;
	}
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getInputtime() {
		return inputtime;
	}
	public void setInputtime(Date inputtime) {
		this.inputtime = inputtime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public String[] getGroupIds() {
		return groupIds;
	}
	public void setGroupIds(String[] groupIds) {
		this.groupIds = groupIds;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getGroupNames() {
		return groupNames;
	}
	public void setGroupNames(String groupNames) {
		this.groupNames = groupNames;
	}
	public String getUserNames() {
		return userNames;
	}
	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}
	public String[] getUserIds() {
		return userIds;
	}
	public void setUserIds(String[] userIds) {
		this.userIds = userIds;
	}
	public String getDateFmtStr() {
		return dateFmtStr;
	}
	public void setDateFmtStr(String dateFmtStr) {
		this.dateFmtStr = dateFmtStr;
	}
	public String getDateFmt() {
		return dateFmt;
	}
	public void setDateFmt(String dateFmt) {
		this.dateFmt = dateFmt;
	}
	public String getGroupIdsStr() {
		return groupIdsStr;
	}
	public void setGroupIdsStr(String groupIdsStr) {
		this.groupIdsStr = groupIdsStr;
	}
	public String getReportDateStr() {
		reportDateStr = DateUtil.formatDate(reportDate,DateUtil.DATE_DAY);
		return reportDateStr;
	}
	public void setReportDateStr(String reportDateStr) {
		this.reportDateStr = reportDateStr;
	}
	
}