package com.qftx.tsm.email.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;

/***
 * 邮箱信息配置
 * @author: zwj
 * @since: 2015-12-9  下午5:26:28
 * @history: 4.x
 */
public class TsmEmailConfig extends BaseObject {
	private static final long serialVersionUID = -3577613874507077338L;
	
	private String id;
	private String orgId; // 机构ID
	private String userId; // 用户ID
	private String passWord; // 邮箱密码
	private String loginUser; // 邮箱登录名
	private String address; // 邮箱地址
	private Integer sendnum; // 默认发送次数（主要是失败重发部分）
	private Date inputtime;
	private Date updatetime;
	private Integer isDel; // 册除状态1-删除，0-未删除
		
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
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getLoginUser() {
		return loginUser;
	}
	public void setLoginUser(String loginUser) {
		this.loginUser = loginUser;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	public Integer getSendnum() {
		return sendnum;
	}
	public void setSendnum(Integer sendnum) {
		this.sendnum = sendnum;
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