package com.qftx.tsm.rest.scontrol;

import com.qftx.base.auth.bean.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

/**
 * Created by Administrator on 2015-10-18.
 */
@RestController
@RequestMapping("/rest")
public class UserController {
    @RequestMapping()
    public Callable<User> nullRest() {
        System.out.println("=====请求异步返回参数,2s返回");
        return new Callable<User>() {
            public User call() throws Exception {
                Thread.sleep(2L * 1000); //暂停两秒
                User user = new User();
                user.setUserId("" + 1L);
                user.setUserName("324");
                return user;
            }
        };
    }
    @RequestMapping("/api")
    public Callable<User> api() {
        System.out.println("=====请求异步返回参数,2s返回");
        return new Callable<User>() {
            public User call() throws Exception {
                Thread.sleep(2L * 1000); //暂停两秒
                User user = new User();
                user.setUserId("" + 1L);
                user.setUserName("haha");
                return user;
            }
        };
    }

    @RequestMapping("/test")
    public User view() {
        User user = new User();
        user.setUserId("" + 1L);
        user.setUserName("haha");
        return user;
    }

    @RequestMapping("/test2")
    public String view2() {
        return "{\"id\" : 1}";
    }

}