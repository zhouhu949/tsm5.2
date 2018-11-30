package com.qftx.tsm.sys.dto;

import java.io.Serializable;


 /** 
 * 查询项 特殊需求查询字段
 */
public class SearchSetSortDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
    private Integer sort;
    private String moduleCode;
    private Short queryCount;
    
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getModuleCode() {
		return moduleCode;
	}
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Short getQueryCount() {
		return queryCount;
	}
	public void setQueryCount(Short queryCount) {
		this.queryCount = queryCount;
	}

    
}
