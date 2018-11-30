package com.qftx.sys.init;

import com.qftx.common.BaseUtest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Properties;

/**
 * User： bxl
 * Date： 2016/1/7
 * Time： 8:42
 */
public class TestInitConfig extends BaseUtest {
    @Autowired(required=false)
    private Properties config;
    @Value("#{config}")
    private Properties config1;
    @Value("#{config['tsm.memcached']}")
    private String memcached;

    @Test
    public void testConfig() {

        System.out.println("缓存地址1=" + config.getProperty("tsm.memcached"));
        System.out.println("缓存地址2=" + config1.getProperty("tsm.memcached"));
        System.out.println("缓存地址3=" + memcached);
    }
}
