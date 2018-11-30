package com.qftx.base.ca.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * User£º bxl
 * Date£º 2015/12/31
 * Time£º 11:23
 */
public class CaDataBean {
    private String code;
    private String descr;
    private CaBaseInfoBean basicInfo;
    private List<CaParamsBean> params=new ArrayList<CaParamsBean>();

    public List<CaParamsBean> getParams() {
        return params;
    }

    public void setParams(List<CaParamsBean> params) {
        this.params = params;
    }

    public CaDataBean() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }
    public CaBaseInfoBean getBasicInfo() {
        return basicInfo;
    }

    public void setBasicInfo(CaBaseInfoBean basicInfo) {
        this.basicInfo = basicInfo;
    }
}
