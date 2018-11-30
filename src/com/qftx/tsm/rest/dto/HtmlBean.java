package com.qftx.tsm.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 网页采集反馈信息
 * Created by bxl on 2015/11/6.
 */
public class HtmlBean implements java.io.Serializable  {
    private String code;//代码 1 已存在，0不在存，1001不支持网址，1002采集错误，
    private Date inputtime;
    private String url;
    private String desc;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getInputtime() {
        return inputtime;
    }

    public void setInputtime(Date inputtime) {
        this.inputtime = inputtime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
