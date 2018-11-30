package com.qftx.tsm.lock;

/**
 * User£º bxl
 * Date£º 2016/6/1
 * Time£º 10:56
 */
public class TestLockBean {
    @Override
    public int hashCode() {
        int code=0;
        code+=(orgId!=null?orgId.hashCode():1)*31+10;
        code+=(account!=null?account.hashCode():1)*31+10;
        return code;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String key;
    private String orgId;
    private String account;

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
