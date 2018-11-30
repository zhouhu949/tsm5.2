package com.qftx.tsm.worklog.service;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.team.service.TsmTeamGroupMemberService;
import com.qftx.base.team.service.TsmTeamGroupService;
import com.qftx.base.util.DateUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.common.domain.BaseJsonResult;
import com.qftx.tsm.plan.user.day.service.TestValue;
import com.qftx.tsm.worklog.bean.WorkLogInfoBean;

public class WorkLogServiceTest extends BaseUtest {
	
	Logger logger = Logger.getLogger(WorkLogServiceTest.class);
	@Autowired
	private WorkLogInfoService service;  
	
	@Autowired
	private TsmTeamGroupService tsmTeamGroupService;
	@Autowired
	private TsmTeamGroupMemberService tsmTeamGroupMemberService;
	
	String[] xiaShuIds ={"sel","3bd02107778e4e59a84e15be5812732d","846833d3987344c29d1aea6d6a9e09d5","d73d4d42a3d343e799bbe3963045d323","3ae5f7c8118d4205a51d4d7cd10e61e6"};
	
	@Test
	public void queryIfWorkLogExistTest() {
		boolean flag = service.queryIfWorkLogExist(TestValue.orgId,TestValue.userId, new Date());
		System.out.println(flag);
	}
	@Test
	public void queryWorkLogByIdTest() {
		WorkLogInfoBean query = new WorkLogInfoBean();
		String orgId = "hzhh9";
		String wliId = "0b28a386dcad4d659c34f25ab337f56b";
		query.setOrgId(orgId);
		query.setWliId(wliId);
		WorkLogInfoBean workLog = service.getByCondtion(query );
		System.out.println(JsonUtil.getJsonString(workLog));
	}
	@Test
	public void queryWorkLogByMonthTest() {
		List<Date> dates = service.queryWorkLogByMonth(TestValue.orgId,TestValue.userId, new Date());
		System.out.println(JsonUtil.getJsonString(dates));
	}
	@Test
	public void getManagerCalendarStatetest() {
		String orgId="xn5";
		String account = "xn5001";
		//service.getManagerCalendarState(orgId, account, System.currentTimeMillis());
	}
	
	
	@Test
	public void insertTest(){
		/*String orgId="aa";
		String id="123";
		String groupId = "123";
		
		ShiroUser user = new ShiroUser();
		user.setOrgId(orgId);
		user.setId(id);
		user.setGroupId(groupId);*/
		
		WorkLogInfoBean workLogInfo = new WorkLogInfoBean();
		
		workLogInfo.setContext("今日工作总结");
		workLogInfo.setWorkPlan("明日工作计划");
		workLogInfo.setLogDate(DateUtil.addDay(new Date(), 1));
		
		BaseJsonResult result = service.insertWorkLog(shiroUser, workLogInfo);
		
		logger.info(result);
	}
}
