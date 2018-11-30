package com.qftx.tsm.plan;

public class ResultDTO {
	private String status;
	private String msg;
	private Object data;
	
	public ResultDTO(String status, String msg) {
		super();
		this.status = status;
		this.msg = msg;
	}
	
	public static ResultDTO erro(String msg){
		return new ResultDTO("erro", msg);
	}
	
	public static ResultDTO Success(Object data){
		ResultDTO dto = new ResultDTO("success", null);
		if(data!=null) dto.setData(data);
		return dto;
	}
	
	public static ResultDTO Success(){
		return new ResultDTO("success", null);
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	
}
