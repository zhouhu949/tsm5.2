package com.qftx.base.auth.ca;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
public class TestCA extends BaseUtest {
    private static Logger logger = Logger.getLogger(TestCA.class);

    /**
     * 模拟WEB端后台登录
     *
     * @throws Exception
     */
    @Test
    public void testWebLoginCA() throws Exception {
        String username = "bxl001";
        String password = "123456";
        String auth_code = ConfigInfoUtils.getStringValue("auth_code");                                 //认证code
        String web_login_auth_url = ConfigInfoUtils.getStringValue("auth_url");            //后台登陆认证URL
        String message_des_key = ConfigInfoUtils.getStringValue("auth_des_key");
        Date nowTimeDate = new Date();                                                                    //当前时间
        String timestamp = DateUtil.format(nowTimeDate, DateUtil.hour24Pattern);                        //时间戳（用于MD5加密）
        Map<String, Map<String, String>> sendMap = new HashMap<String, Map<String, String>>();

        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("version", ConfigInfoUtils.getStringValue("auth_version"));
        paramMap.put("timestamp", DateUtil.format(nowTimeDate, DateUtil.hour24HMSPattern));
        paramMap.put("account", username);
        paramMap.put("password", MD5Utils.getMD5String("HYXWEB" + timestamp + MD5Utils.getMD5String(password)));   //MD5("HYXWEB" + "20150412154235" + MD5("123456"))

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

    /**
     * 模拟PC客户端登录
     *
     * @throws Exception
     */
    @Test
    public void testCleintLoginCA() throws Exception {
        String username = "bxl001";
        String password = "123456";
        String auth_code = "v=3.6";                                 //认证code
        String web_login_auth_url = ConfigInfoUtils.getStringValue("auth_url");            //后台登陆认证URL
        String message_des_key = "ApM!i$^,";
        Date nowTimeDate = new Date();                                                                    //当前时间
        String timestamp = DateUtil.format(nowTimeDate, DateUtil.hour24Pattern);                        //时间戳（用于MD5加密）
        Map<String, Map<String, String>> sendMap = new HashMap<String, Map<String, String>>();
        InetAddress addr = InetAddress.getLocalHost();
        String ip = addr.getHostAddress().toString();
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("version", ConfigInfoUtils.getStringValue("auth_version"));
        paramMap.put("timestamp", DateUtil.format(nowTimeDate, DateUtil.hour24HMSPattern));
        paramMap.put("account", username);
        paramMap.put("ip", ip);
        paramMap.put("password", MD5Utils.getMD5String("HYXPC" + timestamp + MD5Utils.getMD5String(password)));   //MD5("HYXWEB" + "20150412154235" + MD5("123456"))

        sendMap.put("login", paramMap);
        String jsonStr = JSONObject.toJSONString(sendMap, true);
        logger.info("**********[后台登陆认证]登陆发送认证信息===>\n" + jsonStr);
        byte[] data = CodeUtils.encrypt(message_des_key, jsonStr);
        String dataStr = CodeUtils.base64Encode(data).replace("/r/n", "");
        byte[] reData = FwHttpUtils.allSend("http://192.168.1.13:9979/hyxce/pc?v=3.6", dataStr.getBytes("UTF-8"));
        logger.debug("ca1:\n" + new String(reData));
        logger.debug("ca2:\n" + new String(CodeUtils.decrypt(message_des_key.getBytes(), CodeUtils.base64Decode(reData)), "UTF-8"));

    }


    /**
     * 以下是模拟登录，再去验证
     */
    @Test
    public void testCleintLoginToWebCA() throws Exception {

        String username = "bxl001";
        String password = "123456";
        String auth_code = "v=3.6";                                 //认证code
        String web_login_auth_url = ConfigInfoUtils.getStringValue("auth_url");            //后台登陆认证URL
        String message_des_key = "ApM!i$^,";
        Date nowTimeDate = new Date();                                                                    //当前时间
        String timestamp = DateUtil.format(nowTimeDate, DateUtil.hour24Pattern);                        //时间戳（用于MD5加密）
        Map<String, Map<String, String>> sendMap = new HashMap<String, Map<String, String>>();
        InetAddress addr = InetAddress.getLocalHost();
        String ip = addr.getHostAddress().toString();
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("version", ConfigInfoUtils.getStringValue("auth_version"));
        paramMap.put("timestamp", DateUtil.format(nowTimeDate, DateUtil.hour24HMSPattern));
        paramMap.put("account", username);
        paramMap.put("ip", ip);
        paramMap.put("password", MD5Utils.getMD5String("HYXPC" + timestamp + MD5Utils.getMD5String(password)));   //MD5("HYXWEB" + "20150412154235" + MD5("123456"))

        sendMap.put("login", paramMap);
        String jsonStr = JSONObject.toJSONString(sendMap, true);
        logger.info("**********[PC登陆认证]登陆发送认证信息===>\n" + jsonStr);
        byte[] data = CodeUtils.encrypt(message_des_key, jsonStr);
        String dataStr = CodeUtils.base64Encode(data).replace("/r/n", "");
        byte[] reData = null;
       reData = FwHttpUtils.allSend("http://192.168.1.13:9979/hyxce/pc?v=3.6", dataStr.getBytes("UTF-8"));
       // logger.debug("登录数据:\n" + new String(reData));
        String ret=new String(CodeUtils.decrypt(message_des_key.getBytes(), CodeUtils.base64Decode(reData)), "UTF-8");
       logger.debug("登录结果:\n" + ret);
        logger.debug("-------------------------------------------------------------------------------");
        CaDataBean caDataBean=  JSON.parseObject(ret, CaDataBean.class);
        message_des_key = "?i<BQbx>";
        auth_code = "83235f0df36b8521";
        paramMap.clear();
        sendMap.clear();
        paramMap.put("version", ConfigInfoUtils.getStringValue("auth_version"));
        paramMap.put("timestamp", DateUtil.format(nowTimeDate, DateUtil.hour24HMSPattern));
        paramMap.put("account", username);
        paramMap.put("key", MD5Utils.getMD5String("HYXWEB" + timestamp +caDataBean.getBasicInfo().getKey()));   //MD5("HYXWEB" + "20150412154235" + MD5("123456"))
        sendMap.put("key_validate", paramMap);
        jsonStr = JSONObject.toJSONString(sendMap, true);
        logger.info("**********[PC请求WEB--->WEB验证登录]发送认证信息===>\n" + jsonStr);
        data = CodeUtils.encrypt(message_des_key, jsonStr);
        dataStr = CodeUtils.base64Encode(data).replace("/r/n", "");
        String url = web_login_auth_url + "?code=" + auth_code;
        logger.debug("验证地址:" + url);
        reData = FwHttpUtils.allSend(url, dataStr.getBytes("UTF-8"));
       // logger.debug("验证数据:\n" + new String(reData));
        logger.debug("验证结果:" + new String(CodeUtils.decrypt(message_des_key.getBytes(), CodeUtils.base64Decode(reData)), "UTF-8"));
    }
}
