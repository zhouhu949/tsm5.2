package com.qftx.tsm.bill.util;

import com.qftx.base.util.DateUtil;
import com.qftx.common.util.CodeUtils;
import com.qftx.common.util.ConfigInfoUtils;
import com.qftx.common.util.FwHttpUtils;
import com.qftx.common.util.IContants;
import com.qftx.common.util.boss.FwCodec;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HDUtils {
	private static Logger logger = Logger.getLogger(HDUtils.class);
	
	/** 
	 * 发送信息到话单系统
	 * @param paramMap
	 * @param hdUrl
	 * @param key
	 * @param moduleName
	 * @return 
	 * @create  2016年3月4日 上午9:07:26 lixing
	 * @history  
	 */
	public static String sendMsgToHD(Map<String, Object> paramMap,String moduleName){
		String responeStr = "" ;
		String hdUrl = ConfigInfoUtils.getStringValue("hd_interface_url");
		String key = ConfigInfoUtils.getStringValue("aes_key");
		try {
			String param_str = JSONObject.fromObject(paramMap).toString();
			logger.debug("*********"+moduleName+"发送到话单系统的原始字符串："+param_str);
			byte[] dataBytes = FwCodec.aesEnCbc5(key,param_str);
			byte[] reData = FwHttpUtils.allSend(hdUrl, dataBytes);
			responeStr = FwCodec.aesDeCbc5(key, reData);
			logger.debug("*********"+moduleName+"接收话单系统返回字符串："+responeStr);
		}catch(IOException e){
			logger.error("********* "+ moduleName + "到话单系统网络异常:" + e.getMessage() +" *********" );
			responeStr = "9008";
		}catch (Exception e) {
			logger.error("********* "+ moduleName + "到话单系统未知异常:" + e.getMessage() +" *********" );
			responeStr = "9999";
		}
		return responeStr;
	}
	
	/** 
	 * 组装同步单位所有账号时长的请求
	 * @param unitId
	 * @return 
	 * @create  2016年2月29日 上午10:45:46 lixing
	 * @history  
	 */
	public static Map<String,Object> splitRequestParamOfUnitLens(String unitId){
		Map<String, Object> reMap = new HashMap<String, Object>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("timestamp", DateUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
		map.put("compCode", unitId);
		reMap.put("balance_comp",map);
		return reMap;
    }
	
	public static Map<String, Object> splitRequestParamOfUserLens(String account,String orgId){
		Map<String, Object> reMap = new HashMap<String, Object>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("timestamp", DateUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
		map.put("account", account);
		map.put("compCode", orgId);
		reMap.put("balance_account",map);
		return reMap;
	}
	
	/** 
	 * 组装通信时长分配的请求
	 * @param mainAcc
	 * @param accounts
	 * @param times
	 * @param orgId
	 * @return 
	 * @create  2016年3月4日 上午9:57:56 lixing
	 * @history  
	 */
	public static Map<String,Object> splitRequestParamOfTx(String mainAcc,String accounts,Double times,String orgId){
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> re_map = new HashMap<String, Object>();
		map.put("timestamp", DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		map.put("compCode", orgId);
		map.put("mainAccount", mainAcc);
		map.put("account",accounts);
		map.put("operCount", times);
		re_map.put("balanceAllot", map);
		return re_map;
   }
	
	/** 
	 * 组装通信时长回收的请求
	 * @param mainAcc
	 * @param accounts
	 * @param times
	 * @param orgId
	 * @return 
	 * @create  2016年3月4日 上午9:57:56 lixing
	 * @history  
	 */
	public static Map<String,Object> splitRequestParamOfTxRec(String mainAcc,String accounts,Double times,String orgId){
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> re_map = new HashMap<String, Object>();
		map.put("timestamp", DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		map.put("compCode", orgId);
		
		map.put("mainAccount", mainAcc);
		map.put("account",accounts);
		map.put("operCount", times);
		re_map.put("balanceRecycle", map);
		return re_map;
   }
	
}
