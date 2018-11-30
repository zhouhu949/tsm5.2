package com.qftx.tsm.report.bean;

import java.util.Date;

import com.qftx.common.domain.BaseObject;

public class CustomReportShareBean extends BaseObject{
    private String shareId;

    private String customReportId;

    private String orgId;

    private Date updateDate;

    private Integer isDel;

    private Date inputDate;

    private String inputAcc;

    private String shareAcc;

    public String getShareId() {
        return shareId;
    }

    public void setShareId(String shareId) {
        this.shareId = shareId == null ? null : shareId.trim();
    }

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

    public String getShareAcc() {
        return shareAcc;
    }

    public void setShareAcc(String shareAcc) {
        this.shareAcc = shareAcc == null ? null : shareAcc.trim();
    }
}