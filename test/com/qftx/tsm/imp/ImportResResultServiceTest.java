package com.qftx.tsm.imp;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.tsm.imp.bean.ImportResResultBean;
import com.qftx.tsm.imp.service.ImportResResultService;
import com.qftx.tsm.plan.user.day.service.TestValue;

public class ImportResResultServiceTest extends BaseUtest{
	@Autowired 
	private ImportResResultService service;
	
	private Logger logger = Logger.getLogger(ImportResResultServiceTest.class);
	

	@Test
	public void insertTest() {
		for (int i = 0; i < 100; i++) {
			//service.insert(TestValue.orgId);
		}
	}
}
