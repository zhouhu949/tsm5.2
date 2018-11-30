package com.qftx.tsm.follow.dto;

import com.qftx.tsm.cust.bean.TsmCustGuide;
import com.qftx.tsm.sys.bean.Product;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/** 跟进详情页面 除第一次其他刷新页面所需数据 */
public class CustFollowShowGuideDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String custId; // 资源Id
	private Date actionDate; // 联系时间
	private String followId; // 跟进Id
	private CustFollowDto lastCustFollow; // 上一次跟进信息
	private TsmCustGuide tsmCustGuideEntity; // 销售导航信息
	private String procIds; // 默认展示产品IDs
	private String procNames; // 默认的展示产品名称S
	private String defDate; // 下次联系时间
	private String planParam; // 1：只显示计划中客户，2：不显示计划中客户
	private Integer status; // 资源客户状态：1-未分配，2-未跟进，3-跟进中，4-公海客户，5-资源回收站，6-已签约，7-沉默客户，8-流失客户
	private List<Product> suitProcList; // 适用产品列表
	private int isMark; // 1:资源所有者为操作人，2：其他
	private int isSign; // 1:可以签约，2：其他
	private String lastOptionListId; //销售进程ID
	
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public Date getActionDate() {
		return actionDate;
	}
	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}
	public String getFollowId() {
		return followId;
	}
	public void setFollowId(String followId) {
		this.followId = followId;
	}
	public CustFollowDto getLastCustFollow() {
		return lastCustFollow;
	}
	public void setLastCustFollow(CustFollowDto lastCustFollow) {
		this.lastCustFollow = lastCustFollow;
	}
	public TsmCustGuide getTsmCustGuideEntity() {
		return tsmCustGuideEntity;
	}
	public void setTsmCustGuideEntity(TsmCustGuide tsmCustGuideEntity) {
		this.tsmCustGuideEntity = tsmCustGuideEntity;
	}
	public String getProcIds() {
		return procIds;
	}
	public void setProcIds(String procIds) {
		this.procIds = procIds;
	}
	public String getProcNames() {
		return procNames;
	}
	public void setProcNames(String procNames) {
		this.procNames = procNames;
	}
	public String getDefDate() {
		return defDate;
	}
	public void setDefDate(String defDate) {
		this.defDate = defDate;
	}
	public String getPlanParam() {
		return planParam;
	}
	public void setPlanParam(String planParam) {
		this.planParam = planParam;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public List<Product> getSuitProcList() {
		return suitProcList;
	}
	public void setSuitProcList(List<Product> suitProcList) {
		this.suitProcList = suitProcList;
	}
	public int getIsMark() {
		return isMark;
	}
	public void setIsMark(int isMark) {
		this.isMark = isMark;
	}
	public int getIsSign() {
		return isSign;
	}
	public void setIsSign(int isSign) {
		this.isSign = isSign;
	}
	public String getLastOptionListId() {
		return lastOptionListId;
	}
	public void setLastOptionListId(String lastOptionListId) {
		this.lastOptionListId = lastOptionListId;
	}
	
	
}
