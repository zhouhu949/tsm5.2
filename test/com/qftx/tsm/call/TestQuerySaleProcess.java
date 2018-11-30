package com.qftx.tsm.call;

import com.alibaba.fastjson.JSON;
import com.qftx.base.util.HttpUtil;

import java.util.HashMap;

/**
 * User£º bxl
 * Date£º 2016/5/23
 * Time£º 9:52
 */
public class TestQuerySaleProcess {
    public static void main(String[] args) throws Exception {
        HashMap<String,String> map=new HashMap<String,String>();
        map.put("orgId","zgp");
       // map.put("orgId","8decbe1278b646b5a462bbd4bc80bd58");
        //  map.put("account","hyx41001");
        // String   str = HttpUtil.doPostJSON("http://localhost:6060/service/queryTelPhoneDisplay", JSON.toJSONString(map));
        // String   str = HttpUtil.doPostJSON("http://localhost:6060/service/queryTelPhoneDisplay", JSON.toJSONString(map));
        //String   str = HttpUtil.doPostJSON("http://localhost:6060/service/getUserInfo", JSON.toJSONString(map));
        // String   str = HttpUtil.doPostJSON("http://quick.qftx.net/service/getUserInfo", JSON.toJSONString(map));
      //  String   str = HttpUtil.doPostJSON("http://114.55.34.213:6060/service/querySaleProcess", JSON.toJSONString(map));
      String str = HttpUtil.doPost("http://quick.qftx.net/service/querySaleProcess", map);
      //  String  str = HttpUtil.doPost("http://localhost:6060/service/querySaleProcess", map);
        System.out.println( JSON.toJSONString(map));
        System.out.println(str);
    }
}
