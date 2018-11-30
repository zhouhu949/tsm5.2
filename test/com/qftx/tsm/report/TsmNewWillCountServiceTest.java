package com.qftx.tsm.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.tsm.report.service.LossSortService;
import com.qftx.tsm.report.service.TsmNewWillCountService;

public class TsmNewWillCountServiceTest extends BaseUtest{
	@Autowired
	private TsmNewWillCountService tsmNewWillCountService;

	@Test
	public void getLossSortList() {
		System.out.println("开始执行======");
		
		tsmNewWillCountService.updateTsmNewWillCount("fhtx","fhtx004","烽火4",100);
//		int s=tsmNewWillCountService.findTsmNewWillCount("fhtx","fhtx001","5","2017-01-12","2017-07-12");
//		System.out.println(s);
	}

}
