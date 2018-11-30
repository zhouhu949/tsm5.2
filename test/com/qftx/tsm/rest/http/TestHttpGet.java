package com.qftx.tsm.rest.http;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.util.HttpUtil;
import com.qftx.base.util.JsonUtil;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


/**
 * User： bxl
 * Date： 2015/11/19
 * Time： 12:30
 */
public class TestHttpGet {
    public static void main(String[] args) {
       String rest= doGet("http://121.196.225.128/test/logininfo",new HashMap<String, Object>());
        System.out.println(rest);
    }

    public static String doGet(String url, Map<String, Object> params) {
        String apiUrl = url;
        StringBuffer param = new StringBuffer();
        int i = 0;
        for (String key : params.keySet()) {
            if (i == 0)
                param.append("?");
            else
                param.append("&");
            param.append(key).append("=").append(params.get(key));
            i++;
        }
        apiUrl += param;
        String result = null;
        HttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpPost = new HttpGet(apiUrl);
            HttpResponse response = httpclient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();

            System.out.println("执行状态码 : " + statusCode);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream instream = entity.getContent();
                result = IOUtils.toString(instream, "UTF-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void processLogin() {
        String json = HttpUtil.sendGet("http://121.196.225.128/test/logininfo", null);
        System.out.println("json=" + json);
        try {

            ShiroUser user = (ShiroUser) JsonUtil.getObjectJsonString(json, ShiroUser.class);
            if (user != null) {
                UsernamePasswordToken token = new UsernamePasswordToken(user.getAccount(), user.getPassword().toCharArray());
                //   UsernamePasswordToken token = new UsernamePasswordToken();
                Subject userinfo = SecurityUtils.getSubject();
                //  token.setUsername(account);
                //   token.setPassword(password.toCharArray());
                token.setRememberMe(true);
                userinfo.login(token);
            }
        } catch (AuthenticationException e) {
e.printStackTrace();
        }
    }


}
