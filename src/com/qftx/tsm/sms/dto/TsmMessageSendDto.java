package com.qftx.tsm.sms.dto;

import com.qftx.tsm.sms.bean.TsmMessageSend;

public class TsmMessageSendDto extends TsmMessageSend {

	private static final long serialVersionUID = -5410897885412649409L;
	
	  //客户联系
    private Integer custNum;
    //点评提醒
    private Integer bbsNum;
    //未接来电
    private Integer callNum;
    //到期提醒
    private Integer dateNum;
    //通知公告
    private Integer noticeNum;
    //待办审核
    private Integer authNum;
    //系统消息   
    private Integer sysNum;
    //其他消息 
    private Integer otherNum;
    
    private Integer state; // (1:客户联系，2:点评提醒，3:未接来电，4：到期提醒，5：通知公告，6：代办审核,7:系统消息，8：其他消息)
    
    private String dtoTitle; // 标题
    	
    private String dtoContext; // 内容
    
    private String sendAcc; // 发布人
    
    private String phone; // 联系电话
    
    private Integer status;
    
	public Integer getCustNum() {
		return custNum;
	}
	public void setCustNum(Integer custNum) {
		this.custNum = custNum;
	}
	public Integer getBbsNum() {
		return bbsNum;
	}
	public void setBbsNum(Integer bbsNum) {
		this.bbsNum = bbsNum;
	}
	public Integer getCallNum() {
		return callNum;
	}
	public void setCallNum(Integer callNum) {
		this.callNum = callNum;
	}
	public Integer getDateNum() {
		return dateNum;
	}
	public void setDateNum(Integer dateNum) {
		this.dateNum = dateNum;
	}
	public Integer getNoticeNum() {
		return noticeNum;
	}
	public void setNoticeNum(Integer noticeNum) {
		this.noticeNum = noticeNum;
	}
	public Integer getAuthNum() {
		return authNum;
	}
	public void setAuthNum(Integer authNum) {
		this.authNum = authNum;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getDtoTitle() {
		return dtoTitle;
	}
	public void setDtoTitle(String dtoTitle) {
		this.dtoTitle = dtoTitle;
	}
	public String getDtoContext() {
		return dtoContext;
	}
	public void setDtoContext(String dtoContext) {
		this.dtoContext = dtoContext;
	}
	public String getSendAcc() {
		return sendAcc;
	}
	public void setSendAcc(String sendAcc) {
		this.sendAcc = sendAcc;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getSysNum() {
		return sysNum;
	}
	public void setSysNum(Integer sysNum) {
		this.sysNum = sysNum;
	}
	public Integer getOtherNum() {
		return otherNum;
	}
	public void setOtherNum(Integer otherNum) {
		this.otherNum = otherNum;
	}
		
}
