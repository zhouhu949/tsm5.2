package com.qftx.tsm.sys.dto;

import com.qftx.tsm.sys.bean.AnnouncementBean;

import java.util.List;

/**
 * 通知公告实体拓展类
 * @author 徐承恩
 *
 */
public class AnnouncementDto extends AnnouncementBean {

	private static final long serialVersionUID = 1L;
	
	private String userName;	//发布人
	
	private Integer day;		//天数
	
	private String announceReadId; //消息ID
	
	private Integer isRead; //是否已读
	
	private Integer rowSeq; //排序 用于上下条
	
	private List<String> ReadeUser ;// 已读人员列表
	
	private String readeUsers; //已读人员
	
	private List<String> IdList; //消息id列表
	
	
	public List<String> getIdList() {
		return IdList;
	}

	public void setIdList(List<String> idList) {
		IdList = idList;
	}

	public String getReadeUsers() {
		return readeUsers;
	}

	public void setReadeUsers(String readeUsers) {
		this.readeUsers = readeUsers;
	}

	public List<String> getReadeUser() {
		return ReadeUser;
	}

	public void setReadeUser(List<String> readeUser) {
		ReadeUser = readeUser;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAnnounceReadId() {
		return announceReadId;
	}

	public void setAnnounceReadId(String announceReadId) {
		this.announceReadId = announceReadId;
	}

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	public Integer getRowSeq() {
		return rowSeq;
	}

	public void setRowSeq(Integer rowSeq) {
		this.rowSeq = rowSeq;
	}
	
	
}
