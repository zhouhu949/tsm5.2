package com.qftx.tsm.report;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.team.service.TsmTeamGroupService;
import com.qftx.common.BaseUtest;
import com.qftx.tsm.report.service.TsmReportPlanService;

public class TsmReportPlanServiceTest extends BaseUtest{
	@Autowired 
	private TsmReportPlanService service;
	@Autowired
	TsmTeamGroupService tsmTeamGroupService;
	private Logger logger = Logger.getLogger(TsmReportPlanServiceTest.class);
	 
}
