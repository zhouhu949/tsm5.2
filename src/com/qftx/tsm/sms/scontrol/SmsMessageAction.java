package com.qftx.tsm.sms.scontrol;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.common.domain.BaseJsonResult;
import com.qftx.common.util.StringUtils;
import com.qftx.pay.bean.PaySmsCodeBean;
import com.qftx.pay.service.PaySmsCodeService;
import com.qftx.pay.service.SmsSendManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/SendSms")
public class SmsMessageAction {
	@Resource
	PaySmsCodeService paySmsCodeService;
	
	
	@RequestMapping("/getSmsCode")
	@ResponseBody
	public BaseJsonResult getSmsCode(HttpServletRequest request,String mobile){
		ShiroUser user = ShiroUtil.getUser();
		PaySmsCodeBean smsCode =new PaySmsCodeBean();
		smsCode.setOrgId(user.getOrgId());
		smsCode.setAccount(user.getAccount());
		smsCode.setMobile(mobile);
		
//		PaySmsCodeBean dbSmsCode = paySmsCodeService.getLastSmsCode(smsCode);
//		if(dbSmsCode==null || !paySmsCodeService.valid(dbSmsCode)){
			String code = SmsSendManager.getInstance().send(mobile,user.getAccount());
			if(code==null){
				return BaseJsonResult.error("短信发送异常，请联系管理员！");
			}else{
				smsCode.setCode(code);
				smsCode = paySmsCodeService.insert(smsCode);
				//TODO 需要将验证码设置为NULL 不返回到前台
				//smsCode.setCode(null);
				return BaseJsonResult.success(smsCode);
			}
//		}else{
			//dbSmsCode.setCode(null);
//			return BaseJsonResult.success(smsCode);
//		}
	}
	
	@RequestMapping("/checkSmsCode")
	@ResponseBody
	public BaseJsonResult checkSmsCode(HttpServletRequest request,String mobile,String code){
		ShiroUser user = ShiroUtil.getUser();
		PaySmsCodeBean smsCode =new PaySmsCodeBean();
		
		if(StringUtils.isBlank(code)){
			return BaseJsonResult.error("验证码为空！");
		}
		
		smsCode.setCode(null);
		smsCode.setOrgId(user.getOrgId());
		smsCode.setAccount(user.getAccount());
		smsCode.setMobile(mobile);
		PaySmsCodeBean dbSmsCode = paySmsCodeService.getLastSmsCode(smsCode);
		if(dbSmsCode!=null &&code.trim().equals(dbSmsCode.getCode())){
			if(paySmsCodeService.valid(dbSmsCode)){
				PaySmsCodeBean bean=new PaySmsCodeBean();
				bean.setId(dbSmsCode.getId());
				bean.setStatus(1);
				paySmsCodeService.updateTrends(bean);
				return BaseJsonResult.success();
			}else{
				return BaseJsonResult.error("验证码过期，请重新输入验证码！");	
			}
		}else{
			return BaseJsonResult.error("验证码无效，请重新输入验证码!");
			
		}
				
		
//		if(dbSmsCode!=null || paySmsCodeService.valid(dbSmsCode)){
//			System.out.println(code.trim());
//			System.out.println(smsCode.getCode());
//			if(code.trim().equals(dbSmsCode.getCode())){
//				PaySmsCodeBean bean=new PaySmsCodeBean();
//				bean.setId(dbSmsCode.getId());
//				bean.setStatus(1);
//				paySmsCodeService.updateTrends(bean);
//				return BaseJsonResult.success();
//			}else{
//				return BaseJsonResult.error("验证码无效，请重新输入验证码!");
//			}
//		}else{
//			return BaseJsonResult.error("验证码过期，请重新输入验证码！");
//		}
	}
	
	@RequestMapping("/SmsReceiveNew")
	public String SmsReceiveNew(HttpServletRequest request,String mobile){
		ShiroUser user = ShiroUtil.getUser();
		return "/call/idialog/sms_receive_bind_newmob";
		

	}
	
	@RequestMapping("/SmsReceiveOld")
	public String SmsReceiveOld(HttpServletRequest request,String mobile){
		ShiroUser user = ShiroUtil.getUser();
		request.setAttribute("mobile", mobile);
		return "/call/idialog/sms_receive_bind_show_oldmob";
	}
	
	public static void main(String[] args) {
		System.out.println(SmsSendManager.getInstance().send("13757125037",""));

	}


}
