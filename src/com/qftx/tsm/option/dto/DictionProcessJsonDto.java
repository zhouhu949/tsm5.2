package com.qftx.tsm.option.dto;

/***
 * 销售管理设置中 跟进销售进程设置列表
 * @author Administrator
 *
 */
public class DictionProcessJsonDto {  

	private String optionId; // 销售进程ID
	private String optionName; // 销售进程名称
	private String optionValue; // 销售进程设置跟进时间值
	
	public String getOptionId() {
		return optionId;
	}
	public void setOptionId(String optionId) {
		this.optionId = optionId;
	}
	public String getOptionName() {
		return optionName;
	}
	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}
	public String getOptionValue() {
		return optionValue;
	}
	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}
	
	@Override
	public boolean equals(Object obj) {
		 if (this == obj)
	            return true;
	        if (obj == null)
	            return false;
	        if (getClass() != obj.getClass())
	            return false;
	        DictionProcessJsonDto other = (DictionProcessJsonDto) obj;
	        if (optionId == null) {
	            if (other.optionId != null)
	                return false;
	        } else if (!optionId.equals(other.optionId))
	            return false;
	        return true;
	}
	
}
