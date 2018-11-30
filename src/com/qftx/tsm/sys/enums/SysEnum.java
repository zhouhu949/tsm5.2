package com.qftx.tsm.sys.enums;

/**
 * 系统设置 枚举
 * <p>    company：浙江中恩通信技术有限公司
 * <br>createDate：2014-7-2
 * <br>updateDate：2014-7-2
 * @author zwj
 */
public enum SysEnum {
	
	FIELD_DATATYPE_TEXT		        ("1",					"文本类型"),
	FIELD_DATATYPE_DATA				("2",					"日期类型"),
	FIELD_DATATYPE_RADIO		    ("3",					"单选类型"),
	FIELD_DATATYPE_CHECK	    	("4",					"多选类型"),
	FIELD_DATATYPE_TREE	    		("5",					"树"),
	FIELD_DATATYPE_INT	    		("6",					"整数"),
	FIELD_DATATYPE_DOUBLE	    	("7",					"double"),
	FIELD_DATATYPE_YINGYONG	    	("8",					"引用"),
	FIELD_DATATYPE_IMG	    	    ("9",					"图片"),
	FIELD_DATATYPE_REPORT	    	("10",					"统计"),
	FIELD_DATATYPE_MONEY	    	("11",					"金额"),
	
	// 查询项设置 模块集合	
	SEARCH_SET_MODULE_1			("1",					"客户跟进"),
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
	SEARCH_SET_MODULE_16			("16",					"客户回访"),
	
	//qupai 查询项设置 模块集合
	QUPAI_SEARCH_SET_MODULE_1			("1",					"放款管理"),
	QIPAI_SEARCH_SET_MODULE_2			("2",					"放款确认"),
	
	;
		
	private String state;
	private String descr;
	
	SysEnum(String state, String descr) {
		this.state = state;
		this.descr = descr;
	}

	public String getState() {
		return state;
	}

	public String getDescr() {
		return descr;
	}	
	
	
	/** 系统字段类型集合： */
	public static SysEnum[] getFieldDataType() {
		return new SysEnum[]{FIELD_DATATYPE_TEXT,FIELD_DATATYPE_DATA,FIELD_DATATYPE_RADIO,FIELD_DATATYPE_CHECK};
	}
	
	/** 查询项设置 模块集合： */
	public static SysEnum[] getSearchModules() {
		return new SysEnum[]{SEARCH_SET_MODULE_1,SEARCH_SET_MODULE_2,SEARCH_SET_MODULE_3,SEARCH_SET_MODULE_4,SEARCH_SET_MODULE_5,
				SEARCH_SET_MODULE_6,SEARCH_SET_MODULE_7,SEARCH_SET_MODULE_8,SEARCH_SET_MODULE_9,SEARCH_SET_MODULE_10,SEARCH_SET_MODULE_11,
				SEARCH_SET_MODULE_12,SEARCH_SET_MODULE_13,SEARCH_SET_MODULE_14,SEARCH_SET_MODULE_15,SEARCH_SET_MODULE_16};
	}
	
	/** 查询项设置 模块集合： */
	public static SysEnum[] getQupaiSearchModules() {
		return new SysEnum[]{QUPAI_SEARCH_SET_MODULE_1,QIPAI_SEARCH_SET_MODULE_2};
	}
	
	/** 查询项自定义设置 模块集合： */
	public static SysEnum[] getDefinedSearchModules() {
		return new SysEnum[]{SEARCH_SET_MODULE_1,SEARCH_SET_MODULE_3,SEARCH_SET_MODULE_4,SEARCH_SET_MODULE_5,
				SEARCH_SET_MODULE_6,SEARCH_SET_MODULE_7,SEARCH_SET_MODULE_8,SEARCH_SET_MODULE_9,SEARCH_SET_MODULE_10,SEARCH_SET_MODULE_11,
				SEARCH_SET_MODULE_12,SEARCH_SET_MODULE_13,SEARCH_SET_MODULE_14,SEARCH_SET_MODULE_15,SEARCH_SET_MODULE_16};
	}
	
	/** 查询项自定义 qupai设置 模块集合： */
	public static SysEnum[] getQupaiDefinedSearchModules() {
		return new SysEnum[]{QUPAI_SEARCH_SET_MODULE_1,QIPAI_SEARCH_SET_MODULE_2};
	}
	
	/** 查询项设置 拥有共有者模块集合： */
	public static SysEnum[] getComAccModules() {
		return new SysEnum[]{SEARCH_SET_MODULE_5,SEARCH_SET_MODULE_6};
	}
	
	/**
	 * 根据descr找到对应的枚举
	 * @param descr				即为枚举的descr属性
	 * @param enums				枚举集
	 * @return					没有找到对应的返回null
	 */
	public static SysEnum getEnum(String descr,SysEnum[] enums) {
		for (SysEnum e : enums) {
			if (e.getDescr().equals(descr)) {
				return e;
			}
		}
		return null;
	}	
	
	/**
	 * 根据state找到对应的枚举
	 * @param descr				即为枚举的state属性
	 * @param enums				枚举集
	 * @return					没有找到对应的返回null
	 */
	public static SysEnum getEnum1(String state,SysEnum[] enums) {
		for (SysEnum e : enums) {
			if (e.getState().equals(state)) {
				return e;
			}
		}
		return null;
	}	
}
