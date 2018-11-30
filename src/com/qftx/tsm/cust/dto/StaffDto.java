package com.qftx.tsm.cust.dto;

import com.qftx.common.domain.BaseObject;

import java.util.Date;
import java.util.List;

/**
 * 员工资源分配管理
 * 
 * @author: wuwei
 * @since: 2013-10-30 上午10:06:05
 * @history:
 */
public class StaffDto extends BaseObject {
	private static final long serialVersionUID = 5107628259766957197L;
	private String staffId;
	private String staffName;
	private String roleName;
	private String teamGroupName;// 团队名称
	private String teamGroupId;// 团队ID
	private String memberCount;// 团队成员数
	private String staffAcc;
	private String staffResourceSum;// 员工剩余资源
	private String queryStartRemain; // 资源剩余条件开始
	private String queryEndRemain;// 资源剩余条件结束
	private Integer concatTotal;
	private Integer noConcatTotal;
	private String orgId;
	private String type;
	private Date optorDate;
	private String resMemain;// 资源操作条数
	private String optoerName;
	private String optAcc;
	private String optName;
	private Long optCount;
    private List<String> accounts;
    private String resGroupId;
    private String ResGroupName;
    private String queryContition;
	private boolean queryPhone;//是否为电话号码查询
	private Date servetime;
	private Integer dDateType;
	public String getQueryContition() {
		return queryContition;
	}

	public void setQueryContition(String queryContition) {
		this.queryContition = queryContition;
	}

	public Integer getConcatTotal() {
		return concatTotal;
	}

	public void setConcatTotal(Integer concatTotal) {
		this.concatTotal = concatTotal;
	}

	public Integer getNoConcatTotal() {
		return noConcatTotal;
	}

	public void setNoConcatTotal(Integer noConcatTotal) {
		this.noConcatTotal = noConcatTotal;
	}

	public String getOptoerName() {
		return optoerName;
	}

	public void setOptoerName(String optoerName) {
		this.optoerName = optoerName;
	}

	public String getResMemain() {
		return resMemain;
	}

	public void setResMemain(String resMemain) {
		this.resMemain = resMemain;
	}

	public Date getOptorDate() {
		return optorDate;
	}

	public void setOptorDate(Date optorDate) {
		this.optorDate = optorDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getTeamGroupName() {
		return teamGroupName;
	}

	public void setTeamGroupName(String teamGroupName) {
		this.teamGroupName = teamGroupName;
	}

	public String getTeamGroupId() {
		return teamGroupId;
	}

	public void setTeamGroupId(String teamGroupId) {
		this.teamGroupId = teamGroupId;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getStaffAcc() {
		return staffAcc;
	}

	public void setStaffAcc(String staffAcc) {
		this.staffAcc = staffAcc;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getStaffResourceSum() {
		return staffResourceSum;
	}

	public void setStaffResourceSum(String staffResourceSum) {
		this.staffResourceSum = staffResourceSum;
	}

	public String getQueryStartRemain() {
		return queryStartRemain;
	}

	public void setQueryStartRemain(String queryStartRemain) {
		this.queryStartRemain = queryStartRemain;
	}

	public String getQueryEndRemain() {
		return queryEndRemain;
	}

	public void setQueryEndRemain(String queryEndRemain) {
		this.queryEndRemain = queryEndRemain;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getMemberCount() {
		return memberCount;
	}

	public void setMemberCount(String memberCount) {
		this.memberCount = memberCount;
	}


	public String getOptAcc() {
		return optAcc;
	}

	public void setOptAcc(String optAcc) {
		this.optAcc = optAcc;
	}

	public String getOptName() {
		return optName;
	}

	public void setOptName(String optName) {
		this.optName = optName;
	}

	public Long getOptCount() {
		return optCount;
	}

	public void setOptCount(Long optCount) {
		this.optCount = optCount;
	}


	public List<String> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<String> accounts) {
		this.accounts = accounts;
	}

	public String getResGroupId() {
		return resGroupId;
	}

	public void setResGroupId(String resGroupId) {
		this.resGroupId = resGroupId;
	}

	public String getResGroupName() {
		return ResGroupName;
	}

	public void setResGroupName(String resGroupName) {
		ResGroupName = resGroupName;
	}

	public boolean isQueryPhone() {
		return queryPhone;
	}

	public void setQueryPhone(boolean queryPhone) {
		this.queryPhone = queryPhone;
	}

	public Date getServetime() {
		return servetime;
	}

	public void setServetime(Date servetime) {
		this.servetime = servetime;
	}

	public Integer getdDateType() {
		return dDateType;
	}

	public void setdDateType(Integer dDateType) {
		this.dDateType = dDateType;
	}
	
	

}
