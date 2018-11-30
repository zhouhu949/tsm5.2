package com.qftx.tsm.credit.bean;

import java.io.Serializable;
import java.util.Date;

public class TsmLeadReviewInfo implements Serializable {
    private String reviewId;

    private String leadId;

    private Date reviewDate;

    private String reviewAcc;

    private String reviewRemark;

    private Integer reviewResult;

    private Integer reviewNode;

    private String orgId;

    private Byte isDel;

    private static final long serialVersionUID = 1L;

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId == null ? null : reviewId.trim();
    }

    public String getLeadId() {
        return leadId;
    }

    public void setLeadId(String leadId) {
        this.leadId = leadId == null ? null : leadId.trim();
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getReviewAcc() {
        return reviewAcc;
    }

    public void setReviewAcc(String reviewAcc) {
        this.reviewAcc = reviewAcc == null ? null : reviewAcc.trim();
    }

    public String getReviewRemark() {
        return reviewRemark;
    }

    public void setReviewRemark(String reviewRemark) {
        this.reviewRemark = reviewRemark == null ? null : reviewRemark.trim();
    }

    public Integer getReviewResult() {
        return reviewResult;
    }

    public void setReviewResult(Integer reviewResult) {
        this.reviewResult = reviewResult;
    }

    public Integer getReviewNode() {
        return reviewNode;
    }

    public void setReviewNode(Integer reviewNode) {
        this.reviewNode = reviewNode;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public Byte getIsDel() {
        return isDel;
    }

    public void setIsDel(Byte isDel) {
        this.isDel = isDel;
    }
}