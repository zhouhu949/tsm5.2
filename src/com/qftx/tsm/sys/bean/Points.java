package com.qftx.tsm.sys.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;

/**
 * 系统积分配置
 * */
public class Points extends BaseObject{

	private static final long serialVersionUID = -1946603393759273177L;
	
	private String pointsId; 		// 积分ID
	private String orgId;				// 机构ID
	private String iconUrl;			// 图标地址
	private Integer level;			// 积分等级
	private String levelName;		// 积分等级名称
	private Integer sort;				// 排序
	private Integer startNumber;// 开始范围(0表示不判断条件)
	private Integer endNumber; // 结束范围(0表示不判断条件)
	private String inputAcc;		// 录入人
  	private Date inputdate;		// 录入时间
  	private String modifierAcc;
  	private Date modifydate;
  	private Integer isDel;			// 册除状态1-删除，0-未删除
	public String getPointsId() {
		return pointsId;
	}
	public void setPointsId(String pointsId) {
		this.pointsId = pointsId;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Integer getStartNumber() {
		return startNumber;
	}
	public void setStartNumber(Integer startNumber) {
		this.startNumber = startNumber;
	}
	public Integer getEndNumber() {
		return endNumber;
	}
	public void setEndNumber(Integer endNumber) {
		this.endNumber = endNumber;
	}
	public String getInputAcc() {
		return inputAcc;
	}
	public void setInputAcc(String inputAcc) {
		this.inputAcc = inputAcc;
	}
	public Date getInputdate() {
		return inputdate;
	}
	public void setInputdate(Date inputdate) {
		this.inputdate = inputdate;
	}
	public String getModifierAcc() {
		return modifierAcc;
	}
	public void setModifierAcc(String modifierAcc) {
		this.modifierAcc = modifierAcc;
	}
	public Date getModifydate() {
		return modifydate;
	}
	public void setModifydate(Date modifydate) {
		this.modifydate = modifydate;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
}
