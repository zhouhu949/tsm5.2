package com.qftx.tsm.imp;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.tsm.imp.bean.ImportResResultDetailsBean;
import com.qftx.tsm.imp.service.ImportResResultDetailsService;
import com.qftx.tsm.plan.user.day.service.TestValue;

public class ImportResResultDetailsServiceTest extends BaseUtest{
	@Autowired 
	private ImportResResultDetailsService service;
	
	private Logger logger = Logger.getLogger(ImportResResultDetailsServiceTest.class);
	
	@Test
	public void queryTest() {
		List<ImportResResultDetailsBean> list = service.query(TestValue.orgId);
		logger.info(JsonUtil.getJsonString(list));
	}

	@Test
	public void insertTest() {
		for (int i = 0; i < 100; i++) {
			service.insert(TestValue.orgId);
		}
	}
}
