package com.qftx.tsm.cust.dto;

import com.qftx.tsm.cust.bean.ResCustinfoLogBean;

import java.util.Date;

public class ResCustinfoLogDto extends ResCustinfoLogBean {
	private static final long serialVersionUID = 132318606144472680L;
	private String saleName; //销售联系人
	private String mainLinkman;//客户联系人
	private String context;//备注信息-联系小计
	private Date followDate;//下次跟进时间
	private String optionName;//销售进程id
	private String mark; //1-资源备注；2-跟进信息
	public String getSaleName() {
		return saleName;
	}
	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}
	public String getMainLinkman() {
		return mainLinkman;
	}
	public void setMainLinkman(String mainLinkman) {
		this.mainLinkman = mainLinkman;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public Date getFollowDate() {
		return followDate;
	}
	public void setFollowDate(Date followDate) {
		this.followDate = followDate;
	}
	public String getOptionName() {
		return optionName;
	}
	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
}
