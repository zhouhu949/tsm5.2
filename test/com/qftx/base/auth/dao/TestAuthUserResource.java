package com.qftx.base.auth.dao;

import com.qftx.base.auth.bean.User;
import com.qftx.base.auth.dto.Menu;
import com.qftx.base.auth.service.ProductMenuService;
import com.qftx.base.auth.service.UserService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.util.MenuUtil;
import com.qftx.common.BaseUtest;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by bxl on 2015/10/28.
 */

public class TestAuthUserResource extends BaseUtest {

    @Test
    public void testLogin() {
    	ProductMenuService service = (ProductMenuService) context.getBean("menuService");
        UserService userservice  = (UserService)context.getBean("userService");
        User uer= userservice.getByAccount("phone001");
        ShiroUser shiroUser = new ShiroUser();
        shiroUser.setIsBackground(uer.getIsbackground());
        shiroUser.setId(uer.getUserId() );

        LinkedHashMap<String, Menu> menu1Map = service.getMenuTree(shiroUser,0);
        List<Menu> list = new ArrayList<Menu>();
        for (String s : menu1Map.keySet()) {
            list.add(menu1Map.get(s));
        }

        Collections.sort(list, new MenuUtil());
        System.out.println(list.size());
    }
}
