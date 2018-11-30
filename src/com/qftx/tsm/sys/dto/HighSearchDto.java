package com.qftx.tsm.sys.dto;

import java.io.Serializable;
import java.util.List;


/**
 * 高级查询拓展类
 *
 */
public class HighSearchDto implements Serializable {
	private static final long serialVersionUID = 196737115461374501L;

	private String searchName; // 字段名称

	private Short sort;

	private String searchCode;
     
    private Integer dataType; // 字段类型--字段类型--1:文本类型，2：日期类型，3：单选类型，4：多选类型，5：树，6：整数类型，7：double类型

    private Integer isFiledSet; // 是否系统字段动态设置：0-否；1-是  
    
    private Short isQuery; // 是否查询条件：0-否；1-是；3-不能作为查询项
    
	private String value; // 最终保存的值
	
	private String paramValue; // 参数值(如：树类型的url地址！)
	
	private String developCode; // 开发者参数 如(所有者：ownerAcc)
	
	private String defined1; // 自定义1
	
	private String displayContent; // 展示内容
	
	private List<HighSearchChildDto> childList; //  多选、单选子列表
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	public List<HighSearchChildDto> getChildList() {
		return childList;
	}
	public void setChildList(List<HighSearchChildDto> childList) {
		this.childList = childList;
	}
	public String getSearchName() {
		return searchName;
	}
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	public Short getSort() {
		return sort;
	}
	public void setSort(Short sort) {
		this.sort = sort;
	}
	public String getSearchCode() {
		return searchCode;
	}
	public void setSearchCode(String searchCode) {
		this.searchCode = searchCode;
	}
	public Integer getDataType() {
		return dataType;
	}
	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}
	public Integer getIsFiledSet() {
		return isFiledSet;
	}
	public void setIsFiledSet(Integer isFiledSet) {
		this.isFiledSet = isFiledSet;
	}
	public Short getIsQuery() {
		return isQuery;
	}
	public void setIsQuery(Short isQuery) {
		this.isQuery = isQuery;
	}
	public String getDevelopCode() {
		return developCode;
	}
	public void setDevelopCode(String developCode) {
		this.developCode = developCode;
	}
	public String getDefined1() {
		return defined1;
	}
	public void setDefined1(String defined1) {
		this.defined1 = defined1;
	}
	public String getDisplayContent() {
		return displayContent;
	}
	public void setDisplayContent(String displayContent) {
		this.displayContent = displayContent;
	}	
		
}
