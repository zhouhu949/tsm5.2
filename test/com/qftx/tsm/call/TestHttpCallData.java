package com.qftx.tsm.call;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * User： bxl
 * Date： 2015/11/19
 * Time： 12:30
 */
public class TestHttpCallData {
    public static void main(String[] args) {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("orgId", "1");
        //  map.put("id", "1111");
        String rest = doPost("http://192.168.1.210:8080/cr/add", map);
        System.out.println(rest);
        //  rest = doGet("http://192.168.1.210:8080/cr/add", map);
        //System.out.println(rest);
    }

    public static String doGet(String url, Map<String, String> params) {
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

    public static String doPost(String url, Map<String, String> params) {
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        for (String s : params.keySet()) {
            list.add(new BasicNameValuePair(s, params.get(s)));
        }
        String result = null;
        HttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(list));
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


    public static String doPostJSON(String url,  Map<String, String>  map) throws Exception{
        return doPostJSON(url, JSON.toJSONString(map));
    }
    public static String doPostJSON(String url, String json) throws Exception {
        System.out.println("发送数据:"+json);
        String result = null;
        final String APPLICATION_JSON = "application/json";
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        //  httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);
        // StringEntity se = new StringEntity(new String(json.getBytes("UTF-8")));
        StringEntity se = new StringEntity(json, "UTF-8");
        se.setContentType(APPLICATION_JSON);
        httpPost.setEntity(se);
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            InputStream instream = entity.getContent();
            result = IOUtils.toString(instream, "UTF-8");
        }
        return result;
    }

}
