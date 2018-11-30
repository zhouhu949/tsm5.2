package com.qftx.tsm.callrecord.service;

import com.qftx.base.auth.bean.User;
import com.qftx.base.auth.dao.UserMapper;
import com.qftx.base.auth.dto.BaseReturn;
import com.qftx.base.auth.dto.BaseSend;
import com.qftx.base.enums.SubmitStatus;
import com.qftx.base.util.BizSmsUtil;
import com.qftx.common.util.StringUtils;
import com.qftx.tsm.callrecord.util.SmsBatchCheckUtil;
import com.qftx.tsm.sms.bean.TsmSendSmsDetail;
import com.qftx.tsm.sms.dao.TsmSendSmsDetailMapper;
import com.qftx.tsm.sms.service.TsmSendSmsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 通信管理 短信发送
 */
@Service
public class CallSmsSendService{
	Logger logger = Logger.getLogger(CallSmsSendService.class);
	
	@Autowired
	private TsmSendSmsService tsmSendSmsService;
	@Autowired
	private UserMapper userMapper;	
	@Autowired
	private TsmSendSmsDetailMapper tsmSendSmsDetailMapper;
	
	/***
	 * 短信批量发送
	 * 1、判断是否有子号码 2、请求参数获取 3、号码验证 4、拆分短信 5、发送短信 
	 * 			6、修改用户对于的短信条数
	 * @param smsContent
	 * @param reqMap
	 * @param smslabel
	 * @return 
	 * @throws Exception 
	 * @create  2016-1-12 上午11:48:19 zwj
	 * @history  3.x
	 */
	public String send(String orgId,String smsContent, Map<String, Object> reqMap, String smslabel,Integer isChannel) throws Exception {
		String code = null;
		StringBuffer rtnBuffer = new StringBuffer();
	    User user = (User)reqMap.get("user");
	    List<String> name_mobile_list = (List<String>)reqMap.get("validList");
	    double tempNum = SmsBatchCheckUtil.getSmallSmsTotal(smsContent+smslabel);  
	    int smallSmsTotal = Integer.parseInt(new java.text.DecimalFormat("0").format(tempNum));
	    Integer smsTotal = 0;
    	Date date = new Date();
    	// 保存普通短信记录
    	String smsMajorId = StringUtils.getDateAndRandStr(15);		
		reqMap.put("smsMajorId", smsMajorId);
		reqMap.put("smallSmsTotal", smallSmsTotal);
		reqMap.put("date", date);
		reqMap.put("smsContent", smsContent);
		reqMap.put("smslabel", smslabel);
		int i = 0;
		int j = 100; // 每100条短信一批
		if(isChannel !=null && isChannel==2){ // ema短信只支持20
			j = 20;
		}
		String phones = null;
		List<String> name_mobileList = new ArrayList<String>();
		StringBuffer buffer = new StringBuffer(1300);
		for (String name_num : name_mobile_list) {
			name_mobileList.add(name_num);
			if (name_num.indexOf("|") > -1) {
				name_num = name_num.split("\\|")[1];
			}
			buffer.append(name_num).append(";");
			i++;
			if (i == j) { // 每j条短信提交
				phones = buffer.toString();
				i = phones.length();
				phones = phones.substring(0, i - 1);
				reqMap.put("phones", phones);
				reqMap.put("name_mobileList", name_mobileList);
				code = splitSend(orgId,reqMap,isChannel);
				if("0".equals(code)) {
					smsTotal = smsTotal+smallSmsTotal * name_mobileList.size();
				}
				rtnBuffer.append(code);
				i = 0;
				buffer = new StringBuffer(1300);
				name_mobileList = new ArrayList<String>();
			}
		}
		if (i > 0) { // 处理剩余不足j条
			phones = buffer.toString();
			i = phones.length();
			phones = phones.substring(0, i - 1);
			reqMap.put("phones", phones);
			reqMap.put("name_mobileList", name_mobileList);
			code = splitSend(orgId,reqMap,isChannel);
			if("0".equals(code)) {
				smsTotal = smsTotal+smallSmsTotal * name_mobileList.size();
			}
			rtnBuffer.append(code);
		}
		code = rtnBuffer.toString().replaceAll("0", "");		
		if ("".equals(code)){
			code = "0";
		}
		// 生成主要记录
		if(smsTotal > 0){
			tsmSendSmsService.saveSms(smsMajorId,user.getUserAccount(), smslabel, 
					smsContent, smsTotal, orgId, date,0);
		}		
		return code;
	}
	
	/***
	 * 
	 * @param orgId
	 * @param reqMap
	 * @param isChannel 0:手机卡，其余为渠道
	 * @return
	 * @throws Exception 
	 */
	private String splitSend(String orgId,Map<String, Object> reqMap,Integer isChannel) throws Exception {
		User user = (User)reqMap.get("user");
	    List<String> name_mobileViladList = (List<String>)reqMap.get("name_mobileList");
	    String smsMajorId = (String)reqMap.get("smsMajorId");
	    int smallSmsTotal = (Integer)reqMap.get("smallSmsTotal");
	    Date date = (Date)reqMap.get("date");
	    String smsContent = (String)reqMap.get("smsContent");
	    
	    // 将主短信拆分成明细短信
	    String batchId = StringUtils.getDateAndRandStr(15);		
		String code = "3";
		if(isChannel != null && isChannel == 0){			
			// 发送手机卡短信
			code = sendSimSms(user,smsContent,batchId,reqMap,orgId,date);			
		}else if(isChannel != null && isChannel == 2){			
			// 发送Ema短信
			code = sendEmaSms(user,smsContent,batchId,reqMap,orgId,date);			
		}else{
			// 发送渠道短信
			code = sendChannelSms(user,smsContent,batchId,reqMap,orgId,date);		
		}	
		// 返回成功后生成短信明细
		if ("0".equals(code)) {
			batchSaveSmsDetail(orgId,name_mobileViladList, smsMajorId, user.getUserAccount(), smallSmsTotal, date, batchId);
		}
		return code;
	}

	// 发送手机卡短信
	private String sendSimSms(User user,String smsContent,String batchId,Map<String, Object> reqMap,String orgId,Date date)throws Exception{
			/**发送短信  */
			BaseReturn rtn = BizSmsUtil.bizSimSmsSend((String)reqMap.get("phones"), smsContent, batchId+"_"+orgId, user.getUserAccount());
			if (rtn != null) {
				if (BaseSend.STATUS_SUCCES.equals(rtn.getCode())) {
					// 如果发送成功，修改用户表的通信时长
					User upUser = new User();
					upUser.setEnabled(1);
					upUser.setOrgId(orgId);
					upUser.setUserAccount(user.getUserAccount());
					upUser.setCommunicationsTimes((rtn.getBalance()==null ? 0 :rtn.getBalance()));
					userMapper.updateUser(upUser);
					return "0";
				} 
				if(BaseSend.STATUS_EXCEPTION.equals(rtn.getCode())){
					return "短信服务器异常";
				}
				return rtn.getDesc();				
			} 
			return "3";
		}
	
	// 发送渠道短信
	private String sendChannelSms(User user,String smsContent,String batchId,Map<String, Object> reqMap,String orgId,Date date)throws Exception{
		/**发送短信  */
		BaseReturn rtn = BizSmsUtil.bizSmsSend((String)reqMap.get("phones"), smsContent, batchId+"_"+orgId, user.getUserAccount());
		if (rtn != null) {
			if (BaseSend.STATUS_SUCCES.equals(rtn.getCode())) {
				// 如果发送成功，修改用户表的通信时长
				User upUser = new User();
				upUser.setEnabled(1);
				upUser.setOrgId(orgId);
				upUser.setUserAccount(user.getUserAccount());
				upUser.setCommunicationsTimes((rtn.getBalance()==null ? 0 :rtn.getBalance()));
				userMapper.updateUser(upUser);
				return "0";
			} 
			if(BaseSend.STATUS_EXCEPTION.equals(rtn.getCode())){
				return "短信服务器异常";
			}
			return rtn.getDesc();			
		} 			
		return "3";		
	}
	
	// 发送ema短信
	private String sendEmaSms(User user,String smsContent,String batchId,Map<String, Object> reqMap,String orgId,Date date)throws Exception{
			/**发送短信  */
			BaseReturn rtn = BizSmsUtil.bizEmaSmsSend((String)reqMap.get("phones"), smsContent, batchId+"_"+orgId, user.getUserAccount());
			if (rtn != null) {
				if (BaseSend.STATUS_SUCCES.equals(rtn.getCode())) {
					// 如果发送成功，修改用户表的通信时长
					User upUser = new User();
					upUser.setEnabled(1);
					upUser.setOrgId(orgId);
					upUser.setUserAccount(user.getUserAccount());
					upUser.setCommunicationsTimes((rtn.getBalance()==null ? 0 :rtn.getBalance()));
					userMapper.updateUser(upUser);
					return "0";
				} 
				if(BaseSend.STATUS_EXCEPTION.equals(rtn.getCode())){
					return "短信服务器异常";
				}
				return rtn.getDesc();				
			} 
			return "3";
		}
	
	/**
	 * 拆分主短信，批量入库 短信明细
	 * @history
	 */
	private List<String> batchSaveSmsDetail(String orgId,List<String> name_mobileViladList, String majorId, String account, int smallSmsTtaol,
			Date date, String batchId) {
		List<String> id_mobileSmsDetailList = new ArrayList<String>();
		List<TsmSendSmsDetail> smsDetailList = new ArrayList<TsmSendSmsDetail>();
		List<String> nameList = new ArrayList<String>();
		List<String> mobileList = new ArrayList<String>();
		nameList = getMobileOrName(name_mobileViladList, 1);
		mobileList = getMobileOrName(name_mobileViladList, 2);
		for (int i = 0; i < name_mobileViladList.size(); i++) {
			String id = StringUtils.getRandomUUIDStr();
			TsmSendSmsDetail tsmSendDetail = new TsmSendSmsDetail();
			tsmSendDetail.setOrgId(orgId);
			tsmSendDetail.setSmsDetailId(id);
			tsmSendDetail.setSmsId(majorId);
			tsmSendDetail.setMobilePhone(mobileList.get(i));
			tsmSendDetail.setName(nameList.get(i));
			tsmSendDetail.setReceiptStatus("0");
			tsmSendDetail.setSmsNumber(smallSmsTtaol);
			tsmSendDetail.setSendDate(date);
			tsmSendDetail.setBatchId(batchId);
			tsmSendDetail.setSubmitStatus(SubmitStatus.SUBMITTING.getStatus());
			smsDetailList.add(tsmSendDetail);
			id_mobileSmsDetailList.add(id + "_" + mobileList.get(i));
		}
		tsmSendSmsDetailMapper.insertBatch(smsDetailList);
		return id_mobileSmsDetailList;
	}
	
	/**
	 * 获取电话或客户姓名
	 * @param name_mobiles
	 */
	private static List<String> getMobileOrName(List<String> name_mobileViladList, int type) {
		List<String> mobileList = new ArrayList<String>();
		List<String> nameList = new ArrayList<String>();
		for (String name_mobile : name_mobileViladList) {
			String[] subArray = name_mobile.split("\\|");
			if (subArray.length > 1) {
				nameList.add(subArray[0]);
				mobileList.add(subArray[1]);
			} else {
				nameList.add("");
				mobileList.add(subArray[0]);
			}
		}
		if (type == 1) {
			return nameList;
		} else {
			return mobileList;
		}
	}

}