package com.qftx.tsm.plan.user.month.service;

import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.tsm.plan.user.month.bean.PlanUsermonthToWillLogBean;

public class PlanUsermonthToWillLogServiceTest extends BaseUtest{
	@Autowired 
	private PlanUsermonthToWillLogService service;
	
	private Logger logger = Logger.getLogger(PlanUsermonthToWillLogServiceTest.class);
	
	@Test
	public void findSumTest() {
		PlanUsermonthToWillLogBean data = service.findSumFact("orgId", new String[]{"userId1","userId2"}, new Date());
		logger.info(JsonUtil.getJsonString(data));
	}
	
	@Test
	public void insertTest() {
		for (int i = 0; i < 100; i++) {
			service.insert("orgId", "groupId", "userId", 1);
		}
	}
}
