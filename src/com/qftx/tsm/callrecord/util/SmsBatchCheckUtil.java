package com.qftx.tsm.callrecord.util;

import com.qftx.base.auth.bean.User;
import com.qftx.base.auth.service.UserService;
import com.qftx.common.util.RegexUtil;
import com.qftx.common.util.constants.AppConstant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 批量发送短信 【参数验证】
 * @author: zwj
 * @since: 2016-1-12  上午11:15:48
 * @history: 3.x
 */
public class SmsBatchCheckUtil {
	protected static Log logger = LogFactory.getLog(SmsBatchCheckUtil.class);
	
	/** 请求参数验证 
	 * @param smslabel           		签名
	 * @param smsContent      		短信内容
	 * @param userAccount		 	发送人账号
	 * @param name_mobile 			接收人名称_电话
	 * @channelSelect 0:手机卡，其余为渠道
	 * */
	public static Map<String,Object> checkParam4Send(String orgId,String smslabel,String smsContent ,
			String userAccount,String name_mobile,UserService userService){
	
		Map<String,Object> reqMap = new HashMap<String, Object>();
		User user = new User();
		user.setOrgId(orgId);
		user.setUserAccount(userAccount);
		user = userService.getByCondtion(user);
		if (user == null) {
			logger.error("*****【 发送短信】  账号：【" + userAccount + "】 用户不存在！！ *****");
			reqMap.put("code", "9001");
			return reqMap;
		}	
		reqMap.put("user", user);
		List<String> name_mobileViladList = new ArrayList<String>();
		List<String> name_mobileNoViladList = new ArrayList<String>();
		// 号码判断
		checkPhone(name_mobile, name_mobileViladList, name_mobileNoViladList);
		String noViladName_mobiles = "";
		for (String noViladName_mobile : name_mobileNoViladList) {
			noViladName_mobiles = noViladName_mobiles + noViladName_mobile + "；";
		}
		int receiverNum = name_mobile.split(",").length;
		logger.info("*********【发送短信】  账号：【" + userAccount + "】 。接收人【" + receiverNum + "】个。手机格式不正确接收人：【"
				+ name_mobileNoViladList.size() + "】个。不正确的内容为 ：" + noViladName_mobiles + " *********");
		if (name_mobileViladList.size() == 0) {
			logger.error("*********【 发送短信 】  账号：【" + userAccount + "】 接收人所有手机格式不正确！ *********");
			reqMap.put("code", "9002");
			return reqMap;
		} else if (name_mobileViladList.size() > 10000) {
			logger.error("*********【 发送短信 】  账号：【" + userAccount + "】 接收人总数超过10000！ *********");
			reqMap.put("code", "9005");
			return reqMap;
		}
		// 替换特殊字符
		smsContent = smsContent.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&&gt;")
				.replaceAll("'", "&apos;").replaceAll("\"", "&quot;");
		reqMap.put("smsContent",smsContent);
		reqMap.put("validList", name_mobileViladList);
		return reqMap;
	}
	
	/**
	 * 校验电话 (判断长度为11位的号码)
	 */
	private static void checkPhone(String name_mobile,
			List<String> name_mobileViladList,
			List<String> name_mobileNoViladList) {
		String[] arrays = name_mobile.split(",");
		if (arrays.length < 0) {
			return;
		} else {
			Map<String, String> decideMap = new HashMap<String, String>();
			for (String name_num : arrays) {
				String[] subArrays = name_num.split("\\|");
				if (subArrays.length > 0) {
					if (subArrays.length == 1) {// 没有姓名
						if (RegexUtil.checkPhone(subArrays[0], true, null, 11,
								RegexUtil.mobile) == 0) {
							if (!decideMap.containsKey(subArrays[0])) { // 为去除重复号码
								decideMap.put(subArrays[0], subArrays[0]);
								name_mobileViladList.add(name_num);
							}
						} else {
							name_mobileNoViladList.add(name_num);
							continue;
						}
					} else {
						if (RegexUtil.checkPhone(subArrays[1], true, null, 11,
								RegexUtil.mobile) == 0) {// 有姓名
							if (!decideMap.containsKey(subArrays[1])) { // 为去除重复号码
								decideMap.put(subArrays[1], subArrays[1]);
								name_mobileViladList.add(name_num);
							}
						} else {
							name_mobileNoViladList.add(name_num);
							continue;
						}
					}
				} else {
					continue;
				}
			}
		}
	}
	/**
	 * 计算每条短信可拆分的数量
	 * @param content
	 */
	public static double getSmallSmsTotal(String smsContent) {// 内容已经包括签名
		double sumNum = (smsContent.length()) / AppConstant.maxLen;
		sumNum = Math.ceil(sumNum);
		return sumNum;
	}
	
}
