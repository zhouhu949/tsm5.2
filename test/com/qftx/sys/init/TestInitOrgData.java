package com.qftx.sys.init;

import com.qftx.common.BaseUtest;
import com.qftx.common.cached.CachedInitService;
import com.qftx.common.util.constants.AppConstant;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User�� bxl
 * Date�� 2016/1/18
 * Time�� 14:40
 */
public class TestInitOrgData extends BaseUtest {
    @Autowired(required = false)
    private CachedInitService cachedInitService;

    @Test
    public void Init() {
     //   cachedInitService.init("0", 0);

    }
}
