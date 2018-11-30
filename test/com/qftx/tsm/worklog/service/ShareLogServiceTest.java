package com.qftx.tsm.worklog.service;


import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.team.service.TsmTeamGroupService;
import com.qftx.base.util.DateUtil;
import com.qftx.common.BaseUtest;

public class ShareLogServiceTest extends BaseUtest {
	
	Logger logger = Logger.getLogger(ShareLogServiceTest.class);
	@Autowired
	private WorkLogShareService service;
	@Autowired
	private TsmTeamGroupService tsmTeamGroupService;
	 
	@Test
	public void findShareLogByPageTest(){
		Date date = new Date();
		Date fromDate = DateUtil.dateBegin(date);
		Date toDate = DateUtil.dateEnd(date);
		
		String orgId="xn5";
		String userId="8b4350ee50f8469c8075745585af4e7f";
		String userAccount = "xn5001";
		
	}
}
