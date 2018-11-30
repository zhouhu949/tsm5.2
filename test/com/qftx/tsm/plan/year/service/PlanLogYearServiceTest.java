package com.qftx.tsm.plan.year.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.tsm.plan.user.day.service.TestValue;
import com.qftx.tsm.plan.year.bean.PlanLogYearBean;

public class PlanLogYearServiceTest extends BaseUtest{

	@Autowired
	PlanLogYearService service;
	
	Logger logger = Logger.getLogger(PlanLogYearServiceTest.class);
	
	@Test
	public void queryFullYearPlan() {
		List<PlanLogYearBean> datas = service.queryFullYearPlan(TestValue.orgId, "2016");
		System.out.println(JsonUtil.getJsonString(datas));
	}
	@Test
	public void insertPlanLogYearTest(){
		service.insertPlanLogYear(TestValue.userId, TestValue.orgId, "2016", "context", new Date(), "reason");
	}
	
}
