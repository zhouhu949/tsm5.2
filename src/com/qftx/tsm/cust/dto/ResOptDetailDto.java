package com.qftx.tsm.cust.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 资源处理详细dto
 * Created by Administrator on 2016/5/31.
 */

public class ResOptDetailDto implements Serializable {
    private Date startTime;// 开始时间
    private Date endTime; //结束时间
    private Boolean isFinished; //是否处理完 0-未完成；1-未完成
    private String id; //每次id
    private long length; //处理时长
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
