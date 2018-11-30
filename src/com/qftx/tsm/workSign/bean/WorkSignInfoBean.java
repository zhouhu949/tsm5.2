package com.qftx.tsm.workSign.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qftx.base.util.DateUtil;
import com.qftx.common.domain.BaseObject;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.constants.AppConstant;

import java.util.Date;

public class WorkSignInfoBean extends BaseObject{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8588238995340609703L;

	private String wsiId;		//ID

    private String orgId;		//机构ID

    private String groupId;		//部门ID

    private String userId;		//签到者ID

    private double lat;			//纬度

    private double lon;			//经度

    private String address;		//地址

    private String province;	//省

    private String city;		//市

    private String district;	//县\区

    private String customerId;	//客户id

    private String toucherId;	//联系人id

    private Date signDate;		//签到时间

    private Date updateDate;	//更新时间
    
    private Date inputDate;		//录入时间

    private String signInfo;	//签到内容

    private String devType;		//设备类型

    private Integer isDel;		//册除状态1-删除，0-未删除

    private Integer commentNum;	//评论条数

    private Integer shareNum;	//分享数

    private Integer favourNum;	//点赞数
    
    private String imgs;		//图片，多个用,隔开
    
    private String record;		//录音
    
	private Integer recordTime;	//录音时长
	
	private String targetid;		//分享对象（多条以#分割）
    
    
    
    
    private Date fromDate;
    private Date toDate;
    
    private String imgUrl_;
    private String imgUrl;
    private String userName;
    private String name;
    private String symbol;		//查询 大于小于符号        <&lt;   >&gt;        
    private String formatDay;
	private String formatMonth;
	private Long signDateTime;
	
	private String[] groupIds;
	private String[] userIds;
	private String[] wsiIds;
	private String groupIdsStr;
	private String userIdsStr;
	private Long inputdateTime;
	private String shareUserId;	//分享用户ID
	private String queryText;	//查询文本
	private String userAccount;
	
	private String toucherName;
	
	private String[] months={"一","二","三","四","五","六","七","八","九","十","十一","十二"};
	public String getWsiId() {
		return wsiId;
	}
	public void setWsiId(String wsiId) {
		this.wsiId = wsiId;
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
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getToucherId() {
		return toucherId;
	}
	public void setToucherId(String toucherId) {
		this.toucherId = toucherId;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getSignDate() {
		return signDate;
	}
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
		this.formatDay = String.valueOf(DateUtil.day(signDate));
		this.formatMonth = String.valueOf(months[DateUtil.month(signDate)-1]+"月");
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getInputDate() {
		return inputDate;
	}
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}
	public String getSignInfo() {
		return signInfo;
	}
	public void setSignInfo(String signInfo) {
		this.signInfo = signInfo;
	}
	public String getDevType() {
		if(devType==null){
			devType ="PC端";
		}
		return devType;
	}
	public void setDevType(String devType) {
		this.devType = devType;
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
	public String getImgUrl_() {
		if(StringUtils.isBlank(imgUrl)){
			imgUrl_  = AppConstant.DEFAULT_IMG_URL;
		}
		return imgUrl_;
	}
	public void setImgUrl_(String imgUrl_) {
		this.imgUrl_ = imgUrl_;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public Long getSignDateTime() {
		if(signDateTime==null){
			signDateTime = signDate==null?null:signDate.getTime();
		}
		return signDateTime;
	}
	public void setSignDateTime(Long signDateTime) {
		this.signDateTime = signDateTime;
		if(signDateTime!=null)this.signDate= new Date(signDateTime);
	}
	public String[] getUserIds() {
		return userIds;
	}
	public void setUserIds(String[] userIds) {
		this.userIds = userIds;
		
	}
	public String[] getGroupIds() {
		return groupIds;
	}
	public void setGroupIds(String[] groupIds) {
		this.groupIds = groupIds;
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
	public void setInputdateTime(Long inputdateTime) {
		this.inputdateTime = inputdateTime;
		this.inputDate=new Date(inputdateTime);
	}
	public Long getInputdateTime() {
		return inputDate==null?null:inputDate.getTime();
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
	public String getShareUserId() {
		return shareUserId;
	}
	public void setShareUserId(String shareUserId) {
		this.shareUserId = shareUserId;
	}
	public String getQueryText() {
		return queryText;
	}
	public void setQueryText(String queryText) {
		this.queryText = queryText;
	}
	public String[] getMonths() {
		return months;
	}
	public void setMonths(String[] months) {
		this.months = months;
	}
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getImgs() {
		return imgs;
	}
	public void setImgs(String imgs) {
		this.imgs = imgs;
	}
	public String getRecord() {
		return record;
	}
	public void setRecord(String record) {
		this.record = record;
	}
	public Integer getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(Integer recordTime) {
		this.recordTime = recordTime;
	}
	public String getTargetid() {
		return targetid;
	}
	public void setTargetid(String targetid) {
		this.targetid = targetid;
	}
	public String[] getWsiIds() {
		return wsiIds;
	}
	public void setWsiIds(String[] wsiIds) {
		this.wsiIds = wsiIds;
	}
	public String getToucherName() {
		return toucherName;
	}
	public void setToucherName(String toucherName) {
		this.toucherName = toucherName;
	}
	
	
	
	
	
	
	
	
	
   
	
    
}