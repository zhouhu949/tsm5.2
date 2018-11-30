package com.qftx.tsm.log.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;

public class LogWebInfoBean extends BaseObject {
/*
  `id` varchar(32) NOT NULL,
  `org_id` varchar(32) DEFAULT NULL COMMENT '机构名',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户名',
  `name` varchar(20) DEFAULT NULL COMMENT '模块名称',
  `url` varchar(4) DEFAULT NULL COMMENT '地址',
  `time_length` varchar(4) DEFAULT NULL COMMENT '毫秒',
  `data` longtext COMMENT '参数数据',
  `context` varchar(50) DEFAULT NULL COMMENT '描述',
  `inputtime` datetime DEFAULT NULL COMMENT '输入时间',
  `updatetime` datetime DEFAULT NULL COMMENT '修改时间',
  `is_del` int(11) DEFAULT '0' COMMENT '册除状态1-删除，0-未删除',
 */
	private static final long serialVersionUID = -1014851173802452224L;

	private String id;//id
	private String orgId;//机构名
	private String userId;//用户名
	private String custId;//资源ID
	private String account;//资源ID
	private String name;//日志名称
	private String webUrl;
	private Long timeLength;//时长
	private String data;//资源JSON数据
	private String ip;//描述
	private String code;//描述
	private Date inputtime;//输入时间
	private Date updatetime;//修改时间
	private Integer isDel;//册除状态1-删除，0-未删除

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}


	public String getWebUrl() {
		return webUrl;
	}

	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}

	public Long getTimeLength() {
		return timeLength;
	}

	public void setTimeLength(Long timeLength) {
		this.timeLength = timeLength;
	}

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
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
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
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

}
