package com.qftx.tsm.log.util;

import com.qftx.tsm.log.bean.LogContactDayDataBean;

import java.util.HashMap;
import java.util.Map;

public class ContactUtil {
	
	/**新增、导入资源*/
	public static String ADD_RES = "add_res";
	
	/**资源联系*/
	public static String RES_CONTACT = "res_contact";
	
	/**资源联系-放入公海*/
	public static String RES_TO_SEA = "res_to_sea";
	
	/**资源联系-资源转意向*/
	public static String RES_TO_WILL = "res_to_will";
	
	/**资源联系-资源转签约*/
	public static String RES_TO_SIGN = "res_to_sign";
	
	/**公海取回*/
	public static String SEA_TO_RES = "sea_to_res";
	
	/**公海转意向*/
	public static String SEA_TO_WILL = "sea_to_will";
	
	/**意向新增导入*/
	public static String CUST_ADD = "cust_add";
	
	/**意向转公海*/
	public static String CUST_TO_SEA = "cust_to_sea";
	
	/**意向转签约*/
	public static String CUST_TO_SIGN = "cust_to_sign";
	
	/**意向客户联系*/
	public static String CUST_CONTACT = "cust_contact";
	
	private static ContactUtil instance = new ContactUtil();
	
	private static Map<String, LogContactDayDataBean> map;

	public static ContactUtil getInstance(){
		return instance;
	}
	
	public ContactUtil() {
		map = new HashMap<String, LogContactDayDataBean>();
		map.put(ADD_RES, new LogContactDayDataBean(0, 2, 0, 2));
		map.put(RES_CONTACT, new LogContactDayDataBean(1, 2, 1, 2));
		map.put(RES_TO_SEA, new LogContactDayDataBean(1, 2, 4, 4));
		map.put(RES_TO_WILL, new LogContactDayDataBean(1, 2, 2, 3));
		map.put(RES_TO_SIGN, new LogContactDayDataBean(1, 2, 2, 6));
		map.put(SEA_TO_RES, new LogContactDayDataBean(4, 2, 0, 2));
		map.put(SEA_TO_WILL, new LogContactDayDataBean(4, 3, 1, 3));
		map.put(CUST_ADD, new LogContactDayDataBean(3, 3, 1, 3));
		map.put(CUST_TO_SEA, new LogContactDayDataBean(2, 3, 4, 4));
		map.put(CUST_TO_SIGN, new LogContactDayDataBean(2, 3, 2, 6));
		map.put(CUST_CONTACT, new LogContactDayDataBean(2, 3, 2, 3));
	}
	
	public LogContactDayDataBean getParamBean(String operateType){
		return map.get(operateType);
	}
	
}
