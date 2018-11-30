package com.qftx.tsm.cust.dto;

import com.qftx.base.team.bean.TsmTeamGroupMemberBean;

import java.util.List;

public class CustTransferDTO {
	private String groupId;
	private String groupName;
	private List<TsmTeamGroupMemberBean> members;
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public List<TsmTeamGroupMemberBean> getMembers() {
		return members;
	}
	public void setMembers(List<TsmTeamGroupMemberBean> members) {
		this.members = members;
	}
	
	
	
}
