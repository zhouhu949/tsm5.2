package com.qftx.base.auth.ca;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qftx.base.ca.LoginUtil;
import com.qftx.base.ca.bean.CaDataBean;
import com.qftx.common.BaseUtest;
import com.qftx.common.util.*;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * User： bxl
 * Date： 2015/12/31
 * Time： 9:57
 */
public class TestWebAuth extends BaseUtest {
    private static Logger logger = Logger.getLogger(TestWebAuth.class);

    /**
     * 模拟WEB端后台登录
     *
     * @throws Exception
     */
    @Test
    public void testWebLoginCA() throws Exception {
        String username = "qhcs002";
        String password = "123456";
        String auth_code = ConfigInfoUtils.getStringValue("auth_code");                                 //认证code
        String web_login_auth_url = ConfigInfoUtils.getStringValue("auth_url");            //后台登陆认证URL
        String message_des_key = ConfigInfoUtils.getStringValue("auth_des_key");
        logger.debug("auth_code="+auth_code);
        logger.debug("web_login_auth_url="+web_login_auth_url);
        logger.debug("message_des_key="+message_des_key);
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
        logger.debug("原始返回:\n" + new String(reData));
        logger.debug("解码:\n" + new String(CodeUtils.decrypt(message_des_key.getBytes(), CodeUtils.base64Decode(reData)), "UTF-8"));
    }
    @Test
    public void webCa(){
        CaDataBean bean=   LoginUtil.getWebLoginCA("qhcs002", "123456");
       logger.debug(JSON.toJSONString(bean,true));
    }
}
