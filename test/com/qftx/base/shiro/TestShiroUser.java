package com.qftx.base.shiro;

import com.qftx.common.BaseUtest;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

/**
 * Created by bxl on 2015/11/2.
 */

public class TestShiroUser extends BaseUtest {


    @Test
    @Rollback(true)
    public void testQuery() {
        System.out.println(context);
        //    OrgService service = (OrgService) context.getBean("orgService");
    }

    @Test
    public void testLogin() {
        ShiroUser test = ShiroUtil.getShiroUser();
        System.out.println("账号=" + test.getAccount());
        System.out.println("用户组=" + test.getGroupId());
        System.out.println("用户组名=" + test.getGroupName());
    }

    @Test
    public void testShiroUser() {
        System.out.println("账号=" + shiroUser.getAccount());
    }
}
