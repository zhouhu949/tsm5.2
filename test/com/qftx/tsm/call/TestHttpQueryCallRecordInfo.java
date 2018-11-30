package com.qftx.tsm.call;

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
public class TestHttpQueryCallRecordInfo extends BaseUtest {
    private Logger logger = Logger.getLogger(TestQueryCall.class);
    @Autowired
    CallRecordInfoService callRecordInfoService;

    @Test
    public void query() throws Exception {

//
//        String orgId = "wwcs2";
//        DtoCallRecordInfoBean bean=new DtoCallRecordInfoBean();
//        bean.setOrgId(orgId);
//       // bean.setInputAcc(new String[]{"hyxaly004", "hyxaly005"});
//       bean.setFollowId(new String[]{"10c3ea08ce21441c8bb83980593cdb89"});
//        TsmRecordCallDto dto= callRecordInfoService.queryCallRecord(bean);
//        System.out.println(dto);
    }
}
