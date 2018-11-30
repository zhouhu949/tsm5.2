package com.qftx.tsm.option.dto;

/***
 * 销售管理设置中 客户跟进时间段设置
 */
public class CustFollowProcessJsonDto {  

	private String optionId; // 销售进程ID逗号分割，
	private String startTime; // 开始时间段
	private String endTime; // 结束时间段
	private String isAlert; // 是否提示 0：否，1：是
	private String optionName; // 销售进程名称逗号分割，
	
	public String getOptionId() {
		return optionId;
	}

	public void setOptionId(String optionId) {
		this.optionId = optionId;
	}
	
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getIsAlert() {
		return isAlert;
	}

	public void setIsAlert(String isAlert) {
		this.isAlert = isAlert;
	}

	public String getOptionName() {
		return optionName;
	}

	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}

	@Override
	public boolean equals(Object obj) {
		 if (this == obj)
	            return true;
	        if (obj == null)
	            return false;
	        if (getClass() != obj.getClass())
	            return false;
	        CustFollowProcessJsonDto other = (CustFollowProcessJsonDto) obj;
	        if (optionId == null) {
	            if (other.optionId != null)
	                return false;
	        } else if (!optionId.equals(other.optionId))
	            return false;
	        return true;
	}
	
}
