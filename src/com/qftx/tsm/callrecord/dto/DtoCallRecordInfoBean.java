package com.qftx.tsm.callrecord.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qftx.common.domain.BaseObject;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * User�� bxl
 * Date�� 2016/1/8
 * Time�� 14:36
 */
public class DtoCallRecordInfoBean extends BaseObject {
    private String id;
    private String data;
    private String define1; // 客户联系人
    private String define2;
    private String define3;
    private String define4;
    private String define5;
    private String define6;
    private String define7; 
    private String define8; 
    private String define9;
    private String define10;
    private String define11;
    private String define12;
    private String define13;
    private String define14;
    private String define15;
    private String define16;
    private Integer define17;
    private Double define18;
    private String syncState;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date syncTime;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date inputDate;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateDate;
    private Integer isDel;
    private String[] code;
    private String orgId;
    private String orgName;
    private String[] custId;
    private String custName;
    private String[] saleProcessId;
    private String saleProcessName;
    private String callState;
    private Integer callType;

    private String callerNum;
    private String callerCommNum;
    private String calledNum;
    private String transNum;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    private Integer recordState;
    private Integer timeLength;
    private Integer timeLengthEnd;
    private Integer costTimeLength;
    private String recordUrl;
    private String recordKey;
    private String[] inputAcc;
    private String inputName;
    private String dataresource;
    private String communicationNo;
    private String custType;
    private String custState;
    private String[] followId;
    private String queryKey;//模糊查询
    private String osType;//所有者查询方式 1-全部 2-只看自己 3-选中查询
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private String startInputDate; //查询开始时间
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private String endInputDate;   //查询结束时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public String getStartInputDate() {
        return startInputDate;
    }

    public void setStartInputDate(String startInputDate) {
        this.startInputDate = startInputDate;
    }
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public String getEndInputDate() {
        return endInputDate;
    }

    public void setEndInputDate(String endInputDate) {
        this.endInputDate = endInputDate;
    }

    public String getQueryKey() {
        return queryKey;
    }

    public void setQueryKey(String queryKey) {
        this.queryKey = queryKey;
    }

    public Integer getTimeLengthEnd() {
        return timeLengthEnd;
    }

    public void setTimeLengthEnd(Integer timeLengthEnd) {
        this.timeLengthEnd = timeLengthEnd;
    }

    public String getRecordKey() {
        return recordKey;
    }

    public void setRecordKey(String recordKey) {
        this.recordKey = recordKey;
    }

    public String getCustState() {
        return custState;
    }

    public void setCustState(String custState) {
        this.custState = custState;
    }

    public String getInputName() {
        return inputName;
    }

    public void setInputName(String inputName) {
        this.inputName = inputName;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getCostTimeLength() {
        return costTimeLength;
    }

    public void setCostTimeLength(Integer costTimeLength) {
        this.costTimeLength = costTimeLength;
    }

    public String[] getFollowId() {
        return followId;
    }

    public void setFollowId(String[] followId) {
        this.followId = followId;
    }

    public String getCustType() {
        return custType;
    }

    public void setCustType(String custType) {
        this.custType = custType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDefine1() {
        return define1;
    }

    public void setDefine1(String define1) {
        this.define1 = define1;
    }

    public String getDefine2() {
        return define2;
    }

    public void setDefine2(String define2) {
        this.define2 = define2;
    }

    public String getDefine3() {
        return define3;
    }

    public void setDefine3(String define3) {
        this.define3 = define3;
    }

    public String getDefine4() {
        return define4;
    }

    public void setDefine4(String define4) {
        this.define4 = define4;
    }

    public String getDefine5() {
        return define5;
    }

    public void setDefine5(String define5) {
        this.define5 = define5;
    }

    public String getDefine6() {
        return define6;
    }

    public void setDefine6(String define6) {
        this.define6 = define6;
    }

    public String getDefine7() {
        return define7;
    }

    public void setDefine7(String define7) {
        this.define7 = define7;
    }

    public String getDefine8() {
        return define8;
    }

    public void setDefine8(String define8) {
        this.define8 = define8;
    }

    public String getDefine9() {
        return define9;
    }

    public void setDefine9(String define9) {
        this.define9 = define9;
    }

    public String getDefine10() {
        return define10;
    }

    public void setDefine10(String define10) {
        this.define10 = define10;
    }

    public String getDefine11() {
        return define11;
    }

    public void setDefine11(String define11) {
        this.define11 = define11;
    }

    public String getDefine12() {
        return define12;
    }

    public void setDefine12(String define12) {
        this.define12 = define12;
    }

    public String getDefine13() {
        return define13;
    }

    public void setDefine13(String define13) {
        this.define13 = define13;
    }

    public String getDefine14() {
        return define14;
    }

    public void setDefine14(String define14) {
        this.define14 = define14;
    }

    public String getDefine15() {
        return define15;
    }

    public void setDefine15(String define15) {
        this.define15 = define15;
    }

    public String getDefine16() {
        return define16;
    }

    public void setDefine16(String define16) {
        this.define16 = define16;
    }

    public Integer getDefine17() {
        return define17;
    }

    public void setDefine17(Integer define17) {
        this.define17 = define17;
    }

    public Double getDefine18() {
        return define18;
    }

    public void setDefine18(Double define18) {
        this.define18 = define18;
    }

    public String getSyncState() {
        return syncState;
    }

    public void setSyncState(String syncState) {
        this.syncState = syncState;
    }
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public Date getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(Date syncTime) {
        this.syncTime = syncTime;
    }

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

    public String[] getCode() {
        return code;
    }

    public void setCode(String[] code) {
        this.code = code;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String[] getCustId() {
        return custId;
    }

    public void setCustId(String[] custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String[] getSaleProcessId() {
        return saleProcessId;
    }

    public void setSaleProcessId(String[] saleProcessId) {
        this.saleProcessId = saleProcessId;
    }

    public String getCallState() {
        return callState;
    }

    public void setCallState(String callState) {
        this.callState = callState;
    }

    public Integer getCallType() {
        return callType;
    }

    public void setCallType(Integer callType) {
        this.callType = callType;
    }

    public String getSaleProcessName() {
        return saleProcessName;
    }

    public void setSaleProcessName(String saleProcessName) {
        this.saleProcessName = saleProcessName;
    }

    public String getCallerNum() {
        return callerNum;
    }

    public void setCallerNum(String callerNum) {
        this.callerNum = callerNum;
    }

    public String getCallerCommNum() {
        return callerCommNum;
    }

    public void setCallerCommNum(String callerCommNum) {
        this.callerCommNum = callerCommNum;
    }

    public String getCalledNum() {
        return calledNum;
    }

    public void setCalledNum(String calledNum) {
        this.calledNum = calledNum;
    }

    public String getTransNum() {
        return transNum;
    }

    public void setTransNum(String transNum) {
        this.transNum = transNum;
    }
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getRecordState() {
        return recordState;
    }

    public void setRecordState(Integer recordState) {
        this.recordState = recordState;
    }

    public Integer getTimeLength() {
        return timeLength;
    }

    public void setTimeLength(Integer timeLength) {
        this.timeLength = timeLength;
    }

    public String getRecordUrl() {
        return recordUrl;
    }

    public void setRecordUrl(String recordUrl) {
        this.recordUrl = recordUrl;
    }

    public String[] getInputAcc() {
        return inputAcc;
    }

    public void setInputAcc(String[] inputAcc) {
        this.inputAcc = inputAcc;
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

	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}
    
}
