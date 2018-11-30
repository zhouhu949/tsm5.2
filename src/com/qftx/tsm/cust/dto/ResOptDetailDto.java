package com.qftx.tsm.cust.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * ��Դ������ϸdto
 * Created by Administrator on 2016/5/31.
 */

public class ResOptDetailDto implements Serializable {
    private Date startTime;// ��ʼʱ��
    private Date endTime; //����ʱ��
    private Boolean isFinished; //�Ƿ����� 0-δ��ɣ�1-δ���
    private String id; //ÿ��id
    private long length; //����ʱ��
    private Boolean isSucss;

    public Boolean getIsSucss() {
        return isSucss;
    }

    public void setIsSucss(Boolean isSucss) {
        this.isSucss = isSucss;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Boolean getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(Boolean isFinished) {
        this.isFinished = isFinished;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getDiff() {
        return endTime.getTime() - startTime.getTime();
    }
}