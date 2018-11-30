package com.qftx.tsm.log.dto;

import com.qftx.base.util.DateUtil;
import com.qftx.tsm.log.bean.LogContactDayDataBean;

import java.util.List;


 /** 
 * 联系结果详情DTO
 * @author: lixing
 * @since: 2017年10月19日  下午1:34:05
 * @history:
 */
public class LogContactDayDataDto extends LogContactDayDataBean {

	private static final long serialVersionUID = 7905430416497128381L;
	
	private List<String> ownerAccs;//多帐号
	
	private Integer custType;//1-资源 2-意向
	
	private Integer changeType;//1-转意向、意向变更 2-转签约
	
	private String custName;//客户姓名
	
	private String company;//单位名称
	
	private String taoDate;//淘到客户时间 yyyy-MM-dd
	
	private String taoDateAll;//淘到客户时间 yyyy-MM-dd HH:mm:ss
	
	private String initSaleProcessName;//初始化销售进程
	
	private String saleProcessName;//销售进程
	
	private String custTypeName;//客户类型
	
	private String nextFollowDate;//下次联系时间yyyy-MM-dd
	
	private String nextFollowDateAll;//下次联系时间 yyyy-MM-dd HH:mm:ss
	
	private String ownerName;//所有者
	
	private String signDate;//签约时间

	private Integer showCard;//是否展示客户卡片按钮 0-否 1-是
	
	private String groupIdStr;//组织结构 小组今日数据查询
	
	private String dateStr;//日期个人历史数据
	
	public List<String> getOwnerAccs() {
		return ownerAccs;
	}

	public void setOwnerAccs(List<String> ownerAccs) {
		this.ownerAccs = ownerAccs;
	}

	public Integer getCustType() {
		return custType;
	}

	public void setCustType(Integer custType) {
		this.custType = custType;
	}

	public Integer getChangeType() {
		return changeType;
	}

	public void setChangeType(Integer changeType) {
		this.changeType = changeType;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getTaoDate() {
		return taoDate;
	}

	public void setTaoDate(String taoDate) {
		this.taoDate = taoDate;
	}

	public String getTaoDateAll() {
		return taoDateAll;
	}

	public void setTaoDateAll(String taoDateAll) {
		this.taoDateAll = taoDateAll;
	}

	public String getInitSaleProcessName() {
		return initSaleProcessName;
	}

	public void setInitSaleProcessName(String initSaleProcessName) {
		this.initSaleProcessName = initSaleProcessName;
	}

	public String getSaleProcessName() {
		return saleProcessName;
	}

	public void setSaleProcessName(String saleProcessName) {
		this.saleProcessName = saleProcessName;
	}

	public String getCustTypeName() {
		return custTypeName;
	}

	public void setCustTypeName(String custTypeName) {
		this.custTypeName = custTypeName;
	}

	public String getNextFollowDate() {
		return nextFollowDate;
	}

	public void setNextFollowDate(String nextFollowDate) {
		this.nextFollowDate = nextFollowDate;
	}

	public String getNextFollowDateAll() {
		return nextFollowDateAll;
	}

	public void setNextFollowDateAll(String nextFollowDateAll) {
		this.nextFollowDateAll = nextFollowDateAll;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getSignDate() {
		return signDate;
	}

	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}

	public Integer getShowCard() {
		return showCard;
	}

	public void setShowCard(Integer showCard) {
		this.showCard = showCard;
	}

	public String getGroupIdStr() {
		return groupIdStr;
	}

	public void setGroupIdStr(String groupIdStr) {
		this.groupIdStr = groupIdStr;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		if(super.getCurrDate()==null){
			super.setCurrDate(DateUtil.parseDate(dateStr));
		}
		
		this.dateStr = dateStr;
	}
}
