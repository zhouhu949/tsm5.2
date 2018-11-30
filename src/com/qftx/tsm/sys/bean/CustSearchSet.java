package com.qftx.tsm.sys.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;

/**
 * 查询项设置
 */
public class CustSearchSet extends BaseObject {
	private static final long serialVersionUID = 1L;
	
	// 是否查询条件
	public static final short IS_QUERY_NO = 0; 	// 0-否
	public static final short IS_QUERY_YES = 1; // 1-是
	public static final short IS_QUERY_NOTITEM = 3; // 3-不能作为查询项
	
	// 是否列表显示
	public static final short IS_SHOW_YES = 1; 	// 1-是；
	public static final short IS_SHOW_NO = 0; 	// 0-否；
	
	// 是否启用
	public static final int ENABLE_NO = 0;	// 否
	public static final int ENABLE_YES = 1;	// 是
	
	public static final int DATATYPE_PICTURE = 9;	//图片

	private String id;

	private Integer enable; // 是否启用：0-否；1-是；
	
	/***
	  系统模块
	SEARCH_SET_MODULE_1 			("1",					"客户跟进"),
	SEARCH_SET_MODULE_2			("2",					"跟进记录"),
	SEARCH_SET_MODULE_3			("3",					"客户交接"),
	SEARCH_SET_MODULE_4			("4",					"我的客户（资源）"),
	SEARCH_SET_MODULE_5			("5",					"我的客户（意向客户）"),
	SEARCH_SET_MODULE_6			("6",					"我的客户（签约客户）"),
	SEARCH_SET_MODULE_7			("7",					"我的客户（全部客户）"),
	SEARCH_SET_MODULE_8			("8",					"公海客户"),
	SEARCH_SET_MODULE_9			("9",					"待分配资源"),
	SEARCH_SET_MODULE_10			("10",					"已分配资源"),
	SEARCH_SET_MODULE_11			("11",					"全部资源"),
	SEARCH_SET_MODULE_12			("12",					"流失客户"),
	SEARCH_SET_MODULE_13			("13",					"服务管理（我的客户）"),
	SEARCH_SET_MODULE_14			("14",					"服务管理（共享客户）"),
	SEARCH_SET_MODULE_15			("15",					"回访记录"),
	SEARCH_SET_MODULE_16			("16",					"客户回访"
	 */
	private String searchModule; 

	private String searchName; // 字段名称

	private Short isQuery; // 是否查询条件：0-否；1-是；3-不能作为查询项

	private Short sort;

	private Short oldSort; // 用于修改字段时,用于修改排序做标记。
	
	private String inputerAcc;

	private Date inputdate;

	private String modifierAcc;

	private Date modifydate;

	private String orgId;

	private String searchCode; // 字段查询CODE

	private String listCode; // 列表展示LIST_CODE
	
    private Short isShow; // 是否列表显示：0-否；1-是；
    
    private Integer isDisabled; // 是否可编辑：0-全部不可编辑；1-可编辑；2-查询不可编辑；3-列表显示不可编辑；
    
    private Integer dataType; // 字段类型--字段类型--1:文本类型，2：日期类型，3：单选类型，4：多选类型，5：树，6：整数类型，7：double类型

    private Integer isFiledSet; // 是否系统字段动态设置：0-否；1-是
    
    private Integer state; // 专业版 （0：个人客户，1：企业客户，2：共有），3：标准版
    
    // 具体参考 
    private String developCode; // 开发者使用CODE，用于代码管理整合
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSearchModule() {
		return searchModule;
	}

	public void setSearchModule(String searchModule) {
		this.searchModule = searchModule;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public Short getIsQuery() {
		return isQuery;
	}

	public void setIsQuery(Short isQuery) {
		this.isQuery = isQuery;
	}

	public Short getSort() {
		return sort;
	}

	public void setSort(Short sort) {
		this.sort = sort;
	}

	public Short getOldSort() {
		return oldSort;
	}

	public void setOldSort(Short oldSort) {
		this.oldSort = oldSort;
	}

	public String getInputerAcc() {
		return inputerAcc;
	}

	public void setInputerAcc(String inputerAcc) {
		this.inputerAcc = inputerAcc;
	}

	public Date getInputdate() {
		return inputdate;
	}

	public void setInputdate(Date inputdate) {
		this.inputdate = inputdate;
	}

	public String getModifierAcc() {
		return modifierAcc;
	}

	public void setModifierAcc(String modifierAcc) {
		this.modifierAcc = modifierAcc;
	}

	public Date getModifydate() {
		return modifydate;
	}

	public void setModifydate(Date modifydate) {
		this.modifydate = modifydate;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getSearchCode() {
		return searchCode;
	}

	public void setSearchCode(String searchCode) {
		this.searchCode = searchCode;
	}

	public Short getIsShow() {
		return isShow;
	}

	public void setIsShow(Short isShow) {
		this.isShow = isShow;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	public Integer getIsDisabled() {
		return isDisabled;
	}

	public void setIsDisabled(Integer isDisabled) {
		this.isDisabled = isDisabled;
	}

	public Integer getIsFiledSet() {
		return isFiledSet;
	}

	public void setIsFiledSet(Integer isFiledSet) {
		this.isFiledSet = isFiledSet;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getDevelopCode() {
		return developCode;
	}

	public void setDevelopCode(String developCode) {
		this.developCode = developCode;
	}

	public String getListCode() {
		return listCode;
	}

	public void setListCode(String listCode) {
		this.listCode = listCode;
	}
    
}