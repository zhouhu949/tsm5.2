package com.qftx.common.cached;

import com.qftx.base.util.GuidUtil;
import com.qftx.common.BaseUtest;
import com.qftx.tsm.sys.bean.CustFieldSet;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * User£º bxl
 * Date£º 2015/11/19
 * Time£º 17:32
 */
public class TestCachedTime {
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
       CachedUtil.getInstance().disConnect();
    }
}
