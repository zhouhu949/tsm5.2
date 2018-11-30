package com.qftx.export.bean;

import java.util.Date;
import java.util.List;

import com.qftx.common.domain.BaseObject;

public class LogExportInfoBean extends BaseObject {
	private static final long serialVersionUID = -3945795547773774244L;
	
	private String exportId;//导出ID

	private String orgId;//orgId

	private String account;//导出人

	private Integer exportCustType;//导出客户池 1-资源 2-意向 3-签约 4-沉默 5-流失

	private String exportSearchContext;//导出查询条件

	private Integer exportNum;//导出数

	private Date exportDate;//导出时间

	private String exportFilePath;//导出文件路径

	private Integer deletedFile;//是否删除导出文件 0-否 1-是

	/**列表查询*/
	private String userAccsStr;
	
	private List<String> userAccs;
	
	private String userName;
	
	public String getExportId() {
		return exportId;
	}

	public void setExportId(String exportId) {
		this.exportId = exportId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Integer getExportCustType() {
		return exportCustType;
	}

	public void setExportCustType(Integer exportCustType) {
		this.exportCustType = exportCustType;
	}

	public String getExportSearchContext() {
		return exportSearchContext;
	}

	public void setExportSearchContext(String exportSearchContext) {
		this.exportSearchContext = exportSearchContext;
	}

	public Integer getExportNum() {
		return exportNum;
	}

	public void setExportNum(Integer exportNum) {
		this.exportNum = exportNum;
	}

	public Date getExportDate() {
		return exportDate;
	}

	public void setExportDate(Date exportDate) {
		this.exportDate = exportDate;
	}

	public String getExportFilePath() {
		return exportFilePath;
	}

	public void setExportFilePath(String exportFilePath) {
		this.exportFilePath = exportFilePath;
	}

	public Integer getDeletedFile() {
		return deletedFile;
	}

	public void setDeletedFile(Integer deletedFile) {
		this.deletedFile = deletedFile;
	}

	public String getUserAccsStr() {
		return userAccsStr;
	}

	public void setUserAccsStr(String userAccsStr) {
		this.userAccsStr = userAccsStr;
	}

	public List<String> getUserAccs() {
		return userAccs;
	}

	public void setUserAccs(List<String> userAccs) {
		this.userAccs = userAccs;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
