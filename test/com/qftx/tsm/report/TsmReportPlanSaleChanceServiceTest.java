package com.qftx.tsm.report;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.tsm.report.bean.TsmReportPlanSaleChanceBean;
import com.qftx.tsm.report.service.TsmReportPlanSaleChanceService;

public class TsmReportPlanSaleChanceServiceTest extends BaseUtest{
	@Autowired 
	private TsmReportPlanSaleChanceService service;
	
	private Logger logger = Logger.getLogger(TsmReportPlanSaleChanceServiceTest.class);
	
	@Test
	public void findTest() {
		List<TsmReportPlanSaleChanceBean> list = service.find(shiroUser.getOrgId());
		logger.info(JsonUtil.getJsonString(list));
	}

	@Test
	public void insertTest() {
		for (int i = 0; i < 100; i++) {
			service.insert("qftx");
		}
	}
}
