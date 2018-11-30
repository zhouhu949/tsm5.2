package com.qftx.tsm.sms.dto;

import com.qftx.tsm.sms.bean.TsmSendSmsDetail;



 /** 
 *短信发送明细 DTO
 */
public class TsmSendSmsDetailDto extends TsmSendSmsDetail {

	private static final long serialVersionUID = -4917190683491352448L;

	private String ownerName;             //所有者名字
	private String logAct;                //当前登录帐号
	private String groupId;               //分组编号
	private String orgName;               //分组编号
	private String queryText;		 // 用于模糊查询
	private String queryType;		// 查询类型 
	private String showSendDate;
	
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
	public String getShowSendDate() {
		return showSendDate;
	}
	public void setShowSendDate(String showSendDate) {
		this.showSendDate = showSendDate;
	}
	
}
