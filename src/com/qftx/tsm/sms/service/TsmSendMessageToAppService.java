package com.qftx.tsm.sms.service;

import com.alibaba.fastjson.JSON;
import com.qftx.base.util.HttpUtil;
import com.qftx.common.util.ConfigInfoUtils;
import com.qftx.tsm.sms.bean.SendMessageToApp;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/** 向app发送消息  */
@Service
public class TsmSendMessageToAppService {
    private Logger logger = Logger.getLogger(TsmSendMessageToAppService.class);
    @Autowired
    private  TsmSendMessageToWxService tsmSendMessageToWxService;

    /** 发送消息 ,单用户 */
    public void sendMessage(SendMessageToApp bean) throws Exception {
        String sendpar = JSON.toJSONString(bean, true);
		String isopen = ConfigInfoUtils.getStringValue("send_message_to_app_isopen");
		if(isopen=="0"||"0".equals(isopen)){
        logger.debug("向app发送消息数据:\n" + sendpar);
        String url = ConfigInfoUtils.getStringValue("send_message_to_app_url");
        HttpUtil.doPostJSON(url, sendpar);
        logger.debug("发送消息成功");
		}
		//向企业微信发送消息
        //tsmSendMessageToWxService.sendWxMessage(bean);
    }
    
    /** 发送消息 ,单位下所有成员都需要发 */
    public void sendMessageToAll(SendMessageToApp bean) throws Exception {
        String sendpar = JSON.toJSONString(bean, true);
		String isopen = ConfigInfoUtils.getStringValue("send_message_to_app_isopen");
		if(isopen=="0"||"0".equals(isopen)){
        logger.debug("向app发送消息数据:\n" + sendpar);
        String url = ConfigInfoUtils.getStringValue("send_message_to_app_all_url");
        HttpUtil.doPostJSON(url, sendpar);
        logger.debug("发送消息成功");
		}
		//向企业微信发送消息
        //tsmSendMessageToWxService.sendWxMessageToAll(bean);
    }

}
