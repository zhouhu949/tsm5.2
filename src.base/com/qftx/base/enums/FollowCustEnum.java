package com.qftx.base.enums;

 /** 
 * 客户跟进 枚举
 * @author: zwj
 * @since: 2015-3-6  上午11:51:26
 * @history:
 */
public enum FollowCustEnum {
	
	CUST_ALL											("1",					"全部"),
	CUST_TODAY_CONTACT_X				("2",					"今日待联系"),
	CUST_TODAY_CONTACT_Y				("3",					"今日已联系"),
	CUST_WEEK_CONTACT_Y					("4",					"本周已联系"),
	CUST_7_TODAY_CONTACT_W			("5",					"7天未联系"),
	CUST_30_TODAY_CONTACT_W			("6",					"30天未联系"),
	CUST_CONTACT_IMPORT					("7",					"重点客户"),
	
	
	CUST_TYPE_1			("1",					"全部跟进"),
	CUST_TYPE_2			("2",					"意向客户"),
	CUST_TYPE_3			("3",					"签约客户"),
	CUST_TYPE_4			("4",					"沉默客户"),
	CUST_TYPE_5			("5",					"流失客户"),
	
	CUST_STATUS_4			("4",					"公海客户"),
	CUST_STATUS_5			("5",					"意向客户"),
	CUST_STATUS_6			("6",					"签约客户"),
	CUST_STATUS_7			("7",					"沉默客户"),
	CUST_STATUS_8			("8",					"流失客户"),
	
	
	ACTION_TYPE_1			("1",					"电话联系"),
	ACTION_TYPE_2			("2",					"会客联系"),
	ACTION_TYPE_3			("3",					"客户来电"),
	ACTION_TYPE_4			("4",					"短信联系"),
	ACTION_TYPE_5			("5",					"QQ联系"),
	ACTION_TYPE_6			("6",					"邮件联系"),
	;
		
	private String state;
	private String descr;
	
	FollowCustEnum(String state, String descr) {
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
	public static FollowCustEnum[] getFollowCust() {
		return new FollowCustEnum[]{CUST_ALL,CUST_TODAY_CONTACT_X,CUST_TODAY_CONTACT_Y
				,CUST_WEEK_CONTACT_Y,CUST_7_TODAY_CONTACT_W,CUST_30_TODAY_CONTACT_W,CUST_CONTACT_IMPORT};
	}
	
	/** 资源跟进 分类： */
	public static FollowCustEnum[] getCustFollowCust() {
		return new FollowCustEnum[]{CUST_TYPE_1,CUST_TYPE_2,CUST_TYPE_3,CUST_TYPE_4,CUST_TYPE_5};
	}
	
	/** 客户跟进 客户状态： */
	public static FollowCustEnum[] getCustFollowStatus() {
		return new FollowCustEnum[]{CUST_STATUS_4,CUST_STATUS_5,CUST_STATUS_6,CUST_STATUS_7,CUST_STATUS_8};
	}
	
	/** 客户跟进 联系方式： */
	public static FollowCustEnum[] getCustFollowActionType() {
		return new FollowCustEnum[]{ACTION_TYPE_1,ACTION_TYPE_2,ACTION_TYPE_3,ACTION_TYPE_4,ACTION_TYPE_5,ACTION_TYPE_6};
	}
	
	/**
	 * 根据descr找到对应的枚举
	 * @param descr				即为枚举的descr属性
	 * @param enums				枚举集
	 * @return					没有找到对应的返回null
	 */
	public static FollowCustEnum getEnum(String descr,FollowCustEnum[] enums) {
		for (FollowCustEnum e : enums) {
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
	public static FollowCustEnum getEnum1(String state,FollowCustEnum[] enums) {
		for (FollowCustEnum e : enums) {
			if (e.getState().equals(state)) {
				return e;
			}
		}
		return null;
	}	
}
