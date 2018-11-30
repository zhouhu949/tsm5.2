package com.qftx.tsm.sys.dto;

import com.qftx.tsm.sys.bean.TsmCustReview;

import java.util.Date;
import java.util.List;

/**
 * 请求类
 * 
 * @author: wuwei
 * @since: 2015年11月13日 下午1:34:32
 * @history:
 */
public class TsmCustReviewDto extends TsmCustReview {

	private static final long serialVersionUID = 1L;

	private Date actionDate; // 行动时间
	private String reviewName; // 点评人姓名
	private String custId; // 客户编号
	private String ownerName; // 所有者姓名
	private String resCustName; // 客户姓名
	private String mobilePhone;// 客户常用号码
	private String telPhone;// 客户备用号码
	private String qKeyWord;
	private List<String> ownerAccs;
	private String oDateType;
	private String ownerAccsStr;
	private String osType;// 所有者查询方式 1-全部 2-只看自己 3-选中查询
	private String ownerUserIdsStr;
	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}

	public String getOwnerUserIdsStr() {
		return ownerUserIdsStr;
	}

	public void setOwnerUserIdsStr(String ownerUserIdsStr) {
		this.ownerUserIdsStr = ownerUserIdsStr;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName == null ? null : ownerName.trim();
	}

	public Date getActionDate() {
		return actionDate;
	}

	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}

	public String getReviewName() {
		return reviewName;
	}

	public void setReviewName(String reviewName) {
		this.reviewName = reviewName;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getResCustName() {
		return resCustName;
	}

	public void setResCustName(String resCustName) {
		this.resCustName = resCustName;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getTelPhone() {
		return telPhone;
	}

	public void setTelPhone(String telPhone) {
		this.telPhone = telPhone;
	}

	public String getqKeyWord() {
		return qKeyWord;
	}

	public void setqKeyWord(String qKeyWord) {
		this.qKeyWord = qKeyWord;
	}

	public List<String> getOwnerAccs() {
		return ownerAccs;
	}

	public void setOwnerAccs(List<String> ownerAccs) {
		this.ownerAccs = ownerAccs;
	}

	public String getoDateType() {
		return oDateType;
	}

	public void setoDateType(String oDateType) {
		this.oDateType = oDateType;
	}

	public String getOwnerAccsStr() {
		return ownerAccsStr;
	}

	public void setOwnerAccsStr(String ownerAccsStr) {
		this.ownerAccsStr = ownerAccsStr;
	}
}