package com.qftx.tsm.call;

import com.alibaba.fastjson.JSON;
import com.qftx.base.util.HttpUtil;
import com.qftx.common.BaseUtest;
import com.qftx.common.util.ConfigInfoUtils;
import com.qftx.common.util.Page;
import com.qftx.tsm.callrecord.dto.DtoCallRecordInfoBean;
import com.qftx.tsm.callrecord.dto.TsmRecordCallBean;
import com.qftx.tsm.callrecord.dto.TsmRecordCallDto;
import com.qftx.tsm.callrecord.service.CallRecordInfoService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;

/**
 * User： bxl
 * Date： 2016/2/1
 * Time： 13:16
 */
public class TestQueryCall extends BaseUtest {
    private Logger logger = Logger.getLogger(TestQueryCall.class);
    @Autowired
    CallRecordInfoService callRecordInfoService;

    @Test
    public void query() throws Exception {

        DtoCallRecordInfoBean bean = new DtoCallRecordInfoBean();
        bean.setInputAcc(new String[]{shiroUser.getAccount()});
        bean.setOrgId(shiroUser.getOrgId());
        Page page = new Page();
        page.setTotalPage(2000);
        page.setShowCount(10);
        page.setCurrentPage(2);
        bean.setPage(page);
        String sendpar = JSON.toJSONString(bean, true);
        String url = ConfigInfoUtils.getStringValue("call_record_query");
        logger.debug("查询URl:" + url);
        logger.debug("查询参数:\n" + sendpar);
        String str = HttpUtil.doPostJSON(url, sendpar);
        logger.debug(str);
    }

    @Test
    public void testQuery() throws Exception {
        DtoCallRecordInfoBean bean1 = new DtoCallRecordInfoBean();
        bean1.setInputAcc(new String[]{shiroUser.getAccount(), "hn001", "hn001"});
        bean1.setOrgId(shiroUser.getOrgId());
        Page page1 = new Page();
        page1.setTotalPage(2000);
        page1.setShowCount(10);
        page1.setCurrentPage(2);
        bean1.setPage(page1);
        logger.debug("参数:" + JSON.toJSONString(bean1, true));
        TsmRecordCallBean bean = new TsmRecordCallBean();
        bean.setInputAcc(shiroUser.getAccount());
        bean.setOrgId(shiroUser.getOrgId());
        Page page = new Page();
        page.setTotalPage(2000);
        page.setShowCount(10);
        page.setCurrentPage(2);
        bean.setPage(page);
        DtoCallRecordInfoBean item=new DtoCallRecordInfoBean();
        String sendpar = JSON.toJSONString(item, true);
        String url = ConfigInfoUtils.getStringValue("call_record_query");
        logger.debug("查询URl:" + url);
        logger.debug("查询参数:\n" + sendpar);
        String str = HttpUtil.doPostJSON(url, sendpar);
        TsmRecordCallDto queryCallRecordBean = JSON.parseObject(str, TsmRecordCallDto.class);
        logger.debug("call list=" + queryCallRecordBean.getList().size());

    }
}
