package com.qftx.common.aop;

import com.qftx.base.auth.service.UserService;
import com.qftx.common.BaseUtest;
import com.qftx.tsm.rest.bean.CallRecordBean;
import com.qftx.tsm.rest.service.CallRecordService;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * User£º bxl
 * Date£º 2016/7/28
 * Time£º 15:37
 */
public class TestCacheAop extends BaseUtest {
    private static final Logger logger = Logger.getLogger(TestCacheAop.class.getName());
    @Autowired
    private CallRecordService service;
    @Autowired
    UserService userService;
   	@Test
    public void testAop(){
        logger.debug("=================================");

        List<CallRecordBean> list =  service.findAll();
       // logger.debug("list=" + JSON.toJSONString(list,true));
        logger.debug("=================================");
        userService.getByAccount("hn001");
    }
}
