package com.qftx.common;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUserRealm;
import com.qftx.base.shiro.ShiroUtil;
import junit.framework.TestCase;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;


/**
 * User: bxl
 * Date: 11-11-8
 * Time: 下午3:35
 */
@RunWith(BaseLogInit.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class BaseUtest  extends TestCase {
    @Autowired
    protected ApplicationContext context;
    @Autowired
    private ShiroUserRealm shiroUserRealm;
    protected ShiroUser shiroUser;
    @Before
    public void init() {
     /* 	try {
    		System.out.println(context.getId());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
      String username="fhtx001";
        String password="123456";
        DefaultSecurityManager security = new DefaultSecurityManager(shiroUserRealm);
        SecurityUtils.setSecurityManager(security);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password.toCharArray());
        Subject user = SecurityUtils.getSubject();
        token.setRememberMe(true);
        user.login(token);
        shiroUser = ShiroUtil.getShiroUser();*/
        /*LogFactory.useLog4JLogging();
       // context = new FileSystemXmlApplicationContext("classpath:applicationContext.xml");
        String userpath = System.getProperty("user.dir");
        String path = userpath + File.separator + "WebRoot" + File.separator + "WEB-INF" + File.separator;
        String log = path + "etc" + File.separator + "log4j.properties";
        System.out.println("log="+log);
        System.setProperty("logs.home", path);
        PropertyConfigurator.configure(log);*/
    }
}
