package com.qftx.tsm.worklog.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.tsm.worklog.bean.WorkLogAttentionBean;
import com.qftx.tsm.worklog.service.WorkLogAttentionService;

public class WorkLogAttentionServiceTest extends BaseUtest{
	@Autowired 
	private WorkLogAttentionService service;
	
	private Logger logger = Logger.getLogger(WorkLogAttentionServiceTest.class);
	
	@Test
	public void findTest() {
		List<WorkLogAttentionBean> list = service.findList(shiroUser);
		logger.info(JsonUtil.getJsonString(list));
	}

	@Test
	public void insertTest() {
		for (int i = 0; i < 100; i++) {
			service.insert(shiroUser,"aab366191b3a47a39b1f9a4fbfd3411d");
		}
	}
}
