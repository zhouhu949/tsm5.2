package com.qftx.tsm.message.bean;

public class Get_Message_Resp {
	 /*1、提醒分类：客户跟进提醒、意向客户未跟进放入公海前提醒、意向客户未跟进放入公海后提醒、帐号到期提醒、工作点评提醒

      *2、客户跟进的提醒，提醒消息含以下功能：拨打电话、查看历史小记、查看客户详情信息

      *3、意向客户自动放入公海提醒，提醒分3种不同的情况下处理：第一、意向客户放入公海前的提醒，第二、意向客户放入公海后的提醒（未被其它销售取走），第三、意向客户放入公海后的提醒（已被其它销售取走）；这三种情况，分别对应原型页面上的编号1、2、3，提醒模板以及功能如原型所示；其中，当放入公海的客户被其它销售取走，该客户的信息不允许查看，不能拨打电话；其它两种情况下，用户可以拨打电话、查看历史小记、查看详情；

      *4、工作点评提醒：显示点评人，以及点评时间；点评内容完整显示；用户可以给客户打电话、查看历史小记、查看客户详情；

      *5、查看自己客户的提醒（提醒发生时是客户属于当前所有者），查看自己账号的提醒； 
	 * 
	 * */
	private String ID;//该条数据的ID
	private Integer type;//消息类别(0.客户跟进提醒、1.意向客户未跟进放入公海前提醒、2.意向客户未跟进放入公海后提醒、3.帐号到期提醒、4.工作点评提醒 )
	private String title;//消息标题
	private Long time_action;//数据产生时间
	private String info;//消息内容
	private String tel;//意向客户电话（非必须）、客服电话
	private Long  reminderTime;//提醒时间（时间戳,根据日程时间计算,日程时间等于提醒时间则为不提醒）
	private String pl_person;//评论人名称
	private String pl_personid;//评论人ID
	private Long pl_time;//评论时间


	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getTime_action() {
		return time_action;
	}
	public void setTime_action(Long time_action) {
		this.time_action = time_action;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public Long getReminderTime() {
		return reminderTime;
	}
	public void setReminderTime(Long reminderTime) {
		this.reminderTime = reminderTime;
	}
	public String getPl_person() {
		return pl_person;
	}
	public void setPl_person(String pl_person) {
		this.pl_person = pl_person;
	}
	public String getPl_personid() {
		return pl_personid;
	}
	public void setPl_personid(String pl_personid) {
		this.pl_personid = pl_personid;
	}
	public Long getPl_time() {
		return pl_time;
	}
	public void setPl_time(Long pl_time) {
		this.pl_time = pl_time;
	}
	
	

}
