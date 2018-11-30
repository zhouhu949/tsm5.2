package com.qftx.pay;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.pay.bean.PayAccountBean;
import com.qftx.pay.service.PayAccountService;

public class PayAccountServiceTest extends BaseUtest{
	@Autowired 
	private PayAccountService service;
	
	private Logger logger = Logger.getLogger(PayAccountServiceTest.class);
	
	@Test
	public void findTest() {
		List<PayAccountBean> list = service.find(shiroUser.getOrgId());
		logger.info(JsonUtil.getJsonString(list));
	}
	
	@Test
	public void getAccountTet() {
		PayAccountBean account = service.getAccount(shiroUser);
		logger.info(JsonUtil.getJsonString(account));
	}

	@Test
	public void insertTest() {
		PayAccountBean account= new PayAccountBean();
		account.setOrgId(shiroUser.getOrgId());
		account.setAccount(shiroUser.getAccount());
		account.setBalance(10000);
		
		service.insert(account);
	}
}
