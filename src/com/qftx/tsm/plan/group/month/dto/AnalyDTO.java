package com.qftx.tsm.plan.group.month.dto;

public class AnalyDTO {
	private String groupName;
	
	private CustAnaly custAnaly;
	private SignAnaly signAnaly;
	private ResAnaly resAnaly;
	
	public CustAnaly getCustAnaly() {
		return custAnaly;
	}
	public void setCustAnaly(CustAnaly custAnaly) {
		this.custAnaly = custAnaly;
	}
	public SignAnaly getSignAnaly() {
		return signAnaly;
	}
	public void setSignAnaly(SignAnaly signAnaly) {
		this.signAnaly = signAnaly;
	}
	public ResAnaly getResAnaly() {
		return resAnaly;
	}
	public void setResAnaly(ResAnaly resAnaly) {
		this.resAnaly = resAnaly;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	
}
