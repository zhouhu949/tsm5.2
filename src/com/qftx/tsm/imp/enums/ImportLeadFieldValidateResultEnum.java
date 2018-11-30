package com.qftx.tsm.imp.enums;

/**
 * 放款信息导入字段验证结果
 * 
 * @author chenhm
 *
 */
public enum ImportLeadFieldValidateResultEnum {
	SUCCESS											("0",					"成功"),
	LEADCODE_REPEAT								("1",					"放款编号重复"),
//	PHONE_FORMART							("2",					"电话号码格式错误"),
//	CUST_NAME_REPEAT						("3",					"单位名称重复"),
//	COMPANY_URL_REPEAT					("4",					"公司网址重复"),
	DEFECT_REQUIRED							("5",					"缺失必填项"),
	OWN_ILLEGAL_CHAR						("6",					"拥有非法字符"),
	FORMART_ERROR								("7",					"格式错误"),
	FAIL													("10",				"没通过验证"),
	;
		
	/**
	 * @return
	 */
	public static ImportLeadFieldValidateResultEnum[] getImportResults() {
		return new ImportLeadFieldValidateResultEnum[] { SUCCESS, LEADCODE_REPEAT, DEFECT_REQUIRED, OWN_ILLEGAL_CHAR,
				FORMART_ERROR, FAIL };
	}
	
	private String state;
	private String descr;
	
	ImportLeadFieldValidateResultEnum(String state, String descr) {
		this.state = state;
		this.descr = descr;
	}

	public String getState() {
		return state;
	}

	public String getDescr() {
		return descr;
	}	
	
	/**
	 * ���descr�ҵ���Ӧ��ö��
	 * @param descr				��Ϊö�ٵ�descr����
	 * @param enums				ö�ټ�
	 * @return					û���ҵ���Ӧ�ķ���null
	 */
	public static ImportLeadFieldValidateResultEnum getEnum(String descr,ImportLeadFieldValidateResultEnum[] enums) {
		for (ImportLeadFieldValidateResultEnum e : enums) {
			if (e.getDescr().equals(descr)) {
				return e;
			}
		}
		return null;
	}	
	
	/**
	 * ���state�ҵ���Ӧ��ö��
	 * @param descr				��Ϊö�ٵ�state����
	 * @param enums				ö�ټ�
	 * @return					û���ҵ���Ӧ�ķ���null
	 */
	public static ImportLeadFieldValidateResultEnum getEnum1(String state,ImportLeadFieldValidateResultEnum[] enums) {
		for (ImportLeadFieldValidateResultEnum e : enums) {
			if (e.getState().equals(state)) {
				return e;
			}
		}
		return null;
	}	
}
