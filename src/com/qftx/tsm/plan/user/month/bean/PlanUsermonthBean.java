package com.qftx.tsm.plan.user.month.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qftx.common.domain.BaseObject;

import java.util.Date;
/* plan_usermonth 计划_个人月计划*/
public class PlanUsermonthBean extends BaseObject {
	private static final long serialVersionUID = 2109665790682156616L;
	private String id;	//ID
	private String orgId;	//机构ID
	private String groupId;	//部门
	private String userId;	//销售人员
	private String planMonth;	//月
	private String planYear;	//年
	private Double planMoney;	//总计划签约金额
	private Integer planSigncustnum;	//总计划签约客户数
	private Integer planWillcustnum;	//总计划意向客户数
	private String planDesc;	//总计划备注
	private Integer factSigncustnum;	//实际签约客户数
	private Integer factWillcustnum;	//实际向客户数
	private Double factMoney;	//实际金额
	private Integer planMoneyState;	//计划签约金额完成情况积分0未完成，1完成未领取积分，2积分已经领取
	private Integer planSigncustnumState;	//计划签约客户数完成情况积分0未完成，1完成未领取积分，2积分已经领取
	private Integer planWillcustnumState;	//计划意向客户数完成情况积分0未完成，1完成未领取积分，2积分已经领取
	private String planStatus;	//整个计划完成状态(0未完成，1完成)
	private Integer planWillcustnumAdd;	//待确定客户，新增（计划值）
	private Integer planSigncustnumAdd;	//待确定客户，签约（计划值）
	private Double planWillcustnumMoney;	//待确定客户，金额（计划值）
	private Integer factWillcustnumAdd;	//待确定客户，新增(实际值)
	private Integer factSigncustnumAdd;	//待确定客户，签约（实际值）
	private Double factWillcustnumMoney;	//待确定客户，金额（实际值）
	private Integer waitConfirmCustState;	//待确定客户状态(0:未完成,1:完成)
	private String planWillcustnumDesc;	//待确定客户备注
	private String authState;	//审核状态(1未审核，2通过，0未通过)
	private String authUserid;	//审核人ID
	private String authUsername;	//审核人名称
	private String authDesc;	//审核说明
	private Date authTime;	//审核时间
	private String lastCommontId;	//最新评论ID
	private String status;	//执行状态(0未上报，1已上报)上报之后不能更改更，只能等0时可以编辑
	private String context;	//整个计划说明
	private Date updatetime;	//修改时间
	private Date inputtime;	//录入时间(计划日期)
	private Integer isDel;	//册除状态1-删除，0-未删除
	
	private String userName;
	private String userAcc;
	private String groupName;
	private String lastCommont; 
	private String lastCommontUserName; 
	
	private String[] groupIds;
	
	private String[] ids;
	
	private String[] userIds;
	private String userNames;
	private String[] authUserIds;
	private String  startInputtime;
	private String  endInputtime;
	private boolean tooLateEdit;
	private String authType;
	
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
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPlanMonth() {
		return planMonth;
	}
	public void setPlanMonth(String planMonth) {
		this.planMonth = planMonth;
	}
	public String getPlanYear() {
		return planYear;
	}
	public void setPlanYear(String planYear) {
		this.planYear = planYear;
	}
	public Double getPlanMoney() {
		return planMoney;
	}
	public void setPlanMoney(Double planMoney) {
		this.planMoney = planMoney;
	}
	public Integer getPlanSigncustnum() {
		return planSigncustnum;
	}
	public void setPlanSigncustnum(Integer planSigncustnum) {
		this.planSigncustnum = planSigncustnum;
	}
	public Integer getPlanWillcustnum() {
		return planWillcustnum;
	}
	public void setPlanWillcustnum(Integer planWillcustnum) {
		this.planWillcustnum = planWillcustnum;
	}
	public String getPlanDesc() {
		return planDesc;
	}
	public void setPlanDesc(String planDesc) {
		this.planDesc = planDesc;
	}
	public Integer getFactSigncustnum() {
		return factSigncustnum;
	}
	public void setFactSigncustnum(Integer factSigncustnum) {
		this.factSigncustnum = factSigncustnum;
	}
	public Integer getFactWillcustnum() {
		return factWillcustnum;
	}
	public void setFactWillcustnum(Integer factWillcustnum) {
		this.factWillcustnum = factWillcustnum;
	}
	public Double getFactMoney() {
		return factMoney;
	}
	public void setFactMoney(Double factMoney) {
		this.factMoney = factMoney;
	}
	public Integer getPlanMoneyState() {
		return planMoneyState;
	}
	public void setPlanMoneyState(Integer planMoneyState) {
		this.planMoneyState = planMoneyState;
	}
	public Integer getPlanSigncustnumState() {
		return planSigncustnumState;
	}
	public void setPlanSigncustnumState(Integer planSigncustnumState) {
		this.planSigncustnumState = planSigncustnumState;
	}
	public Integer getPlanWillcustnumState() {
		return planWillcustnumState;
	}
	public void setPlanWillcustnumState(Integer planWillcustnumState) {
		this.planWillcustnumState = planWillcustnumState;
	}
	public String getPlanStatus() {
		return planStatus;
	}
	public void setPlanStatus(String planStatus) {
		this.planStatus = planStatus;
	}
	public Integer getPlanWillcustnumAdd() {
		return planWillcustnumAdd;
	}
	public void setPlanWillcustnumAdd(Integer planWillcustnumAdd) {
		this.planWillcustnumAdd = planWillcustnumAdd;
	}
	public Integer getPlanSigncustnumAdd() {
		return planSigncustnumAdd;
	}
	public void setPlanSigncustnumAdd(Integer planSigncustnumAdd) {
		this.planSigncustnumAdd = planSigncustnumAdd;
	}
	public Double getPlanWillcustnumMoney() {
		return planWillcustnumMoney;
	}
	public void setPlanWillcustnumMoney(Double planWillcustnumMoney) {
		this.planWillcustnumMoney = planWillcustnumMoney;
	}
	public Integer getFactWillcustnumAdd() {
		return factWillcustnumAdd;
	}
	public void setFactWillcustnumAdd(Integer factWillcustnumAdd) {
		this.factWillcustnumAdd = factWillcustnumAdd;
	}
	public Integer getFactSigncustnumAdd() {
		return factSigncustnumAdd;
	}
	public void setFactSigncustnumAdd(Integer factSigncustnumAdd) {
		this.factSigncustnumAdd = factSigncustnumAdd;
	}
	public Double getFactWillcustnumMoney() {
		return factWillcustnumMoney;
	}
	public void setFactWillcustnumMoney(Double factWillcustnumMoney) {
		this.factWillcustnumMoney = factWillcustnumMoney;
	}
	public Integer getWaitConfirmCustState() {
		return waitConfirmCustState;
	}
	public void setWaitConfirmCustState(Integer waitConfirmCustState) {
		this.waitConfirmCustState = waitConfirmCustState;
	}
	public String getPlanWillcustnumDesc() {
		return planWillcustnumDesc;
	}
	public void setPlanWillcustnumDesc(String planWillcustnumDesc) {
		this.planWillcustnumDesc = planWillcustnumDesc;
	}
	public String getAuthState() {
		return authState;
	}
	public void setAuthState(String authState) {
		this.authState = authState;
	}
	public String getAuthUserid() {
		return authUserid;
	}
	public void setAuthUserid(String authUserid) {
		this.authUserid = authUserid;
	}
	public String getAuthUsername() {
		if("plan_auth_admin".equals(authUserid)){
			authUsername ="系统";
		}
		return authUsername;
	}
	public void setAuthUsername(String authUsername) {
		this.authUsername = authUsername;
	}
	public String getAuthDesc() {
		return authDesc;
	}
	public void setAuthDesc(String authDesc) {
		this.authDesc = authDesc;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getAuthTime() {
		return authTime;
	}
	public void setAuthTime(Date authTime) {
		this.authTime = authTime;
	}
	public String getLastCommontId() {
		return lastCommontId;
	}
	public void setLastCommontId(String lastCommontId) {
		this.lastCommontId = lastCommontId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getInputtime() {
		return inputtime;
	}
	public void setInputtime(Date inputtime) {
		this.inputtime = inputtime;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLastCommont() {
		return lastCommont;
	}
	public void setLastCommont(String lastCommont) {
		this.lastCommont = lastCommont;
	}
	public String getLastCommontUserName() {
		return lastCommontUserName;
	}
	public void setLastCommontUserName(String lastCommontUserName) {
		this.lastCommontUserName = lastCommontUserName;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getUserAcc() {
		return userAcc;
	}
	public void setUserAcc(String userAcc) {
		this.userAcc = userAcc;
	}
	public String[] getGroupIds() {
		return groupIds;
	}
	public void setGroupIds(String[] groupIds) {
		this.groupIds = groupIds;
	}
	public String[] getUserIds() {
		return userIds;
	}
	public void setUserIds(String[] userIds) {
		this.userIds = userIds;
	}
	public String[] getAuthUserIds() {
		return authUserIds;
	}
	public void setAuthUserIds(String[] authUserIds) {
		this.authUserIds = authUserIds;
	}
	public String getStartInputtime() {
		return startInputtime;
	}
	public void setStartInputtime(String startInputtime) {
		this.startInputtime = startInputtime;
	}
	public String getEndInputtime() {
		return endInputtime;
	}
	public void setEndInputtime(String endInputtime) {
		this.endInputtime = endInputtime;
	}
	public String[] getIds() {
		return ids;
	}
	public void setIds(String[] ids) {
		this.ids = ids;
	}
	public String getUserNames() {
		return userNames;
	}
	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}
	public boolean isTooLateEdit() {
		return tooLateEdit;
	}
	public void setTooLateEdit(boolean tooLateEdit) {
		this.tooLateEdit = tooLateEdit;
	}
	public String getAuthType() {
		if(authUserid!=null){
			if("plan_auth_admin".equals(authUserid)){
				authType="自动";
			} else{
				authType="手动";
			}
		} 
		return authType;
	}
	public void setAuthType(String authType) {
		this.authType = authType;
	}
	
}