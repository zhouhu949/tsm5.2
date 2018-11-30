package com.qftx.tsm.imp;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.common.BaseUtest;
import com.qftx.tsm.imp.service.ImportResFileService;
import com.qftx.tsm.plan.user.day.service.TestValue;

public class ImportResFileServiceTest extends BaseUtest{
	@Autowired 
	private ImportResFileService service;
	
	private Logger logger = Logger.getLogger(ImportResFileServiceTest.class);
	
	@Test
	public void insertTest() {
		for (int i = 0; i < 100; i++) {
		}
	}
	
	@Test
	public void updateHeaderTest(){
		service.updateHeader(TestValue.orgId, "09581249f7954089a480d7baf088ef94", "header",100);
	}
	
	
	@Test
	public void excel2DbTest(){
		//service.excel2Db(TestValue.groupId,"09581249f7954089a480d7baf088ef94");
	}
}
