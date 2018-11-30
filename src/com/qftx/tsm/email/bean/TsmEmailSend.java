package com.qftx.tsm.email.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;

/***
 * 邮箱发送
 * @author: zwj
 * @since: 2015-12-9  下午5:26:28
 * @history: 4.x
 */
public class TsmEmailSend extends BaseObject {
	private static final long serialVersionUID = -3368993298409866710L;
	
	private String id;
	private String orgId; // 机构ID
	private String userId; // 用户ID
	private String title; // 邮件标题
	private String context; //邮件内容
	private String fileIds; // sys_file id 逗号分隔
	private String sendUser; // 邮件发件人
	private Date inputTime;
	private Date updateTime;
	private Integer isDel;  // 删除状态1-删除，0-未删除
	private Integer status; // 发送状态(0未发送，1已发送)
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getFileIds() {
		return fileIds;
	}
	public void setFileIds(String fileIds) {
		this.fileIds = fileIds;
	}
	public String getSendUser() {
		return sendUser;
	}
	public void setSendUser(String sendUser) {
		this.sendUser = sendUser;
	}
	public Date getInputTime() {
		return inputTime;
	}
	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

}