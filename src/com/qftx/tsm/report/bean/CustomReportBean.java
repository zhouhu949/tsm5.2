package com.qftx.tsm.report.bean;

import java.util.Date;

import com.qftx.common.domain.BaseObject;

public class CustomReportBean extends BaseObject{
    private String customReportId;

    private String orgId;

    private String customReportName;

    private Date updateDate;

    private Integer isDel;

    private Date inputDate;

    private String inputAcc;

    private Integer isDouble;

    private String data;

    public String getCustomReportId() {
        return customReportId;
    }

    public void setCustomReportId(String customReportId) {
        this.customReportId = customReportId == null ? null : customReportId.trim();
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getCustomReportName() {
        return customReportName;
    }

    public void setCustomReportName(String customReportName) {
        this.customReportName = customReportName == null ? null : customReportName.trim();
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

    public String getInputAcc() {
        return inputAcc;
    }

    public void setInputAcc(String inputAcc) {
        this.inputAcc = inputAcc == null ? null : inputAcc.trim();
    }

    public Integer getIsDouble() {
        return isDouble;
    }

    public void setIsDouble(Integer isDouble) {
        this.isDouble = isDouble;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data == null ? null : data.trim();
    }
}