package com.qftx.pay.api;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.qftx.base.auth.service.UserService;
import com.qftx.base.util.HttpUtil;
import com.qftx.common.domain.BaseJsonResult;
import com.qftx.common.util.ConfigInfoUtils;
import com.qftx.tsm.main.service.MainService;

@Service
public class PayApiFacade {
	Logger logger = Logger.getLogger(PayApiFacade.class);
	@Autowired
	private MainService mainService;
	
	public BaseJsonResult pay(PayApiRequest request){
		String requestJson = null;
		String requestJsonEncode = null;
		String responsJson = null;
		try {
			requestJson = JSON.toJSONString(request);
			requestJsonEncode = Base64Util.enBase64(requestJson);
			//"http://127.0.0.1/pay/api/award"
			String apiUrl = ConfigInfoUtils.getStringValue("tsm_pay_url")+"/pay/api/award";
			responsJson = HttpUtil.doPostJSON(apiUrl, requestJsonEncode);
			BaseJsonResult respons = JSON.parseObject(responsJson, BaseJsonResult.class);
			if(!(boolean)respons.get("status")){
				String msg = "requestJson:"+requestJson+"\n"+"requestJsonEncode:"+requestJsonEncode+"\n"+"responsJson:"+responsJson;
				logger.error(msg);
			}else{
				mainService.sendHbBarrage(request.getDesc(), request.getOrgId(), request.getUserAcc(), "admin");
				logger.error("*****红包发送成功*****："+request.getDesc()+"——单位："+ request.getOrgId()+"——账户："+ request.getUserAcc());
			}
			return respons;
		} catch (Exception e) {
			String msg = "requestJson:"+requestJson+"\n"+"requestJsonEncode:"+requestJsonEncode+"\n"+"responsJson:"+responsJson;
			logger.error("接口异常:"+msg,e);
			return BaseJsonResult.error("接口异常！");
		}
	}
}
