package com.qftx.tsm.message;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.qftx.base.util.HttpUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.common.util.ConfigInfoUtils;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.tsm.message.bean.Get_Message_Resp;
import com.qftx.tsm.message.bean.SendMessageBean;
import com.qftx.tsm.sms.bean.SendMessageToApp;
import com.qftx.tsm.sms.service.TsmMessageSendService;
import com.qftx.tsm.sms.service.TsmSendMessageToAppService;

public class TestSendMessageToApp extends BaseUtest{
    @Autowired
    private TsmSendMessageToAppService tsmSendMessageToAppService;

//    @Test
//    public void sendMessageToApp() throws Exception {
//        String url = "http://192.168.1.140:8082/receiveMessage/getAllMessage";
//        System.out.println(url);
//        Get_Message_Resp bean=new Get_Message_Resp();
//        bean.setID("201707271736abcdefg");
//        bean.setTitle("测试给app发消息");
//        bean.setInfo("消息内容");
//        bean.setType(1);
//        
//        bean.setReminderTime(new Date().getTime());
//        bean.setTel("13757125037");
//        bean.setTime_action(new Date().getTime());
//        
//        bean.setPl_person("");
//        bean.setPl_personid("");
//        bean.setPl_time(new Date().getTime());
//
//        System.out.println(JSON.toJSONString(bean, true));
//        String rest = HttpUtil.doPostJSON(url, JSON.toJSONString(bean, true));
//        System.out.println("***********************************************");
//        System.out.println(rest);
//    }
    
    @Test
    public void sendMessageToApp() throws Exception {
//    	SendMessageToApp bean=new SendMessageToApp();
//        bean.setTitle("测试发送消息标题");
//        bean.setSummary("测试发送消息内容");
//        bean.setLevel("1");
//        bean.setType("1");
//        bean.setObj("abcdefg");
//        tsmSendMessageToAppService.sendMessage(bean);
    	/******************************************************/
		//向appALL发送公告
		SendMessageToApp appbean=new SendMessageToApp();
		appbean.setOrgId("newboss");
		appbean.setUserAccount("");
		appbean.setTitle("测试1");
		appbean.setSummary("测试消息内容");
		appbean.setLevel("3");
		appbean.setType("5");

		Map<String,String> map=new HashMap<String,String>();
		String announceId = SysBaseModelUtil.getModelId();
		map.put("id", announceId);
		map.put("orgId", "newboss");
		appbean.setObj(JsonUtil.getJsonString(map));
		tsmSendMessageToAppService.sendMessageToAll(appbean);
		/******************************************************/
//		String isopen = ConfigInfoUtils.getStringValue("send_message_to_app_isopen");
//		if(isopen=="0"||"0".equals(isopen)){
//		//向app发送公告
//		SendMessageToApp appbean=new SendMessageToApp();
//		appbean.setOrgId("newboss");
//		appbean.setUserAccount("newboss004");
//		appbean.setTitle("工作点评");
//		appbean.setSummary("谁是谁给你工作点评");
//		appbean.setLevel("3");
//		appbean.setType("8");
//
//		Map<String,String> map=new HashMap<String,String>();
//		String announceId = SysBaseModelUtil.getModelId();
//		map.put("id", announceId);
//		map.put("orgId", "newboss");
//		appbean.setObj(JsonUtil.getJsonString(map));
//		tsmSendMessageToAppService.sendMessage(appbean);
//		}
        System.out.println("***********************************************");

    }

}
