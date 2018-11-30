package com.qftx.tsm.follow.dto;

import com.qftx.tsm.cust.bean.ResourceGroupBean;
import com.qftx.tsm.cust.bean.TsmCustGuide;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.sys.bean.Product;
import com.qftx.tsm.tao.dto.OptionDto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/** 跟进详情 前端所需数据 【第一次进入页面时所带】*/
public class CustFollowShowDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String startPage; // 启始页
	private Integer state; // 0:个人客户，1：企业客户
	private String isAlarm; // 如果有值 表示从跟进警报 触发
	private String custType; // 1:全部，2:意向客户，3:签约客户，4:流失客户，5:沉默客户
	private String custCation; // 跟进数据分类
	private String custId; // 资源Id
	private Date actionDate; // 联系时间
	private String followId; // 跟进Id
	private String planParam; // 1：只显示计划中客户，2：不显示计划中客户
	private Integer status; // 资源客户状态：1-未分配，2-未跟进，3-跟进中，4-公海客户，5-资源回收站，6-已签约，7-沉默客户，8-流失客户
	private CustFollowDto lastCustFollow; // 上一次跟进信息
	private List<OptionBean> saleProcessList; // 销售进程列表
	private List<OptionBean> custTypeList; // 客户类型列表
	private List<OptionDto> labelList; // 页面行动标签
	private List<ResourceGroupBean> resGroupList; // 资源分组
	private TsmCustGuide tsmCustGuideEntity; // 销售导航信息
	private List<Product> suitProcList; // 适用产品列表
	private String procIds; // 默认展示产品IDs
	private String procNames; // 默认的展示产品名称S
	private String defDate; // 下次联系时间
	private Map<String, Object> labelMap;
	
	public String getStartPage() {
		return startPage;
	}
	public void setStartPage(String startPage) {
		this.startPage = startPage;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getIsAlarm() {
		return isAlarm;
	}
	public void setIsAlarm(String isAlarm) {
		this.isAlarm = isAlarm;
	}
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public String getCustCation() {
		return custCation;
	}
	public void setCustCation(String custCation) {
		this.custCation = custCation;
	}
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
	public CustFollowDto getLastCustFollow() {
		return lastCustFollow;
	}
	public void setLastCustFollow(CustFollowDto lastCustFollow) {
		this.lastCustFollow = lastCustFollow;
	}
	public List<OptionBean> getSaleProcessList() {
		return saleProcessList;
	}
	public void setSaleProcessList(List<OptionBean> saleProcessList) {
		this.saleProcessList = saleProcessList;
	}
	public List<OptionBean> getCustTypeList() {
		return custTypeList;
	}
	public void setCustTypeList(List<OptionBean> custTypeList) {
		this.custTypeList = custTypeList;
	}


	public List<OptionDto> getLabelList() {
		return labelList;
	}
	public void setLabelList(List<OptionDto> labelList) {
		this.labelList = labelList;
	}
	public TsmCustGuide getTsmCustGuideEntity() {
		return tsmCustGuideEntity;
	}
	public void setTsmCustGuideEntity(TsmCustGuide tsmCustGuideEntity) {
		this.tsmCustGuideEntity = tsmCustGuideEntity;
	}
	public List<Product> getSuitProcList() {
		return suitProcList;
	}
	public void setSuitProcList(List<Product> suitProcList) {
		this.suitProcList = suitProcList;
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
	public List<ResourceGroupBean> getResGroupList() {
		return resGroupList;
	}
	public void setResGroupList(List<ResourceGroupBean> resGroupList) {
		this.resGroupList = resGroupList;
	}
	public Map<String, Object> getLabelMap() {
		return labelMap;
	}
	public void setLabelMap(Map<String, Object> labelMap) {
		this.labelMap = labelMap;
	}
	
	
	
}
