package com.qftx.tsm.plan.user.month.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.tsm.plan.user.day.service.TestValue;
import com.qftx.tsm.plan.user.month.bean.PlanUserMonthNumSort;
import com.qftx.tsm.plan.user.month.bean.PlanUsermonthBean;
import com.qftx.tsm.plan.user.month.dao.PlanUsermonthMapper;

public class PlanUserMonthServiceTest extends BaseUtest {
	@Autowired
	private PlanUserMonthService service;
	@Autowired
	private PlanUsermonthMapper mapper;
	Logger logger = Logger.getLogger(PlanUserMonthServiceTest.class);

	@Test
	public void queryByYearTest() {
		List<PlanUsermonthBean> list = service.queryByYear(TestValue.orgId,TestValue.userId,"2015");
		logger.info(JsonUtil.getJsonString(list));
	}

	@Test
	public void getByIdTest() {
		PlanUsermonthBean data = service.getById(TestValue.orgId,"d85c3b8ad37e4f818dbe793fb2609e27");
		logger.info(JsonUtil.getJsonString(data));
	}

	@Test
	public void queryHistoryTest() {
		/*List<PlanUsermonthBean> list = service.queryHistory(TestValue.orgId, TestValue.groupId, TestValue.userId, "2015", "11", "0");
		logger.info(JsonUtil.getJsonString(list));
		
		List<PlanUsermonthBean> list1 = service.queryHistory(TestValue.orgId,null,null,null,null,null);
		logger.info(JsonUtil.getJsonString(list1));*/
	}
	
	@Test
	public void queryHistoryByYearAndMonthTest() {
		/*Date to = new Date();
		Date from = DateUtil.addDate(to, 0, -12, 0);
		List<PlanUsermonthBean> list = service.queryHistoryByYearAndMonth(
				TestValue.orgId, TestValue.userId, from, to);
		logger.info(list.size());*/
	}
	
	@Test
	public void authPlanTest() {
		String groupPlanId="";
		//service.authPlan("0d5a88d0c07e46cdb8cd860e19ace719",groupPlanId, TestValue.orgId, "1", TestValue.userId, "admin", "未审核");
		//service.authPlan("118525cab6564da1ba743737af2adc39",groupPlanId, TestValue.orgId, "0", TestValue.userId, "admin", "审核未通过");
		//service.authPlan("5600f5914e6145ea98f545367bb429aa",groupPlanId, TestValue.orgId, "2", TestValue.userId, "admin", "审核通过");
	}
	
	@Test
	public void upReportPlanTest() {
		service.upReportPlan("0d5a88d0c07e46cdb8cd860e19ace719", TestValue.orgId, "0");
		service.upReportPlan("118525cab6564da1ba743737af2adc39", TestValue.orgId, "1");
	}

	@Test
	public void updateFactNumTest(){
		//service.updateFactNum("0d5a88d0c07e46cdb8cd860e19ace719", TestValue.orgId, 998, 998, 99.8);
	}
	
	@Test
	public void updateTrendsTest() {
		PlanUsermonthBean bean = new PlanUsermonthBean();
		bean.setPlanMoney(100d);
		bean.setId("8083b62da5444c8fbc68a8e2d443b7eb");
		bean.setOrgId(TestValue.orgId);
		mapper.updateTrends(bean);
	}
	
	@Test
	public void receivePointTest(){
		
	}
	
	@Test
	public void findByGroupIdTest(){
		service.findByGroupId(TestValue.orgId, TestValue.groupId, "2015", "11");
		
	}
	
	@Test
	public void querySumByGroupTest() {
		PlanUsermonthBean data = service.querySumByGroup(TestValue.orgId, TestValue.groupId, "2016", "2");
		logger.info(JsonUtil.getJsonString(data));
	}
	@Test
	public void noPlanUsersTest() {
		List<String> data = service.noPlanUsers(TestValue.orgId, new ArrayList<String>(), new Date());
		
		logger.info(JsonUtil.getJsonString(data));
	}
	
	
	@Test
	public void insertTest() {
		Date date = new Date();

		/*for (int i = 0; i < 12; i++) {
			service.insert(TestValue.orgId, TestValue.groupId,
					TestValue.userId, "计划说明"+i,
					String.valueOf(DateUtil.year(date)),
					String.valueOf(DateUtil.month(date)));
			date = DateUtil.addDate(date, 0, 1, 0);
		}*/

	}
}
