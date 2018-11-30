package com.qftx.tsm.rest.http;

import com.qftx.base.util.HttpUtil;

import java.util.HashMap;


/**
 * User： bxl
 * Date： 2015/11/19
 * Time： 12:30
 */
public class TestHttpQueryTest {
    public static void main(String[] args) throws Exception {
        String url = "http://localhost:6063/callReport/queryTest1";
      //  url = "http://total6063.test.com/callReport/queryTest1";
        System.out.println(HttpUtil.doPost(url, new HashMap<String, String>()));
    }


}
