package com.qftx.tsm.callrecord.dto;

import com.qftx.tsm.callrecord.bean.CallRecordBlack;

import java.util.List;

/**
 * 黑名单 dto
 * @author: zwj
 * @since: 2015-12-12  下午2:30:27
 * @history: 4.x
 */
public class CallRecordBlackDto extends CallRecordBlack{
	private static final long serialVersionUID = 5786718569257990656L;
	private List<String> ownerAccs; // 所有者集合
	private String accs;
	private String ownerAcc;
	private Integer roleType; // 0:普通销售，1：管理者
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
	
	
	
}
