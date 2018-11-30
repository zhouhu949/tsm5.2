package com.qftx.tsm.sms.dto;

import com.qftx.tsm.sms.bean.TsmSendSms;

import java.util.List;


 /** 
 *发送短信实体

 */
public class TsmSendSmsDto extends TsmSendSms {

	
	private static final long serialVersionUID = 1232896512627570429L;

	private String ownerName;             //所有者名字
	private String logAct;                //当前登录帐号
	private String groupId;               //分组编号
	private String orgName;               //分组编号
	private List<String> ownerAccs; // 所有者集合
	private String accs;
	private String ownerAcc;
	private Integer roleType; // 0:普通销售，1：管理者
	private Integer isSys; // 默认查询所有短信记录，包括挂机短信记录,1:表示不查询 挂机短信记录
	private Integer failNum; // 失败次数
	private Integer successNum; // 成功次数
	private String osType;//所有者查询方式 1-全部 2-只看自己 3-选中查询
	
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getLogAct() {
		return logAct;
	}
	public void setLogAct(String logAct) {
		this.logAct = logAct;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName == null ? null : orgName.trim();
	}
	public String getOwnerAcc() {
		return ownerAcc;
	}
	public void setOwnerAcc(String ownerAcc) {
		this.ownerAcc = ownerAcc;
	}
	public Integer getRoleType() {
		return roleType;
	}
	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}
	public List<String> getOwnerAccs() {
		return ownerAccs;
	}
	public void setOwnerAccs(List<String> ownerAccs) {
		this.ownerAccs = ownerAccs;
	}
	public String getAccs() {
		return accs;
	}
	public void setAccs(String accs) {
		this.accs = accs;
	}
	public Integer getIsSys() {
		return isSys;
	}
	public void setIsSys(Integer isSys) {
		this.isSys = isSys;
	}
	public Integer getFailNum() {
		return failNum;
	}
	public void setFailNum(Integer failNum) {
		this.failNum = failNum;
	}
	public Integer getSuccessNum() {
		return successNum;
	}
	public void setSuccessNum(Integer successNum) {
		this.successNum = successNum;
	}
	public String getOsType() {
		return osType;
	}
	public void setOsType(String osType) {
		this.osType = osType;
	}

}
