package com.qftx.tsm.plan.user.day.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.tsm.plan.user.day.bean.PlanUserdaySigncustBean;

public class PlanUserdaySigncustServiceTest extends BaseUtest {
	@Autowired
	private PlanUserdaySigncustService service;
	Logger logger = Logger.getLogger(PlanUserdaySigncustServiceTest.class);
	
	@Test
	public void queryDataLableTest1() {
		logger.info(service.queryDataLable(TestValue.orgId, TestValue.userId.split(","), new Date()));
	}
	
	@Test
	public void findFromResTest() {
	//	List<PlanUserdaySigncustBean> list = service.findFromRes(TestValue.orgId, TestValue.custIds);
		//logger.info(JsonUtil.getJsonString(list));
	}
	
	//@Test
	public void insertTest() {
	}

}
