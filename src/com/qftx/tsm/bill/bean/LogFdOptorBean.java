package com.qftx.tsm.bill.bean;

import com.qftx.common.domain.BaseObject;

import java.math.BigDecimal;
import java.util.Date;

/** 
 * 蜂豆操作日志表
 * @author: lixing
 * @since: 2016-2-29  下午7:50:47
 * @history:
 */
public class LogFdOptorBean extends BaseObject{
	private static final long serialVersionUID = 5757566947199517209L;
	
    private String allocationId;             //主键
    private String allocationAcc;            //操作帐号
    private Double operateTimelength;    //操作时长
    private Short operateType;               //操作类型：1-前台账户分配；2-前台账户回收；3-单位管理员增加；4-单位管理员减少
    private Date operateTime;                //操作时间
    private String operateAcc;               //操作人
    private String orgId;                    //机构ID
    private String packageCode;              //套餐编码
    private String orderNumber;              //订单编码
    private Double afterOperateTimelength;	 //剩余时长

    public String getAllocationId() {
        return allocationId;
    }

    public void setAllocationId(String allocationId) {
        this.allocationId = allocationId == null ? null : allocationId.trim();
    }

    public String getAllocationAcc() {
        return allocationAcc;
    }

    public void setAllocationAcc(String allocationAcc) {
        this.allocationAcc = allocationAcc == null ? null : allocationAcc.trim();
    }

    public Double getOperateTimelength() {
        return operateTimelength;
    }

    public void setOperateTimelength(Double operateTimelength) {
        this.operateTimelength = operateTimelength;
    }

    public Short getOperateType() {
        return operateType;
    }

    public void setOperateType(Short operateType) {
        this.operateType = operateType;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperateAcc() {
        return operateAcc;
    }

    public void setOperateAcc(String operateAcc) {
        this.operateAcc = operateAcc == null ? null : operateAcc.trim();
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

	public String getPackageCode() {
		return packageCode;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Double getAfterOperateTimelength() {
		return afterOperateTimelength;
	}

	public void setAfterOperateTimelength(Double afterOperateTimelength) {
		this.afterOperateTimelength = afterOperateTimelength;
	}
	
}