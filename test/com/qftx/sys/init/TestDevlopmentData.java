package com.qftx.sys.init;

import com.qftx.base.auth.bean.Org;
import com.qftx.base.auth.bean.User;
import com.qftx.base.auth.dao.OrgGroupMapper;
import com.qftx.base.auth.dao.OrgMapper;
import com.qftx.base.auth.dao.UserMapper;
import com.qftx.base.util.GuidUtil;
import com.qftx.common.BaseUtest;
import com.qftx.common.cached.CachedInitService;
import com.qftx.common.filter.AppContextUtil;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * User： bxl
 * Date： 2016/1/18
 * Time： 14:40
 */
public class TestDevlopmentData extends BaseUtest {
    @Autowired(required = false)
    private CachedInitService cachedInitService;
    @Autowired(required = false)
    private OrgMapper orgMapper;
    @Autowired(required = false)
    private OrgGroupMapper orgGroupMapper;
    @Autowired(required = false)
    private UserMapper userMapper;

    @Test
    public void Init() {
        System.out.println("context="+AppContextUtil.getBean("userMapper"));
        //8decbe1278b646b5a462bbd4bc80bd88 个人
        //8decbe1278b646b5a462bbd4bc80bd58  企业
       // Org org = orgService.getByPrimaryKey("8decbe1278b646b5a462bbd4bc80bd88");
        List<User> list = userMapper.findAllUserByUnitId("8decbe1278b646b5a462bbd4bc80bd58");
        for (User user : list) {
            User bean=new User();
            BeanUtils.copyProperties(user, bean);
            bean.setUserId(GuidUtil.getId());
            bean.setOrgId("8decbe1278b646b5a462bbd4bc80bd88");
          //  userMapper.insert(bean);
            System.out.println(user.getUserAccount());
        }
        //   cachedInitService.init("0", 0);

    }
}
