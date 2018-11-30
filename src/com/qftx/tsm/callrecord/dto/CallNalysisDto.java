package com.qftx.tsm.callrecord.dto;

/**
 * 团队通话效率统计分析 接口返回json 值
 * @author: zwj
 * @since: 2015-12-12  下午2:30:27
 * @history: 4.x
 */
public class CallNalysisDto {
	
	private String callNum;
	private Integer costTimeLength;
	private Integer timeLength;
	private String inputAcc;
	private String custType;
	public String getInputAcc() {
		return inputAcc;
	}
	public void setInputAcc(String inputAcc) {
		this.inputAcc = inputAcc;
	}

	public String getCallNum() {
		return callNum;
	}

	public void setCallNum(String callNum) {
		this.callNum = callNum;
	}

	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public Integer getCostTimeLength() {
		return costTimeLength;
	}
	public void setCostTimeLength(Integer costTimeLength) {
		this.costTimeLength = costTimeLength;
	}
	public Integer getTimeLength() {
		return timeLength;
	}
	public void setTimeLength(Integer timeLength) {
		this.timeLength = timeLength;
	}
		
}
