package com.qftx.pay;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.pay.bean.PaySmsCodeBean;
import com.qftx.pay.service.PaySmsCodeService;

public class PaySmsCodeServiceTest extends BaseUtest{
	@Autowired 
	private PaySmsCodeService service;
	
	private Logger logger = Logger.getLogger(PaySmsCodeServiceTest.class);
	
	@Test
	public void findTest() {
		/*List<PaySmsCodeBean> list = service.find(shiroUser.getOrgId());
		logger.info(JsonUtil.getJsonString(list));*/
	}

	@Test
	public void insertTest() {
		for (int i = 0; i < 100; i++) {
			//service.insert(shiroUser.getOrgId());
		}
	}
}
