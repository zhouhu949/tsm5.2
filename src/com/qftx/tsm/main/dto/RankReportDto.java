package com.qftx.tsm.main.dto;

import java.io.Serializable;
import java.math.BigDecimal;



 /** 
 * 销售首页-排行榜Dto
 * @author: lixing
 * @since: 2015年12月7日  下午7:16:49
 * @history:
 */
public class RankReportDto implements Serializable {
	private static final long serialVersionUID = 3017447882634883124L;
	
	private Integer rank;//排名
	private String userName;//姓名
	private Integer signNum;//签约客户数
	private BigDecimal saleNum;//签约金额
	private Integer willNum;//意向客户数
	private String userAccount;//帐号
	private BigDecimal callTime;//时长
	private Integer callOutTotal;
	//
	private String imgUrl; //头像地址
	private String cardType; //弹幕类型：1：生日弹屏 ，2：排行榜 ，3：签约
	private String content; //弹幕内容
	
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getSignNum() {
		return signNum;
	}
	public void setSignNum(Integer signNum) {
		this.signNum = signNum;
	}
	public BigDecimal getSaleNum() {
		return saleNum;
	}
	public void setSaleNum(BigDecimal saleNum) {
		this.saleNum = saleNum;
	}
	public Integer getWillNum() {
		return willNum;
	}
	public void setWillNum(Integer willNum) {
		this.willNum = willNum;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public BigDecimal getCallTime() {
		return callTime;
	}
	public void setCallTime(BigDecimal callTime) {
		this.callTime = callTime;
	}
	public Integer getCallOutTotal() {
		return callOutTotal;
	}
	public void setCallOutTotal(Integer callOutTotal) {
		this.callOutTotal = callOutTotal;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
