package com.qftx.tsm.call;

import com.alibaba.fastjson.JSON;
import com.qftx.base.auth.bean.User;
import com.qftx.base.auth.service.UserService;
import com.qftx.base.util.DateUtil;
import com.qftx.common.BaseUtest;
import com.qftx.common.util.Page;
import com.qftx.tsm.callrecord.dto.CallRecordListDto;
import com.qftx.tsm.callrecord.dto.CallRecordPageDto;
import com.qftx.tsm.callrecord.dto.ConditionDto;
import com.qftx.tsm.callrecord.dto.TsmRecordCallBean;
import com.qftx.tsm.callrecord.dto.TsmRecordCallDto;
import com.qftx.tsm.callrecord.service.CallRecordInfoService;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 模拟通话数据提交
 * User： bxl
 * Date： 2015/12/2
 * Time： 13:39
 */
public class TestQueryCallData extends BaseUtest {
    private Logger logger = Logger.getLogger(TestQueryCallData.class);

    @Autowired(required = false)
    private UserService userService;
    @Autowired
    private CallRecordInfoService callRecordInfoService;

    @Test
    public void testQuery() throws Exception {
    	ConditionDto dto = new ConditionDto();
    	dto.getPage().setCurrentPage(1);
    	dto.getPage().setShowCount(20);
    	dto.getPage().setTotalPage(0);
    	dto.getPage().setTotalResult(0);
    	dto.setOrgId("ny9");
    	dto.setInputAccs(Arrays.asList("ny9001".split(",")));
    	dto.setSaleProcessId("");
    	dto.setCustState("");
    	dto.setStartTimeBegin("2017-12-14 01:00:00");
    	dto.setStartTimeEnd("2017-12-15 01:00:00");
        try {
        	CallRecordListDto queryCall = callRecordInfoService.queryCallRecord(dto);
            logger.debug("返回bean参数=" + JSON.toJSONString(queryCall.getBeans(), true));
            logger.debug("返回page参数=" + JSON.toJSONString(queryCall.getCondition(), true));
        } catch (Exception e) {
            logger.error("query call error:" + e.getMessage(), e);
        }
    }


}
