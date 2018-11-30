package com.qftx.tsm.plan.group.month.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qftx.common.domain.BaseObject;

import java.util.Date;
import java.util.List;
/* plan_groupmonth_analy 计划_部门月计划_分析*/
public class PlanGroupmonthAnalyBean extends BaseObject {
	private String id;	//id
	private String orgId;	//机构ID
	private String groupId;	//部门
	private Integer groupType;	//group类型 (0:团队1:部门）
	private Integer resNum;	//联系资源数
	private Integer willNum;	//转意向数
	private Integer signNum;	//转签约数
	private Integer silenceCustNum;	//沉默客户数
	private Integer lostCustNum;	//流失客户数
	private Integer newCustSignNum;	//新客户签约数
	private Integer oldCustSignNum;	//老客户签约数
	private Double newCustSignMoney;	//新客户签约金额
	private Double oldCustSignMoney;	//老客户签约金额
	private Double signMoney;	//全部签约金额
	private String context;	//备注
	private Date updatetime;	//修改时间
	private Date inputtime;	//录入时间(计划日期)
	private Integer isDel;	//册除状态1-删除，0-未删除
	
	private Date fromDate;	//
	private Date toDate;	//
	
	private String groupName;
	private int callTime; //通话时长
	private int chargTime;//计费时长
	private List<String> groupIds;
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
	public Integer getResNum() {
		return resNum;
	}
	public void setResNum(Integer resNum) {
		this.resNum = resNum;
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
	public Integer getSilenceCustNum() {
		return silenceCustNum;
	}
	public void setSilenceCustNum(Integer silenceCustNum) {
		this.silenceCustNum = silenceCustNum;
	}
	public Integer getLostCustNum() {
		return lostCustNum;
	}
	public void setLostCustNum(Integer lostCustNum) {
		this.lostCustNum = lostCustNum;
	}
	public Integer getNewCustSignNum() {
		return newCustSignNum;
	}
	public void setNewCustSignNum(Integer newCustSignNum) {
		this.newCustSignNum = newCustSignNum;
	}
	public Integer getOldCustSignNum() {
		return oldCustSignNum;
	}
	public void setOldCustSignNum(Integer oldCustSignNum) {
		this.oldCustSignNum = oldCustSignNum;
	}
	public Double getNewCustSignMoney() {
		return newCustSignMoney;
	}
	public void setNewCustSignMoney(Double newCustSignMoney) {
		this.newCustSignMoney = newCustSignMoney;
	}
	public Double getOldCustSignMoney() {
		return oldCustSignMoney;
	}
	public void setOldCustSignMoney(Double oldCustSignMoney) {
		this.oldCustSignMoney = oldCustSignMoney;
	}
	public Double getSignMoney() {
		return signMoney;
	}
	public void setSignMoney(Double signMoney) {
		this.signMoney = signMoney;
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
	public int getCallTime() {
		return callTime;
	}
	public void setCallTime(int callTime) {
		this.callTime = callTime;
	}
	public int getChargTime() {
		return chargTime;
	}
	public void setChargTime(int chargTime) {
		this.chargTime = chargTime;
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
	public List<String> getGroupIds() {
		return groupIds;
	}
	public void setGroupIds(List<String> groupIds) {
		this.groupIds = groupIds;
	}
}