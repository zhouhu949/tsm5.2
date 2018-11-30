package com.qftx.tsm.plan.year.service;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.tsm.plan.user.day.service.TestValue;
import com.qftx.tsm.plan.year.dto.ProgressDto;

public class PlanProgressServiceTest extends BaseUtest {
	@Autowired
	PlanProgressService service;
	Logger logger = Logger.getLogger(PlanProgressServiceTest.class);
	@Test
	public void findFullYearOrderByMonthTest() {
		ProgressDto dto = service.findFullYearOrderByMonth(TestValue.orgId,
				"2015");
		System.out.println(JsonUtil.getJsonString(dto));
	}
}
