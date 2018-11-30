package com.qftx.tsm.sms.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;


 /** 
  * 短信接收记录
  * @author: zwj
  * @since: 2015-12-9  上午11:38:14
  * @history: 3.x
  */
public class TsmSmsReceive extends BaseObject{
	private static final long serialVersionUID = 1300453783801133550L;
	
	private String smsId;		//短信ID
    private Date receiveDate;	//接收时间
    private String name;		//客户姓名
    private String mobilePhone;	//客户号码
    private String smsComment;	//短信内容
    private String account;		//用户账户
    private Short isDel;		//是否删除：1-删除，0-未删除
    private String orgId;		//机构ID

    public String getSmsId() {
        return smsId;
    }

    public void setSmsId(String smsId) {
        this.smsId = smsId == null ? null : smsId.trim();
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getSmsComment() {
        return smsComment;
    }

    public void setSmsComment(String smsComment) {
        this.smsComment = smsComment == null ? null : smsComment.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public Short getIsDel() {
        return isDel;
    }

    public void setIsDel(Short isDel) {
        this.isDel = isDel;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }
}