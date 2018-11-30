package com.qftx.tsm.credit.dto;

import java.util.List;
import java.util.Set;
import com.qftx.tsm.credit.bean.CreditLeadInfoBean;
import com.qftx.tsm.credit.bean.TsmLeadReviewInfo;


public class TsmLoanReviewInfoDto extends CreditLeadInfoBean implements Cloneable {

	private static final long serialVersionUID = -2904301692720746024L;

	public static final String OS_TYPE_1 = "1"; //
	public static final String OS_TYPE_2 = "2"; //

	public static final Integer STATUS_LOAN_DOING = 1; // 待确认
	public static final Integer STATUS_LOAN_DONE = 2; // 已确认
	public static final Integer STATUS_LOAN_REJECT = 3; // 驳回
	
	public static final Integer REVIEW_LOAN_DOING = 1; // 待确认
	public static final Integer REVIEW_LOAN_DONE = 2; // 已确认
	public static final Integer REVIEW_LOAN_REJECT = 3; // 已确认

	
	
	private String noteType;//查询标签
	
	private String adminAcc;// 管理者帐号 用于管理者登陆时查询列表
	private List<String> ownerAccs;
	private String ownerAccsStr;
	
	private Integer isPage;  //1 分页  2 不分页

	// 文本字段查询条件
	private String queryType = "custName";// 查询类型 1.客户名称-- custName，2.身份证号--cardId  3，放款编号---loanNo
	private String queryText;// 接收文本查询内容

	private String osType;// 所有者查询方式 1-全部 2-只看自己 3-选中查询
	private Integer state;// 0-个人资源，1-企业资源，企业的多些字段(4.0新加，其余是3.1版相同)

	// 借款日查询条件
	private Integer loanDateType;
	private String loanStartDate; // 借款日 开始
	private String loanEndDate; // 借款日 结束

	// 到期日查询条件
	private Integer dueDateType;
	private String dueStartDate; // 到期日 开始
	private String dueEndDate; // 到期日 结束

	// 提交时间查询条件
	private Integer submitDateType;
	private String submitStartDate; // 提交时间 开始
	private String submitEndDate; // 提交时间 结束
	
	// 提交时间查询条件
	private Integer auditDateType;
	private String auditStartDate; // 提交时间 开始
	private String auditEndDate; // 提交时间 结束

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

	private Set<String> custIds; //多选id集合
	private Set<String> leadIds;
	
	private Integer result; //2通过 3驳回
	private String remark; //备注
	private String exportColumnStr; //导出
	private String auditStatusName;
	private String reviewAuth;
	private List<TsmLeadReviewInfo> reviewList;
	
	private String auditName; //预审核人名字
	private String inchargeName; //负责人名字
	private String ownerName; //创建人
	private Integer auditType;
	
	public Integer getAuditDateType() {
		return auditDateType;
	}

	public void setAuditDateType(Integer auditDateType) {
		this.auditDateType = auditDateType;
	}

	public String getAuditStartDate() {
		return auditStartDate;
	}

	public void setAuditStartDate(String auditStartDate) {
		this.auditStartDate = auditStartDate;
	}

	public String getAuditEndDate() {
		return auditEndDate;
	}

	public void setAuditEndDate(String auditEndDate) {
		this.auditEndDate = auditEndDate;
	}

	public Set<String> getLeadIds() {
		return leadIds;
	}

	public void setLeadIds(Set<String> leadIds) {
		this.leadIds = leadIds;
	}

	public Integer getAuditType() {
		return auditType;
	}

	public void setAuditType(Integer auditType) {
		this.auditType = auditType;
	}

	public String getAuditName() {
		return auditName;
	}

	public void setAuditName(String auditName) {
		this.auditName = auditName;
	}

	public String getInchargeName() {
		return inchargeName;
	}

	public void setInchargeName(String inchargeName) {
		this.inchargeName = inchargeName;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public List<TsmLeadReviewInfo> getReviewList() {
		return reviewList;
	}

	public void setReviewList(List<TsmLeadReviewInfo> reviewList) {
		this.reviewList = reviewList;
	}

	public String getReviewAuth() {
		return reviewAuth;
	}

	public void setReviewAuth(String reviewAuth) {
		this.reviewAuth = reviewAuth;
	}

	public String getAuditStatusName() {
		return auditStatusName;
	}

	public void setAuditStatusName(String auditStatusName) {
		this.auditStatusName = auditStatusName;
	}

	public TsmLoanReviewInfoDto(){
	}
	
	public String getExportColumnStr() {
		return exportColumnStr;
	}

	public void setExportColumnStr(String exportColumnStr) {
		this.exportColumnStr = exportColumnStr;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public Integer getIsPage() {
		return isPage;
	}

	public void setIsPage(Integer isPage) {
		this.isPage = isPage;
	}

	public Integer getResult() {
		return result;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Set<String> getCustIds() {
		return custIds;
	}

	public void setCustIds(Set<String> custIds) {
		this.custIds = custIds;
	}

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

	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}

	public Integer getLoanDateType() {
		return loanDateType;
	}

	public void setLoanDateType(Integer loanDateType) {
		this.loanDateType = loanDateType;
	}

	public String getLoanStartDate() {
		return loanStartDate;
	}

	public void setLoanStartDate(String loanStartDate) {
		this.loanStartDate = loanStartDate;
	}

	public String getLoanEndDate() {
		return loanEndDate;
	}

	public void setLoanEndDate(String loanEndDate) {
		this.loanEndDate = loanEndDate;
	}

	public Integer getDueDateType() {
		return dueDateType;
	}

	public void setDueDateType(Integer dueDateType) {
		this.dueDateType = dueDateType;
	}

	public String getDueStartDate() {
		return dueStartDate;
	}

	public void setDueStartDate(String dueStartDate) {
		this.dueStartDate = dueStartDate;
	}

	public String getDueEndDate() {
		return dueEndDate;
	}

	public void setDueEndDate(String dueEndDate) {
		this.dueEndDate = dueEndDate;
	}

	public Integer getSubmitDateType() {
		return submitDateType;
	}

	public void setSubmitDateType(Integer submitDateType) {
		this.submitDateType = submitDateType;
	}

	public String getSubmitStartDate() {
		return submitStartDate;
	}

	public void setSubmitStartDate(String submitStartDate) {
		this.submitStartDate = submitStartDate;
	}

	public String getSubmitEndDate() {
		return submitEndDate;
	}

	public void setSubmitEndDate(String submitEndDate) {
		this.submitEndDate = submitEndDate;
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

	public String getNoteType() {
		return noteType;
	}

	public void setNoteType(String noteType) {
		this.noteType = noteType;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}
