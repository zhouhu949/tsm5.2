package com.qftx.tsm.resource.service;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.common.BaseUtest;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.tsm.cust.bean.ResDetailCallBean;
import com.qftx.tsm.cust.service.ResDetailCallService;

public class ResDetailCallTest extends BaseUtest {

	@Autowired
	private ResDetailCallService resDetailCallService;
	
	//@Test
	public void insert(){
		ResDetailCallBean resDetailCallBean = new ResDetailCallBean();
		resDetailCallBean.setId("2222222");
		resDetailCallBean.setCallNum(1);
		resDetailCallBean.setInputName("test");
		resDetailCallBean.setInputTime(new Date());
		resDetailCallBean.setUpdateName("test");
		resDetailCallBean.setUpdateTime(new Date());
		resDetailCallBean.setOrgId("ay");
		resDetailCallBean.setIsDel(0);
		resDetailCallBean.setResDetailId("1111111");
		resDetailCallBean.setResId(SysBaseModelUtil.getModelId());
		try {
			
			resDetailCallService.save(resDetailCallBean);;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	public void getBean(){
		try {
			
			resDetailCallService.getByPrimaryKeyAndOrgId("ay", "2222222");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}	
	
	//@Test
	public void update(){
		ResDetailCallBean resDetailCallBean = new ResDetailCallBean();
		resDetailCallBean.setId("2222222");
		resDetailCallBean.setCallNum(1);
		resDetailCallBean.setInputName("test2");
		resDetailCallBean.setInputTime(new Date());
		resDetailCallBean.setUpdateName("test2");
		resDetailCallBean.setUpdateTime(new Date());
		resDetailCallBean.setOrgId("ay");
		resDetailCallBean.setResDetailId("1111111");
		resDetailCallBean.setResId(SysBaseModelUtil.getModelId());
		try {
			resDetailCallService.modify(resDetailCallBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	
	@Test
	public void delete(){
		try {
			resDetailCallService.remove("ay", "2222222");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
		
}
