package com.qftx.tsm.report.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qftx.base.util.NumUtil;
import com.qftx.common.domain.BaseObject;

import java.util.Date;
/* tsm_report_call_info 统计分析-通话数据*/
public class TsmReportCallInfoBean extends BaseObject {
	private String id;	//ID编码
	private String orgId;	//单位ID
	private String account;	//账户
	private Integer callOutNum;	//呼出总数
	private Integer callInNum;	//呼出已接
	private Integer callRes;	//呼叫资源
	private Integer inCallAllNum;	//总呼入数
	private Integer inCallAnswerNum;//呼入已接数
	private Integer inCallTime;	//呼入时长
	private Integer allCallTime;	//总时长
	private Integer validCallOut;	//有效呼叫
	private Integer chargeTime;	//计费时长
	private Integer callTime;	//呼出时长
	private Date inputtime;	//录入时间
	private Date updateDate;	//更新时间
	private String dateFmt;
	
	private Integer willNum;
	private Integer signNum;
	private Double signMoney;
	private Double saleChanceMoney;	//逾期签单金额(5.1新增)
	private Double saleMoney;	//签单金额(5.1新增)
	private Double percent;//行动完成率
	private String percentStr;
	
	private String groupId;
	private String groupName;
	private String[] accounts;
	private String userName;
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
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Integer getCallOutNum() {
		return callOutNum;
	}
	public void setCallOutNum(Integer callOutNum) {
		this.callOutNum = callOutNum;
	}
	public Integer getCallInNum() {
		return callInNum;
	}
	public void setCallInNum(Integer callInNum) {
		this.callInNum = callInNum;
	}
	public Integer getCallRes() {
		return callRes;
	}
	public void setCallRes(Integer callRes) {
		this.callRes = callRes;
	}
	public Integer getValidCallOut() {
		return validCallOut;
	}
	public void setValidCallOut(Integer validCallOut) {
		this.validCallOut = validCallOut;
	}
	public Integer getChargeTime() {
		return chargeTime;
	}
	public void setChargeTime(Integer chargeTime) {
		this.chargeTime = chargeTime;
	}
	public Integer getCallTime() {
		return callTime;
	}
	public void setCallTime(Integer callTime) {
		this.callTime = callTime;
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
	public String getDateFmt() {
		return dateFmt;
	}
	public void setDateFmt(String dateFmt) {
		this.dateFmt = dateFmt;
	}
	public String[] getAccounts() {
		return accounts;
	}
	public void setAccounts(String[] accounts) {
		this.accounts = accounts;
	}
	public Integer getWillNum() {
		return willNum;
	}
	public void setWillNum(Integer willNum) {
		this.willNum = willNum;
	}
	public Integer getSignNum() {
		return signNum;
	}
	public void setSignNum(Integer signNum) {
		this.signNum = signNum;
	}
	public Double getSignMoney() {
		return signMoney;
	}
	public void setSignMoney(Double signMoney) {
		this.signMoney = signMoney;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
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
	public Integer getInCallAllNum() {
		return inCallAllNum;
	}
	public Integer getInCallAnswerNum() {
		return inCallAnswerNum;
	}
	public Integer getInCallTime() {
		return inCallTime;
	}
	public Integer getAllCallTime() {
		return allCallTime;
	}
	public void setInCallAllNum(Integer inCallAllNum) {
		this.inCallAllNum = inCallAllNum;
	}
	public void setInCallAnswerNum(Integer inCallAnswerNum) {
		this.inCallAnswerNum = inCallAnswerNum;
	}
	public void setInCallTime(Integer inCallTime) {
		this.inCallTime = inCallTime;
	}
	public void setAllCallTime(Integer allCallTime) {
		this.allCallTime = allCallTime;
	}
	public Double getPercent() {
		return percent;
	}
	public void setPercent(Double percent) {
		this.percent = percent;
	}
	public String getPercentStr() {
		if(percent!=null) percentStr = NumUtil.formart(percent*100);
		return percentStr;
	}
	public void setPercentStr(String percentStr) {
		this.percentStr = percentStr;
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
	
}