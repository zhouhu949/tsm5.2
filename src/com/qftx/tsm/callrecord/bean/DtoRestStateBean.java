package com.qftx.tsm.callrecord.bean;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * REST������Ϣ
 * Created by bxl on 2015/11/6.
 */
public class DtoRestStateBean implements java.io.Serializable  {
    private String code;//1-设置为签约客户不能打电话；2-设置为意向客户不能打电话；3设置公海客户不能打电话；11设置为共有客户不能打电话;0-可以打
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date inputtime;
    private String desc;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getInputtime() {
        return inputtime;
    }

    public void setInputtime(Date inputtime) {
        this.inputtime = inputtime;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
