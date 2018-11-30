package com.qftx.tsm.credit.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.qftx.common.domain.BaseObject;

/**
 * 放款信息表(tsm_lead_info) 实体类
 * 
 * @author: chenhm
 * @since: 2018年07月12日 上午9:23:43
 * @history: 4.0
 */
public class CreditLeadInfoBean extends BaseObject {
	private static final long serialVersionUID = -1227249200327056217L;
	
	// 放款状态 1待放款
	public static final int STATUS_DOING = 1;
	// 放款状态 2已放款
	public static final int STATUS_DONE = 2;
	// 放款状态 3驳回
	public static final int STATUS_REJECT = 3;
	
	// 审核状态 1待确认
	public static final int AUDIT_STATUS_DOING = 1;
	// 审核状态 2已确认
	public static final int AUDIT_STATUS_DONE = 2;
	// 审核状态 3驳回
	public static final int AUDIT_STATUS_REJECT = 3;
	
	// 存放类型字段对应关系
	public static final Map<String, Map<Integer, String>> maps = new HashMap<String, Map<Integer, String>>();
	static {
		Map<Integer, String> status_map = new HashMap<Integer, String>(3);
		status_map.put(STATUS_DOING, "待放款");
		status_map.put(STATUS_DONE, "已放款");
		status_map.put(STATUS_REJECT, "驳回");
		maps.put("status", status_map);
	}
	
	// 存放类型字段对应关系
	public static final Map<String, Map<Integer, String>> auditMaps = new HashMap<String, Map<Integer, String>>();
	static {
		Map<Integer, String> status_map = new HashMap<Integer, String>(3);
		status_map.put(AUDIT_STATUS_DOING, "待确认");
		status_map.put(AUDIT_STATUS_DONE, "已确认");
		status_map.put(AUDIT_STATUS_REJECT, "驳回");
		auditMaps.put("auditStatus", status_map);
	}
	
	// 引用字段关键词
	public static final String FIELDNAME_KEY_CARDID = "身份证";
	public static final String FIELDNAME_KEY_BANKCARD = "银行卡";
	public static final String FIELDNAME_KEY_OPENINGBANK = "开户行";

	private String leadId;// 放款ID
	private String leadCode;// 放款编号
	private String custName;// 客户名称（客户姓名）
	private String company;	//单位名称(个人客户才有)
	private String rciId;	//资源ID
	private String orgId; // 机构ID
	private String importDeptId;//导入部门ID
	private String cardId;// 身份证号
	private String phone;// 联系电话
	private BigDecimal borrowMoney;// 借款金额
	private BigDecimal accountMoney;// 用户到账金额
	private BigDecimal serviceMoney;// 综合服务费
	private BigDecimal billMoney;// 借据金额
	private Date borrowDate;// 借款日
	private Date repayDate;// 到期日
	private String serviceMoney2;	//服务费2
	private String serviceMoney3;	//服务费3
	private String bankCard;// 银行卡号
	private String lender;// 出借人
	private String openingBank;// 开户行
	private String loanBill;// 借款凭证
	private String ownerAcc;// 创建人
	private Date createDate;// 创建时间
	private String importDeptName;// 团队名称, 导入部门名称
	private String fundAccount;// 打款账户
	private String inchargeAcc;// 负责人
	private Integer status;// 放款状态(待放款、已放款、驳回)
	private Integer batch;	// 放贷批次
	
	private Integer auditStatus;// 审核状态(待确认、已确认、驳回。暂时传参用，库里无具体值)
	private Date submitTime;// 提交时间
	private Date auditTime;// 审核时间
	private String auditAcc; //审核人
	private Integer auditNode;
	private Integer allNode;
	private Integer isDel;	//是否删除：0-未删除，1-删除
	
	private String defined1; // 自定义1
	private String defined2; // 自定义2
	private String defined3; // 自定义3
	private String defined4; // 自定义4
	private String defined5; // 自定义5
	private String defined6; // 自定义6
	private String defined7; // 自定义7
	private String defined8; // 自定义8
	private String defined9; // 自定义9
	private String defined10; // 自定义10
	private String defined11; // 自定义11
	private String defined12; // 自定义12
	private String defined13; // 自定义13
	private String defined14; // 自定义14
	private String defined15; // 自定义15
	
	private Date defined16;// 自定义16
	private Date defined17;// 自定义17
	private Date defined18;// 自定义18
	
	public String getAuditAcc() {
		return auditAcc;
	}
	public void setAuditAcc(String auditAcc) {
		this.auditAcc = auditAcc;
	}
	public Integer getAuditNode() {
		return auditNode;
	}
	public void setAuditNode(Integer auditNode) {
		this.auditNode = auditNode;
	}
	public Integer getAllNode() {
		return allNode;
	}
	public void setAllNode(Integer allNode) {
		this.allNode = allNode;
	}
	public String getLeadId() {
		return leadId;
	}
	public void setLeadId(String leadId) {
		this.leadId = leadId;
	}
	public String getLeadCode() {
		return leadCode;
	}
	public void setLeadCode(String leadCode) {
		this.leadCode = leadCode;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getRciId() {
		return rciId;
	}
	public void setRciId(String rciId) {
		this.rciId = rciId;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getImportDeptId() {
		return importDeptId;
	}
	public void setImportDeptId(String importDeptId) {
		this.importDeptId = importDeptId;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public BigDecimal getBorrowMoney() {
		return borrowMoney;
	}
	public void setBorrowMoney(BigDecimal borrowMoney) {
		this.borrowMoney = borrowMoney;
	}
	public BigDecimal getAccountMoney() {
		return accountMoney;
	}
	public void setAccountMoney(BigDecimal accountMoney) {
		this.accountMoney = accountMoney;
	}
	public BigDecimal getServiceMoney() {
		return serviceMoney;
	}
	public void setServiceMoney(BigDecimal serviceMoney) {
		this.serviceMoney = serviceMoney;
	}
	public BigDecimal getBillMoney() {
		return billMoney;
	}
	public void setBillMoney(BigDecimal billMoney) {
		this.billMoney = billMoney;
	}
	public Date getBorrowDate() {
		return borrowDate;
	}
	public void setBorrowDate(Date borrowDate) {
		this.borrowDate = borrowDate;
	}
	public Date getRepayDate() {
		return repayDate;
	}
	public void setRepayDate(Date repayDate) {
		this.repayDate = repayDate;
	}
	public String getBankCard() {
		return bankCard;
	}
	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}
	public String getLender() {
		return lender;
	}
	public void setLender(String lender) {
		this.lender = lender;
	}
	public String getOpeningBank() {
		return openingBank;
	}
	public void setOpeningBank(String openingBank) {
		this.openingBank = openingBank;
	}
	public String getLoanBill() {
		return loanBill;
	}
	public void setLoanBill(String loanBill) {
		this.loanBill = loanBill;
	}
	public String getOwnerAcc() {
		return ownerAcc;
	}
	public void setOwnerAcc(String ownerAcc) {
		this.ownerAcc = ownerAcc;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getImportDeptName() {
		return importDeptName;
	}
	public void setImportDeptName(String importDeptName) {
		this.importDeptName = importDeptName;
	}
	public String getFundAccount() {
		return fundAccount;
	}
	public void setFundAccount(String fundAccount) {
		this.fundAccount = fundAccount;
	}
	public String getInchargeAcc() {
		return inchargeAcc;
	}
	public void setInchargeAcc(String inchargeAcc) {
		this.inchargeAcc = inchargeAcc;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}
	public Date getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}
	public Date getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	public String getDefined1() {
		return defined1;
	}
	public void setDefined1(String defined1) {
		this.defined1 = defined1;
	}
	public String getDefined2() {
		return defined2;
	}
	public void setDefined2(String defined2) {
		this.defined2 = defined2;
	}
	public String getDefined3() {
		return defined3;
	}
	public void setDefined3(String defined3) {
		this.defined3 = defined3;
	}
	public String getDefined4() {
		return defined4;
	}
	public void setDefined4(String defined4) {
		this.defined4 = defined4;
	}
	public String getDefined5() {
		return defined5;
	}
	public void setDefined5(String defined5) {
		this.defined5 = defined5;
	}
	public String getDefined6() {
		return defined6;
	}
	public void setDefined6(String defined6) {
		this.defined6 = defined6;
	}
	public String getDefined7() {
		return defined7;
	}
	public void setDefined7(String defined7) {
		this.defined7 = defined7;
	}
	public String getDefined8() {
		return defined8;
	}
	public void setDefined8(String defined8) {
		this.defined8 = defined8;
	}
	public String getDefined9() {
		return defined9;
	}
	public void setDefined9(String defined9) {
		this.defined9 = defined9;
	}
	public String getDefined10() {
		return defined10;
	}
	public void setDefined10(String defined10) {
		this.defined10 = defined10;
	}
	public String getDefined11() {
		return defined11;
	}
	public void setDefined11(String defined11) {
		this.defined11 = defined11;
	}
	public String getDefined12() {
		return defined12;
	}
	public void setDefined12(String defined12) {
		this.defined12 = defined12;
	}
	public String getDefined13() {
		return defined13;
	}
	public void setDefined13(String defined13) {
		this.defined13 = defined13;
	}
	public String getDefined14() {
		return defined14;
	}
	public void setDefined14(String defined14) {
		this.defined14 = defined14;
	}
	public String getDefined15() {
		return defined15;
	}
	public void setDefined15(String defined15) {
		this.defined15 = defined15;
	}
	public Date getDefined16() {
		return defined16;
	}
	public void setDefined16(Date defined16) {
		this.defined16 = defined16;
	}
	public Date getDefined17() {
		return defined17;
	}
	public void setDefined17(Date defined17) {
		this.defined17 = defined17;
	}
	public Date getDefined18() {
		return defined18;
	}
	public void setDefined18(Date defined18) {
		this.defined18 = defined18;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	public Integer getBatch() {
		return batch;
	}
	public void setBatch(Integer batch) {
		this.batch = batch;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getServiceMoney2() {
		return serviceMoney2;
	}
	public void setServiceMoney2(String serviceMoney2) {
		this.serviceMoney2 = serviceMoney2;
	}
	public String getServiceMoney3() {
		return serviceMoney3;
	}
	public void setServiceMoney3(String serviceMoney3) {
		this.serviceMoney3 = serviceMoney3;
	}
	
}
