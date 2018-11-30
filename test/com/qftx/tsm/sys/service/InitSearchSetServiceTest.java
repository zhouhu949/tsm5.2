package com.qftx.tsm.sys.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.tsm.plan.user.day.service.TestValue;
import com.qftx.tsm.sys.bean.CustSearchSet;
import com.qftx.tsm.sys.bean.SysFileBean;
import com.qftx.tsm.sys.dto.HighSearchChildDto;
import com.qftx.tsm.sys.dto.HighSearchDto;

public class InitSearchSetServiceTest extends BaseUtest{
	@Autowired 
	private HighSearchService highSearchService;
	
	@Test
	public void test(){
		try {
			//highSearchService.executeOption("getStr","fhtx1","");
			//highSearchService.executeOption("getStr","fhtx1","123");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		List<HighSearchDto>list = new ArrayList<HighSearchDto>();
		HighSearchDto dto = new HighSearchDto();
		dto.setSearchCode("custTypeId");
		dto.setSearchName("�ͻ�����");
		dto.setDataType(3);
		dto.setChildList(getCustTypeChildList());
		dto.setSort((short)1);
		list.add(dto);
		dto = new HighSearchDto();
		dto.setSearchCode("optionId");
		dto.setSearchName("���۽���");
		dto.setDataType(3);
		dto.setChildList(getOptionChildList());
		dto.setSort((short)2);
		list.add(dto);
		dto = new HighSearchDto();
		dto.setSearchCode("nextStartTime");
		dto.setSearchName("�´���ϵʱ��");
		dto.setDataType(2);
		dto.setSort((short)3);
		dto.setChildList(getTimeChildList());
		list.add(dto);
		dto = new HighSearchDto();
		dto.setSearchCode("ownerAcc");
		dto.setSearchName("������");
		dto.setDataType(5);
		dto.setSort((short)4);
		dto.setParamValue("/orgGroup/get_group_user_json");
		list.add(dto);
		System.out.println(JsonUtil.getJsonString(list));
	}
	
	private static List<HighSearchChildDto>getCustTypeChildList(){
		List<HighSearchChildDto>list = new ArrayList<HighSearchChildDto>();
		HighSearchChildDto dto = new HighSearchChildDto();
		dto.setName("Ǳ�ڿͻ�");
		dto.setValue("111");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("vip�ͻ�");
		dto.setValue("222");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("�Ҵ�ͻ�");
		dto.setValue("333");
		dto.setIsCheck(0);
		list.add(dto);
		return list;
	}
	
	private static List<HighSearchChildDto>getTimeChildList(){
		List<HighSearchChildDto>list = new ArrayList<HighSearchChildDto>();
		HighSearchChildDto dto = new HighSearchChildDto();
		dto.setName("��ʼʱ��");
		dto.setValue("2017-06-14");
		dto.setOrder(0);
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("����ʱ��");
		dto.setValue("2017-06-15");
		dto.setOrder(1);
		dto.setIsCheck(0);
		list.add(dto);
		return list;
	}
	
	private static List<HighSearchChildDto>getOptionChildList(){
		List<HighSearchChildDto>list = new ArrayList<HighSearchChildDto>();
		HighSearchChildDto dto = new HighSearchChildDto();
		dto.setName("A��һ����ϵ");
		dto.setValue("111");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("B�ڶ�����ϵ");
		dto.setValue("222");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("CǩԼ����");
		dto.setValue("333");
		dto.setIsCheck(0);
		list.add(dto);
		return list;
	}
}