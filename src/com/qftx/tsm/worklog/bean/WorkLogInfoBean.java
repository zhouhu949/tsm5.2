package com.qftx.tsm.worklog.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qftx.base.util.DateUtil;
import com.qftx.common.domain.BaseObject;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.constants.AppConstant;

import java.util.Date;
//工作日志_记录表
public class WorkLogInfoBean extends BaseObject{
	private static final long serialVersionUID = -3978807674688505560L;
	private String wliId;//ID
	private String orgId;//机构ID
	private String groupId;	//部门ID
	private String userId;//用户ID
	private Date logDate;//日志时间
	private String context;	//今日工作总结
	private String workPlan;	//明日工作计划
	private String devType;	//设备类型
	private String imgs;	//图片，多个用,隔开
	private String files;	//附件相关,多个附件用#隔开（类别_文件地址_文件大小:type_filepath_size）,type: 0.图片文件1.视频文件2.音频文件
	private Integer filesSize;	//附件总大小(废弃)
	private String record;	//录音
	private Integer recordTime;	//录音时长
	private Date inputdate;	//录入时间
	private Date updatedate;	//更新时间
	private Integer status;	//状态(0，保存，1提交)
	private Integer isDel;	//册除状态1-删除，0-未删除
	private Integer commentNum;	//评论条数
	private Integer shareNum;	//分享数
	private Integer favourNum;	//点赞数

	private boolean delable;//是否可以删除
	private boolean favour;//是否已经点赞
	
	private String formatDay;
	private String formatMonth;
	
	private String userAccount;
	private String userName;
	private String imgUrl;
	private String imgUrl_;
	private Date fromDate;
	private Date toDate;
	private Long inputdateTime;
	private Long logDateTime;
	private Boolean isAll;
	private String symbol;//查询 大于小于符号        <&lt;   >&gt;         
	private String[] groupIds;
	private String[] userIds;
	private String groupIdsStr;
	private String userIdsStr;
	private String inputDateStr;
	private boolean attention;//是否关注
	private String shareUserId;//分享用户ID
	private String queryText;//查询文本
	private String rejectUserId;//剔除自己的日志
	
	private String[] months={"一","二","三","四","五","六","七","八","九","十","十一","十二"};
	public String getWliId() {
		return wliId;
	}
	public void setWliId(String wliId) {
		this.wliId = wliId;
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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getLogDate() {
		return logDate;
	}
	public void setLogDate(Date logDate) {
		this.logDate = logDate;
		this.formatDay = String.valueOf(DateUtil.day(logDate));
		this.formatMonth = String.valueOf(months[DateUtil.month(logDate)-1]+"月");
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getWorkPlan() {
		return workPlan==null?"":workPlan;
	}
	public void setWorkPlan(String workPlan) {
		this.workPlan = workPlan;
	}
	public String getDevType() {
		if(devType==null){
			devType ="PC客户端";
		}
		return devType;
	}
	public void setDevType(String devType) {
		this.devType = devType;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getInputdate() {
		return inputdate;
	}
	public void setInputdate(Date inputdate) {
		this.inputdate = inputdate;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	public Integer getCommentNum() {
		return commentNum;
	}
	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}
	public Integer getShareNum() {
		return shareNum;
	}
	public void setShareNum(Integer shareNum) {
		this.shareNum = shareNum;
	}
	public Integer getFavourNum() {
		return favourNum;
	}
	public void setFavourNum(Integer favourNum) {
		this.favourNum = favourNum;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFormatDay() {
		return formatDay;
	}
	public void setFormatDay(String formatDay) {
		this.formatDay = formatDay;
	}
	public String getFormatMonth() {
		return formatMonth;
	}
	public void setFormatMonth(String formatMonth) {
		this.formatMonth = formatMonth;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public String[] getMonths() {
		return months;
	}
	public void setMonths(String[] months) {
		this.months = months;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public Long getInputdateTime() {
		return inputdate==null?null:inputdate.getTime();
	}
	public void setInputdateTime(Long inputdateTime) {
		this.inputdateTime = inputdateTime;
		this.inputdate=new Date(inputdateTime);
	}
	public Long getLogDateTime() {
		if(logDateTime==null){
			logDateTime = logDate==null?null:logDate.getTime();
		}
		return logDateTime;
	}
	public void setLogDateTime(Long logDateTime) {
		this.logDateTime = logDateTime;
		if(logDateTime!=null)this.logDate= new Date(logDateTime);
	}
	public String[] getGroupIds() {
		return groupIds;
	}
	public void setGroupIds(String[] groupIds) {
		this.groupIds = groupIds;
	}
	public String[] getUserIds() {
		return userIds;
	}
	public void setUserIds(String[] userIds) {
		this.userIds = userIds;
		
	}
	public Boolean getIsAll() {
		return isAll;
	}
	public void setIsAll(Boolean isAll) {
		this.isAll = isAll;
	}
	public String getGroupIdsStr() {
		return getStrFromArray(groupIds);
	}
	public void setGroupIdsStr(String groupIdsStr) {
		if(!StringUtils.isBlank(groupIdsStr)){
			groupIds=groupIdsStr.split(",");
		}
		this.groupIdsStr = groupIdsStr;
	}
	public String getUserIdsStr() {
		return getStrFromArray(userIds);
	}
	public void setUserIdsStr(String userIdsStr) {
		if(!StringUtils.isBlank(userIdsStr)){
			userIds=userIdsStr.split(",");
		}
		this.userIdsStr = userIdsStr;
	}
	
	public String getStrFromArray(String [] array){
		if(array==null || array.length==0) return null;
		
		StringBuilder sb=new StringBuilder();
		for (String id : array) {
			if(sb.length()!=0) sb.append(",");
			sb.append(id);
		}
		return sb.toString();
	}
	public String getInputDateStr() {
		if(inputdate!=null){
			return DateUtil.formatDate(inputdate, "MM月dd日  HH:mm:ss");
		}else{
			return null;
		}
	}
	public void setInputDateStr(String inputDateStr) {
		this.inputDateStr = inputDateStr;
	}
	public boolean isAttention() {
		return attention;
	}
	public void setAttention(boolean attention) {
		this.attention = attention;
	}
	public String getQueryText() {
		return queryText;
	}
	public void setQueryText(String queryText) {
		this.queryText = queryText;
	}
	public String getShareUserId() {
		return shareUserId;
	}
	public void setShareUserId(String shareUserId) {
		this.shareUserId = shareUserId;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getImgUrl_() {
		if(StringUtils.isBlank(imgUrl)){
			imgUrl_  = AppConstant.DEFAULT_IMG_URL;
		}else {
			imgUrl_ =imgUrl;
		}
		return imgUrl_;
	}
	public void setImgUrl_(String imgUrl_) {
		this.imgUrl_ = imgUrl_;
	}
	public boolean isDelable() {
		return delable;
	}
	public void setDelable(boolean delable) {
		this.delable = delable;
	}
	public boolean isFavour() {
		return favour;
	}
	public void setFavour(boolean favour) {
		this.favour = favour;
	}
public String getImgs() {
		return imgs;
	}
	public void setImgs(String imgs) {
		this.imgs = imgs;
	}
	public String getFiles() {
		return files;
	}
	public void setFiles(String files) {
		this.files = files;
	}
	public String getRecord() {
		return record;
	}
	public void setRecord(String record) {
		this.record = record;
	}
	public Integer getFilesSize() {
		return filesSize;
	}
	public void setFilesSize(Integer filesSize) {
		this.filesSize = filesSize;
	}
	public Integer getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(Integer recordTime) {
		this.recordTime = recordTime;
	}
	public String getRejectUserId() {
		return rejectUserId;
	}
	public void setRejectUserId(String rejectUserId) {
		this.rejectUserId = rejectUserId;
	}
	
}
