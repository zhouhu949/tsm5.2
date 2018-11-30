package com.qftx.tsm.cust.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;


 /** 
 * 回收分配记录日志
 * @author: WUWEI
 * @since: 2015年11月17日  下午3:42:24
 * @history:
 */
public class AllocatRecycleLogBean extends BaseObject {
    /**
	 * 
	 */
	private static final long serialVersionUID = -547270278347127375L;

	private String resOptorId;

    private String type;

    private Date optorResDate;

    private Integer optorQuantity;

    private String optoerAcc;

    private String ownerAcc;

    private String ownerName;

    private String orgId;

    private String optoerName;

    public String getResOptorId() {
        return resOptorId;
    }

    public void setResOptorId(String resOptorId) {
        this.resOptorId = resOptorId == null ? null : resOptorId.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Date getOptorResDate() {
        return optorResDate;
    }

    public void setOptorResDate(Date optorResDate) {
        this.optorResDate = optorResDate;
    }

    public Integer getOptorQuantity() {
        return optorQuantity;
    }

    public void setOptorQuantity(Integer optorQuantity) {
        this.optorQuantity = optorQuantity;
    }

    public String getOptoerAcc() {
        return optoerAcc;
    }

    public void setOptoerAcc(String optoerAcc) {
        this.optoerAcc = optoerAcc == null ? null : optoerAcc.trim();
    }

    public String getOwnerAcc() {
        return ownerAcc;
    }

    public void setOwnerAcc(String ownerAcc) {
        this.ownerAcc = ownerAcc == null ? null : ownerAcc.trim();
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName == null ? null : ownerName.trim();
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getOptoerName() {
        return optoerName;
    }

    public void setOptoerName(String optoerName) {
        this.optoerName = optoerName == null ? null : optoerName.trim();
    }
}