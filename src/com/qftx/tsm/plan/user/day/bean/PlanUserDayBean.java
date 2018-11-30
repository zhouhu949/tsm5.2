package com.qftx.tsm.plan.user.day.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qftx.base.util.DateUtil;
import com.qftx.common.domain.BaseObject;

import java.util.Date;
/* plan_userday 计划_个人日计划*/
public class PlanUserDayBean extends BaseObject {
	private static final long serialVersionUID = 9181776869956055845L;
	
	private String id; // 日计划ID
	private String orgId; // 机构ID
	private String groupId; // 部门
	private String userId; // 销售人员
	private String context; // 计划说明
	private String status; // 执行状态(0未上报，1已上报)，上报后，用户不能编辑
	private Date planDate; // 计划日期
	private String authState; // 0未审核，1通过，2未通过
	private Date updatetime; // 修改时间
	private Date inputtime; // 录入时间(计划日期)
	private Integer isDel; // 册除状态1-删除，0-未删除
	private String authDesc; // 审核点评
	
	private String groupName;//部门名称
	private String userName;
	private int resourceCount;
	private int resourceContactCount;
	private int signcustCount;
	private int signcustContactCount;
	private int willcustCount;
	private int willcustContactCount;
	
	private String type;//res资源  will意向  sign签约
	private String saleProcessId;//销售进程ID
	
	private String[] custIds;
	private Date fromDate;
	private Date toDate;
	private String planDateStr;
	private String authStateStr;
	private String userAcc;
	
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
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getPlanDate() {
		return planDate;
	}
	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}
	public String getAuthState() {
		return authState;
	}
	public void setAuthState(String authState) {
		this.authState = authState;
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
	public String getAuthDesc() {
		return authDesc;
	}
	public void setAuthDesc(String authDesc) {
		this.authDesc = authDesc;
	}
	public int getResourceCount() {
		return resourceCount;
	}
	public void setResourceCount(int resourceCount) {
		this.resourceCount = resourceCount;
	}
	public int getSigncustCount() {
		return signcustCount;
	}
	public void setSigncustCount(int signcustCount) {
		this.signcustCount = signcustCount;
	}
	public int getWillcustCount() {
		return willcustCount;
	}
	public void setWillcustCount(int willcustCount) {
		this.willcustCount = willcustCount;
	}
	public int getResourceContactCount() {
		return resourceContactCount;
	}
	public void setResourceContactCount(int resourceContactCount) {
		this.resourceContactCount = resourceContactCount;
	}
	public int getSigncustContactCount() {
		return signcustContactCount;
	}
	public void setSigncustContactCount(int signcustContactCount) {
		this.signcustContactCount = signcustContactCount;
	}
	public int getWillcustContactCount() {
		return willcustContactCount;
	}
	public void setWillcustContactCount(int willcustContactCount) {
		this.willcustContactCount = willcustContactCount;
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
	public String getPlanDateStr() {
		if(planDate !=null&&planDateStr==null){
			planDateStr =  DateUtil.formatDate(planDate,DateUtil.DATE_DAY);
		} 
		return planDateStr;
	}
	public void setPlanDateStr(String planDateStr) {
		this.planDateStr = planDateStr;
	}
	public String getAuthStateStr() {
		if(authState!=null &&authStateStr==null){
			if("0".equals(authState)){
				authStateStr="待审核";
			}else if("1".equals(authState)){
				authStateStr="通过";
			}else if("2".equals(authState)){
				authStateStr="驳回";
			}
		}
		return authStateStr;
	}
	public void setAuthStateStr(String authStateStr) {
		this.authStateStr = authStateStr;
	}
	public String[] getCustIds() {
		return custIds;
	}
	public void setCustIds(String[] custIds) {
		this.custIds = custIds;
	}
	public String getUserAcc() {
		return userAcc;
	}
	public void setUserAcc(String userAcc) {
		this.userAcc = userAcc;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSaleProcessId() {
		return saleProcessId;
	}
	public void setSaleProcessId(String saleProcessId) {
		this.saleProcessId = saleProcessId;
	}
	
}