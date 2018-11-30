package com.qftx.tsm.rest.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qftx.common.domain.BaseObject;

import java.util.Date;

public class CallRecordBean extends BaseObject {
    private String callId;
    private String callType;
    private String callerNum;
    private String calledNum;
    private Date startTime;
    private Double timeElngth;
    private Date inputDate;
    private Integer isDel;
    private String code;
    private String recordUrl;
    private String inputAcc;

    private String orgId;



    private String modifierAcc;
    private Date modifydate;
    private String custId;
    private String ownerAcc;
    private String dataresource;
    private String communicationNo;
    private String uploaded;

    /**
     * `call_id` varchar(32) not null comment 'ͨ����¼id',
     `call_type` decimal(1,0) default null comment '��������:1--�ѽ�����;2--�Ѳ��绰;3--δ������;4--δ��ȥ��',
     `caller_num` varchar(30) default null comment '����',
     `called_num` varchar(30) default null comment '����',
     `start_time` datetime default null comment '��ʼͨ��ʱ��',
     `time_elngth` decimal(8,0) default null comment 'ͨ��ʱ����',
     `input_date` datetime default null comment '¼��ʱ��',
     `is_del` decimal(1,0) default '0' comment '�Ƿ�ɾ��:1-ɾ����0-δɾ��',
     `code` varchar(32) default null comment 'ͨ��Ψһ����',
     `record_url` varchar(300) default null comment '¼����ַ',
     `input_acc` varchar(30) default null comment '¼����',
     `org_id` varchar(32) default null comment '����id',
     `modifier_acc` varchar(30) default null comment '�޸����ʺ�',
     `modifydate` datetime default null comment '�޸�ʱ��',
     `cust_id` varchar(32) default null comment '�ͻ�id',
     `owner_acc` varchar(30) default null comment '�������ʺ�',
     `dataresource` varchar(2) default '1' comment '������Դ 1 -pc�ˣ�2-�ֻ���',
     `communication_no` varchar(20) default null comment 'ͨ��-ͨ�ź���',
     `uploaded` varchar(2) default null comment '�Ƿ��ϴ�:ֵΪ1��ʾ���ϴ���Ϊ�ձ�ʾδ�ϴ�',

     */


    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getCallerNum() {
        return callerNum;
    }

    public void setCallerNum(String callerNum) {
        this.callerNum = callerNum;
    }

    public String getCalledNum() {
        return calledNum;
    }

    public void setCalledNum(String calledNum) {
        this.calledNum = calledNum;
    }
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Double getTimeElngth() {
        return timeElngth;
    }

    public void setTimeElngth(Double timeElngth) {
        this.timeElngth = timeElngth;
    }
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public Date getInputDate() {
        return inputDate;
    }

    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRecordUrl() {
        return recordUrl;
    }

    public void setRecordUrl(String recordUrl) {
        this.recordUrl = recordUrl;
    }

    public String getInputAcc() {
        return inputAcc;
    }

    public void setInputAcc(String inputAcc) {
        this.inputAcc = inputAcc;
    }

    public String getModifierAcc() {
        return modifierAcc;
    }

    public void setModifierAcc(String modifierAcc) {
        this.modifierAcc = modifierAcc;
    }
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public Date getModifydate() {
        return modifydate;
    }

    public void setModifydate(Date modifydate) {
        this.modifydate = modifydate;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getOwnerAcc() {
        return ownerAcc;
    }

    public void setOwnerAcc(String ownerAcc) {
        this.ownerAcc = ownerAcc;
    }

    public String getDataresource() {
        return dataresource;
    }

    public void setDataresource(String dataresource) {
        this.dataresource = dataresource;
    }

    public String getCommunicationNo() {
        return communicationNo;
    }

    public void setCommunicationNo(String communicationNo) {
        this.communicationNo = communicationNo;
    }

    public String getUploaded() {
        return uploaded;
    }

    public void setUploaded(String uploaded) {
        this.uploaded = uploaded;
    }





}
