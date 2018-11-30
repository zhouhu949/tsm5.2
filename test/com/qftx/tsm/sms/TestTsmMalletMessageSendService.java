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

public class TestTsmMalletMessageSendService extends BaseUtest{
	
    private Logger logger = Logger.getLogger(TestQueryCallData.class);

    @Autowired(required = false)
    private UserService userService;
    @Autowired
    private CallRecordInfoService callRecordInfoService;
    @Autowired
    private TsmMalletMessageSendService tsmMalletMessageSendService;

    @Test
    public void testQuery() throws Exception {
        SendMessageBean bean=new SendMessageBean();
        bean.setOgrId("fhtx");
        bean.setAccount("fhtx001");
        bean.setTitle("钱包消息222");
        bean.setContent("余额增加99999");
        bean.setType("25");
        SendMessageBean resbean=tsmMalletMessageSendService.sendMessage(bean);
        System.out.println(JSON.toJSON(resbean));
        System.out.println(resbean.getRetCode()+"___"+resbean.getRetMessage());
    	
    }
     
}
