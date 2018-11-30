package com.qftx.tsm.sys.dto;

import com.qftx.tsm.sys.bean.ServiceVisit;

import java.util.Date;
import java.util.List;


 /** 
 * 服务记录扩展查询类
 */
public class ServiceVisitDto extends ServiceVisit {
private static final long serialVersionUID = 3202756429970739481L;

private String optionName ;
private String custName;
private String company;
private String ownerAcc;
private String vstartDate;  //最近访问时间
private String vendDate;    //最近访问时间
private String nstartDate;  //下次访问时间
private String nendDate;    //下次访问时间
private String isToday;  // 1,查今日记录，null,查所有,7,一个星期
private List<String> shrAccs; // 共享组账号列表

//3.0
private String saleFollowStartDate;//销售最近跟进开始时间
private String saleFollowEndDate;//销售最近跟进结束时间
private Date saleFollowDate;//销售最近联系时间
private String linkPhone;//客户常用联系电话
private String [] lables;//标签
private List<String> ownerAccs;
private String accs; //用于查询 所有者账号 字符串,号分割

public List<String> getOwnerAccs() {
	return ownerAccs;
}

public void setOwnerAccs(List<String> ownerAccs) {
	this.ownerAccs = ownerAccs;
}

public String[] getLables() {
	return lables;
}

public void setLables(String[] lables) {
	this.lables = lables;
}

public String getLinkPhone() {
	return linkPhone;
}

public void setLinkPhone(String linkPhone) {
	this.linkPhone = linkPhone;
}

public Date getSaleFollowDate() {
	return saleFollowDate;
}

public void setSaleFollowDate(Date saleFollowDate) {
	this.saleFollowDate = saleFollowDate;
}

public String getSaleFollowStartDate() {
	return saleFollowStartDate;
}

public void setSaleFollowStartDate(String saleFollowStartDate) {
	this.saleFollowStartDate = saleFollowStartDate;
}

public String getSaleFollowEndDate() {
	return saleFollowEndDate;
}

public void setSaleFollowEndDate(String saleFollowEndDate) {
	this.saleFollowEndDate = saleFollowEndDate;
}

public String getOptionName() {
	return optionName;
}

public void setOptionName(String optionName) {
	this.optionName = optionName;
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

public String getOwnerAcc() {
	return ownerAcc;
}

public void setOwnerAcc(String ownerAcc) {
	this.ownerAcc = ownerAcc;
}

public String getVstartDate() {
	return vstartDate;
}

public void setVstartDate(String vstartDate) {
	this.vstartDate = vstartDate;
}

public String getVendDate() {
	return vendDate;
}

public void setVendDate(String vendDate) {
	this.vendDate = vendDate;
}

public String getNstartDate() {
	return nstartDate;
}

public void setNstartDate(String nstartDate) {
	this.nstartDate = nstartDate;
}

public String getNendDate() {
	return nendDate;
}
public void setNendDate(String nendDate) {
	this.nendDate = nendDate;
}
public String getIsToday() {
	return isToday;
}
public void setIsToday(String isToday) {
	this.isToday = isToday;
}
public List<String> getShrAccs() {
	return shrAccs;
}
public void setShrAccs(List<String> shrAccs) {
	this.shrAccs = shrAccs;
}
public String getAccs() {
	return accs;
}
public void setAccs(String accs) {
	this.accs = accs;
}

}
