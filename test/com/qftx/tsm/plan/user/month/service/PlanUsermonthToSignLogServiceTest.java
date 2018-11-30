package com.qftx.tsm.plan.user.month.service;

import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.tsm.plan.user.month.bean.PlanUsermonthToSignLogBean;

public class PlanUsermonthToSignLogServiceTest extends BaseUtest{
	@Autowired 
	private PlanUsermonthToSignLogService service;
	
	private Logger logger = Logger.getLogger(PlanUsermonthToSignLogServiceTest.class);
	
	@Test
	public void findSumTest() {
		PlanUsermonthToSignLogBean data = service.findSumFact("orgId", new String[]{"userId1","userId2"}, new Date());
		logger.info(JsonUtil.getJsonString(data));
	}
}
