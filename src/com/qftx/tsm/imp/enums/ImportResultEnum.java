package com.qftx.tsm.imp.enums;

 /** 
 * 导入结果 枚举
 * @author: zwj
 * @history:
 */
public enum ImportResultEnum {
	SUCCESS											("0",					"成功"),
	PHONE_REPEAT								("1",					"电话号码重复"),
	PHONE_FORMART							("2",					"电话号码格式错误"),
	CUST_NAME_REPEAT						("3",					"单位名称重复"),
	COMPANY_URL_REPEAT					("4",					"公司网址重复"),
	DEFECT_REQUIRED							("5",					"缺失必填项"),
	OWN_ILLEGAL_CHAR						("6",					"拥有非法字符"),
	FORMART_ERROR								("7",					"格式错误"),
	FAIL													("10",				"没通过验证"),
	CUST_NAME_NOTEXIST						("11",				"客户名称不存在"),
	;
		
	private String state;
	private String descr;
	
	ImportResultEnum(String state, String descr) {
		this.state = state;
		this.descr = descr;
	}

	public String getState() {
		return state;
	}

	public String getDescr() {
		return descr;
	}	
	
	
	/** 客户跟进数据分类： */
	public static ImportResultEnum[] getImportResults() {
		return new ImportResultEnum[]{SUCCESS,PHONE_REPEAT,PHONE_FORMART,CUST_NAME_REPEAT
				,COMPANY_URL_REPEAT,DEFECT_REQUIRED,OWN_ILLEGAL_CHAR,FORMART_ERROR,FAIL,CUST_NAME_NOTEXIST};
	}
	
	
	/**
	 * 根据descr找到对应的枚举
	 * @param descr				即为枚举的descr属性
	 * @param enums				枚举集
	 * @return					没有找到对应的返回null
	 */
	public static ImportResultEnum getEnum(String descr,ImportResultEnum[] enums) {
		for (ImportResultEnum e : enums) {
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
	public static ImportResultEnum getEnum1(String state,ImportResultEnum[] enums) {
		for (ImportResultEnum e : enums) {
			if (e.getState().equals(state)) {
				return e;
			}
		}
		return null;
	}	
}
