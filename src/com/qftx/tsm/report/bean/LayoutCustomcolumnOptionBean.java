package com.qftx.tsm.report.bean;

import java.io.Serializable;
import java.util.Date;

public class LayoutCustomcolumnOptionBean implements Serializable {
    /**
     * id
     */
    private String id;

    /**
     * 单位ID
     */
    private String orgId;

    /**
     * 分组ID
     */
    private String groupId;

    /**
     * 帐号
     */
    private String userAccount;

    /**
     * 姓名
     */
    private String userName;

    /**
     * 自定义字段CODE
     */
    private String fieldCode;

    /**
     * optionlist_id
     */
    private String optionlistId;

    /**
     * 客户数量
     */
    private Integer custNums;

    /**
     * 录入时间
     */
    private Date inputtime;

    /**
     * layout_customcolumn_option
     */
    private static final long serialVersionUID = 1L;

    /**
     * id
     * @return id id
     */
    public String getId() {
        return id;
    }

    /**
     * id
     * @param id id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * 单位ID
     * @return org_id 单位ID
     */
    public String getOrgId() {
        return orgId;
    }

    /**
     * 单位ID
     * @param orgId 单位ID
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    /**
     * 分组ID
     * @return group_id 分组ID
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * 分组ID
     * @param groupId 分组ID
     */
    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
    }

    /**
     * 帐号
     * @return user_account 帐号
     */
    public String getUserAccount() {
        return userAccount;
    }

    /**
     * 帐号
     * @param userAccount 帐号
     */
    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount == null ? null : userAccount.trim();
    }

    /**
     * 姓名
     * @return user_name 姓名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 姓名
     * @param userName 姓名
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * 自定义字段CODE
     * @return FIELD_CODE 自定义字段CODE
     */
    public String getFieldCode() {
        return fieldCode;
    }

    /**
     * 自定义字段CODE
     * @param fieldCode 自定义字段CODE
     */
    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode == null ? null : fieldCode.trim();
    }

    /**
     * optionlist_id
     * @return optionlist_id optionlist_id
     */
    public String getOptionlistId() {
        return optionlistId;
    }

    /**
     * optionlist_id
     * @param optionlistId optionlist_id
     */
    public void setOptionlistId(String optionlistId) {
        this.optionlistId = optionlistId == null ? null : optionlistId.trim();
    }

    /**
     * 客户数量
     * @return cust_nums 客户数量
     */
    public Integer getCustNums() {
        return custNums;
    }

    /**
     * 客户数量
     * @param custNums 客户数量
     */
    public void setCustNums(Integer custNums) {
        this.custNums = custNums;
    }

    /**
     * 录入时间
     * @return inputtime 录入时间
     */
    public Date getInputtime() {
        return inputtime;
    }

    /**
     * 录入时间
     * @param inputtime 录入时间
     */
    public void setInputtime(Date inputtime) {
        this.inputtime = inputtime;
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
        LayoutCustomcolumnOptionBean other = (LayoutCustomcolumnOptionBean) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOrgId() == null ? other.getOrgId() == null : this.getOrgId().equals(other.getOrgId()))
            && (this.getGroupId() == null ? other.getGroupId() == null : this.getGroupId().equals(other.getGroupId()))
            && (this.getUserAccount() == null ? other.getUserAccount() == null : this.getUserAccount().equals(other.getUserAccount()))
            && (this.getUserName() == null ? other.getUserName() == null : this.getUserName().equals(other.getUserName()))
            && (this.getFieldCode() == null ? other.getFieldCode() == null : this.getFieldCode().equals(other.getFieldCode()))
            && (this.getOptionlistId() == null ? other.getOptionlistId() == null : this.getOptionlistId().equals(other.getOptionlistId()))
            && (this.getCustNums() == null ? other.getCustNums() == null : this.getCustNums().equals(other.getCustNums()))
            && (this.getInputtime() == null ? other.getInputtime() == null : this.getInputtime().equals(other.getInputtime()));
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
        result = prime * result + ((getOrgId() == null) ? 0 : getOrgId().hashCode());
        result = prime * result + ((getGroupId() == null) ? 0 : getGroupId().hashCode());
        result = prime * result + ((getUserAccount() == null) ? 0 : getUserAccount().hashCode());
        result = prime * result + ((getUserName() == null) ? 0 : getUserName().hashCode());
        result = prime * result + ((getFieldCode() == null) ? 0 : getFieldCode().hashCode());
        result = prime * result + ((getOptionlistId() == null) ? 0 : getOptionlistId().hashCode());
        result = prime * result + ((getCustNums() == null) ? 0 : getCustNums().hashCode());
        result = prime * result + ((getInputtime() == null) ? 0 : getInputtime().hashCode());
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
        sb.append(", orgId=").append(orgId);
        sb.append(", groupId=").append(groupId);
        sb.append(", userAccount=").append(userAccount);
        sb.append(", userName=").append(userName);
        sb.append(", fieldCode=").append(fieldCode);
        sb.append(", optionlistId=").append(optionlistId);
        sb.append(", custNums=").append(custNums);
        sb.append(", inputtime=").append(inputtime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}