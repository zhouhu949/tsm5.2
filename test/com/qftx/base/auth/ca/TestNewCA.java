package com.qftx.base.auth.ca;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.qftx.base.ca.bean.CaDataBean;
import com.qftx.common.BaseUtest;
import com.qftx.common.util.CodeUtils;
import com.qftx.common.util.DateUtil;
import com.qftx.common.util.FwHttpUtils;
import com.qftx.common.util.MD5Utils;
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
public class TestNewCA extends BaseUtest {
    private static Logger logger = Logger.getLogger(TestNewCA.class);

    /**
     * 以下是模拟登录，再去验证
     */
    @Test
    public void testCleintLoginToWebCA() throws Exception {
        /**
         * 测试帐号：zn001、zn002
         密码皆为：123456
         */

        String auth_url = "http://192.168.1.15:9979/hyxce/app";
        String auth_des_key = "?i<BQbx>";
        String auth_version = "1.1.0";

        String username = "zn001";
        String password = "123456";
        String auth_code = "v=3.6";                                 //认证code
        String web_login_auth_url = auth_url;            //后台登陆认证URL
        String message_des_key = "ApM!i$^,";
        Date nowTimeDate = new Date();                                                                    //当前时间
        String timestamp = DateUtil.format(nowTimeDate, DateUtil.hour24Pattern);                        //时间戳（用于MD5加密）
        Map<String, Map<String, String>> sendMap = new HashMap<String, Map<String, String>>();

        InetAddress addr = InetAddress.getLocalHost();
        String ip = addr.getHostAddress().toString();


        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("version", auth_version);
        paramMap.put("timestamp", DateUtil.format(nowTimeDate, DateUtil.hour24HMSPattern));
        paramMap.put("account", username);
        paramMap.put("innerIp", ip);
        paramMap.put("password", MD5Utils.getMD5String("HYXPC" + timestamp + MD5Utils.getMD5String(password)));   //MD5("HYXWEB" + "20150412154235" + MD5("123456"))
        paramMap.put("password", MD5Utils.getMD5String(timestamp + MD5Utils.getMD5String(password)));   //MD5("HYXWEB" + "20150412154235" + MD5("123456"))

        sendMap.put("login", paramMap);
        String jsonStr = JSONObject.toJSONString(sendMap, true);
        logger.info("**********[PC登陆认证]登陆发送认证信息===>\n" + jsonStr);
        auth_des_key = "=3e.S4%d";
        byte[] data = CodeUtils.encrypt(auth_des_key, jsonStr);
        String dataStr = CodeUtils.base64Encode(data).replace("/r/n", "");
        byte[] reData = null;
        String login_url = "http://192.168.1.13:9979/hyxce/pc?v=3.6";

        login_url = "http://192.168.1.15:9979/hyxce/pc?code=fof97ulwkrcxpupj";
        reData = FwHttpUtils.allSend(login_url, dataStr.getBytes("UTF-8"));
        // logger.debug("登录数据:\n" + new String(reData));


        String ret = new String(CodeUtils.decrypt(auth_des_key.getBytes(), CodeUtils.base64Decode(reData)), "UTF-8");
        HashMap caDataBean = JSON.parseObject(ret, HashMap.class);
        String key = (String) caDataBean.get("key");
        logger.debug("登录结果:\n" + ret);
       logger.debug("-------------------------------------------------------------------------------");
        //解析登录结果
        getValidate(username, key);
    }

    private void getValidate(String account, String key) throws Exception {
        String message_des_key = "?i<BQbx>";
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("timestamp", DateUtil.format(new Date(), DateUtil.hour24HMSPattern));
        paramMap.put("account", account);
        String timestamp = DateUtil.format(new Date(), DateUtil.hour24Pattern);
        logger.debug("客户端登录传过来的key="+key);
        key=MD5Utils.getMD5String(timestamp + key);
        logger.debug("客户端登录key加密后=" + key);
        paramMap.put("key", key);   //MD5("HYXWEB" + "20150412154235" + MD5("123456"))
        Map<String, Object> sendMap = new HashMap<String, Object>();
        sendMap.put("key_validate", paramMap);
        String jsonStr = JSONObject.toJSONString(sendMap, true);
        logger.info("**********[PC请求WEB--->WEB验证登录]发送认证信息===>\n" + jsonStr);
        byte[] data = CodeUtils.encrypt(message_des_key, jsonStr);
        String dataStr = CodeUtils.base64Encode(data).replace("/r/n", "");
        String url = "http://192.168.1.15:9979/hyxce/app?code=83235f0df36b8521";
        logger.debug("验证地址:" + url);
        byte[] reData = FwHttpUtils.allSend(url, dataStr.getBytes("UTF-8"));
        String retString=new String(CodeUtils.decrypt(message_des_key.getBytes(), CodeUtils.base64Decode(reData)), "UTF-8");
        // logger.debug("验证数据:\n" + new String(reData));

        logger.debug("验证结果:" + retString);
        CaDataBean retBean=   JSON.parseObject(retString, CaDataBean.class);
        logger.debug("结果:"+retBean.getCode());
    }
}
