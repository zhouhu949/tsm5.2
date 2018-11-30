package com.qftx.tsm.report;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.qftx.common.BaseUtest;
import com.qftx.tsm.report.dto.ResCustReportDto;

public class CustomReportTest extends BaseUtest{
	
	@Test
	public void test1() {
		ResCustReportDto dto = new ResCustReportDto();
		dto.setResGroupId("asdfadsfsadfsadf");
		dto.setResGroupMatch(1);
		String string = JSON.toJSON(dto).toString();
		String string1= JSON.toJSONString(dto);
		System.out.println(string);
		System.out.println("-------------------------");
		System.out.println(string1);
		ResCustReportDto parse = JSON.parseObject(string, ResCustReportDto.class);
		System.out.println(parse.toString());
	}
}
