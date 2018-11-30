package com.qftx.tsm.worklog.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qftx.base.util.GuidUtil;
import com.qftx.common.domain.BaseObject;

import java.util.Date;

//工作日志_分享表
public class WorkLogShareBean extends BaseObject{
	private static final long serialVersionUID = -643082645657549235L;
	private String wlsId;//日专分享ID
	private String wliId;//日志ID
	private String userId;//分享可看用户ID
	private String orgId;//机构ID
	private String shareUserid;	//分享人（可看日志的用户ID）
	private String shareUseracc;	//分享人（可看日志的用户账户）
	private Integer type;	//0.评论1.签到2.日志3.日程4.消息（备用）5.通知公告6.其它
	private Date inputTime;	//插入时间
	private Date updateTime;	//更新时间
	private Integer isDel;	//是否删除（0：否 1：是）
	
	private String  userName;//用户姓名
	
	public WorkLogShareBean() {
		super();
	}
	
	public WorkLogShareBean(String wliId, String userId, String orgId,String shareUserAcc,String shareUserId) {
		super();
		this.wlsId = GuidUtil.getUUID();
		this.wliId = wliId;
		this.userId = userId;
		this.orgId = orgId;
		this.shareUserid = shareUserId;
		this.shareUseracc = shareUserAcc;
	}


	public String getWlsId() {
		return wlsId;
	}
	public void setWlsId(String wlsId) {
		this.wlsId = wlsId;
	}
	public String getWliId() {
		return wliId;
	}
	public void setWliId(String wliId) {
		this.wliId = wliId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getShareUserid() {
		return shareUserid;
	}
	public void setShareUserid(String shareUserid) {
		this.shareUserid = shareUserid;
	}
	public String getShareUseracc() {
		return shareUseracc;
	}
	public void setShareUseracc(String shareUseracc) {
		this.shareUseracc = shareUseracc;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getInputTime() {
		return inputTime;
	}
	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	@Override
	public boolean equals(Object object) {
		if(object==null||!(object instanceof WorkLogShareBean)){
			return false;
		}
		WorkLogShareBean share = (WorkLogShareBean)object;
		return this.wliId.equals(share.wliId)&&
				this.shareUserid.equals(share.shareUserid)&&
				this.userId.equals(share.userId)&&
				this.orgId.equals(share.orgId);
	} 
	
}
