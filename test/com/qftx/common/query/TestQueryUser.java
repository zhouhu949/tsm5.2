package com.qftx.common.query;

import com.qftx.base.auth.bean.User;
import com.qftx.base.auth.service.UserService;
import com.qftx.base.util.JsonUtil;
import com.qftx.base.util.TestInit;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * Created by bxl on 2015/9/18.
 */
public class TestQueryUser {
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        ApplicationContext ac = TestInit.getApplicationContext();
        UserService flowService = (UserService) ac.getBean("userService");
        User userBean=new User();
        List<User> list = flowService.getListPage(userBean);
        System.out.println(userBean.getPage().getTotalPage());
        System.out.println(JsonUtil.getJsonString(list));
        for (User flow : list) {
            if (flow != null){
                System.out.println(flow.getOrgId() + "|" + flow.getUserId());
            }
        }
        System.out.println("time=" + (System.currentTimeMillis() - start));
    }

}
