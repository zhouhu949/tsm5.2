package com.qftx.tsm.log;

import com.qftx.base.auth.service.UserService;
import com.qftx.base.util.GuidUtil;
import com.qftx.common.BaseUtest;
import com.qftx.common.filter.AppContextUtil;
import com.qftx.tsm.log.bean.LogWebInfoBean;
import com.qftx.tsm.log.dao.LogWebInfoMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User£º bxl
 * Date£º 2016/1/15
 * Time£º 10:38
 */
public class TestWebLog  extends BaseUtest {
    @Autowired(required = false)
    private LogWebInfoMapper logWebInfoMapper;
@Test
    public void query(){
        LogWebInfoBean bean = new LogWebInfoBean();
        bean.setId(GuidUtil.getId());
        bean.setTimeLength(10L);
      //  bean.setIp(":234");
        bean.setWebUrl("234234");
     //   bean.setUserId(user.getId());
      //  bean.setName(user.getName());
      //  bean.setOrgId(user.getOrgId());
        bean.setUpdatetime(new Date());
        bean.setInputtime(new Date());
        bean.setIsDel(0);

        if(logWebInfoMapper!=null)logWebInfoMapper.insert(bean);
    }
    @Test
    public void save(){
        LogWebInfoBean bean = new LogWebInfoBean();
        bean.setId(GuidUtil.getId());
        bean.setTimeLength(10L);
        //  bean.setIp(":234");
        bean.setWebUrl("234234");
        //   bean.setUserId(user.getId());
        //  bean.setName(user.getName());
        //  bean.setOrgId(user.getOrgId());
        bean.setUpdatetime(new Date());
        bean.setInputtime(new Date());
        bean.setIsDel(0);
        List<LogWebInfoBean> list = new ArrayList<LogWebInfoBean>();
        list.add(bean);
        System.out.println(list.size());
        logWebInfoMapper.insertBatch(list);
        list.clear();
        System.out.println(list.size());
    }
}
