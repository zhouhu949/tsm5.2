package com.qftx.tsm.plan.user.day.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qftx.base.util.DateUtil;
import com.qftx.common.domain.BaseObject;
import com.qftx.tsm.plan.facade.enu.PlanResCR;

import java.util.Date;
/* plan_userday_resource 计划_个人日计划_资源*/
public class PlanUserDayResourceBean extends BaseObject {
	private static final long serialVersionUID = 7116650282224531038L;
	private String id; // 日计划ID
	private String orgId; // 机构ID
	private String sudId; // 个人计划ID
	private String custLevel; // 优先级[0未设,1高]
	private String custId; // 客户ID
	private String custName; // 客户名称
	private String company;//客户单位
	private String custUser; // 联系人
	private String custSex; // 性别
	private Date custInputtime;	//分配时间
	private String custTel;	//联系电话
	private String custDesc; // 完成情况
	private String custContext; // 资源备注
	private Integer source;	//客户来源 1:自有导入 2:分配交接 3:公海取回(5.1新增) 4：Ai初筛  5：在线表单 6：数据合作
	private String status; // 联系状态(0未联系，1已联系)
	private Integer state;	//临时计划状态（0：非临时计划，1：临时计划,2：明日计划）
	private String context; // 计划说明
	private Date updatetime; // 修改时间
	private Date inputtime; // 录入时间(分配时间)
	private Integer isDel; // 册除状态1-删除，0-未删除
	private String contactResult;	//联系结果
	
	private String cust_ownerAcc;//资源表字段	拥有者
	private String cust_custId;//资源表字段	custId
	private String cust_type;//资源表字段	type
	private String cust_status;//资源表字段	status
	
	
	private String contactResultStr;	//联系结果
	
	private Date actionDate;//最近联系时间
	private String actionDateStr;
	private String custInputtimeStr;	//分配时间
	
	private String queryStr;//客户名 联系人 联系电话 模糊搜索字符串

	private String[] ids;
	private String[] custIds;
	private String[] rejectCustIds;
	private String userId;
	
	private String groupId;
	private String groupName;//所属分组
	private String queryType;//查询类型
	private Date planDate;
	private Date planDateFrom;
	private Date planDateTo;
	private boolean ifFuture = false;//是否未来计划计划 大于等于当前时间的计划
	private boolean typeChange = false;//状态是否变化
	private String userAcc;
	
	private String pstartDate;
	private String pendDate;
	private Integer oDateType;
	
	private String startActionDate;
	private String endActionDate;
	private Integer lDateType;
	
	private String lastLogId;//资源备注ID
	private Integer isHaveResRemark;
	private String sourceStr;
	private boolean permision =true;//权限
	
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

	public String getSudId() {
		return sudId;
	}

	public void setSudId(String sudId) {
		this.sudId = sudId;
	}

	public String getCustLevel() {
		return custLevel;
	}

	public void setCustLevel(String custLevel) {
		this.custLevel = custLevel;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustUser() {
		return custUser;
	}

	public void setCustUser(String custUser) {
		this.custUser = custUser;
	}

	public String getCustSex() {
		return custSex;
	}

	public void setCustSex(String custSex) {
		this.custSex = custSex;
	}
	public void setCustInputtime(Date custInputtime) {
		this.custInputtime = custInputtime;
	}
	public String getCustTel() {
		return custTel;
	}
	public void setCustTel(String custTel) {
		this.custTel = custTel;
	}
	public String getCustDesc() {
		return custDesc;
	}

	public void setCustDesc(String custDesc) {
		this.custDesc = custDesc;
	}

	public String getCustContext() {
		return custContext;
	}

	public void setCustContext(String custContext) {
		this.custContext = custContext;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
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
	public String getContactResult() {
		return contactResult;
	}
	public void setContactResult(String contactResult) {
		this.contactResult = contactResult;
	}
	
	public String getQueryStr() {
		return queryStr;
	}

	public void setQueryStr(String queryStr) {
		this.queryStr = queryStr;
	}
	
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public String[] getCustIds() {
		return custIds;
	}

	public void setCustIds(String[] custIds) {
		this.custIds = custIds;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getUserAcc() {
		return userAcc;
	}

	public void setUserAcc(String userAcc) {
		this.userAcc = userAcc;
	}

	public boolean isIfFuture() {
		return ifFuture;
	}

	public void setIfFuture(boolean ifFuture) {
		this.ifFuture = ifFuture;
	}

	public boolean isTypeChange() {
		return typeChange;
	}

	public void setTypeChange(boolean typeChange) {
		this.typeChange = typeChange;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getPstartDate() {
		return pstartDate;
	}

	public String getPendDate() {
		return pendDate;
	}

	public Integer getoDateType() {
		return oDateType;
	}

	public String getStartActionDate() {
		return startActionDate;
	}

	public String getEndActionDate() {
		return endActionDate;
	}

	public Integer getlDateType() {
		return lDateType;
	}

	public Integer getIsHaveResRemark() {
		return isHaveResRemark;
	}

	public void setPstartDate(String pstartDate) {
		this.pstartDate = pstartDate;
	}

	public void setPendDate(String pendDate) {
		this.pendDate = pendDate;
	}

	public void setoDateType(Integer oDateType) {
		this.oDateType = oDateType;
	}

	public void setStartActionDate(String startActionDate) {
		this.startActionDate = startActionDate;
	}

	public void setEndActionDate(String endActionDate) {
		this.endActionDate = endActionDate;
	}

	public void setlDateType(Integer lDateType) {
		this.lDateType = lDateType;
	}

	public void setIsHaveResRemark(Integer isHaveResRemark) {
		this.isHaveResRemark = isHaveResRemark;
	}

	public Date getPlanDateFrom() {
		return planDateFrom;
	}

	public Date getPlanDateTo() {
		return planDateTo;
	}

	public void setPlanDateFrom(Date planDateFrom) {
		this.planDateFrom = planDateFrom;
	}

	public void setPlanDateTo(Date planDateTo) {
		this.planDateTo = planDateTo;
	}

	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}

	public String getLastLogId() {
		return lastLogId;
	}

	public void setLastLogId(String lastLogId) {
		this.lastLogId = lastLogId;
	}

	public Date getPlanDate() {
		return planDate;
	}

	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}

	public String[] getRejectCustIds() {
		return rejectCustIds;
	}

	public void setRejectCustIds(String[] rejectCustIds) {
		this.rejectCustIds = rejectCustIds;
	}

	public Date getCustInputtime() {
		return custInputtime;
	}

	public Date getActionDate() {
		return actionDate;
	}

	public String getActionDateStr() {
		return DateUtil.formatDate(actionDate, DateUtil.DATE_MIN);
	}

	public String getCustInputtimeStr() {
		return DateUtil.formatDate(custInputtime, DateUtil.DATE_MIN);
	}

	public String getContactResultStr() {
		if(contactResult==null){
			contactResultStr = PlanResCR.NO_CONTACT.getResult();
		}else{
			contactResultStr =contactResult;
		}
		return contactResultStr;
	}

	public String getSourceStr() {
		//客户来源 1:自有导入 2:分配交接 3:公海取回(5.1新增) 4：Ai初筛  5：在线表单 6：数据合作
		if(source==null){
			
		}else{
			if(source==1){
				sourceStr="自有导入";
			}else if(source==2){
				sourceStr="分配交接";
			}else if(source==3){
				sourceStr="公海取回";
			}else if(source==4){
				sourceStr="AI初筛";
			}else if(source==5){
				sourceStr="在线表单";
			}
		}
		return sourceStr;
	}

	public void setSourceStr(String sourceStr) {
		this.sourceStr = sourceStr;
	}

	public String getCust_ownerAcc() {
		return cust_ownerAcc;
	}

	public void setCust_ownerAcc(String cust_ownerAcc) {
		this.cust_ownerAcc = cust_ownerAcc;
	}

	public String getCust_custId() {
		return cust_custId;
	}

	public void setCust_custId(String cust_custId) {
		this.cust_custId = cust_custId;
	}

	public String getCust_type() {
		return cust_type;
	}

	public void setCust_type(String cust_type) {
		this.cust_type = cust_type;
	}

	public String getCust_status() {
		return cust_status;
	}

	public void setCust_status(String cust_status) {
		this.cust_status = cust_status;
	}

	public boolean isPermision() {
		return permision;
	}

	public void setPermision(boolean permision) {
		this.permision = permision;
	}
	
}