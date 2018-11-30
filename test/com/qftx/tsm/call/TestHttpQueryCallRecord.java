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
public class TestHttpQueryCallRecord extends BaseUtest {
    private Logger logger = Logger.getLogger(TestQueryCall.class);
    @Autowired
    CallRecordInfoService callRecordInfoService;

    @Test
    public void query() throws Exception {

//        String cust = "fded890b5e49490fa32c70809c969fc8";
//        String orgId = "hyxaly";
//        DtoCallRecordInfoBean bean=new DtoCallRecordInfoBean();
//        bean.setOrgId(orgId);
//        bean.setInputAcc(new String[]{"hyxaly004","hyxaly005"});
//       // bean.setCustId(new String[]{cust});
//        TsmRecordCallDto dto= callRecordInfoService.queryCallRecord(bean);
//        System.out.println(dto);
    }
}
