package com.qftx.tsm.cust.bean;

import com.qftx.common.domain.BaseObject;


/**
 * 销售导航，产品，关联表
 */
public class TsmCustGuideProc extends BaseObject{
	private static final long serialVersionUID = -8521003687186068248L;
	
	private String id;
	private String guideId;       // 客户导航ID
    private String procId;            // 产品ID
    private String orgId;				// 机构ID
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGuideId() {
		return guideId;
	}
	public void setGuideId(String guideId) {
		this.guideId = guideId;
	}
	public String getProcId() {
		return procId;
	}
	public void setProcId(String procId) {
		this.procId = procId;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

}