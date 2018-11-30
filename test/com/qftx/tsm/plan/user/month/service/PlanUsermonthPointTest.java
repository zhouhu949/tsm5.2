package com.qftx.tsm.plan.user.month.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.common.cached.CachedService;
import com.qftx.tsm.plan.user.day.service.TestValue;
import com.qftx.tsm.plan.user.month.bean.PlanUsermonthPointBean;
import com.qftx.tsm.plan.user.month.bean.PointCache;
import com.qftx.tsm.plan.user.month.service.PlanUsermonthPointService;

public class PlanUsermonthPointTest extends BaseUtest {
	@Autowired
	private PlanUsermonthPointService service;
	@Autowired CachedService cachedService;
	Logger logger = Logger.getLogger(PlanUsermonthPointTest.class);
	
	@org.junit.Test
	public void queryPlanUsermonthPoint() {
		List<PlanUsermonthPointBean> data = service.queryByPlanId(TestValue.orgId, "5f7cb0cafcd74f548ab71644f35df489");
		logger.info(JsonUtil.getJsonString(data));
		
	}
	//@org.junit.Test
	public void insertTest() {
		String planId="5f7cb0cafcd74f548ab71644f35df489";
		String context="积分领取说明！";
		service.insert(TestValue.orgId, planId, "0", "0", context);
		service.insert(TestValue.orgId, planId, "1", "0", context);
		service.insert(TestValue.orgId, planId, "2", "1", context);
	}
	@Test
	public void a(){
		PointCache pointCache = new PointCache(TestValue.orgId, cachedService);
		
		System.out.println(pointCache.getSinglePoint());
		System.out.println(pointCache.getPlanPoint());
		System.out.println(pointCache.getPlan3MonthPoint());
	}
}
