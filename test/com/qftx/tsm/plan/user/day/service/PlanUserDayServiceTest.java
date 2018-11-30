package com.qftx.tsm.plan.user.day.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.tsm.plan.user.day.bean.PlanUserDayBean;
import com.qftx.tsm.plan.user.day.dto.PLanUserDayAuthListDTO;

public class PlanUserDayServiceTest extends BaseUtest {
	@Autowired
	private PlanUserDayService service;
	Logger logger = Logger.getLogger(PlanUserDayServiceTest.class);
	
	@Autowired PlanUserDayResourceService resService;
	@Autowired PlanUserdayWillcustService wilService;
	@Autowired PlanUserdaySigncustService sigService;
	
	@Test
	public void getPlanUserDayTest() {
		logger.info(JsonUtil.getJsonString(service.getPlanUserDay(TestValue.orgId, TestValue.userId, new Date())));
	}
	
	@Test
	public void queryPlanByIdTest(){
		PlanUserDayBean data = service.queryPlanById("33c9a2164bc8484388858904f420ffd4", "d633d008ef5b4f93ad6be6426ca2c0c1");
		System.out.println(JsonUtil.getJsonString(data));
	}
}
