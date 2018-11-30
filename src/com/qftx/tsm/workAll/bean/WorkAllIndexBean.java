package com.qftx.tsm.workAll.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qftx.base.util.DateUtil;
import com.qftx.common.domain.BaseObject;
import com.qftx.common.util.StringUtils;

import java.util.Date;

public class WorkAllIndexBean  extends BaseObject{
    private String id;

    private String workId;

    private String orgId;

    private String userId;

    private String userAcc;

    private Integer isDel;

    private Date inputDate;

    private Integer type;
    
    private Long inputdateTime;
    private String formatDay;
	private String formatMonth;
	
	private String userAccount;
	private String userName;
	private Date fromDate;
	private Date toDate;
	private String[] groupIds;
	private String[] userIds;
	private String groupIdsStr;
	private String userIdsStr;
	private String inputDateStr;
	private String shareUserId;//分享用户ID
	private String queryText;//查询文本
    private String symbol;		//查询 大于小于符号        <&lt;   >&gt;        
    private String[] months={"一","二","三","四","五","六","七","八","九","十","十一","十二"};
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId == null ? null : workId.trim();
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getUserAcc() {
        return userAcc;
    }

    public void setUserAcc(String userAcc) {
        this.userAcc = userAcc == null ? null : userAcc.trim();
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public Date getInputDate() {
        return inputDate;
    }

    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
        this.formatDay = String.valueOf(DateUtil.day(inputDate));
		this.formatMonth = String.valueOf(months[DateUtil.month(inputDate)-1]+"月");
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    
    public void setInputdateTime(Long inputdateTime) {
		this.inputdateTime = inputdateTime;
		this.inputDate=new Date(inputdateTime);
	}
	public Long getInputdateTime() {
		return inputDate==null?null:inputDate.getTime();
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
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
		if(inputDate!=null){
			return DateUtil.formatDate(inputDate, "MM月dd日  HH:mm:ss");
		}else{
			return null;
		}
	}
	public void setInputDateStr(String inputDateStr) {
		this.inputDateStr = inputDateStr;
	}
	public String getShareUserId() {
		return shareUserId;
	}
	public void setShareUserId(String shareUserId) {
		this.shareUserId = shareUserId;
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

	public String getQueryText() {
		return queryText;
	}

	public void setQueryText(String queryText) {
		this.queryText = queryText;
	}
	
}