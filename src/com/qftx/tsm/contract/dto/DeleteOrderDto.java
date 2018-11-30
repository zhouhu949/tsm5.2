package com.qftx.tsm.contract.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DeleteOrderDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -973702411931473155L;
	private Date checkDate;
	private String custId;
	private String signUserId;
	private String signAcc;
	private String groupId;
	private BigDecimal money;
	
	public DeleteOrderDto(Date checkDate,String custId,String signUserId,String signAcc,String groupId,BigDecimal money) {
		// TODO Auto-generated constructor stub
		this.checkDate = checkDate;
		this.custId = custId;
		this.signUserId = signUserId;
		this.signAcc = signAcc;
		this.groupId = groupId;
		this.money = money;
	}
	
	public Date getCheckDate() {
		return checkDate;
	}
	public String getCustId() {
		return custId;
	}
	public String getSignAcc() {
		return signAcc;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getSignUserId() {
		return signUserId;
	}

	public void setSignUserId(String signUserId) {
		this.signUserId = signUserId;
	}

	public void setSignAcc(String signAcc) {
		this.signAcc = signAcc;
	}
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	
}
