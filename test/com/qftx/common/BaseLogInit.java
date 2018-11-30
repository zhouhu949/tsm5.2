package com.qftx.common;

import org.apache.ibatis.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;

/**
 * Created by bxl on 2015/11/13.
 */
public class BaseLogInit extends SpringJUnit4ClassRunner {
    static {
        String userpath = System.getProperty("user.dir");
        String path = userpath + File.separator + "WebRoot" + File.separator + "WEB-INF" + File.separator;
        String log = path + "etc" + File.separator + "log4j.properties";
        System.out.println("log=" + log);
        System.setProperty("logs.home", path);
        PropertyConfigurator.configure(log);
        LogFactory.useLog4JLogging();
        //  Log4jConfigurer.initLogging("classpath:com/config/log4j.properties");
    }
    public BaseLogInit(Class<?> clazz) throws InitializationError {
        super(clazz);
    }

}
