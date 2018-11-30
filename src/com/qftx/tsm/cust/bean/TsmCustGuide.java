package com.qftx.tsm.cust.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;


/**
 * 销售导航
 */
public class TsmCustGuide extends BaseObject{

	private static final long serialVersionUID = 2365216999757020557L;
	
	private String custGuideId;       // 客户导航ID
    private String custId;            // 客户ID
    private String orgId;				// 机构ID
    private String custTypeId;        // 客户类型选项ID
    private String saleProcessId;     // 销售进程选项ID
    private Date expectDate;          // 预期签单时间
    private Long expectSale;          // 预期销售
    private String custInterest;      // 客户兴趣
    private String custArgue;         // 客户争议
    private String competitor;        // 竞争对手
    private String saleWay;           // 销售对策
    private String remark;            // 备注
    private String otherProd;         // 其他产品
    private String inputerAcc;        // 录入人
    private Date inputdate;           // 录入时间
    private String modifierAcc;       // 修改人
    private Date modifydate;          // 修改时间
    private String saleProcessName;

    public String getCustGuideId() {
        return custGuideId;
    }

    public void setCustGuideId(String custGuideId) {
        this.custGuideId = custGuideId == null ? null : custGuideId.trim();
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId == null ? null : custId.trim();
    }

    public String getCustTypeId() {
        return custTypeId;
    }

    public void setCustTypeId(String custTypeId) {
        this.custTypeId = custTypeId == null ? null : custTypeId.trim();
    }

    public String getSaleProcessId() {
        return saleProcessId;
    }

    public void setSaleProcessId(String saleProcessId) {
        this.saleProcessId = saleProcessId == null ? null : saleProcessId.trim();
    }

    public Date getExpectDate() {
        return expectDate;
    }

    public void setExpectDate(Date expectDate) {
        this.expectDate = expectDate;
    }

    public Long getExpectSale() {
        return expectSale;
    }

    public void setExpectSale(Long expectSale) {
        this.expectSale = expectSale;
    }

    public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getCustInterest() {
        return custInterest;
    }

    public void setCustInterest(String custInterest) {
        this.custInterest = custInterest == null ? null : custInterest.trim();
    }

    public String getCustArgue() {
        return custArgue;
    }

    public void setCustArgue(String custArgue) {
        this.custArgue = custArgue == null ? null : custArgue.trim();
    }

    public String getCompetitor() {
        return competitor;
    }

    public void setCompetitor(String competitor) {
        this.competitor = competitor == null ? null : competitor.trim();
    }

    public String getSaleWay() {
        return saleWay;
    }

    public void setSaleWay(String saleWay) {
        this.saleWay = saleWay == null ? null : saleWay.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getOtherProd() {
        return otherProd;
    }

    public void setOtherProd(String otherProd) {
        this.otherProd = otherProd == null ? null : otherProd.trim();
    }

    public String getInputerAcc() {
        return inputerAcc;
    }

    public void setInputerAcc(String inputerAcc) {
        this.inputerAcc = inputerAcc == null ? null : inputerAcc.trim();
    }

    public Date getInputdate() {
        return inputdate;
    }

    public void setInputdate(Date inputdate) {
        this.inputdate = inputdate;
    }

    public String getModifierAcc() {
        return modifierAcc;
    }

    public void setModifierAcc(String modifierAcc) {
        this.modifierAcc = modifierAcc == null ? null : modifierAcc.trim();
    }

    public Date getModifydate() {
        return modifydate;
    }

    public void setModifydate(Date modifydate) {
        this.modifydate = modifydate;
    }

	public String getSaleProcessName() {
		return saleProcessName;
	}

	public void setSaleProcessName(String saleProcessName) {
		this.saleProcessName = saleProcessName;
	}
}