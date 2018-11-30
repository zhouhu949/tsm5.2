package com.qftx.tsm.worklog.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qftx.base.util.GuidUtil;
import com.qftx.common.domain.BaseObject;

import java.util.Date;

public class WorkLogDefaultBean extends BaseObject{
	private static final long serialVersionUID = -1866374370097926474L;
	
	private String wldId;	//日专默认分享ID
	private String userId;	//用户ID
	private String orgId;	//机构ID
	private String defaultShareUserid;	//分享给其他用户的id
	private String defaultShareUseracc;	//分享给其他用户的账户
	private Date inputTime;	//插入时间
	private Date updateTime;	//更新时间
	private Integer isDel;	//是否删除（0：否 1：是）
	
	private String userName;
	
	public WorkLogDefaultBean() {
		super();
	}
	
	public WorkLogDefaultBean(String orgId,String userId,String shareUserId,String shareUserAcc) {
		super();
		this.wldId = GuidUtil.getUUID();
		this.userId = userId;
		this.orgId = orgId;
		this.defaultShareUserid = shareUserId;
		this.defaultShareUseracc = shareUserAcc;
	}
	
	public String getWldId() {
		return wldId;
	}
	public void setWldId(String wldId) {
		this.wldId = wldId;
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
	
	public String getDefaultShareUserid() {
		return defaultShareUserid;
	}

	public void setDefaultShareUserid(String defaultShareUserid) {
		this.defaultShareUserid = defaultShareUserid;
	}

	public String getDefaultShareUseracc() {
		return defaultShareUseracc;
	}

	public void setDefaultShareUseracc(String defaultShareUseracc) {
		this.defaultShareUseracc = defaultShareUseracc;
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

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public boolean equals(Object object) {
		if(object==null||!(object instanceof WorkLogDefaultBean)){
			return false;
		}
		WorkLogDefaultBean share = (WorkLogDefaultBean)object;
		return 
				this.defaultShareUserid.equals(share.defaultShareUserid)&&
				this.userId.equals(share.userId)&&
				this.orgId.equals(share.orgId);
	} 
}
