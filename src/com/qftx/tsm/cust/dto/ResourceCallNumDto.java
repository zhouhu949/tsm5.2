package com.qftx.tsm.cust.dto;

public class ResourceCallNumDto {
    private String id;

    private String custId;

    private Long callConnect;

    private Long callTotal;

    private String orgId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId == null ? null : custId.trim();
    }

    public Long getCallConnect() {
        return callConnect;
    }

    public void setCallConnect(Long callConnect) {
        this.callConnect = callConnect;
    }

    public Long getCallTotal() {
        return callTotal;
    }

    public void setCallTotal(Long callTotal) {
        this.callTotal = callTotal;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }
}