package com.qftx.tsm.sms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.qftx.base.auth.bean.User;
import com.qftx.base.auth.service.UserService;
import com.qftx.base.util.HttpUtil;
import com.qftx.common.BaseUtest;
import com.qftx.common.util.Page;
import com.qftx.tsm.call.TestQueryCallData;
import com.qftx.tsm.callrecord.dto.TsmRecordCallBean;
import com.qftx.tsm.callrecord.dto.TsmRecordCallDto;
import com.qftx.tsm.callrecord.service.CallRecordInfoService;
import com.qftx.tsm.message.bean.SendMessageBean;
import com.qftx.tsm.sms.service.TsmMalletMessageSendService;
import com.qftx.tsm.sms.service.TsmMessageSendService;

public class TestMessageSendService extends BaseUtest{
	
    private Logger logger = Logger.getLogger(TestQueryCallData.class);

    @Autowired(required = false)
    private UserService userService;
    @Autowired
    private CallRecordInfoService callRecordInfoService;
    @Autowired
    private TsmMessageSendService tsmMessageSendService;

    @Test
    public void testQuery() throws Exception {
    	tsmMessageSendService.addSysMessage("fhtx","fhtx002", "fhtx001", "标题", "内容");
    	
    }
     
}
