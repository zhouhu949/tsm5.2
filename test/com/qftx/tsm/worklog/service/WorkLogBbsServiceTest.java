package com.qftx.tsm.worklog.service;


import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.common.BaseUtest;
import com.qftx.tsm.worklog.bean.WorkLogBbsBean;

public class WorkLogBbsServiceTest extends BaseUtest {
	
	Logger logger = Logger.getLogger(WorkLogBbsServiceTest.class);
	@Autowired
	private WorkLogBbsService service;  
	
	@Test
	public void insertTest(){
		for(int i=0;i<100;i++){
			WorkLogBbsBean workLogBbs = new WorkLogBbsBean();
			workLogBbs.setWliId("8f98c26e10f24182a18b22f57e024e86");
			workLogBbs.setContext("sys Test");
			//service.insertBbs("xn5002", "xn5", workLogBbs);
		}
	}
	
	@Test
	public void findListPageTest(){
		WorkLogBbsBean item= new WorkLogBbsBean();
		
		try {
			service.findListPage(shiroUser, item);
		} catch (Exception e) {
		
			e.printStackTrace();
		}
		
	}
	
	
}
