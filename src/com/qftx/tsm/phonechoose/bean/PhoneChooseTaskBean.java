package com.qftx.tsm.phonechoose.bean;

import java.util.Date;

import com.qftx.common.domain.BaseObject;

/**
 * 号码筛查任务表 tsm_phone_choose_task
 * 
 * @author chenhm
 *
 */
public class PhoneChooseTaskBean extends BaseObject {
	
	/**
	 * [orgId]_[resGroupId]
	 */
	public static final String TASK_CACHE_KEY = "%s_%s";
	
	// 筛选任务状态： 1-开始筛查，2-筛查进行中，3-筛查完成
	public static final byte STATUS_BEGIN = 1;
	public static final byte STATUS_CHOOSING = 3;
	public static final byte STATUS_END = 3;
	
    /**
     * 筛选任务ID
     */
    private String taskId;

    /**
     * 机构ID
     */
    private String orgId;

    /**
     * 资源分组ID
     */
    private String resGroupId;

    /**
     * 筛选任务状态： 1-开始筛查，2-筛查进行中，3-筛查完成
     */
    private Byte status;

    /**
     * 已处理数
     */
    private Integer processedCount;

    /**
     * 筛选数
     */
    private Integer totalCount;

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
     * 未处理原因
     */
    private String undisposedReson ;
    
    /**
     * 消耗的蜂豆值
     */
    private Integer moneyConsume;
    
    /**
     * 筛选模块   1001：我的客户 - 资源 106：资源管理 - 待分配资源
     */
    private Integer moduleId;
    
  
   

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	/**
     * tsm_phone_choose_task
     */
    private static final long serialVersionUID = 1L;

    /**
     * 筛选任务ID
     * @return TASK_ID 筛选任务ID
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * 筛选任务ID
     * @param taskId 筛选任务ID
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
     * 资源分组ID
     * @return RES_GROUP_ID 资源分组ID
     */
    public String getResGroupId() {
        return resGroupId;
    }

    /**
     * 资源分组ID
     * @param resGroupId 资源分组ID
     */
    public void setResGroupId(String resGroupId) {
        this.resGroupId = resGroupId == null ? null : resGroupId.trim();
    }

    /**
     * 筛选任务状态： 1-开始筛查，2-正在准备数据，3-正在获取筛查结果，4-筛查完成
     * @return STATUS 筛选任务状态： 1-开始筛查，2-正在准备数据，3-正在获取筛查结果，4-筛查完成
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 筛选任务状态： 1-开始筛查，2-正在准备数据，3-正在获取筛查结果，4-筛查完成
     * @param status 筛选任务状态： 1-开始筛查，2-正在准备数据，3-正在获取筛查结果，4-筛查完成
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 已处理数
     * @return PROCESSED_COUNT 已处理数
     */
    public Integer getProcessedCount() {
        return processedCount;
    }

    /**
     * 已处理数
     * @param processedCount 已处理数
     */
    public void setProcessedCount(Integer processedCount) {
        this.processedCount = processedCount;
    }

    /**
     * 筛选数
     * @return TOTAL_COUNT 筛选数
     */
    public Integer getTotalCount() {
        return totalCount;
    }

    /**
     * 筛选数
     * @param totalCount 筛选数
     */
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
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
    
    

    public String getUndisposedReson() {
		return undisposedReson;
	}

	public void setUndisposedReson(String undisposedReson) {
		this.undisposedReson = undisposedReson;
	}

	public Integer getMoneyConsume() {
		return moneyConsume;
	}

	public void setMoneyConsume(Integer moneyConsume) {
		this.moneyConsume = moneyConsume;
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
        PhoneChooseTaskBean other = (PhoneChooseTaskBean) that;
        return (this.getTaskId() == null ? other.getTaskId() == null : this.getTaskId().equals(other.getTaskId()))
            && (this.getOrgId() == null ? other.getOrgId() == null : this.getOrgId().equals(other.getOrgId()))
            && (this.getResGroupId() == null ? other.getResGroupId() == null : this.getResGroupId().equals(other.getResGroupId()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getProcessedCount() == null ? other.getProcessedCount() == null : this.getProcessedCount().equals(other.getProcessedCount()))
            && (this.getTotalCount() == null ? other.getTotalCount() == null : this.getTotalCount().equals(other.getTotalCount()))
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
        result = prime * result + ((getTaskId() == null) ? 0 : getTaskId().hashCode());
        result = prime * result + ((getOrgId() == null) ? 0 : getOrgId().hashCode());
        result = prime * result + ((getResGroupId() == null) ? 0 : getResGroupId().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getProcessedCount() == null) ? 0 : getProcessedCount().hashCode());
        result = prime * result + ((getTotalCount() == null) ? 0 : getTotalCount().hashCode());
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
        sb.append(", taskId=").append(taskId);
        sb.append(", orgId=").append(orgId);
        sb.append(", resGroupId=").append(resGroupId);
        sb.append(", status=").append(status);
        sb.append(", processedCount=").append(processedCount);
        sb.append(", totalCount=").append(totalCount);
        sb.append(", inputDate=").append(inputDate);
        sb.append(", inputAcc=").append(inputAcc);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", updateAcc=").append(updateAcc);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}