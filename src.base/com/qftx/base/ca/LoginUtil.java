package com.qftx.base.ca;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qftx.base.ca.bean.CaDataBean;
import com.qftx.common.util.*;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * User： bxl
 * Date： 2016/1/18
 * Time： 10:15
 */
public class LoginUtil {
    private static Logger logger = Logger.getLogger(LoginUtil.class);

    public static CaDataBean getValidate(String account, String key) throws Exception {
        String message_des_key = ConfigInfoUtils.getStringValue("auth_des_key");
        String authUrl = ConfigInfoUtils.getStringValue("auth_url")+"?code="+ ConfigInfoUtils.getStringValue("auth_code");
        // String message_des_key = "?i<BQbx>";
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("timestamp", com.qftx.common.util.DateUtil.format(new Date(), com.qftx.common.util.DateUtil.hour24HMSPattern));
        paramMap.put("account", account);
        String timestamp = com.qftx.common.util.DateUtil.format(new Date(), com.qftx.common.util.DateUtil.hour24Pattern);
        logger.debug("客户端登录传过来的key=" + key);
        key = MD5Utils.getMD5String(timestamp + key);
        logger.debug("客户端登录key加密后=" + key);
        paramMap.put("key", key);   //MD5("HYXWEB" + "20150412154235" + MD5("123456"))
        Map<String, Object> sendMap = new HashMap<String, Object>();
        sendMap.put("key_validate", paramMap);
        String jsonStr = JSONObject.toJSONString(sendMap, true);
        logger.info("**********[PC请求WEB--->WEB验证登录]发送认证信息===>\n" + jsonStr);
        logger.debug("message_des_key=" + message_des_key);
        byte[] data = CodeUtils.encrypt(message_des_key, jsonStr);
        String dataStr = CodeUtils.base64Encode(data).replace("/r/n", "");
        // String url = "http://192.168.1.15:9979/hyxce/app?code=83235f0df36b8521";
        logger.debug("验证地址:" + authUrl);
        byte[] reData = FwHttpUtils.allSend(authUrl, dataStr.getBytes("UTF-8"));
        String retString = new String(CodeUtils.decrypt(message_des_key.getBytes(), CodeUtils.base64Decode(reData)), "UTF-8");
        // logger.debug("验证数据:\n" + new String(reData));
        logger.debug("验证结果:" + retString);
        CaDataBean retBean = JSON.parseObject(retString, CaDataBean.class);
        logger.debug("结果:" + retBean.getCode());
        return retBean;
    }

    public static CaDataBean getWebLoginCA(String username, String password)  {
        CaDataBean retBean = null;
        try {
            String auth_code = ConfigInfoUtils.getStringValue("auth_code");                                 //认证code
            String web_login_auth_url = ConfigInfoUtils.getStringValue("auth_url");            //后台登陆认证URL
            String message_des_key = ConfigInfoUtils.getStringValue("auth_des_key");
            logger.debug("auth_code=" + auth_code);
            logger.debug("web_login_auth_url=" + web_login_auth_url);
            logger.debug("message_des_key=" + message_des_key);
            Date nowTimeDate = new Date();                                                                    //当前时间
            String timestamp = DateUtil.format(nowTimeDate, DateUtil.hour24Pattern);                        //时间戳（用于MD5加密）
            Map<String, Map<String, String>> sendMap = new HashMap<String, Map<String, String>>();
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("timestamp", DateUtil.format(nowTimeDate, DateUtil.hour24HMSPattern));
            paramMap.put("account", username);
            paramMap.put("password", MD5Utils.getMD5String(timestamp + MD5Utils.getMD5String(password)));   //MD5("HYXWEB" + "20150412154235" + MD5("123456"))
            sendMap.put("login", paramMap);
            String jsonStr = JSONObject.toJSONString(sendMap, true);
            logger.info("**********[后台登陆认证]登陆发送认证信息===>\n" + jsonStr);
            byte[] data = CodeUtils.encrypt(message_des_key, jsonStr);
            String dataStr = CodeUtils.base64Encode(data).replace("/r/n", "");
            String url = web_login_auth_url + "?code=" + auth_code;
            logger.debug("地址:" + url);
            byte[] reData = FwHttpUtils.allSend(url, dataStr.getBytes("UTF-8"));
            String retString = new String(CodeUtils.decrypt(message_des_key.getBytes(), CodeUtils.base64Decode(reData)), "UTF-8");
            logger.debug("原始返回:\n" + new String(reData));
            logger.debug("解码:\n" + retString);
            retBean = JSON.parseObject(retString, CaDataBean.class);
            logger.debug("结果:" + retBean.getCode());
        } catch (Exception e) {
            logger.error("验证登录权限失败:" + e.getMessage(), e);
            retBean = new CaDataBean();
            retBean.setCode("_ERROR");
            retBean.setDescr("失败:"+e.getMessage());
        }
        return retBean;
    }
}
