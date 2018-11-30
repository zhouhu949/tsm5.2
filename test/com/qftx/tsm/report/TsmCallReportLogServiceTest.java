package com.qftx.tsm.report;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.tsm.report.bean.TsmCallReportLogBean;
import com.qftx.tsm.report.service.TsmCallReportLogService;
import com.qftx.tsm.plan.user.day.service.TestValue;

public class TsmCallReportLogServiceTest extends BaseUtest{
	@Autowired 
	private TsmCallReportLogService service;
	
	private Logger logger = Logger.getLogger(TsmCallReportLogServiceTest.class);
	
	@Test
	public void queryTest() {
		List<TsmCallReportLogBean> list = service.query(TestValue.orgId);
		logger.info(JsonUtil.getJsonString(list));
	}

	@Test
	public void insertTest() {
		for (int i = 0; i < 100; i++) {
			service.insert(TestValue.orgId);
		}
	}
}
