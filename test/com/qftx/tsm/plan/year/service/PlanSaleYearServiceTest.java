package com.qftx.tsm.plan.year.service;


import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.tsm.plan.user.day.service.TestValue;
import com.qftx.tsm.plan.year.bean.PlanSaleTrendBean;
import com.qftx.tsm.plan.year.bean.PlanSaleYearBean;
import com.qftx.tsm.plan.year.dto.AllYearPlanDTO;
import com.qftx.tsm.plan.year.dto.PlanChangeDTO;

public class PlanSaleYearServiceTest extends BaseUtest {
	@Autowired
	PlanSaleYearService service;
	Logger logger = Logger.getLogger(PlanSaleYearServiceTest.class);
	
	static ShiroUser user = new ShiroUser();
	
	
	@Test
	public void queryFullYearPlan() {
		
		try {
			AllYearPlanDTO datas = service.queryFullYearPlan(user.getOrgId(),user.getId(), new Date());
			System.out.println(JsonUtil.getJsonString(datas));
		} catch (Exception e) {
			logger.error("", e);
		}
	}
	
	@Test
	public void getPlanYearProgressTest(){
		PlanSaleTrendBean data = service.getPlanYearProgress(TestValue.orgId, new Date());
		System.out.println(JsonUtil.getJsonString(data));
	}
	
	@Test
	public void test(){
		String str="[{\"groupId\":\"12\",\"month\":\"1\",\"money\":\"111\"}]";
		
		List<PlanChangeDTO> list= JsonUtil.getListJson(str, PlanChangeDTO.class);
		for (PlanChangeDTO planChanges : list) {
			System.out.println(planChanges);
		}
	}
	
	@Test
	public void queryPlanSalTrendTest(){
		
		try {
			List<PlanSaleTrendBean> datas = service.queryPlanSalTrend(user.getOrgId(), "", "", "");
			System.out.println(JsonUtil.getJsonString(datas));
		} catch (Exception e) {
			logger.error("", e);
		}
	}
	@Test
	public void findAllGroupByMonthTest(){
		List<PlanSaleYearBean> list = service.findAllGroupByMonth(TestValue.orgId, "2016", "2");
		
		System.out.println(list);
	}
	@Test
	public void getPlanSaleYearBeanTest(){
		service.getPlanSaleYearBean(TestValue.orgId, new String[]{"12"}, "2016", "2");
	}
	
	@Test
	public void insertPlanLogYearTest(){
		
	}
}
