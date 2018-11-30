package com.qftx.tsm.phonechoose.bean;

import java.util.Date;

import com.qftx.common.domain.BaseObject;

/**
 * 号码筛查明细表 tsm_phone_choose_detail
 * 
 * @author chenhm
 *
 */
public class PhoneChooseDetailBean extends BaseObject {
	
	// 状态： 1-空号，2-停机，3-沉默，4-正常
	public static final byte STATUS_DEAD = 1;
	public static final byte STATUS_SHUTDOWN = 2;
	public static final byte STATUS_SILENT = 3;
	public static final byte STATUS_AVAILABLE = 4;
	
	
	/**
     * ID，主键
     */
    private String id;

    /**
     * 
     */
    private String taskId;

    /**
     * 机构ID
     */
    private String orgId;

    /**
     * 资源ID
     */
    private String rciId;

    /**
     * 手机
     */
    private String mobilephone;

    /**
     * 状态： 1-空号，2-停机，3-沉默，4-正常
     */
    private Byte status;

    /**
     * 录入时间
     */
    private Date inputDate;

    /**
     * 录入人
     */
    private String inputAcc;

    /**
     * 修改时间
     */
    private Date updateDate;

    /**
     * 修改人
     */
    private String updateAcc;

    /**
     * tsm_phone_choose_detail
     */
    private static final long serialVersionUID = 1L;

    /**
     * ID，主键
     * @return ID ID，主键
     */
    public String getId() {
        return id;
    }

    /**
     * ID，主键
     * @param id ID，主键
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 
     * @return TASK_ID 
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * 
     * @param taskId 
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }

    /**
     * 机构ID
     * @return ORG_ID 机构ID
     */
    public String getOrgId() {
        return orgId;
    }

    /**
     * 机构ID
     * @param orgId 机构ID
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    /**
     * 资源ID
     * @return RCI_ID 资源ID
     */
    public String getRciId() {
        return rciId;
    }

    /**
     * 资源ID
     * @param rciId 资源ID
     */
    public void setRciId(String rciId) {
        this.rciId = rciId == null ? null : rciId.trim();
    }

    /**
     * 手机
     * @return MOBILEPHONE 手机
     */
    public String getMobilephone() {
        return mobilephone;
    }

    /**
     * 手机
     * @param mobilephone 手机
     */
    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone == null ? null : mobilephone.trim();
    }

    /**
     * 状态： 1-空号，2-停机，3-沉默，4-正常
     * @return STATUS 状态： 1-空号，2-停机，3-沉默，4-正常
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 状态： 1-空号，2-停机，3-沉默，4-正常
     * @param status 状态： 1-空号，2-停机，3-沉默，4-正常
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 录入时间
     * @return INPUT_DATE 录入时间
     */
    public Date getInputDate() {
        return inputDate;
    }

    /**
     * 录入时间
     * @param inputDate 录入时间
     */
    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }

    /**
     * 录入人
     * @return INPUT_ACC 录入人
     */
    public String getInputAcc() {
        return inputAcc;
    }

    /**
     * 录入人
     * @param inputAcc 录入人
     */
    public void setInputAcc(String inputAcc) {
        this.inputAcc = inputAcc == null ? null : inputAcc.trim();
    }

    /**
     * 修改时间
     * @return UPDATE_DATE 修改时间
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 修改时间
     * @param updateDate 修改时间
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 修改人
     * @return UPDATE_ACC 修改人
     */
    public String getUpdateAcc() {
        return updateAcc;
    }

    /**
     * 修改人
     * @param updateAcc 修改人
     */
    public void setUpdateAcc(String updateAcc) {
        this.updateAcc = updateAcc == null ? null : updateAcc.trim();
    }

    /**
     *
     * @mbg.generated
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        PhoneChooseDetailBean other = (PhoneChooseDetailBean) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTaskId() == null ? other.getTaskId() == null : this.getTaskId().equals(other.getTaskId()))
            && (this.getOrgId() == null ? other.getOrgId() == null : this.getOrgId().equals(other.getOrgId()))
            && (this.getRciId() == null ? other.getRciId() == null : this.getRciId().equals(other.getRciId()))
            && (this.getMobilephone() == null ? other.getMobilephone() == null : this.getMobilephone().equals(other.getMobilephone()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getInputDate() == null ? other.getInputDate() == null : this.getInputDate().equals(other.getInputDate()))
            && (this.getInputAcc() == null ? other.getInputAcc() == null : this.getInputAcc().equals(other.getInputAcc()))
            && (this.getUpdateDate() == null ? other.getUpdateDate() == null : this.getUpdateDate().equals(other.getUpdateDate()))
            && (this.getUpdateAcc() == null ? other.getUpdateAcc() == null : this.getUpdateAcc().equals(other.getUpdateAcc()));
    }

    /**
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTaskId() == null) ? 0 : getTaskId().hashCode());
        result = prime * result + ((getOrgId() == null) ? 0 : getOrgId().hashCode());
        result = prime * result + ((getRciId() == null) ? 0 : getRciId().hashCode());
        result = prime * result + ((getMobilephone() == null) ? 0 : getMobilephone().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getInputDate() == null) ? 0 : getInputDate().hashCode());
        result = prime * result + ((getInputAcc() == null) ? 0 : getInputAcc().hashCode());
        result = prime * result + ((getUpdateDate() == null) ? 0 : getUpdateDate().hashCode());
        result = prime * result + ((getUpdateAcc() == null) ? 0 : getUpdateAcc().hashCode());
        return result;
    }

    /**
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", taskId=").append(taskId);
        sb.append(", orgId=").append(orgId);
        sb.append(", rciId=").append(rciId);
        sb.append(", mobilephone=").append(mobilephone);
        sb.append(", status=").append(status);
        sb.append(", inputDate=").append(inputDate);
        sb.append(", inputAcc=").append(inputAcc);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", updateAcc=").append(updateAcc);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}