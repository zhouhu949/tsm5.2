package com.qftx.tsm.follow.dto;

import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.cust.bean.ResCustInfoDetailBean;
import com.qftx.tsm.sys.bean.CustFieldSet;
import com.qftx.tsm.tao.dto.CustDto;
import com.qftx.tsm.tao.dto.FieldDto;

import java.io.Serializable;
import java.util.List;

/** 跟进详情 前端 客户信息所需 数据  */
public class CustFollowShowCustInfoDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private ResCustInfoBean custEntity; // 客户信息
	private List<CustFieldSet> fieldSets; // 字段列表
	private String pname; // 省市区
	private String cname; // 省市区
	private String oname; // 省市区
	private List<ResCustInfoDetailBean> details; // 联系人列表
	private ResCustInfoDetailBean detail; // 默认联系人信息
	private Integer idReplaceWord; // 0:不隐藏号码，1:隐藏号码
	private List<FieldDto> custData; // 客户信息 页面显示字段列表
	private CustDto custInfo; // 联系人信息
	
	public ResCustInfoBean getCustEntity() {
		return custEntity;
	}
	public void setCustEntity(ResCustInfoBean custEntity) {
		this.custEntity = custEntity;
	}
	public List<CustFieldSet> getFieldSets() {
		return fieldSets;
	}
	public void setFieldSets(List<CustFieldSet> fieldSets) {
		this.fieldSets = fieldSets;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getOname() {
		return oname;
	}
	public void setOname(String oname) {
		this.oname = oname;
	}
	public List<ResCustInfoDetailBean> getDetails() {
		return details;
	}
	public void setDetails(List<ResCustInfoDetailBean> details) {
		this.details = details;
	}
	public ResCustInfoDetailBean getDetail() {
		return detail;
	}
	public void setDetail(ResCustInfoDetailBean detail) {
		this.detail = detail;
	}
	public Integer getIdReplaceWord() {
		return idReplaceWord;
	}
	public void setIdReplaceWord(Integer idReplaceWord) {
		this.idReplaceWord = idReplaceWord;
	}
	public List<FieldDto> getCustData() {
		return custData;
	}
	public void setCustData(List<FieldDto> custData) {
		this.custData = custData;
	}
	public CustDto getCustInfo() {
		return custInfo;
	}
	public void setCustInfo(CustDto custInfo) {
		this.custInfo = custInfo;
	}
}
