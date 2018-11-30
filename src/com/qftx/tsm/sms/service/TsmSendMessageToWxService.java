package com.qftx.tsm.sms.service;

import com.alibaba.fastjson.JSON;
import com.qftx.base.util.HttpUtil;
import com.qftx.common.util.ConfigInfoUtils;
import com.qftx.tsm.sms.bean.SendMessageToApp;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/** 向微信发送消息  */
@Service
public class TsmSendMessageToWxService {
    private Logger logger = Logger.getLogger(TsmSendMessageToWxService.class);

    /** 发送消息 ,单用户 */
    public void sendWxMessage(SendMessageToApp bean) throws Exception {
        String sendpar = JSON.toJSONString(bean, true);
		String isopen = ConfigInfoUtils.getStringValue("send_message_to_wx_isopen");
		if(isopen=="1"||"1".equals(isopen)){
        logger.debug("向企业微信发送消息数据:\n" + sendpar);
        String url = ConfigInfoUtils.getStringValue("send_message_to_wx_url");
        HttpUtil.doPostJSON(url, sendpar);
        logger.debug("发送消息成功");
		}
    }
    
    /** 发送消息 ,单位下所有成员都需要发 */
    public void sendWxMessageToAll(SendMessageToApp bean) throws Exception {
        String sendpar = JSON.toJSONString(bean, true);
		String isopen = ConfigInfoUtils.getStringValue("send_message_to_wx_isopen");
		if(isopen=="1"||"1".equals(isopen)){
        logger.debug("向企业微信发送消息数据:\n" + sendpar);
        String url = ConfigInfoUtils.getStringValue("send_message_to_wx_all_url");
        HttpUtil.doPostJSON(url, sendpar);
        logger.debug("发送消息成功");
		}
    }

}
