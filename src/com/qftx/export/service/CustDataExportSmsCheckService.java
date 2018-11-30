package com.qftx.export.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qftx.common.util.StringUtils;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.pay.bean.PaySmsCodeBean;
import com.qftx.pay.service.PaySmsCodeService;
import com.qftx.pay.service.SmsSendManager;

@Service
public class CustDataExportSmsCheckService {
	@Autowired
	private PaySmsCodeService paySmsCodeService;
	
	public boolean sendSmsCheckCode(String mobilephone,String orgId,String userAccount,String userName,Integer exportType){
		boolean flag = false;
		String code = SmsSendManager.getInstance().sendExportCode(mobilephone,StringUtils.isBlank(userName) ? userAccount : userName,exportType,userAccount);
		if(code != null){
			flag = true;
			PaySmsCodeBean bean = new PaySmsCodeBean();
			bean.setId(SysBaseModelUtil.getModelId());
			bean.setOrgId(orgId);
			bean.setAccount(userAccount);
			bean.setIsDel(0);
			bean.setStatus(0);
			bean.setInputtime(new Date());
			bean.setMobile(mobilephone);
			bean.setCode(code);
			paySmsCodeService.insert(bean);
		}
		return flag;
	}
	
	public boolean smsCodeCheck(String code,String orgId,String userAccount){
		boolean flag = false;
		PaySmsCodeBean bean = new PaySmsCodeBean();
		bean.setOrgId(orgId);
		bean.setAccount(userAccount);
		bean.setCode(code);
		PaySmsCodeBean lastBean = paySmsCodeService.getLastSmsCode(bean);
		flag = paySmsCodeService.valid(lastBean);
		if(flag){
			lastBean.setStatus(1);
			lastBean.setUpdatetime(new Date());
			paySmsCodeService.updateTrends(lastBean);
		}
		return flag;
	}
}
