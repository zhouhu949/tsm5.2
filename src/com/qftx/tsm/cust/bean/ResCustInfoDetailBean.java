package com.qftx.tsm.cust.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qftx.common.domain.BaseObject;

import java.util.Date;

public class ResCustInfoDetailBean extends BaseObject {
	private static final long serialVersionUID = -5233866859181217900L;
	
	private String tscidId;//
	private String rciId;//资源ID
	private String orgId;//机构ID
	private String sex;//性别：1-男， 2-女
	private String name;//联系人姓名
	private String work;//工作职位
	private String telphone;//电话
	private String telphonebak;//备用电话
	private String email;//邮件地址
	private String context;//备注
	private Date inputtime;//输入时间
	private Date updatetime;//修改时间
	private Integer isDel;//是否删除：0-否，1-是
	private String fax;//传真电话
	private Date birthday;//生日
	private String keyword;//关键字
	private String groupname;//所在部门名称
	private String wangwang;//旺旺
	private String qq;//qq
	private Integer isDefault;//是否为默认联系人：0-否 1-是
	private String conDefined1; //  自定义1
	private String conDefined2; //  自定义2
	private String conDefined3; //  自定义3
	private String conDefined4; //  自定义4
	private Date conDefined5; //  自定义5
	private String callNum;
	public String getTscidId() {
		return tscidId;
	}
	public void setTscidId(String tscidId) {
		this.tscidId = tscidId;
	}
	public String getRciId() {
		return rciId;
	}
	public void setRciId(String rciId) {
		this.rciId = rciId;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWork() {
		return work;
	}
	public void setWork(String work) {
		this.work = work;
	}
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	public String getTelphonebak() {
		return telphonebak;
	}
	public void setTelphonebak(String telphonebak) {
		this.telphonebak = telphonebak;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getInputtime() {
		return inputtime;
	}
	public void setInputtime(Date inputtime) {
		this.inputtime = inputtime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
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
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public String getWangwang() {
		return wangwang;
	}
	public void setWangwang(String wangwang) {
		this.wangwang = wangwang;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public Integer getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
	public String getConDefined1() {
		return conDefined1;
	}
	public void setConDefined1(String conDefined1) {
		this.conDefined1 = conDefined1;
	}
	public String getConDefined2() {
		return conDefined2;
	}
	public void setConDefined2(String conDefined2) {
		this.conDefined2 = conDefined2;
	}
	public String getCallNum() {
		return callNum;
	}
	public void setCallNum(String callNum) {
		this.callNum = callNum;
	}
	public String getConDefined3() {
		return conDefined3;
	}
	public void setConDefined3(String conDefined3) {
		this.conDefined3 = conDefined3;
	}
	public String getConDefined4() {
		return conDefined4;
	}
	public void setConDefined4(String conDefined4) {
		this.conDefined4 = conDefined4;
	}
	public Date getConDefined5() {
		return conDefined5;
	}
	public void setConDefined5(Date conDefined5) {
		this.conDefined5 = conDefined5;
	}
	
}
