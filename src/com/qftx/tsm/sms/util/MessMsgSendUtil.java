package com.qftx.tsm.sms.util;

import com.qftx.base.util.BizMessageUtil;
import com.qftx.common.util.DateUtil;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.sms.bean.TsmMessageSend;

import java.util.Date;


/***
 * 发送消息 公共接口
 * @author: zwj
 * @since: 2015-12-12  下午4:09:59
 * @history: 3.x
 */
public class MessMsgSendUtil {
	
	/***
	 * 发送消息接口
	 * @param msgType 消息类型
	 * 
		AppConstant.MSG_TYPE_AUDIT_ORDER = 8; // 订单审核
		AppConstant.MSG_TYPE_REVIEW_WORK = 10; // 工作点评
		AppConstant.MSG_TYPE_REVIEW_DAILY = 11; // 工作日报点评
		AppConstant.MSG_TYPE_REVIEW_DAILY_REPLY = 12; // 工作日报点评回复
		AppConstant.MSG_TYPE_REVIEW_MONTHLY =13; // 月度计划点评
		AppConstant.MSG_TYPE_PLAN_NOT_SUB = 14; // 计划未提交到期提醒
		AppConstant.MSG_TYPE_AUDIT_MONTHLY = 15; // 月度计划审核
		AppConstant.MSG_TYPE_AUDIT_DAILY = 16; // 日计划审核
		AppConstant.MSG_TYPE_AUDIT_DELAY = 17; // 延期审核
		AppConstant.MSG_TYPE_NOTICE = 18; // 通知公告
	 * 
	 * @param sendTo 接收人账号
	 * @param sendFrom 操作人账号
	 * @param orgId 	机构ID
	 * @create  2015-12-12 下午4:14:51 zwj
	 * @history  3.x
	 */
	public static void sendMessage(Integer msgType,String sendTo,String sendFrom,String orgId){
		// 记录消息
		TsmMessageSend msgSend = new TsmMessageSend();
		msgSend.setMessageId(StringUtils.getDateAndRandStr(15));
		msgSend.setSendTo(sendTo);
		msgSend.setSendFrom(sendFrom);
		msgSend.setOrgId(orgId);
		msgSend.setMsgType(msgType);
		msgSend.setSendDate(new Date());
		install(msgSend);
	}
	
	/**组装 消息进行发送 */
	private static void install(TsmMessageSend msgSend){
		String data = DateUtil.format(new Date(), DateUtil.hour12HMSPattern);
		String type = "1";
		if(msgSend.getMsgType() == AppConstant.MSG_TYPE_REVIEW_WORK){
			type= "2";
			msgSend.setTitle("工作点评");
			msgSend.setMessageContent(data +" ,您收到来自"+msgSend.getSendFrom()+"的工作点评");
		} else if(msgSend.getMsgType() == AppConstant.MSG_TYPE_REVIEW_DAILY){
			type= "2";
			msgSend.setTitle("工作日报点评");
			msgSend.setMessageContent(data +" ,您收到来自"+msgSend.getSendFrom()+"的日报点评");
		}else if(msgSend.getMsgType() == AppConstant.MSG_TYPE_REVIEW_WORK){
			type= "2";
			msgSend.setTitle("工作日报点评回复");
			msgSend.setMessageContent(data +" ,"+msgSend.getSendFrom()+"回复了你的点评");
		}else if(msgSend.getMsgType() == AppConstant.MSG_TYPE_REVIEW_MONTHLY){
			type= "2";
			msgSend.setTitle("月度计划点评");
			msgSend.setMessageContent(data +" ,您收到来自"+msgSend.getSendFrom()+"的月度计划点评");
		}else if(msgSend.getMsgType() == AppConstant.MSG_TYPE_REVIEW_MONTHLY){
			type= "2";
			msgSend.setTitle("月度计划点评");
			msgSend.setMessageContent(data +" ,您收到来自"+msgSend.getSendFrom()+"的月度计划点评");
		}else if(msgSend.getMsgType() == AppConstant.MSG_TYPE_REVIEW_MONTHLY){
			type= "6";
			msgSend.setTitle("月度计划审核");
			msgSend.setMessageContent(data +" ,您收到一条新的计划审核信息");
		}else if(msgSend.getMsgType() == AppConstant.MSG_TYPE_AUDIT_DAILY){
			type= "6";
			msgSend.setTitle("日度计划审核");
			msgSend.setMessageContent(data +" ,您收到一条新的计划审核信息");
		}else if(msgSend.getMsgType() == AppConstant.MSG_TYPE_AUDIT_DELAY){
			type= "6";
			msgSend.setTitle("延期审核");
			msgSend.setMessageContent(data +" ,您收到一条新的延期审核信息");
		}else if(msgSend.getMsgType() == AppConstant.MSG_TYPE_AUDIT_ORDER){
			type= "6";
			msgSend.setTitle("订单审核");
			msgSend.setMessageContent(data +" ,您收到一条新的订单审核信息");
		}else if(msgSend.getMsgType() == AppConstant.MSG_TYPE_NOTICE){
			type= "5";
			msgSend.setTitle("通知公告");
			msgSend.setMessageContent(data +" ,您收到一条新的通知公告");
		}
		// 发点评消息
		BizMessageUtil.sendBizMsg(msgSend,type);
	}
	
}
