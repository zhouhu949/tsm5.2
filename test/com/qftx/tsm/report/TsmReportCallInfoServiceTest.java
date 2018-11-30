package com.qftx.tsm.report;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.team.bean.TeamGroupBean;
import com.qftx.base.team.service.TsmTeamGroupService;
import com.qftx.base.util.DateUtil;
import com.qftx.common.BaseUtest;
import com.qftx.tsm.callrecord.dto.TsmRecordCallBean;
import com.qftx.tsm.plan.user.day.service.TestValue;
import com.qftx.tsm.report.service.TsmReportCallInfoService;

public class TsmReportCallInfoServiceTest extends BaseUtest{
	@Autowired 
	private TsmReportCallInfoService service;
	@Autowired
	private TsmTeamGroupService tsmTeamGroupService;
	
	private Logger logger = Logger.getLogger(TsmReportCallInfoServiceTest.class);
	
	@Test
	public void findByAccAndDateTest(){
		Date date = new Date();
		service.findByAccAndDate(TestValue.orgId,TestValue.userAccount,DateUtil.addDate(date, 0, 0, -10),date,"asc");
	}
	
	@Test
	public void findSumByAccAndDateTest(){
		Date date = new Date();
		service.findSumByAccAndDate(TestValue.orgId,TestValue.userAccount,DateUtil.addDate(date, 0, -3, 0),date,"asc");
	}
	
	@Test
	public void findSumByDateTest(){
		String orgId="hyx49";
		
		Date date = new Date();
		List<TeamGroupBean> groups = tsmTeamGroupService.queryManageTeamList("hyx49", "hyx49003");
		String[] groupIds =tsmTeamGroupService.getGroupIds(groups);
		
		service.findSumByDate(orgId, groupIds, DateUtil.addDate(date, 0, 0, -15), date,"%Y-%m-%d", "asc");
	}
	@Test
	public void findByGroupAndDateTest(){
		String orgId="hyx49";
		Date date = new Date();
		date = DateUtil.addDate(date, 0, 0, -2);
		//service.findByGroupAndDate(orgId, "42c41353bdcc497db57cedd0dee6664f", DateUtil.dateBegin(date), DateUtil.dateEnd(date), "asc");
	}
	
	@Test
	public void findSumByGroupTest(){
		String orgId="hyx49";
		
		Date date = new Date();
		List<TeamGroupBean> groups = tsmTeamGroupService.queryManageTeamList("hyx49", "hyx49003");
		String[] groupIds =tsmTeamGroupService.getGroupIds(groups);
		
		service.findSumByGroup(orgId, groupIds, DateUtil.addDate(date, 0, 0, -15), date, "asc");
	}
	
	@Test
	public void findSumByGroupAndDateTest(){
		String orgId="hyx49";
		
		Date date = new Date();
		Date fromDate = DateUtil.monthBegin(date);
		Date toDate = DateUtil.monthEnd(date);
		List<TeamGroupBean> groups = tsmTeamGroupService.queryManageTeamList("hyx49", "hyx49003");
		String[] groupIds =tsmTeamGroupService.getGroupIds(groups);
		
		//service.findSumByGroupAndDate(orgId, groupIds[0], fromDate, toDate);
	}
	
	public TsmRecordCallBean newRecord(String callState,int timeLength,int costTimeLength,String custType){
		TsmRecordCallBean bean = new TsmRecordCallBean();
		bean.setOrgId(TestValue.orgId);
		bean.setInputAcc(TestValue.userAccount);
		bean.setStartTime(new Date());
		bean.setCallState(callState);
		bean.setTimeLength(timeLength);
		bean.setCostTimeLength(costTimeLength);
		bean.setCustType(custType);
		
		return bean;
	}

	@Test
	public void insertTest() {
		Date date= new Date();
		for (int i = 0; i < 100; i++) {
			//service.getIdByDate(TestValue.orgId,TestValue.userAccount,DateUtil.addDate(date, 0, 0, -i));
			service.insert(TestValue.orgId,TestValue.userAccount,DateUtil.addDate(date, 0, 0, -i));
		}
	}
}
