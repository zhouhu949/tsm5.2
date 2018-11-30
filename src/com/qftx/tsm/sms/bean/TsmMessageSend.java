package com.qftx.tsm.sms.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;
import java.util.List;

/** 
 * 消息发送
 * @author: zwj
 * @since: 2015-12-9  上午11:37:09
 * @history: 4.x
 */
public class TsmMessageSend extends BaseObject {
	private static final long serialVersionUID = 8271591162679687228L;

	private String messageId; // 消息Id

    private Date sendDate; // 发送时间

    private Date updateDate; // 记录更新时间

    private Short isDel; // 

    private String orgId; //  机构ID

    private String submitStatus; // 提交状态

    private String sendFrom; // 该消息是谁发的

    private String sendTo; // 该消息发给谁

    private String title; // 标题

    private String messageContent; // 小助手内容
    
    private String msgCenterContent; // 消息中心内容

    private Date startTime; // 开始时间（精确到分钟，无值时不用传此参）

    private Date endTime; // 结束时间（精确到分钟，无值时不用传此参）
    
    private String remark; // 消息备注 如（点评消息时，这里需要存储资源ID）
    
    private String businessId; // 业务ID 如：(公告ID，点评ID,资源ID....)
    
    private String content; // 内容冗余字段（目前为了显示点评内容，因为点评目前有多张表）
    /***
     * 消息类型 
     * AppConstant.MSG_TYPE_FOLLOW = 1; // 客户跟进
		AppConstant.MSG_TYPE_DELAY = 2; // 延后呼叫
		AppConstant.MSG_TYPE_ALERT = 3; // 跟进警报
		AppConstant.MSG_TYPE_SMS = 5; // 短信不足
		AppConstant.MSG_TYPE_SEAT = 6; // 坐席到期
		AppConstant.MSG_TYPE_COMMUNICAION = 7; // 通信时长不足
		AppConstant.MSG_TYPE_AUDIT_ORDER = 8; // 订单审核
		AppConstant.MSG_TYPE_NO_RECEVCALL = 9; // 未接来电
		AppConstant.MSG_TYPE_REVIEW_WORK = 10; // 工作点评
		AppConstant.MSG_TYPE_REVIEW_DAILY = 11; // 工作日报点评
		AppConstant.MSG_TYPE_REVIEW_DAILY_REPLY = 12; // 工作日报点评回复
		AppConstant.MSG_TYPE_REVIEW_MONTHLY =13; // 月度计划点评
		AppConstant.MSG_TYPE_PLAN_NOT_SUB = 14; // 计划未提交到期提醒
		AppConstant.MSG_TYPE_AUDIT_MONTHLY = 15; // 月度计划审核
		AppConstant.MSG_TYPE_AUDIT_DAILY = 16; // 日计划审核
		AppConstant.MSG_TYPE_AUDIT_DELAY = 17; // 延期审核
		AppConstant.MSG_TYPE_AUDIT_NOTICE = 18; // 通知公告
		AppConstant.MSG_TYPE_AUDIT_GROUP_MONTHLY = 19; // 小组月计划审核
		AppConstant.MSG_TYPE_AUDIT_DEPAT_MONTHLY = 20; // 部门月计划审核
		AppConstant.MSG_TYPE_SYS = 24; // 系统维护消息
	    AppConstant.MSG_TYPE_MON = 25; // 钱包通知
	    AppConstant.MSG_TYPE_OTHER = 26; // 其他消息
	    AppConstant.MSG_TYPE_SYS_VERSION=27;//系统版本说明
     */
    private Integer msgType; 
    
    private Integer contractType; // 联系方式
    
    private Integer isRead; // 是否已读，0：未读；1：已读
    
    private Date birthDay;  //生日
    
    private Integer isBarOpen;//是否开启弹幕
    
    private List<String> birthDayUsers; //生日的人合集
    
    private String birthDayUserAccount; //生日的人账号
    
    private String birthDayUserName; //生日的人姓名
    
    private String imgUrl; //头像地址
    
    private String barrType;//弹幕类型
    
    private String cardType;
    
    private Integer ifbirthDay;//是否生日，1表示今天生日
    
    private Integer ifhonor;//是否荣誉榜单，1表示在荣誉榜单中

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId == null ? null : messageId.trim();
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Short getIsDel() {
        return isDel;
    }

    public void setIsDel(Short isDel) {
        this.isDel = isDel;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getSubmitStatus() {
        return submitStatus;
    }

    public void setSubmitStatus(String submitStatus) {
        this.submitStatus = submitStatus == null ? null : submitStatus.trim();
    }

    public String getSendFrom() {
        return sendFrom;
    }

    public void setSendFrom(String sendFrom) {
        this.sendFrom = sendFrom == null ? null : sendFrom.trim();
    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo == null ? null : sendTo.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent == null ? null : messageContent.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getMsgType() {
		return msgType;
	}

	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	public Integer getContractType() {
		return contractType;
	}

	public void setContractType(Integer contractType) {
		this.contractType = contractType;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getMsgCenterContent() {
		return msgCenterContent;
	}

	public void setMsgCenterContent(String msgCenterContent) {
		this.msgCenterContent = msgCenterContent;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public List<String> getBirthDayUsers() {
		return birthDayUsers;
	}

	public void setBirthDayUsers(List<String> birthDayUsers) {
		this.birthDayUsers = birthDayUsers;
	}

	public String getBirthDayUserAccount() {
		return birthDayUserAccount;
	}

	public void setBirthDayUserAccount(String birthDayUserAccount) {
		this.birthDayUserAccount = birthDayUserAccount;
	}

	public String getBirthDayUserName() {
		return birthDayUserName;
	}

	public void setBirthDayUserName(String birthDayUserName) {
		this.birthDayUserName = birthDayUserName;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getIsBarOpen() {
		return isBarOpen;
	}

	public void setIsBarOpen(Integer isBarOpen) {
		this.isBarOpen = isBarOpen;
	}

	public String getBarrType() {
		return barrType;
	}

	public void setBarrType(String barrType) {
		this.barrType = barrType;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public Integer getIfbirthDay() {
		return ifbirthDay;
	}

	public void setIfbirthDay(Integer ifbirthDay) {
		this.ifbirthDay = ifbirthDay;
	}

	public Integer getIfhonor() {
		return ifhonor;
	}

	public void setIfhonor(Integer ifhonor) {
		this.ifhonor = ifhonor;
	}


	
		
}