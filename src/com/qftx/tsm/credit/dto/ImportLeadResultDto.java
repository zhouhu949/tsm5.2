package com.qftx.tsm.credit.dto;

import com.qftx.tsm.credit.bean.ImportLeadResultBean;

/**
 * 导入结果 dto
 * 
 * @author chenhm
 */
public class ImportLeadResultDto extends ImportLeadResultBean{
	private static final long serialVersionUID = -9216717763509675875L;
	
	private String symbol;//查询 大于小于符号        <&lt;   >&gt;         
	private String type; // first  向上，last 向下
	private String impTime;
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getImpTime() {
		return impTime;
	}
	public void setImpTime(String impTime) {
		this.impTime = impTime;
	} 
	
	
}
