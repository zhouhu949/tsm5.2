package com.qftx.common.cached;

import com.alibaba.fastjson.JSON;
import com.qftx.base.auth.bean.OrgGroup;
import com.qftx.base.auth.bean.OrgGroupUser;
import com.qftx.tsm.option.bean.DataDictionaryBean;

import java.util.List;
import java.util.Map;

/**
 * User£º bxl
 * Date£º 2015/11/19
 * Time£º 17:32
 */
public class TestYunCached {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            String key = "1_" + i;
           CachedUtil.getInstance().set(key, "i_" + i, 6);//Ãë
        }
        Thread.sleep(1000);
        for (int i = 0; i < 10; i++) {
            System.out.println(CachedUtil.getInstance().get("1_" + i));
        }
        System.out.println("--------------------------------------");
        Thread.sleep(5000);
        for (int i = 0; i < 10; i++) {
            System.out.println(CachedUtil.getInstance().get("1_" + i));
        }
     //  CachedUtil.getInstance().disConnect();
        String orgId="yfts";
      //  List<OrgGroup> list = (List<OrgGroup>) CachedUtil.getInstance().get(CachedNames.ORG_GROUP + CachedNames.SEPARATOR + "yfts");
        List<DataDictionaryBean> list = (List<DataDictionaryBean>) CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + CachedNames.DATADICTIONARY);

        System.out.println("list=" + JSON.toJSONString(list, true));
        Map<String, String> map = (Map<String, String>)  CachedUtil.getInstance().get("read_db_setting");
        System.out.println("map="+JSON.toJSONString(map,true));
    }
}
