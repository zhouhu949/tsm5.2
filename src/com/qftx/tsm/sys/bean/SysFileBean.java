package com.qftx.tsm.sys.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;

public class SysFileBean extends BaseObject {
	private static final long serialVersionUID = -5584863803920572454L;
	
	private String id;//id
	private String orgId;//机构ID
	private String userId;//用户ID
	private String code;//附件唯一码
	private String type;//附件类型(1邮件，2合同，3客户)
	private String fileType;//文件类型
	private String fileName;//文件名称
	private Integer fileSize;//文件大小(KB)
	private String filePath;//文件路径
	private String fileUrl;//文件路径
	private Date updatetime;//修改时间
	private Date inputtime;//录入时间
	private Integer isDel;//册除状态1-删除，0-未删除
	private Integer oss; // 上传方式是否是oss, 0：否，1：是(5.1新增)
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Integer getFileSize() {
		return fileSize;
	}
	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public Date getInputtime() {
		return inputtime;
	}
	public void setInputtime(Date inputtime) {
		this.inputtime = inputtime;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	public Integer getOss() {
		return oss;
	}
	public void setOss(Integer oss) {
		this.oss = oss;
	}
	
}
