package com.qftx.tsm.message.dto;

/**
 * User： bxl
 * Date： 2016/1/12
 * Time： 19:54
 */
public class DtoMessageCountBean implements java.io.Serializable {
    //客户联系
    private Integer custNum;
    //点评提醒
    private Integer bbsNum;
    //未接来电
    private Integer callNum;
    //到期提醒
    private Integer dateNum;
    //通知公告
    private Integer noticeNum;
    //待办审核
    private Integer authNum;

    public Integer getCustNum() {
        return custNum;
    }

    public void setCustNum(Integer custNum) {
        this.custNum = custNum;
    }

    public Integer getBbsNum() {
        return bbsNum;
    }

    public void setBbsNum(Integer bbsNum) {
        this.bbsNum = bbsNum;
    }

    public Integer getCallNum() {
        return callNum;
    }

    public void setCallNum(Integer callNum) {
        this.callNum = callNum;
    }

    public Integer getDateNum() {
        return dateNum;
    }

    public void setDateNum(Integer dateNum) {
        this.dateNum = dateNum;
    }

    public Integer getNoticeNum() {
        return noticeNum;
    }

    public void setNoticeNum(Integer noticeNum) {
        this.noticeNum = noticeNum;
    }

    public Integer getAuthNum() {
        return authNum;
    }

    public void setAuthNum(Integer authNum) {
        this.authNum = authNum;
    }
}
