package com.qftx.tsm.call;

import com.alibaba.fastjson.JSON;
import com.qftx.base.util.HttpUtil;
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
public class TestHttpCallQueryUserNum {
    public static void main(String[] args) throws Exception {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("orgId", "hyxaly");
        map.put("startDate", "2016-01-14 00:00:00");
        map.put("endDate", "2016-03-18 23:59:59");
        //  map.put("id", "1111");


        String rest = HttpUtil.doPost("http://call.qftx.net/service/queryUserNum", map);
       //  rest = HttpUtil.doPost("http://localhost:6090/service/queryUserNum", map);
        System.out.println(rest);
    }



}
