package com.qftx.tsm.follow.dto;

import com.qftx.tsm.follow.bean.CustSaleChanceBean;

import java.util.List;

public class CustSaleChanceDto extends CustSaleChanceBean implements Cloneable{
	
	private String queryText; // 用于模糊查询
	private String queryType;// 查询类型 
	private Integer roleType; // 角色类别：0--销售，1--管理者
	private String accs; //用于查询 所有者账号 字符串,号分割
	private String osType;//所有者查询方式 1-全部 2-只看自己 3-选中查询
	private String isType;//负责人查询方式 1-全部 2-只看自己 3-选中查询
	private List<String> ownerAccs; // 所有者集合
	private String theoryStartSignDate;//预计签单开始时间
	private String theoryEndSignDate;//预计签单结束时间
	private Integer isState; // 0：个人客户,1:企业客户
	private String ownerAcc;// 客户所有者
	private String showTheorySignDate;
	private List<String>resCustIds; // 在计划中的资源ID/资源ID集合
	private String resCustId; // RES_CUST_ID 客户ID
	
	private Integer status; // 客户状态 1:全部，2:意向客户，3:签约客户
	private Integer type;
	private String ownerName; // 拥有者名称	
	private String followId;
	private String inputName;//录入人名称
	
	private String iAccs;//负责人
	private List<String> inputAccs;
	
	private List<String> saleChanceIds;
	
	
	public String getQueryText() {
		return queryText;
	}
	public void setQueryText(String queryText) {
		this.queryText = queryText;
	}
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public Integer getRoleType() {
		return roleType;
	}
	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}
	public String getAccs() {
		return accs;
	}
	public void setAccs(String accs) {
		this.accs = accs;
	}
	public String getOsType() {
		return osType;
	}
	public void setOsType(String osType) {
		this.osType = osType;
	}
	public List<String> getOwnerAccs() {
		return ownerAccs;
	}
	public void setOwnerAccs(List<String> ownerAccs) {
		this.ownerAccs = ownerAccs;
	}
	public String getTheoryStartSignDate() {
		return theoryStartSignDate;
	}
	public void setTheoryStartSignDate(String theoryStartSignDate) {
		this.theoryStartSignDate = theoryStartSignDate;
	}
	public String getTheoryEndSignDate() {
		return theoryEndSignDate;
	}
	public void setTheoryEndSignDate(String theoryEndSignDate) {
		this.theoryEndSignDate = theoryEndSignDate;
	}
	
	public Integer getIsState() {
		return isState;
	}
	public void setIsState(Integer isState) {
		this.isState = isState;
	}
	public String getOwnerAcc() {
		return ownerAcc;
	}
	public void setOwnerAcc(String ownerAcc) {
		this.ownerAcc = ownerAcc;
	}
	public String getShowTheorySignDate() {
		return showTheorySignDate;
	}
	public void setShowTheorySignDate(String showTheorySignDate) {
		this.showTheorySignDate = showTheorySignDate;
	}
	public List<String> getResCustIds() {
		return resCustIds;
	}
	public void setResCustIds(List<String> resCustIds) {
		this.resCustIds = resCustIds;
	}
	public String getResCustId() {
		return resCustId;
	}
	public void setResCustId(String resCustId) {
		this.resCustId = resCustId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public List<String> getSaleChanceIds() {
		return saleChanceIds;
	}
	public void setSaleChanceIds(List<String> saleChanceIds) {
		this.saleChanceIds = saleChanceIds;
	}
	public String getFollowId() {
		return followId;
	}
	public void setFollowId(String followId) {
		this.followId = followId;
	}
	public String getInputName() {
		return inputName;
	}
	public void setInputName(String inputName) {
		this.inputName = inputName;
	}
	public String getiAccs() {
		return iAccs;
	}
	public void setiAccs(String iAccs) {
		this.iAccs = iAccs;
	}
	public List<String> getInputAccs() {
		return inputAccs;
	}
	public void setInputAccs(List<String> inputAccs) {
		this.inputAccs = inputAccs;
	}
	public String getIsType() {
		return isType;
	}
	public void setIsType(String isType) {
		this.isType = isType;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
