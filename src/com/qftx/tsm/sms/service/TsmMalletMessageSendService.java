package com.qftx.tsm.sms.service;

import com.alibaba.fastjson.JSON;
import com.qftx.base.util.HttpUtil;
import com.qftx.common.util.ConfigInfoUtils;
import com.qftx.tsm.message.bean.SendMessageBean;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class TsmMalletMessageSendService {
    private Logger logger = Logger.getLogger(TsmMalletMessageSendService.class);

    /** 发送钱包消息  */
    public SendMessageBean sendMessage(SendMessageBean bean) throws Exception {
        String jsondata = send(bean);
        SendMessageBean sendMessageBean = JSON.parseObject(jsondata, SendMessageBean.class);
        logger.debug("发送钱包消息");
        return sendMessageBean;
    }
    
    public String send(SendMessageBean bean) throws Exception {
        String sendpar = JSON.toJSONString(bean, true);
        logger.debug("查询参数:\n" + sendpar);
        String url = ConfigInfoUtils.getStringValue("send_mallet_message_url");
        String str = HttpUtil.doPostJSON(url, sendpar);
        logger.debug("返回数据:\n" + str);
        return str;
    }

}
