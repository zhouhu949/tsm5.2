package com.qftx.tsm.cust.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;

/**
 * 资源操作表
 * @author xiangli
 */
public class ResOptorBean extends BaseObject{
	private static final long serialVersionUID = 1L;
	
    private String resOptorId;      //资源操作ID
    private String resCustId;       //资源客户ID
    private Short type;             //操作类型：1-资源：管理员导入，2-资源：个人新增或导入，3-资源：分配，4-资源：沟通失败，5-资源：信息错误，6-资源：非目标客户，7-资源：人工回收
    private Date optorResDate;      //操作时间
    private String optoerAcc;       //操作者
    private String ownerAcc;        //所有人帐号
    private String ownerName;       //所有人名称
    private String orgId;           //机构编号    
    
    public String getResOptorId() {
        return resOptorId;
    }

    public void setResOptorId(String resOptorId) {
        this.resOptorId = resOptorId == null ? null : resOptorId.trim();
    }

    public String getResCustId() {
        return resCustId;
    }

    public void setResCustId(String resCustId) {
        this.resCustId = resCustId == null ? null : resCustId.trim();
    }

    public Short getType() {
        return type;
    }

    /** 操作类型：1-资源：管理员导入，2-资源：个人新增或导入，3-资源：分配，4-资源：沟通失败，5-资源：信息错误，6-资源：非目标客户，7-资源：人工回收，8-资源：再分配*/
    public void setType(Short type) {
        this.type = type;
    }

    public Date getOptorResDate() {
        return optorResDate;
    }

    public void setOptorResDate(Date optorResDate) {
        this.optorResDate = optorResDate;
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
		this.orgId = orgId;
	}
    
}