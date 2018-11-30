package com.qftx.tsm.plan.group.month.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qftx.common.domain.BaseObject;
import com.qftx.tsm.plan.user.month.bean.QueryYearMonthBean;

import java.util.Date;
import java.util.List;
/* plan_groupmonth 计划_部门月计划*/
public class PlanGroupmonthBean extends BaseObject {
	private static final long serialVersionUID = -4315902004528536082L;
	private String id;	//ID
	private String orgId;	//机构ID
	private String groupId;	//部门
	private Integer groupType;	//group类型 (0:团队1:部门）
	private String planMonth;	//月
	private String planYear;	//年
	private Double planMoney;	//总计划签约金额
	private Integer planSigncustnum;	//总计划签约客户数
	private Integer planWillcustnum;	//总计划意向客户数
	private Double factMoney;	//实际签约金额
	private Integer factSigncustnum;	//实际签约客户数
	private Integer factWillcustnum;	//实际意向客户数
	private String planDesc;	//总计划备注
	private Integer warpSigncustnum;	//偏差值签约客户数
	private Integer warpWillcustnum;	//偏差值意向客户数
	private Double warpMoney;	//偏差值金额
	private String warpDesc;	//偏差调整原因
	private Integer planMoneyState;	//计划签约金额完成情况0未完成，1完成(积分未领取),2（积分领取）
	private Integer planSigncustnumState;	//计划签约客户数完成情况0未完成，1完成(积分未领取),2（积分领取）
	private Integer planWillcustnumState;	//计划意向客户数完成情况0未完成，1完成(积分未领取),2（积分领取）
	private String authState;	//审核状态(1未审核，2通过，0未通过)
	private String authUserid;	//审核人ID
	private String authUsername;	//审核人名称
	private Date authTime;	//审核时间
	private String lastCommontId;	//最新评论ID
	private String authDesc;	//审核说明
	private String status;	//执行状态(0未上报，1已上报)上报之后不能更改更，只能等0时可以编辑
	private String planStatus;	//计划状态(0未完成，1完成(积分未领取),2（积分领取）)
	private String context;	//计划说明
	private Date updatetime;	//修改时间
	private Date inputtime;	//录入时间(计划日期)
	private Integer isDel;	//册除状态1-删除，0-未删除
	
	//团队成员计划
	private Double 	usersPlanMoney;			//团队成员总计划签约金额
	private Integer usersPlanSigncustnum;	//团队成员总计划签约客户数
	private Integer usersPlanWillcustnum;	//团队成员总计划意向客户数
	
	//关联字段
	private String groupName;
	private String lastCommont; 
	private String lastCommontUserName; 
	
	//查询条件
	private String[] groupIds;
	private String groupIdsStr;
	private String groupNames;
	private String[] ids;
	private String[] authUserIds;
	private String startInputtime;
	private String endInputtime;
	private List<QueryYearMonthBean> querys;
	private int memberCount;
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
	public Integer getGroupType() {
		return groupType;
	}
	public void setGroupType(Integer groupType) {
		this.groupType = groupType;
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
	public Double getFactMoney() {
		return factMoney;
	}
	public void setFactMoney(Double factMoney) {
		this.factMoney = factMoney;
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
	public String getPlanDesc() {
		return planDesc;
	}
	public void setPlanDesc(String planDesc) {
		this.planDesc = planDesc;
	}
	public Integer getWarpSigncustnum() {
		return warpSigncustnum;
	}
	public void setWarpSigncustnum(Integer warpSigncustnum) {
		this.warpSigncustnum = warpSigncustnum;
	}
	public Integer getWarpWillcustnum() {
		return warpWillcustnum;
	}
	public void setWarpWillcustnum(Integer warpWillcustnum) {
		this.warpWillcustnum = warpWillcustnum;
	}
	public Double getWarpMoney() {
		return warpMoney;
	}
	public void setWarpMoney(Double warpMoney) {
		this.warpMoney = warpMoney;
	}
	public String getWarpDesc() {
		return warpDesc;
	}
	public void setWarpDesc(String warpDesc) {
		this.warpDesc = warpDesc;
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
	public String getAuthDesc() {
		return authDesc;
	}
	public void setAuthDesc(String authDesc) {
		this.authDesc = authDesc;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPlanStatus() {
		return planStatus;
	}
	public void setPlanStatus(String planStatus) {
		this.planStatus = planStatus;
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
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
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
	public String[] getGroupIds() {
		return groupIds;
	}
	public void setGroupIds(String[] groupIds) {
		this.groupIds = groupIds;
	}
	public List<QueryYearMonthBean> getQuerys() {
		return querys;
	}
	public void setQuerys(List<QueryYearMonthBean> querys) {
		this.querys = querys;
	}
	public int getMemberCount() {
		return memberCount;
	}
	public void setMemberCount(int memberCount) {
		this.memberCount = memberCount;
	}
	public Double getUsersPlanMoney() {
		return usersPlanMoney;
	}
	public void setUsersPlanMoney(Double usersPlanMoney) {
		this.usersPlanMoney = usersPlanMoney;
	}
	public Integer getUsersPlanSigncustnum() {
		return usersPlanSigncustnum;
	}
	public void setUsersPlanSigncustnum(Integer usersPlanSigncustnum) {
		this.usersPlanSigncustnum = usersPlanSigncustnum;
	}
	public Integer getUsersPlanWillcustnum() {
		return usersPlanWillcustnum;
	}
	public void setUsersPlanWillcustnum(Integer usersPlanWillcustnum) {
		this.usersPlanWillcustnum = usersPlanWillcustnum;
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
	public String[] getAuthUserIds() {
		return authUserIds;
	}
	public void setAuthUserIds(String[] authUserIds) {
		this.authUserIds = authUserIds;
	}
	public String[] getIds() {
		return ids;
	}
	public void setIds(String[] ids) {
		this.ids = ids;
	}
	public String getGroupNames() {
		return groupNames;
	}
	public void setGroupNames(String groupNames) {
		this.groupNames = groupNames;
	}
	public String getGroupIdsStr() {
		return groupIdsStr;
	}
	public void setGroupIdsStr(String groupIdsStr) {
		this.groupIdsStr = groupIdsStr;
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