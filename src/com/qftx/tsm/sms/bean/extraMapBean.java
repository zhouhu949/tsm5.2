package com.qftx.tsm.sms.bean;

public class extraMapBean {
	private String level;//通知级别（1级：其他同事发布的签到、日志、日程；2级：客户跟进提醒、客户自动放入公海提醒；3级：通知公告、日程开始时间提醒、账号到期提醒、工作点评提醒、打开一个网页）
	private String type;//0.签到；1.日志；2.日程；3.客户跟进；4.客户自动放入公海；5.通知公告；6.日程开始时间提醒；7.账号到期提醒；8.工作点评提醒；9.打开一个网页
	private String   obj;//自定义类型，最后转换成String，查详情参数： 用于点击通知跳转用，该参数无限定格式，可以是ID（eg:工作点评时为点评对象的id，点击该提醒进入详情页面），也可以是一个网址，也可以是文本或者jsonobject
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

	
}
