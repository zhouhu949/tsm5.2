package com.qftx.tsm.imp;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.tsm.imp.bean.ImportResInfoBean;
import com.qftx.tsm.imp.service.ImportResInfoService;
import com.qftx.tsm.plan.user.day.service.TestValue;

public class ImportResInfoServiceTest extends BaseUtest{
	@Autowired 
	private ImportResInfoService service;
	
	private Logger logger = Logger.getLogger(ImportResInfoServiceTest.class);
	
	@Test
	public void queryTest() {
		List<ImportResInfoBean> list = service.query(TestValue.orgId);
		logger.info(JsonUtil.getJsonString(list));
	}

	@Test
	public void analyzeTest(){
		//service.analyze(TestValue.orgId,TestValue.groupId, "c70ccbd0719a470289e79d0491ec37da","admin",0);
	}
	
	@Test
	public void jsonTest(){
		String string="[\"名称\",\"性别\",\"生日\",\"重点关注\",\"常用电话\",\"备用固话\",\"传真\",\"单位名称\",\"关键字\",\"邮箱\",\"IM\",\"单位主页\",\"所在部门\",\"职务\",\"自定义1\",\"自定义2\",\"自定义3\",\"自定义4\",\"自定义5\",\"备用电话2\",\"联系地址\",\"备注\"]";
		String[] array = JsonUtil.getStringArrayJson(string);
	}
	
	@Test
	public void insertTest() {
		for (int i = 0; i < 100; i++) {
		}
	}
}
