package com.qftx.tsm.cust.dto;

import com.qftx.tsm.cust.bean.TsmCustGuide;

/**
 * 销售导航
 *
 */
public class TsmCustGuideDto extends TsmCustGuide{
	
	private static final long serialVersionUID = 1L;	
	
    private String custTypeName;        // 客户类型选项值
    private String custTypeCode;        // 客户类型编码
    private String saleProcessName;     // 销售进程选项值
    private String saleProcessCode;     // 销售进程编码
    private String orgId;               // 机构id
    private String custId;              // 客户编号
    
	public String getCustTypeCode() {
		return custTypeCode;
	}
	public void setCustTypeCode(String custTypeCode) {
		this.custTypeCode = custTypeCode;
	}
	public String getSaleProcessCode() {
		return saleProcessCode;
	}
	public void setSaleProcessCode(String saleProcessCode) {
		this.saleProcessCode = saleProcessCode;
	}
	public String getCustTypeName() {
		return custTypeName;
	}
	public void setCustTypeName(String custTypeName) {
		this.custTypeName = custTypeName;
	}
	public String getSaleProcessName() {
		return saleProcessName;
	}
	public void setSaleProcessName(String saleProcessName) {
		this.saleProcessName = saleProcessName;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
    
    
}