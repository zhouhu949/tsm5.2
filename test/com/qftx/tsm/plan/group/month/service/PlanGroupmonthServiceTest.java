package com.qftx.tsm.plan.group.month.service;

import java.util.Date;
import java.util.List;

import javax.xml.crypto.Data;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.util.DateUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.tsm.plan.group.month.bean.PlanGroupmonthBean;
import com.qftx.tsm.plan.user.day.service.TestValue;

public class PlanGroupmonthServiceTest extends BaseUtest {
	@Autowired
	private PlanGroupmonthService service;

	Logger logger = Logger.getLogger(PlanGroupmonthServiceTest.class);
	
	

	@Test
	public void queryPlanByMonthTest() {
		/*PlanGroupmonthBean list = service.queryPlanByMonthAndGroupId(TestValue.orgId,
				TestValue.groupId, "2015", "11");
		logger.info(JsonUtil.getJsonString(list));*/
	}
	 
	@Test
	public void queryPlanByYearTest() {
		List<PlanGroupmonthBean> list = service.queryPlanByYearAndGroupId(
				TestValue.orgId, TestValue.groupId, "2015");
		logger.info(JsonUtil.getJsonString(list));
	}
	
	@Test
	public void queryGroupPlanByMonthTest() {
		String [] groupIds = {};
		List<PlanGroupmonthBean> list = service.queryPlanByGroupIds(TestValue.orgId,groupIds, TestValue.groupId, "2015");
		logger.info(JsonUtil.getJsonString(list));
	}
	
	@Test
	public void queryPlanByIdTest() {
		String id="0915b159a0cc4974a83a1fb21327d7e5";
		PlanGroupmonthBean bean = service.getById(TestValue.orgId, id);
		logger.info(JsonUtil.getJsonString(bean));
	}
	
	@Test
	public void updatePlanNumTest() {
		String id="0915b159a0cc4974a83a1fb21327d7e5";
		service.updatePlanNum(id, TestValue.orgId, 100, 100, 100d);
	}
	
	@Test
	public void updateFactNumTest() {
		service.updateFactNum(TestValue.orgId, TestValue.groupId, 200, 200, 200d, new Date(),0);
	}
	
	@Test
	public void updatePlanWrapNumTest() {
		String id="0915b159a0cc4974a83a1fb21327d7e5";
		service.updatePlanWrapNum(id, TestValue.orgId, 300, 300, 300d, "调整说明");
	}
	
	@Test
	public void queryHistoryTest() {
		Date from = new Date();
		Date to =DateUtil.addDate(from, 0, 1, 0);
		service.queryHistory(TestValue.orgId,TestValue.groupId, from, to);
	}
	
	@Test
	public void queryHistoryTest1() {
		Date from = new Date();
		Date to =DateUtil.addDate(from, 0, 1, 0);
		service.queryHistory(TestValue.orgId,new String[]{TestValue.groupId}, from, to);
	}
	
	@Test
	public void authPlanTest() {
		String groupPlanId="";
	}
	
	@Test
	public void upReportPlanTest() {
		service.upReportPlan("0915b159a0cc4974a83a1fb21327d7e5", TestValue.orgId, "0");
		service.upReportPlan("1795bfd3825841fa834de61b1818036f", TestValue.orgId, "1");
	}
	
	@Test
	public void findNoReportGroupsTest() {
		List<String> data = service.findNoReportGroups(TestValue.orgId, new Date());
		
		System.out.println(JsonUtil.getJsonString(data));
	}

	@Test
	public void insertTest() {
		
		/*for (int i = 1; i <= 12; i++) {
			service.insert(TestValue.orgId, "15", "2016",
					String.valueOf(i),0);
		}
		
		for (int i = 1; i <= 12; i++) {
			service.insert(TestValue.orgId, "16", "2016",
					String.valueOf(i),0);
		}*/
	}

}
