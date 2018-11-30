package com.qftx.common.drds.query;

import com.qftx.base.auth.bean.User;
import com.qftx.base.auth.service.UserService;
import com.qftx.base.util.JsonUtil;
import com.qftx.base.util.TestInit;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * Created by bxl on 2015/9/18.
 */
public class TestQueryAuthUser {
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        ApplicationContext ac = TestInit.getApplicationContext();
        UserService flowService = (UserService) ac.getBean("userService");
       // flowService.get("234");
        List<User> list = flowService.getList();


       // list = flowService.findAll("d9fd7d961f90404387a2910372ece37d");


        System.out.println(JsonUtil.getJsonString(list));
        for (User flow : list) {
            if (flow != null)
                System.out.println(flow.getOrgId()+"|"+flow.getUserId());
        }
        System.out.println("time=" + (System.currentTimeMillis() - start));
    }

}
