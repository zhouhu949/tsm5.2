package com.qftx.tsm.call;

import com.alibaba.fastjson.JSON;
import com.qftx.common.BaseUtest;
import com.qftx.tsm.callrecord.dto.DtoCallRecordInfoBean;
import com.qftx.tsm.callrecord.dto.TsmRecordCallDto;
import com.qftx.tsm.callrecord.service.CallRecordInfoService;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * User�� bxl
 * Date�� 2016/2/25
 * Time�� 11:37
 */
public class TestQuery extends BaseUtest {
    private Logger logger = Logger.getLogger(TestQueryCall.class);
    @Autowired
    CallRecordInfoService callRecordInfoService;

    @Test
    public void query() throws Exception {

//        String cust = "fded890b5e49490fa32c70809c969fc8";
//        String orgId = "8decbe1278b646b5a462bbd4bc80bd58";
//        DtoCallRecordInfoBean bean=new DtoCallRecordInfoBean();
//        bean.setOrgId(orgId);
//        bean.setCustId(new String[]{cust});
//        bean.setQueryKey("234234");
//        bean.setOrderKey(" input_date desc");
//        TsmRecordCallDto dto= callRecordInfoService.queryCallRecord(bean);
//        System.out.println(JSON.toJSONString(dto.getItem(),true));
    }
}
