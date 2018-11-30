package com.qftx.tsm.callrecord.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.qftx.common.domain.BaseObject;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
/***
 * 通话记录 实体
 * @author: zwj
 * @since: 2015-12-12  下午2:22:58
 * @history: 3.x
 */
public class TsmRecordCallBean extends BaseObject {
    private static final long serialVersionUID = 7802748441153980914L;
    private String id;
    private String data;
    private String define1;
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
    private String code;
    private String orgId;
    private String orgName;
    private String custId;
    private String custName;
    private String custState;
    private String saleProcessId;
    private String callState;
    private Integer callType;
    private String saleProcessName;
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
    private String inputAcc;
    private String inputName;
    private String dataresource; // 数据来源1pc端，2ye，3ippbx，4快话，5App
    private String communicationNo;
    private String custType;
    private String followId;
    private String queryKey;

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

    public Integer getRecordState() {
        return recordState;
    }

    public void setRecordState(Integer recordState) {
        this.recordState = recordState;
    }

    public String getCustType() {
        return custType;
    }

    public void setCustType(String custType) {
        this.custType = custType;
    }

    public String getDefine5() {
        return define5;
    }

    public void setDefine5(String define5) {
        this.define5 = define5;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getSaleProcessId() {
        return saleProcessId;
    }

    public void setSaleProcessId(String saleProcessId) {
        this.saleProcessId = saleProcessId;
    }

    public String getSaleProcessName() {
        return saleProcessName;
    }

    public void setSaleProcessName(String saleProcessName) {
        this.saleProcessName = saleProcessName;
    }


    public Integer getCallType() {
        return callType;
    }

    public void setCallType(Integer callType) {
        this.callType = callType;
    }


    public String getCallState() {
        return callState;
    }

    public void setCallState(String callState) {
        this.callState = callState;
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


    public Integer getTimeLength() {
		return timeLength;
	}

	public void setTimeLength(Integer timeLength) {
		this.timeLength = timeLength;
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


    public String getDefine8() {
        return define8;
    }

    public void setDefine8(String define8) {
        this.define8 = define8;
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

    public void setDefine17(Integer define17) {
        this.define17 = define17;
    }

    public void setDefine18(Double define18) {
        this.define18 = define18;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getCustState() {
        return custState;
    }

    public void setCustState(String custState) {
        this.custState = custState;
    }

    public Integer getCostTimeLength() {
        return costTimeLength;
    }

    public void setCostTimeLength(Integer costTimeLength) {
        this.costTimeLength = costTimeLength;
    }

    public String getInputName() {
        return inputName;
    }

    public void setInputName(String inputName) {
        this.inputName = inputName;
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

    public String getFollowId() {
		return followId;
	}

	public void setFollowId(String followId) {
		this.followId = followId;
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

	public String getQueryKey() {
		return queryKey;
	}

	public void setQureyKey(String queryKey) {
		this.queryKey = queryKey;
	}

	public Integer getDefine17() {
		return define17;
	}

	public Double getDefine18() {
		return define18;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TsmRecordCallBean that = (TsmRecordCallBean) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (orgId != null ? !orgId.equals(that.orgId) : that.orgId != null) return false;
        if (orgName != null ? !orgName.equals(that.orgName) : that.orgName != null) return false;
        if (custId != null ? !custId.equals(that.custId) : that.custId != null) return false;
        if (custName != null ? !custName.equals(that.custName) : that.custName != null) return false;
        if (saleProcessId != null ? !saleProcessId.equals(that.saleProcessId) : that.saleProcessId != null)
            return false;
        if (saleProcessName != null ? !saleProcessName.equals(that.saleProcessName) : that.saleProcessName != null)
            return false;
        if (callType != null ? !callType.equals(that.callType) : that.callType != null) return false;
        if (callState != null ? !callState.equals(that.callState) : that.callState != null) return false;
        if (callerNum != null ? !callerNum.equals(that.callerNum) : that.callerNum != null) return false;
        if (callerCommNum != null ? !callerCommNum.equals(that.callerCommNum) : that.callerCommNum != null)
            return false;
        if (calledNum != null ? !calledNum.equals(that.calledNum) : that.calledNum != null) return false;
        if (transNum != null ? !transNum.equals(that.transNum) : that.transNum != null) return false;
        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) return false;
        if (timeLength != null ? !timeLength.equals(that.timeLength) : that.timeLength != null) return false;
        if (recordUrl != null ? !recordUrl.equals(that.recordUrl) : that.recordUrl != null) return false;
        if (inputAcc != null ? !inputAcc.equals(that.inputAcc) : that.inputAcc != null) return false;
        if (dataresource != null ? !dataresource.equals(that.dataresource) : that.dataresource != null) return false;
        if (communicationNo != null ? !communicationNo.equals(that.communicationNo) : that.communicationNo != null)
            return false;
        if (data != null ? !data.equals(that.data) : that.data != null) return false;
        if (define1 != null ? !define1.equals(that.define1) : that.define1 != null) return false;
        if (define2 != null ? !define2.equals(that.define2) : that.define2 != null) return false;
        if (define3 != null ? !define3.equals(that.define3) : that.define3 != null) return false;
        if (define4 != null ? !define4.equals(that.define4) : that.define4 != null) return false;
        if (define8 != null ? !define8.equals(that.define8) : that.define8 != null) return false;
        if (define7 != null ? !define7.equals(that.define7) : that.define7 != null) return false;
        if (define6 != null ? !define6.equals(that.define6) : that.define6 != null) return false;
        if (define8 != null ? !define8.equals(that.define8) : that.define8 != null) return false;
        if (define9 != null ? !define9.equals(that.define9) : that.define9 != null) return false;
        if (define10 != null ? !define10.equals(that.define10) : that.define10 != null) return false;
        if (define11 != null ? !define11.equals(that.define11) : that.define11 != null) return false;
        if (define12 != null ? !define12.equals(that.define12) : that.define12 != null) return false;
        if (define13 != null ? !define13.equals(that.define13) : that.define13 != null) return false;
        if (define14 != null ? !define14.equals(that.define14) : that.define14 != null) return false;
        if (define15 != null ? !define15.equals(that.define15) : that.define15 != null) return false;
        if (define16 != null ? !define16.equals(that.define16) : that.define16 != null) return false;
        if (define17 != null ? !define17.equals(that.define17) : that.define17 != null) return false;
        if (define18 != null ? !define18.equals(that.define18) : that.define18 != null) return false;
        if (syncState != null ? !syncState.equals(that.syncState) : that.syncState != null) return false;
        if (syncTime != null ? !syncTime.equals(that.syncTime) : that.syncTime != null) return false;
        if (inputDate != null ? !inputDate.equals(that.inputDate) : that.inputDate != null) return false;
        if (isDel != null ? !isDel.equals(that.isDel) : that.isDel != null) return false;
        if (queryKey != null ? !queryKey.equals(that.queryKey) : that.queryKey != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (orgId != null ? orgId.hashCode() : 0);
        result = 31 * result + (orgName != null ? orgName.hashCode() : 0);
        result = 31 * result + (custId != null ? custId.hashCode() : 0);
        result = 31 * result + (custName != null ? custName.hashCode() : 0);
        result = 31 * result + (saleProcessId != null ? saleProcessId.hashCode() : 0);
        result = 31 * result + (saleProcessName != null ? saleProcessName.hashCode() : 0);
        result = 31 * result + (callType != null ? callType.hashCode() : 0);
        result = 31 * result + (callState != null ? callState.hashCode() : 0);
        result = 31 * result + (callerNum != null ? callerNum.hashCode() : 0);
        result = 31 * result + (callerCommNum != null ? callerCommNum.hashCode() : 0);
        result = 31 * result + (calledNum != null ? calledNum.hashCode() : 0);
        result = 31 * result + (transNum != null ? transNum.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (timeLength != null ? timeLength.hashCode() : 0);
        result = 31 * result + (recordUrl != null ? recordUrl.hashCode() : 0);
        result = 31 * result + (inputAcc != null ? inputAcc.hashCode() : 0);
        result = 31 * result + (dataresource != null ? dataresource.hashCode() : 0);
        result = 31 * result + (communicationNo != null ? communicationNo.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (define1 != null ? define1.hashCode() : 0);
        result = 31 * result + (define2 != null ? define2.hashCode() : 0);
        result = 31 * result + (define3 != null ? define3.hashCode() : 0);
        result = 31 * result + (define4 != null ? define4.hashCode() : 0);
        result = 31 * result + (define8 != null ? define8.hashCode() : 0);
        result = 31 * result + (define7 != null ? define7.hashCode() : 0);
        result = 31 * result + (define6 != null ? define6.hashCode() : 0);
        result = 31 * result + (define9 != null ? define9.hashCode() : 0);
        result = 31 * result + (define10 != null ? define10.hashCode() : 0);
        result = 31 * result + (define11 != null ? define11.hashCode() : 0);
        result = 31 * result + (define12 != null ? define12.hashCode() : 0);
        result = 31 * result + (define13 != null ? define13.hashCode() : 0);
        result = 31 * result + (define14 != null ? define14.hashCode() : 0);
        result = 31 * result + (define15 != null ? define15.hashCode() : 0);
        result = 31 * result + (define16 != null ? define16.hashCode() : 0);
        result = 31 * result + (define17 != null ? define17.hashCode() : 0);
        result = 31 * result + (define18 != null ? define18.hashCode() : 0);
        result = 31 * result + (syncState != null ? syncState.hashCode() : 0);
        result = 31 * result + (syncTime != null ? syncTime.hashCode() : 0);
        result = 31 * result + (inputDate != null ? inputDate.hashCode() : 0);
        result = 31 * result + (isDel != null ? isDel.hashCode() : 0);
        result = 31 * result + (queryKey != null ? queryKey.hashCode() : 0);
        
        return result;
    }
}
