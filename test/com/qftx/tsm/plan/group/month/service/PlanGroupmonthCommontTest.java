package com.qftx.tsm.plan.group.month.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.tsm.plan.group.month.bean.PlanGroupmonthCommontBean;
import com.qftx.tsm.plan.user.day.service.TestValue;

public class PlanGroupmonthCommontTest extends BaseUtest {
	@Autowired
	private PlanGroupmonthCommontService service;
	Logger logger = Logger.getLogger(PlanGroupmonthCommontTest.class);
	
	@org.junit.Test
	public void queryPlanUsermonthCommont() {
		List<PlanGroupmonthCommontBean> data = service.queryByPlanId(TestValue.orgId, "5f7cb0cafcd74f548ab71644f35df489");
		logger.info(JsonUtil.getJsonString(data));
		
	}
	//@org.junit.Test
	public void insertTest() {
		String planId="5f7cb0cafcd74f548ab71644f35df489";
		String context="评论评论！";
		for(int i=0;i<10;i++){
			service.insert(TestValue.orgId, TestValue.groupId, TestValue.userId, planId, context);
		}
	}
}
