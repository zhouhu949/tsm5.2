package com.qftx.tsm.report.dto;

import com.qftx.tsm.main.bean.MainInfoBean;

import java.util.Date;

public class UserContactDTO extends MainInfoBean{
	private String will_id;//ID编码
	private String will_orgId;//机构ID
	private String will_type;//类型(0资源，1意向)
	private String will_account;//账户
	private Integer will_totalNum;//总数
	private Integer will_signNum;//转签约
	private Integer will_custNum;//转意向
	private Integer will_poolNum;//公海池
	private Integer will_noNum;//无进展
	private Integer will_callNum;//时长
	private Date will_inputtime;//录入时间
	private Date will_updateDate;//更新时间
	private Integer will_isSet;//录音主机设置：0-未设置，1-设置
	
	private String userId;
	private Integer resPlanCount;
	private Integer resNoContact;
	private Integer willPlanCount;
	private Integer willNoContact;
	
	/* 通话数据*/
	private Integer callOutNum;	//呼叫总数
	private Integer callInNum;	//已接总数
	private Integer callRes;	//呼叫资源
	private Integer validCallOut;	//有效呼叫
	private Integer chargeTime;	//计费时长
	private Integer callTime;	//通话时长
	
	private Double money; //签约金额
	private Integer tradCustNum;//交易客户数
	
	private Date fromDate;
	private Date toDate;
	private String groupId;
	private String[] groupIds;
	private String userName;
	private String groupName;

	public String getWill_id() {
		return will_id;
	}

	public void setWill_id(String will_id) {
		this.will_id = will_id;
	}

	public String getWill_orgId() {
		return will_orgId;
	}

	public void setWill_orgId(String will_orgId) {
		this.will_orgId = will_orgId;
	}

	public String getWill_type() {
		return will_type;
	}

	public void setWill_type(String will_type) {
		this.will_type = will_type;
	}

	public String getWill_account() {
		return will_account;
	}

	public void setWill_account(String will_account) {
		this.will_account = will_account;
	}

	public Integer getWill_totalNum() {
		return will_totalNum;
	}

	public void setWill_totalNum(Integer will_totalNum) {
		this.will_totalNum = will_totalNum;
	}

	public Integer getWill_signNum() {
		return will_signNum;
	}

	public void setWill_signNum(Integer will_signNum) {
		this.will_signNum = will_signNum;
	}

	public Integer getWill_custNum() {
		return will_custNum;
	}

	public void setWill_custNum(Integer will_custNum) {
		this.will_custNum = will_custNum;
	}

	public Integer getWill_poolNum() {
		return will_poolNum;
	}

	public void setWill_poolNum(Integer will_poolNum) {
		this.will_poolNum = will_poolNum;
	}

	public Integer getWill_noNum() {
		return will_noNum;
	}

	public void setWill_noNum(Integer will_noNum) {
		this.will_noNum = will_noNum;
	}

	public Integer getWill_callNum() {
		return will_callNum;
	}

	public void setWill_callNum(Integer will_callNum) {
		this.will_callNum = will_callNum;
	}

	public Date getWill_inputtime() {
		return will_inputtime;
	}

	public void setWill_inputtime(Date will_inputtime) {
		this.will_inputtime = will_inputtime;
	}

	public Date getWill_updateDate() {
		return will_updateDate;
	}

	public void setWill_updateDate(Date will_updateDate) {
		this.will_updateDate = will_updateDate;
	}

	public Integer getWill_isSet() {
		return will_isSet;
	}

	public void setWill_isSet(Integer will_isSet) {
		this.will_isSet = will_isSet;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getResPlanCount() {
		return resPlanCount;
	}

	public void setResPlanCount(Integer resPlanCount) {
		this.resPlanCount = resPlanCount;
	}

	public Integer getResNoContact() {
		return resNoContact;
	}

	public void setResNoContact(Integer resNoContact) {
		this.resNoContact = resNoContact;
	}

	public Integer getWillPlanCount() {
		return willPlanCount;
	}

	public void setWillPlanCount(Integer willPlanCount) {
		this.willPlanCount = willPlanCount;
	}

	public Integer getWillNoContact() {
		return willNoContact;
	}

	public void setWillNoContact(Integer willNoContact) {
		this.willNoContact = willNoContact;
	}

	public Integer getCallTime() {
		return callTime;
	}

	public void setCallTime(Integer callTime) {
		this.callTime = callTime;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String[] getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(String[] groupIds) {
		this.groupIds = groupIds;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
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

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Integer getTradCustNum() {
		return tradCustNum;
	}

	public void setTradCustNum(Integer tradCustNum) {
		this.tradCustNum = tradCustNum;
	}
	
}
