package com.qftx.tsm.plan.user.day.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.tsm.cust.service.ResCustInfoService;
import com.qftx.tsm.plan.user.day.bean.PlanUserDayResourceBean;

public class PlanUserDayResourceServiceTest extends BaseUtest {
	@Autowired
	private PlanUserDayResourceService service;
	@Autowired
	private ResCustInfoService resCustService;
	Logger logger = Logger.getLogger(PlanUserDayResourceServiceTest.class);
	
	@Test
	public void queryBySudIdTest() {
		PlanUserDayResourceBean bean = new PlanUserDayResourceBean();
		bean.setOrgId(TestValue.orgId);
		bean.setSudId(TestValue.sudId);
		
		List<PlanUserDayResourceBean> list = service.queryPageBySudId(bean);
	}
	 	 
	@Test
	public void findIsPrecedenceTest(){
		Integer data = resCustService.findIsPrecedence(TestValue.orgId, "c2d46e0960fc491eaa9463ad5e03a585");
		System.out.println(String.valueOf(data));
		System.out.println(String.valueOf(new Integer(1)));
		System.out.println(String.valueOf(null));
	}
	
	@Test
	public void queryDataLableTest() {
		logger.info(service.queryDataLable(TestValue.orgId,  "b5ee70f59e4c4038bffef147c5ad3aca"));
	}
	
	@Test
	public void queryDataLableTest1() {
		//logger.info(service.queryDataLable(TestValue.orgId,TestValue.userId.split(",") , new Date()));
	}
	@Test
	public void findByCustIdsTest(){
		resCustService.findByCustIds(TestValue.orgId, Arrays.asList(TestValue.custIds));
	}
	
	@Test
	public void findFromResTest(){
		/*List<PlanUserDayResourceBean> list = service.findFromRes(TestValue.orgId, TestValue.custIds);
	
		System.out.println(JsonUtil.getJsonString(list));*/
	}
	
}
