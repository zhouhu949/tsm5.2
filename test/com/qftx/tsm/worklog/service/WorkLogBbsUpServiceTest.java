package com.qftx.tsm.worklog.service;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.common.BaseUtest;
import com.qftx.tsm.plan.user.day.service.TestValue;

public class WorkLogBbsUpServiceTest extends BaseUtest{
	@Autowired 
	private WorkLogBbsUpService service;
	
	private Logger logger = Logger.getLogger(WorkLogBbsUpServiceTest.class);
	
	@Test
	public void insertTest() {
		for (int i = 0; i < 100; i++) {
			//service.insert(TestValue.orgId,"","userId"+i);
		}
	}
}
