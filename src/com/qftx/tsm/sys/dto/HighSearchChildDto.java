package com.qftx.tsm.sys.dto;

import java.io.Serializable;


/**
 * 高级查询 多选、单选类型列表 拓展类
 *
 */
public class HighSearchChildDto  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7586656496877361023L;
	private String value;	 // 存储值
	private String name; // 页面展示
	private Integer isCheck; // 是否选中 0：否，1：是
	private Integer order; // 0：开始，1：结束(目前用于开始结束时间);
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getIsCheck() {
		return isCheck;
	}
	public void setIsCheck(Integer isCheck) {
		this.isCheck = isCheck;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	
}
