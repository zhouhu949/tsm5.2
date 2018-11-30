package com.qftx.tsm.cust.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;
import java.util.List;

public class BlackListBean extends BaseObject{
	private static final long serialVersionUID = -1575323035138134084L;

	private String blackId;                 //客户操作ID
    private String orgId;              //机构编号
    private String userId;             //添加人id
    private String userName;           //用户名
    private String userAccount;        //添加人账户
    private Date inputDate;            //添加时间
    private String phone;              //联系电话
    private String type;  			   //黑名单类型： 0表示投诉客户类型，1表示企业黑名单,2：表示即限制呼入也限制呼出
	private int  is_Delete; 
    private List<String> userList; // 所有者集合
	private String userAccounts; //
	private List<String> blackIdList; //删除id集合
	private String blacks;
	private List<String> typeList;

	public List<String> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<String> typeList) {
		this.typeList = typeList;
	}

	public List<String> getBlackIdList() {
		return blackIdList;
	}
	public void setBlackIdList(List<String> blackIdList) {
		this.blackIdList = blackIdList;
	}
	public String getBlacks() {
		return blacks;
	}
	public void setBlacks(String blacks) {
		this.blacks = blacks;
	}
	public int getIs_Delete() {
		return is_Delete;
	}
	public void setIs_Delete(int is_Delete) {
		this.is_Delete = is_Delete;
	}
	public List<String> getUserList() {
		return userList;
	}
	public void setUserList(List<String> userList) {
		this.userList = userList;
	}
	public String getUserAccounts() {
		return userAccounts;
	}
	public void setUserAccounts(String userAccounts) {
		this.userAccounts = userAccounts;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setType(String type) {
		this.type = type;
	}
	private String defined1;           //
    private String defined2;           //
    private String defined3;           //
    private String defined4;		   //
    private String defined5;           //
	public String getBlackId() {
		return blackId;
	}
	public void setBlackId(String blackId) {
		this.blackId = blackId;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public Date getInputDate() {
		return inputDate;
	}
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getType() {
		return type;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
