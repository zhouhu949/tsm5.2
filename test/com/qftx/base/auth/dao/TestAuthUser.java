package com.qftx.base.auth.dao;

import com.qftx.base.auth.bean.User;
import com.qftx.base.auth.service.UserService;
import com.qftx.common.BaseUtest;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

/**
 * Created by bxl on 2015/10/28.
 */

public class TestAuthUser extends BaseUtest {

    @Test
    public void testLogin() {
        UserService service  = (UserService)context.getBean("userService");
       // User authUser= service.getByAccount("admin");
        User authUser= service.getByAccount("hn001");
       //  authUser= service.findByLogin("admin","123456");
        System.out.println(authUser.getUserPassword());
        System.out.println(authUser.getIsbackground());
        System.out.println("groupid="+authUser.getGroupId());
        System.out.println(authUser.getIsunit());
        List<User> list= service.getListPage(authUser);
        System.out.println(service.queryPageToJson(new HashMap<String, String>()).size());

    }
}
