package com.qftx.base.auth.dao;

import com.alibaba.fastjson.JSON;
import com.qftx.base.auth.dto.Menu;
import com.qftx.base.auth.service.ProductMenuService;
import com.qftx.base.util.JsonUtil;
import com.qftx.base.util.MenuUtil;
import com.qftx.common.BaseUtest;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * User�� bxl
 * Date�� 2016/3/4
 * Time�� 13:37
 */
public class TestMenuList extends BaseUtest {
    private static final Logger logger = Logger.getLogger(TestMenuList.class.getName());
    @Autowired
    private ProductMenuService productMenuService;


    @Test
    @Rollback(true)
    public void testQueryMenuByUser() {
        //   ShiroUser shiroUser = ShiroUtil.getShiroUser();

        logger.debug("shiroUser=" + JsonUtil.getJsonString(shiroUser));
        LinkedHashMap<String, Menu> menu1Map = productMenuService.getMenuTree(shiroUser, 0);
        logger.debug("menu1Map=" + menu1Map.size());
        List<Menu> list = new ArrayList<Menu>();
        for (String s : menu1Map.keySet()) {
            list.add(menu1Map.get(s));
        }
        logger.debug("list=" + list.size());
        Collections.sort(list, new MenuUtil());
        System.out.println(JSON.toJSONString(list,true));
    }
}
