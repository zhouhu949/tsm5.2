package com.qftx.tsm.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.tsm.report.service.LossSortService;

public class LossSortSeriviceTest extends BaseUtest {
	@Autowired
	private LossSortService lossSortService;

	@Test
	public void getLossSortList() {
		System.out.println("开始执行======");
		Map<String, String> map = new HashMap<String, String>();
		map.put("orgId", "8decbe1278b646b5a462bbd4bc80bd58");
		map.put("account", "hn001");
		lossSortService.getLossSortList(map);
		List<List<Object>> list = lossSortService.getLossSortList(map);
		System.out.println("返回结果：" + JsonUtil.getJsonString(list));
	}
}
