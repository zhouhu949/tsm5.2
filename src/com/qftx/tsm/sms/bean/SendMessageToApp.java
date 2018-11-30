package com.qftx.tsm.sms.bean;

public class SendMessageToApp {
	private String title;//通知标题(系统字段)
	private String summary;//通知内容（系统字段）
	private String level;//通知级别（1级：其他同事发布的签到、日志、日程；2级：客户跟进提醒、客户自动放入公海提醒；3级：通知公告、账号到期提醒、工作点评提醒）
	private String type;//3.客户跟进；4.客户自动放入公海；5.通知公告；7.账号到期提醒；8.工作点评提醒；
	private String   obj;//自定义类型，最后转换成String，查详情参数： 用于点击通知跳转用，该参数无限定格式，可以是ID（eg:工作点评时为点评对象的id，点击该提醒进入详情页面），也可以是一个网址，也可以是文本或者jsonobject
	private String orgId;//单位id
	private String userAccount;//发送给单用户需有值，按单位发不需要值
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getObj() {
		return obj;
	}
	public void setObj(String obj) {
		this.obj = obj;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}


	

}
