package com.qftx.base.auth.dao;

import com.alibaba.druid.support.logging.Log4jImpl;
import com.qftx.base.auth.bean.Org;
import com.qftx.base.auth.service.OrgService;
import com.qftx.common.BaseUtest;
import junit.framework.Assert;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bxl on 2015/11/2.
 */
public class TestAuthOrg extends BaseUtest {
  //  @Test
   public void testShouldUseLog4J() {
        org.apache.ibatis.logging.LogFactory.useLog4JLogging();
      //  LogFactory.useLog4JLogging();
         Log log = LogFactory.getLog(Object.class);
        log.error("234423");
        System.out.println(log.isDebugEnabled());
         log.debug("234234");
        System.out.println(log.getClass().getName());
        System.out.println(Log4jImpl.class.getName());
      //  Assert.assertEquals(log.getClass().getName(), Log4jImpl.class.getName());
       }
   //@Test
   public void testUseCommonsLogging1() {
        LogFactory.useCommonsLogging();
     //   Log log = LogFactory.getLog(Object.class);
     //   System.out.println(log.getClass().getName());
     //   System.out.println(Log4jImpl.class.getName());
     //   Assert.assertEquals(log.getClass().getName(), Log4jImpl.class.getName());
       }

    @Test
    public void testQuery() {
      //  LogFactory.useCommonsLogging();
       OrgService service = (OrgService) context.getBean("orgService");
        Map<String, String> params = new HashMap<String, String>();
        params.put("orgCode", "");
        params.put("orgName", "e");
        params.put("startDate","2015-12-10");
        params.put("endDate","2015-12-12");
        Assert.assertEquals("1", "1");
        System.out.println("list="+service.getListPage(new Org()));
        //  System.out.println(service.queryPageToJson(new HashMap<String, String>()).size());

    }
}
