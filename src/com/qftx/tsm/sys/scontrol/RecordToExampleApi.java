package com.qftx.tsm.sys.scontrol;

import com.alibaba.fastjson.JSON;
import com.qftx.base.util.DateUtil;
import com.qftx.common.util.CodeUtils;
import com.qftx.common.util.ConfigInfoUtils;
import com.qftx.common.util.FwHttpUtils;
import com.qftx.common.util.IContants;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//录音转范例
public class RecordToExampleApi {
	private static Logger logger = Logger.getLogger(RecordToExampleApi.class);

	/**
	 * 通话转化录音范例 (目前只支持阿里云，本地部署不支持)
	 * 
	 * @param orgId
	 *            单位id
	 * @param code
	 *            录音服务器编码
	 * @param callId
	 * @param attachParam
	 * @return
	 * @create 2016年12月15日 下午2:41:02 Administrator
	 * @history
	 */
	public static String convertCallRecord(String orgId, String code, String callId, String attachParam) {
		String url = ConfigInfoUtils.getStringValue("record_to_example_url")+"/app";;
		String key = ConfigInfoUtils.getStringValue("record_to_example_key");
		String resultCode = "0"; // 0 -成功，-1 失败
		Map<String, Map<String, Object>> sendMap = new HashMap<String, Map<String, Object>>();
		try {
			logger.debug("请求地址 url="+url+",key="+key);
			Date nowTimeDate = new Date(); // 当前时间
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("timestamp", DateUtil.formatDate(nowTimeDate, DateUtil.Data_ALL));
			paramMap.put("recordCode", code);
			paramMap.put("compId", orgId);
			paramMap.put("attachParam", attachParam);
			paramMap.put("callId", callId);
			sendMap.put("callRecord_2_demo", paramMap);
			String jsonStr = com.alibaba.fastjson.JSONObject.toJSONString(sendMap);
			logger.debug("**********[通话转化录音范例]发送修改信息===>" + jsonStr);
			byte[] data = CodeUtils.encrypt(key, jsonStr);
			String dataStr = CodeUtils.base64Encode(data).replace("/r/n", "");
			byte[] reData = FwHttpUtils.allSend(url, dataStr.getBytes(IContants.CHAR_ENCODING));
			String reJsonStr = new String(CodeUtils.decrypt(key.getBytes(IContants.CHAR_ENCODING), CodeUtils.base64Decode(reData)), IContants.CHAR_ENCODING);
			logger.debug("**********[通话转化录音范例]返回结果===>" + reJsonStr);
			Map reMap = (Map) com.alibaba.fastjson.JSONObject.toJavaObject(JSON.parseObject(reJsonStr), Map.class);
			resultCode = reMap.get("code").toString();
			String descr = reMap.get("descr").toString();
			if (!resultCode.equals("_SUCCESS") && !"noAliYunRecord".equals(resultCode)) {
				logger.debug("**********[通话转化录音范例]失败（code：" + resultCode + "；descr：" + descr + "）");
				resultCode = "-1";
			} else {
				resultCode = "0";
			}
		} catch (IOException e) {
			logger.error("**********[通话转化录音范例]失败 ：请求地址 url="+url+",key="+key +"param="+com.alibaba.fastjson.JSONObject.toJSONString(sendMap)+" message：" + e.getMessage(), e);
			resultCode = "-1";
		} catch (Exception e) {
			logger.error("**********[通话转化录音范例]失败 ：请求地址 url="+url+",key="+key +"param="+com.alibaba.fastjson.JSONObject.toJSONString(sendMap)+" message：" + e.getMessage(), e);
			resultCode = "-1";
		}
		return resultCode;
	}

	// 删除范例
	public static String deleteExample(String orgId, String params) {
		String ids = "";
		String url = ConfigInfoUtils.getStringValue("record_to_example_url")+"/app";
		String key = ConfigInfoUtils.getStringValue("record_to_example_key");
		logger.debug("请求地址 url="+url+",key="+key);
		String[] arrays = params.split("\\,");
		Map<String, Map<String, Object>> sendMap = new HashMap<String, Map<String, Object>>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Date nowTimeDate = new Date(); // 当前时间
		if (params != null && params.length() > 0) {
			try {
				for (String str : arrays) {
					if (!str.contains("aliyun")) {
						continue;
					}
					paramMap.put("timestamp", DateUtil.formatDate(nowTimeDate, DateUtil.Data_ALL));
					paramMap.put("recordCode", str.split("_")[1]);
					paramMap.put("compId", orgId);
					paramMap.put("callId", str.split("_")[0]);
					sendMap.put("recordDemo_del", paramMap);
					String jsonStr = com.alibaba.fastjson.JSONObject.toJSONString(sendMap);
					logger.debug("**********[删除录音范例]发送修改信息===>" + jsonStr);
					byte[] data = CodeUtils.encrypt(key, jsonStr);
					String dataStr = CodeUtils.base64Encode(data).replace("/r/n", "");
					byte[] reData = FwHttpUtils.allSend(url, dataStr.getBytes(IContants.CHAR_ENCODING));
					String reJsonStr = new String(CodeUtils.decrypt(key.getBytes(IContants.CHAR_ENCODING), CodeUtils.base64Decode(reData)),
							IContants.CHAR_ENCODING);
					logger.debug("**********[删除录音范例]返回结果===>" + reJsonStr);
					Map reMap = (Map) com.alibaba.fastjson.JSONObject.toJavaObject(JSON.parseObject(reJsonStr), Map.class);
					String resultCode = reMap.get("code").toString();
					String descr = reMap.get("descr").toString();
					if (!resultCode.equals("_SUCCESS")) {
						logger.debug("**********[删除录音范例]认失败（code：" + resultCode + "；descr：" + descr + "）");
					} else {
						ids = ids + ",";
					}
				}
			} catch (IOException e) {
				logger.error("**********[删除录音范例]失败 ：请求地址 url="+url+",key="+key +"param="+com.alibaba.fastjson.JSONObject.toJSONString(sendMap)+" message：" + e.getMessage(), e);
			} catch (Exception e) {
				logger.error("**********[删除录音范例]失败 ：请求地址 url="+url+",key="+key +"param="+com.alibaba.fastjson.JSONObject.toJSONString(sendMap)+" message：" + e.getMessage(), e);
			}
		}
		return ids;
	}

	public static void main(String arg[]) {
		String messageString =convertCallRecord( "sy4", "aliyunoss0000001", "158d505dfc33e90bfd3933579bc540a6", "161228&type=1");
		//String ids = deleteExample("qftx", "cc03a4a813b34aaa9a2a4a1590ddcfd5_aaaaaaaaaaaaaaaa");
		System.out.println("messageString=" + messageString);
	}
}
