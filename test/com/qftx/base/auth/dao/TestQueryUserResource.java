package com.qftx.base.auth.dao;

import com.qftx.base.auth.bean.User;
import com.qftx.base.auth.dto.Menu;
import com.qftx.base.auth.service.RoleService;
import com.qftx.base.auth.service.UserService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.util.MenuUtil;
import com.qftx.common.BaseUtest;
import com.qftx.common.util.constants.SysConstant;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bxl on 2015/10/28.
 */

public class TestQueryUserResource extends BaseUtest {
    @Autowired
    private AuthProductResourceMapper resourceMapper;
/*    @Autowired
    private ResourceService resourceService;*/
    @Autowired
    private RoleService roleService;

    @Test
    public void testLogin() {
    	 Map<String,Object>map1 = new HashMap<String, Object>();
    	 map1.put("orderStatus", "1");
    	 map1.put("userId", shiroUser.getId());
    	 map1.put("isbackground", shiroUser.getIsBackground());
    	 map1.put("resourceType", "01");
    	 map1.put("state", shiroUser.getIsState());
    	 map1.put("proCode", "hyxzy");
        List<Menu> menuAllList  = resourceMapper.findMenusListByUserId(map1);
        System.out.println("size="+menuAllList.size());
        for (Menu menu : menuAllList) {
            System.out.println(menu.getResourceString());
        }

    }   @Test
    public void testQuery() {
     /* //  ����û�ID ��ѯ���в˵�
        resourceService.getMenusListByUserId(String userId)

       // ����û�ID ��ѯ���а�ť
        resourceService.getButtonsListByUserId(String userId)

      //  ����û�ID����ID ��ѯ��ɫ
        roleService.getRoleByUserIdList(String orgId, String userId)
*/

    }

}
