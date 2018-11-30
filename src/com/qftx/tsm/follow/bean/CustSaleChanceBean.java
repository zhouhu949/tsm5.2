package com.qftx.tsm.follow.bean;

import com.qftx.common.domain.BaseObject;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/** 
 * 销售机会 实体类
 * @author: zjh
 * @history:
 */
/**
 * @author Administrator
 *
 */
public class CustSaleChanceBean  extends BaseObject{
	private String saleChanceId;		//销售机会ID
	
 	private String orgId;				//机构ID

    private String saleChanceName;		//销售机会名称

    private String custId;				//客户ID

    private BigDecimal theorySignMoney; //预计签单金额

    private Integer theorySuccessRate;	//预计成功率
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date theorySignDate;		//预计签单时间

    private String signPlan;			//销售对策

    private String rival;				//竞争对手

    private String remark;				//商业备注
    
    private Date updateDate;			//更新时间
    
    private Integer isDel;				//是否删除 1:删除 0:未删除
   
    private Integer isFollow;			//是否作废 1:作废,0:跟进中
    
    private Date inputDate;				//录入时间
    
    private String company; 			// 单位名称

    private String custName; 			// NAME 客户姓名
    
    private String inputAcc;			//录入账号
    
	public String getSaleChanceId() {
		return saleChanceId;
	}

	public void setSaleChanceId(String saleChanceId) {
		this.saleChanceId = saleChanceId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getSaleChanceName() {
		return saleChanceName;
	}

	public void setSaleChanceName(String saleChanceName) {
		this.saleChanceName = saleChanceName;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public BigDecimal getTheorySignMoney() {
		return theorySignMoney;
	}

	public void setTheorySignMoney(BigDecimal theorySignMoney) {
		this.theorySignMoney = theorySignMoney;
	}

	public Integer getTheorySuccessRate() {
		return theorySuccessRate;
	}

	public void setTheorySuccessRate(Integer theorySuccessRate) {
		this.theorySuccessRate = theorySuccessRate;
	}

	public Date getTheorySignDate() {
		return theorySignDate;
	}

	public void setTheorySignDate(Date theorySignDate) {
		this.theorySignDate = theorySignDate;
	}

	public String getSignPlan() {
		return signPlan;
	}

	public void setSignPlan(String signPlan) {
		this.signPlan = signPlan;
	}

	public String getRival() {
		return rival;
	}

	public void setRival(String rival) {
		this.rival = rival;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getInputAcc() {
		return inputAcc;
	}

	public void setInputAcc(String inputAcc) {
		this.inputAcc = inputAcc;
	}

	public Integer getIsFollow() {
		return isFollow;
	}

	public void setIsFollow(Integer isFollow) {
		this.isFollow = isFollow;
	}
	
	
	
	
	
	
    
    


}
