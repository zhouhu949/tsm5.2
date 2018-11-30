package com.qftx.tsm.imp.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qftx.common.domain.BaseObject;

import java.util.Date;
/* import_res_file 资源导入_文件*/
public class ImportResFileBean extends BaseObject {

	private static final long serialVersionUID = 7501063514995727617L;
	
	private String id;	//导入批次ID
	private String orgId;	//机构ID
	private String groupId;	//部门
	private String userId;	//用户id
	private String fileId;	//文件id
	private String resGroupId;	//资源分组ID
	private Integer status;	//状态0未分析，1已分析
	private Integer rowcount;	//行数
	private String header;	//表头
	private String mapper;	//字段映射
	private String context;	//说明
	private Date startTime;	//分析开始时间
	private Date endTime;	//分析结束时间
	private Date updatetime;	//修改时间
	private Date inputtime;	//录入时间
	private Integer isDel;	//册除状态1-删除，0-未删除
	private Integer state;	//分析状态（0未开始，1开始，2结束)
	private Integer resStatus;	//导入类型：1： 未分配   2：已分配且未跟进
	private String mark; // 1：资源库，2：意向库，3：签约库
	private String processId; // 销售进程ID
	
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
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getResGroupId() {
		return resGroupId;
	}
	public void setResGroupId(String resGroupId) {
		this.resGroupId = resGroupId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getRowcount() {
		return rowcount;
	}
	public void setRowcount(Integer rowcount) {
		this.rowcount = rowcount;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getMapper() {
		return mapper;
	}
	public void setMapper(String mapper) {
		this.mapper = mapper;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
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
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getResStatus() {
		return resStatus;
	}
	public void setResStatus(Integer resStatus) {
		this.resStatus = resStatus;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	
}
