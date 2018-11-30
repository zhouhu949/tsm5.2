package com.qftx.pay.service;

import com.qftx.base.auth.dto.BaseReturn;
import com.qftx.base.auth.dto.BaseSend;
import com.qftx.base.util.BizSmsUtil;
import com.qftx.base.util.GuidUtil;
import com.qftx.common.util.RandomUtil;
import com.qftx.common.util.constants.AppConstant;

public class SmsSendManager {

	public static SmsSendManager instance;

	private SmsSendManager() {
	}

	public static SmsSendManager getInstance() {
		if (instance == null) {
			synchronized (SmsSendManager.class) {
				if (instance == null) {
					instance = new SmsSendManager();
				}
			}
		}
		return instance;
	}

	public String send(String mobil,String account) {
		String code = RandomUtil.randomNumString(4);
		String content = "您的验证码为:"+code+",有效时间"+AppConstant.SMS_CODE_VALID_TIME+"分钟,请勿泄露给他人";

		BaseReturn rtn = BizSmsUtil.bizSystemSmsSend(mobil, content,GuidUtil.getTimestapId(),account);

		if (rtn != null && BaseSend.STATUS_SUCCES.equals(rtn.getCode())) {
			return code;
		}else{
			return null;
		}
	}

	public String sendExportCode(String mobil,String name,Integer exportType,String account) {
		String code = RandomUtil.randomNumString(4);
		String poolName = "";
		if(exportType == 1) poolName="资源池";
		else if(exportType == 2) poolName="意向客户池";
		else if(exportType == 3) poolName="签约客户池";
		else if(exportType == 4) poolName="沉默客户池";
		else if(exportType == 5) poolName="流失客户池";
		else if(exportType == 6) poolName="通话记录";
		String content = name+"提交了一份"+poolName+"的数据导出申请，本次申请验证码为"+code+"；该验证码"+AppConstant.SMS_CODE_VALID_TIME+"分钟内有效。";
		
		BaseReturn rtn = BizSmsUtil.bizSystemSmsSend(mobil, content,GuidUtil.getTimestapId(), account);

		if (rtn != null && BaseSend.STATUS_SUCCES.equals(rtn.getCode())) {
			return code;
		}else{
			return null;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(SmsSendManager.getInstance().send("15868498057",""));

	}

}
