package com.qftx.tsm.email.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;

/***
 * 邮件 模板
 * @author: zwj
 * @since: 2015-12-9  下午5:26:28
 * @history: 4.x
 */
public class TsmEmailTemp extends BaseObject {
	private static final long serialVersionUID = -6785721964552827778L;
	
	private String id;
	private String orgId;
	private String title; // 签名标题
	private String context; // 签名内容
	private Date inputtime;
	private String inputAcc;
	private Date updatetime;
	private String updateAcc;
	private Integer isDel; // 删除状态1-删除，0-未删除
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
	public String getInputAcc() {
		return inputAcc;
	}
	public void setInputAcc(String inputAcc) {
		this.inputAcc = inputAcc;
	}
	public String getUpdateAcc() {
		return updateAcc;
	}
	public void setUpdateAcc(String updateAcc) {
		this.updateAcc = updateAcc;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	public Date getInputtime() {
		return inputtime;
	}
	public void setInputtime(Date inputtime) {
		this.inputtime = inputtime;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
			
}