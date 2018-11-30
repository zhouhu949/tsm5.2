package com.qftx.tsm.sys.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.tsm.plan.user.day.service.TestValue;
import com.qftx.tsm.sys.bean.SysFileBean;

public class SysFileServiceTest extends BaseUtest{
	@Autowired 
	private SysFileService service;
	
	private Logger logger = Logger.getLogger(SysFileServiceTest.class);
	
	@Test
	public void queryTest() { 
		List<SysFileBean> list = service.getList();
		logger.info(JsonUtil.getJsonString(list));
	}

	@Test
	public void insertTest() { 
		for(int i=0;i<100;i++){
		}
	}
}
