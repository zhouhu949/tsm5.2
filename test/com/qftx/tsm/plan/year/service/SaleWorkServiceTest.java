package com.qftx.tsm.plan.year.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.tsm.plan.year.service.SaleWorkService;

public class SaleWorkServiceTest extends BaseUtest{

	Logger logger = Logger.getLogger(SaleWorkServiceTest.class);
	@Autowired
	private SaleWorkService service;
	static ShiroUser user = new ShiroUser();
	@org.junit.Test
	public void queryFullYearPlan() {
		System.out.println(JsonUtil.getJsonString(service.querySaleWorks(user.getOrgId())));
	}
}
