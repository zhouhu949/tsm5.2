package com.qftx.tsm.worklog.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qftx.common.domain.BaseObject;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.constants.AppConstant;

import java.util.Date;
import java.util.List;
/* work_log_bbs_up 工作日志_论评顶表*/
public class WorkLogBbsUpBean extends BaseObject {
	private String id;	//id
	private String wlbId;	//评论id
	private String userId;	//用户id
	private String orgId;	//单位id
	private Integer type;	//点赞类型 0:评论点赞 1:日志点赞
	private Date inputdate;	//插入时间
	private Date updatedate;	// 更新时间
	private Integer isDel;	//是否删除1-删除,0-未删除
	
	private String imgUrl;
	private String imgUrl_;
	private String userName;
	private List<String> wlbIds;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWlbId() {
		return wlbId;
	}
	public void setWlbId(String wlbId) {
		this.wlbId = wlbId;
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
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getInputdate() {
		return inputdate;
	}
	public void setInputdate(Date inputdate) {
		this.inputdate = inputdate;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	public List<String> getWlbIds() {
		return wlbIds;
	}
	public void setWlbIds(List<String> wlbIds) {
		this.wlbIds = wlbIds;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getImgUrl_() {
		if(StringUtils.isBlank(imgUrl)){
			imgUrl_  = AppConstant.DEFAULT_IMG_URL;
		}else{
			imgUrl_ = imgUrl;
		}
		return imgUrl_;
	}
	public void setImgUrl_(String imgUrl_) {
		this.imgUrl_ = imgUrl_;
	}
}