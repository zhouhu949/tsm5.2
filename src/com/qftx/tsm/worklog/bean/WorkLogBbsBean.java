package com.qftx.tsm.worklog.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qftx.common.domain.BaseObject;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.constants.AppConstant;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


//工作日志_论评表
public class WorkLogBbsBean extends BaseObject{
	private static final long serialVersionUID = -938875560901283411L;
	private String wlbId;//ID
	private String wliId;//日志ID与info表关联的，一个日志有多条评论
	private String replyWlbId;	//回复评论的ID（回复日志此字段为空） 5.0废弃
	private String replyUserId;	//回复用户ID
	private String context;	//评论内容
	private Integer type;	//0.评论1.签到2.日志3.日程4.消息（备用）5.通知公告6.其它
	private String devType;	//设备类型(5.0新增)
	private String userId;	//用户ID
	private String orgId;	//机构ID
	private Date inputdate;	//插入时间
	private Date updatedate;	//更新时间
	private Integer upNum;	//顶次数点一次加1
	private Integer commentNum;	//评论条数
	private Integer isDel;	//册除状态1-删除，0-未删除

	
	private String userAccount;
	private String userName;
	private String imgUrl;
	
	private String replyUserAccount;//回复评论的账号
	private String replyUserName;	//回复用户名
	
	private String context_;//评论内容
	private String imgUrl_;
	private boolean delable;//是否可以删除
	private boolean favour;//是否已经点赞
	
	final Pattern pattern = Pattern.compile("\\[qface:(\\d+)\\]");
	
	public String getWlbId() {
		return wlbId;
	}
	public void setWlbId(String wlbId) {
		this.wlbId = wlbId;
	}
	public String getWliId() {
		return wliId;
	}
	public void setWliId(String wliId) {
		this.wliId = wliId;
	}
	public String getReplyWlbId() {
		return replyWlbId;
	}
	public void setReplyWlbId(String replyWlbId) {
		this.replyWlbId = replyWlbId;
	}
	public String getReplyUserId() {
		return replyUserId;
	}
	public void setReplyUserId(String replyUserId) {
		this.replyUserId = replyUserId;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getInputdate() {
		return inputdate;
	}
	public void setInputdate(Date inputdate) {
		this.inputdate = inputdate;
	}
	
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	public Integer getUpNum() {
		return upNum;
	}
	public void setUpNum(Integer upNum) {
		this.upNum = upNum;
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
	public String getContext_() {
		this.context_ = this.context;
		if(context_!=null && context_.length()>=9){
			Matcher mat = pattern.matcher(context_);
			while(mat.find()){
				String gif = mat.group(1);
				context_ = context_.replaceFirst("\\[qface:"+gif+"\\]", "<img width=\"30px\" height=\"30px\" src=\"/static/js/qqface/arclist/"+gif+".gif\" border=\"0\">");
			}
		}
		return context_;
	}
	public void setContext_(String context_) {
		this.context_ = context_;
	}
	public Date getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
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
		}else{
			return imgUrl;
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
	public String getReplyUserAccount() {
		return replyUserAccount;
	}
	public String getReplyUserName() {
		return replyUserName;
	}
	public void setReplyUserAccount(String replyUserAccount) {
		this.replyUserAccount = replyUserAccount;
	}
	public void setReplyUserName(String replyUserName) {
		this.replyUserName = replyUserName;
	}
	public boolean isFavour() {
		return favour;
	}
	public void setFavour(boolean favour) {
		this.favour = favour;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getDevType() {
		if(StringUtils.isBlank(devType)){
			devType = "PC客户端";
		}
		return devType;
	}
	public void setDevType(String devType) {
		this.devType = devType;
	}
	public Integer getCommentNum() {
		return commentNum;
	}
	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}
	
}