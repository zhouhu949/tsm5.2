package com.qftx.tsm.plan.group.month.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.tsm.plan.group.month.bean.PlanAuthlogBean;
import com.qftx.tsm.plan.user.day.service.TestValue;

public class PlanAuthlogTest extends BaseUtest {
	@Autowired
	private PlanAuthlogService service;
	Logger logger = Logger.getLogger(PlanAuthlogTest.class);
	
	@org.junit.Test
	public void queryByPlanIdTest() {
		List<PlanAuthlogBean> data = service.queryByPlanId(TestValue.orgId, "5f7cb0cafcd74f548ab71644f35df489","1");
		logger.info(JsonUtil.getJsonString(data));
		
	}
	//@org.junit.Test
	public void insertTest() {
		String planId="5f7cb0cafcd74f548ab71644f35df489";
		String context="审核不通过！";
		String planType ="1";//计划类型0：用户日计划 1：用户约计划 2：团队月计划
		String authState="2";//审核状态(1通过，2驳回)
		
		for(int i=0;i<10;i++){
			service.insert(TestValue.orgId, TestValue.groupId, TestValue.userId, planType, planId, authState, context);
		}
	}
}
