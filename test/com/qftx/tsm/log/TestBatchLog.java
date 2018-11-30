package com.qftx.tsm.log;

import java.util.Arrays;
import java.util.Map;

import net.sf.json.JSONObject;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.common.BaseUtest;
import com.qftx.common.util.OperateEnum;
import com.qftx.common.util.StringUtils;
import com.qftx.tsm.log.service.LogBatchInfoService;

public class TestBatchLog extends BaseUtest {
	@Autowired(required = false)
	private LogBatchInfoService service;
	
	@Test
	public void insert(){
		try {
			//service.saveLogInfo("123", "456",OperateEnum.LOG_DIST, Arrays.asList("aaa,bbb,ccc".split(",")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
