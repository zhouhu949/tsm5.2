package com.qftx.tsm.cust.dto;

import java.io.Serializable;

/**
 * 存放淘客户缓存资源
 * 
 * @author: wuwei
 * @since: 2015年12月31日 上午9:09:52
 * @history:
 */
public class TaoResDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8147357391167686512L;
	private String resId; // 资源id
	private String name; // 名称
	private String phone;// 电话
	private int status; // 回收 或 变更
	private int isCall;// 是否被回收公海或淘到成为意向
	private String callDatePostponed; // 延后呼叫日期
	private String reasonForCalldelay; // 延后呼叫原因	
	private int filterType;
	public int getIsCall() {
		return isCall;
	}

	public void setIsCall(int isCall) {
		this.isCall = isCall;
	}

	public String getResId() {
		return resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getFilterType() {
		return filterType;
	}

	public void setFilterType(int filterType) {
		this.filterType = filterType;
	}

	public String getCallDatePostponed() {
		return callDatePostponed;
	}

	public void setCallDatePostponed(String callDatePostponed) {
		this.callDatePostponed = callDatePostponed;
	}

	public String getReasonForCalldelay() {
		return reasonForCalldelay;
	}

	public void setReasonForCalldelay(String reasonForCalldelay) {
		this.reasonForCalldelay = reasonForCalldelay;
	}

}
