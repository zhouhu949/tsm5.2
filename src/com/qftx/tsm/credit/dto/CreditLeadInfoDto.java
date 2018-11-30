package com.qftx.tsm.credit.dto;

import java.util.List;

import com.qftx.tsm.credit.bean.CreditLeadInfoBean;

/**
 * 放款信息表(tsm_lead_info)
 * 
 * @author chenhm
 *
 */
public class CreditLeadInfoDto extends CreditLeadInfoBean implements Cloneable {
	private static final long serialVersionUID = -2904301692720746024L;
	
	public static final String OS_TYPE_1 = "1";	//
	public static final String OS_TYPE_2 = "2";	//
	
	private String adminAcc;//管理者帐号 用于管理者登陆时查询列表
	
	// 创建人查询条件
	private List<String> ownerAccs;
	private String ownerAccsStr;
	private String ownerType;//所有者查询方式 1-全部 2-只看自己 3-选中查询
	
	// 负责人查询条件
	private List<String> inchargeAccs;
	private String inchargeAccsStr;
	private String inchargeType;//所有者查询方式 1-全部 2-只看自己 3-选中查询
	
	// 按主键进行查询
	private List<String> leadIds;
	
	private List<String> labels;
	
	// 文本字段查询条件
	private String queryType = "custName";//查询类型 对应tsm_cust_search_set表中search_code字段值
	private String queryText;//接收文本查询内容

	
	
	// 借款日查询条件
	private Integer bDateType;
	private String bstartDate;	//借款日 开始
	private String bendDate;	//借款日 结束
	
	// 到期日查询条件
	private Integer rDateType;
	private String rstartDate;	//到期日 开始
	private String rendDate;	//到期日 结束
	
	
	// 显示列表
	private String ownerName;		//创建人名称
	private String inchargeName;	//负责人名称
	
	// 自定义日期字段显示
	private String showdefined16;
	private String showdefined17;
	private String showdefined18;
	
	private String startdefined16;
	private String enddefined16;
	private String startdefined17;
	private String enddefined17;
	private String startdefined18;
	private String enddefined18;
	
	
	public String getAdminAcc() {
		return adminAcc;
	}
	public void setAdminAcc(String adminAcc) {
		this.adminAcc = adminAcc;
	}
	public List<String> getOwnerAccs() {
		return ownerAccs;
	}
	public void setOwnerAccs(List<String> ownerAccs) {
		this.ownerAccs = ownerAccs;
	}
	public String getOwnerAccsStr() {
		return ownerAccsStr;
	}
	public void setOwnerAccsStr(String ownerAccsStr) {
		this.ownerAccsStr = ownerAccsStr;
	}
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	public String getQueryText() {
		return queryText;
	}
	public void setQueryText(String queryText) {
		this.queryText = queryText;
	}
	public Integer getbDateType() {
		return bDateType;
	}
	public void setbDateType(Integer bDateType) {
		this.bDateType = bDateType;
	}
	public String getBstartDate() {
		return bstartDate;
	}
	public void setBstartDate(String bstartDate) {
		this.bstartDate = bstartDate;
	}
	public String getBendDate() {
		return bendDate;
	}
	public void setBendDate(String bendDate) {
		this.bendDate = bendDate;
	}
	public Integer getrDateType() {
		return rDateType;
	}
	public void setrDateType(Integer rDateType) {
		this.rDateType = rDateType;
	}
	public String getRstartDate() {
		return rstartDate;
	}
	public void setRstartDate(String rstartDate) {
		this.rstartDate = rstartDate;
	}
	public String getRendDate() {
		return rendDate;
	}
	public void setRendDate(String rendDate) {
		this.rendDate = rendDate;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getInchargeName() {
		return inchargeName;
	}
	public void setInchargeName(String inchargeName) {
		this.inchargeName = inchargeName;
	}
	public String getShowdefined16() {
		return showdefined16;
	}
	public void setShowdefined16(String showdefined16) {
		this.showdefined16 = showdefined16;
	}
	public String getShowdefined17() {
		return showdefined17;
	}
	public void setShowdefined17(String showdefined17) {
		this.showdefined17 = showdefined17;
	}
	public String getShowdefined18() {
		return showdefined18;
	}
	public void setShowdefined18(String showdefined18) {
		this.showdefined18 = showdefined18;
	}
	public String getStartdefined16() {
		return startdefined16;
	}
	public void setStartdefined16(String startdefined16) {
		this.startdefined16 = startdefined16;
	}
	public String getEnddefined16() {
		return enddefined16;
	}
	public void setEnddefined16(String enddefined16) {
		this.enddefined16 = enddefined16;
	}
	public String getStartdefined17() {
		return startdefined17;
	}
	public void setStartdefined17(String startdefined17) {
		this.startdefined17 = startdefined17;
	}
	public String getEnddefined17() {
		return enddefined17;
	}
	public void setEnddefined17(String enddefined17) {
		this.enddefined17 = enddefined17;
	}
	public String getStartdefined18() {
		return startdefined18;
	}
	public void setStartdefined18(String startdefined18) {
		this.startdefined18 = startdefined18;
	}
	public String getEnddefined18() {
		return enddefined18;
	}
	public void setEnddefined18(String enddefined18) {
		this.enddefined18 = enddefined18;
	}
	public List<String> getInchargeAccs() {
		return inchargeAccs;
	}
	public void setInchargeAccs(List<String> inchargeAccs) {
		this.inchargeAccs = inchargeAccs;
	}
	public String getInchargeAccsStr() {
		return inchargeAccsStr;
	}
	public void setInchargeAccsStr(String inchargeAccsStr) {
		this.inchargeAccsStr = inchargeAccsStr;
	}
	public List<String> getLeadIds() {
		return leadIds;
	}
	public void setLeadIds(List<String> leadIds) {
		this.leadIds = leadIds;
	}
	public List<String> getLabels() {
		return labels;
	}
	public void setLabels(List<String> labels) {
		this.labels = labels;
	}
	public String getOwnerType() {
		return ownerType;
	}
	public void setOwnerType(String ownerType) {
		this.ownerType = ownerType;
	}
	public String getInchargeType() {
		return inchargeType;
	}
	public void setInchargeType(String inchargeType) {
		this.inchargeType = inchargeType;
	}
	
}
