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
import com.qftx.tsm.report.service.TsmReportWillService;

public class TsmReportWillServiceTest extends BaseUtest{
	@Autowired
	private TsmReportWillService tsmReportWillService;
	

	@Test
	public void getLossSortList() {
		System.out.println("开始执行======");
		
//		tsmNewWillCountService.updateTsmNewWillCount("fhtx","fhtx002","烽火通信002",1);
//		tsmReportWillService.addTsmReportWillSum("fhtx", "fhtx003", "", 1);
		tsmReportWillService.addTsmReportWillOptionSum("fhtx", "fhtx001", "", "f5860f3ae9bd42ceb619a3bc3afc87aa", "", 10);
//		tsmReportWillService.addTsmReportWillandOption("fhtx", "fhtx001", "烽火通信001", 1, "e20263ba30824474acd118925d3693da", "", 1);	
//		tsmReportWillService.addTsmReportWillOptionSumCheck("fhtx", "fhtx001", "", "f5860f3ae9bd42ceb619a3bc3afc87aa", "", "01e53d7dc1064b5487a0a88c93ab6f61", 1);
	}

}
