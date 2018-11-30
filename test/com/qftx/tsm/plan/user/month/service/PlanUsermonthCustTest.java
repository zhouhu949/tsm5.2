package com.qftx.tsm.plan.user.month.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.tsm.plan.user.day.service.TestValue;
import com.qftx.tsm.plan.user.month.bean.PlanHistoryBean;
import com.qftx.tsm.plan.user.month.bean.PlanUsermonthCustBean;
import com.qftx.tsm.plan.user.month.dao.PlanUsermonthCustMapper;
import com.qftx.tsm.plan.user.month.service.PlanUsermonthCustService;

public class PlanUsermonthCustTest extends BaseUtest {
	@Autowired
	private PlanUsermonthCustService service;
	@Autowired
	private PlanUsermonthCustMapper mapper;
	Logger logger = Logger.getLogger(PlanUsermonthCustTest.class);
	
	//@Test
	public void queryPlanUsermonthCust() {
		List<PlanUsermonthCustBean> data = service.queryByPlanUserMonthId(TestValue.orgId, "5f7cb0cafcd74f548ab71644f35df489");
		logger.info(JsonUtil.getJsonString(data));
		
	}
	
	@Test
	public void findHistoryListPageTest() {
		PlanHistoryBean item = new PlanHistoryBean();
		item.setOrgId(TestValue.orgId);
		item.setIsDel(0);
		item.setPlanYear("2016");
		item.setPlanMonth("1");
		item.setGroupIds(new String[]{"14","15"});
		item.setUserIds(new String[]{TestValue.userId});
		
		List<PlanHistoryBean> list = service.findHistoryListPage(item);
		System.out.println(JsonUtil.getJsonString(list));
	}
	
	@Test
	public void queryCustSumTest(){
		PlanUsermonthCustBean bean = new PlanUsermonthCustBean();
		bean.setOrgId(TestValue.orgId);
		bean.setPlanId("8083b62da5444c8fbc68a8e2d443b7eb");
		mapper.queryCustSum(bean);
	}
	
	@Test
	public void updateFactNumTest(){
		//service.updateFactNum(orgId, planId, custId, factSingcustNum, factMoney);
		//service.updateFactNum(TestValue.orgId, "b9c65dbecd38460aa72ad0e151af284d", "fce0fb195d1b441a8ea29d258689d970", 100);
	}
	
	@Test
	public void findFromResTest(){
		service.findFromRes(TestValue.orgId, TestValue.custIds);
		
	}
	
	//@Test
	public void insertTest() {
		String[] custIds ={"e0fd4720442f4d208449816d913e1da9","86d9e462c0704c6b999ad9e2606eaea1","7abca19d83484509a075d84f97d2dfb1"
				,"bc2c1657e98f4ae886eb885b5229f2f7","178bdaffb49b4b9c9a0054a063f82274","e566cba8d3a24d90be0bba52e1fb212e","c2d46e0960fc491eaa9463ad5e03a585","0313501eb7a748bf87d89b3cfc789e2e","01165ee640764d85b12c7d81dd17f0f6","3522c67ef9574576a7de6248761f04d4"};
		
		for (String custId : custIds) {
			String planId="5f7cb0cafcd74f548ab71644f35df489";
			double planMoney=1000;
			String context="计划说明";
		}
		
	}
}
