package com.qftx.tsm.bill.util;

import com.qftx.base.auth.dto.BaseSend;
import com.qftx.base.util.DateUtil;
import com.qftx.common.util.CodeUtils;
import com.qftx.common.util.ConfigInfoUtils;
import com.qftx.common.util.FwHttpUtils;
import com.qftx.common.util.IContants;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SmsUtils {
	private static Logger logger = Logger.getLogger(SmsUtils.class);
	
	/** 
	 * 发送信息到短信系统
	 * @param paramMap
	 * @param hdUrl
	 * @param key
	 * @param moduleName
	 * @return 
	 * @create  2016年3月4日 上午9:07:26 lixing
	 * @history  
	 */
	public static String sendMsgToSms(Map<String, Object> paramMap,String moduleName){
		String responeStr = "" ;
		
		String hdUrl = ConfigInfoUtils.getStringValue(BaseSend.SMS_OUT_URL);
		String code = ConfigInfoUtils.getStringValue(BaseSend.AUTH_CODE);
		String key = ConfigInfoUtils.getStringValue(BaseSend.SMS_SEND_DES_KEY);
		try {
			String param_str = JSONObject.fromObject(paramMap).toString();
			logger.debug("*********"+moduleName+"发送到短信系统的原始字符串："+param_str);
			byte[] data = CodeUtils.encrypt(key, param_str);
			String dataStr = CodeUtils.base64Encode(data).replace("/r/n", "");
			byte[] reData = FwHttpUtils.allSend(hdUrl+code, dataStr.getBytes(IContants.CHAR_ENCODING));
			responeStr = new String(CodeUtils.decrypt(key.getBytes(IContants.CHAR_ENCODING), CodeUtils.base64Decode(reData)),IContants.CHAR_ENCODING);
			logger.debug("*********"+moduleName+"接收短信系统返回字符串："+responeStr);
		}catch(IOException e){
			logger.error("********* "+ moduleName + "到短信系统网络异常:" + e.getMessage() +" *********" );
			responeStr = "9008";
		}catch (Exception e) {
			logger.error("********* "+ moduleName + "到短信系统未知异常:" + e.getMessage() +" *********" );
			responeStr = "9999";
		}
		return responeStr;
	}
	
	
	/** 
	 * 组装短信时长分配的请求
	 * @param mainAcc
	 * @param accounts
	 * @param count
	 * @return 
	 * @create  2016年3月4日 上午9:57:56 lixing
	 * @history  
	 */
	public static Map<String,Object> splitRequestParamOfSms(String mainAcc,String accounts,Integer count){
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> re_map = new HashMap<String, Object>();
		String version = ConfigInfoUtils.getStringValue(BaseSend.SMS_BEAT_VERSION);
		map.put("version", version);
		map.put("timestamp", DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		map.put("mainAct", mainAcc);
		map.put("accounts",accounts);
		map.put("count", count);
		re_map.put("count_allot", map);
		return re_map;
   }
	
	/** 
	 * 组装短信时长回收的请求
	 * @param mainAcc
	 * @param accounts
	 * @param count
	 * @return 
	 * @create  2016年3月4日 上午9:57:56 lixing
	 * @history  
	 */
	public static Map<String,Object> splitRequestParamOfSmsRec(String mainAcc,String accounts,Integer count){
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> re_map = new HashMap<String, Object>();
		String version = ConfigInfoUtils.getStringValue(BaseSend.SMS_BEAT_VERSION);
		map.put("version", version);
		map.put("timestamp", DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		map.put("mainAct", mainAcc);
		map.put("accounts",accounts);
		map.put("count", count);
		re_map.put("count_regain", map);
		return re_map;
   }
	
}
