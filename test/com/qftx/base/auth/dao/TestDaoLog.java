package com.qftx.base.auth.dao;

import com.qftx.base.auth.bean.Org;
import com.qftx.base.auth.service.OrgService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.common.BaseUtest;
import junit.framework.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bxl on 2015/11/2.
 */

public class TestDaoLog extends BaseUtest {
    @Autowired
    private OrgService service;

    @Test
    @Rollback(true)
    public void testQuery() {
        System.out.println(context);
        //    OrgService service = (OrgService) context.getBean("orgService");
        Map<String, String> params = new HashMap<String, String>();
        params.put("orgCode", "");
        params.put("orgName", "e");
        params.put("startDate", "2015-12-10");
        params.put("endDate", "2015-12-12");
        assertEquals("1", "1");
        System.out.println("list=" + service.getListPage(new Org()));
        System.out.println(service.queryPageToJson(new HashMap<String, String>()).size());
    }
    @Test
    public void testLogin() {
        ShiroUser test= ShiroUtil.getShiroUser();
        System.out.println("账号="+test.getAccount());
     }
    @Test
    public void testShiroUser() {
        System.out.println("账号="+ shiroUser.getAccount());
    }
}
