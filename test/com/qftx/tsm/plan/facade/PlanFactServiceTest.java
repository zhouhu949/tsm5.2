
package com.qftx.tsm.plan.facade;

import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.util.DateUtil;
import com.qftx.common.BaseUtest;
import com.qftx.tsm.plan.facade.enu.PlanSignCR;
import com.qftx.tsm.plan.user.day.service.TestValue;
public class PlanFactServiceTest extends BaseUtest{
	@Autowired
	PlanFactService service;
	
	Logger logger = Logger.getLogger(PlanFactServiceTest.class);
	
	@Test
	public void toSignTest(){
		String orgId="hzwcjy";
		String groupId="0021257c444f47bc82a2c439bf84103a";
		String userId="d2b1e4a7b73b4b4eae0f5c79e585040d";
		String custId="04712502ecd042dd8c8be918ab91dc8d";
		int signNum=0;
		double factMoney=10000d;
		
		service.toSign(orgId, groupId, userId, custId, signNum, factMoney);
	}
	
	@Test
	public void cancelSignTest(){
		String orgId="hyx42";
		String groupId="af1cef35be894c259658e14265862341";
		String userId="a15f3079dda24f7a97de70cb79fd2d57";
		String custId="2771a3fa1eaa41f3a4d3c7f8b623c5c9";
		int signNum=0;
		double factMoney = -6600;
		int type = 0;
		Date date = DateUtil.getData("2016-06-07 19:16:45");
		service.cancelSign(orgId, groupId, userId, custId, signNum, factMoney, date, type);
	}
	
	@Test
	public void toWillTest(){
		service.toWill(TestValue.orgId,TestValue.groupId,  TestValue.userId);
		
	}
	
	@Test
	public void updateContactResult(){
		 service.updateContactResult(TestValue.orgId,TestValue.groupId,  TestValue.userId, "40c31c9831144da5b72c9fbe6e7c34ec", "sign",  PlanSignCR.TURN_SILENCE.getResult());
		 
	}
	
	@Test
	public void updateContractMoney(){
		service.updateContractMoney(TestValue.orgId,TestValue.groupId, 0, 11.5);
		service.updateContractMoney(TestValue.orgId,TestValue.groupId, 1, 11.5);
		
	}
	
	@Test
	public void updateContractNum(){
		service.updateContractNum(TestValue.orgId,TestValue.groupId, 0, 10);
		service.updateContractNum(TestValue.orgId,TestValue.groupId, 1, 10);
		
	}
	
	@Test
	public void updateLostNum(){
		service.updateLostNum(TestValue.orgId,TestValue.groupId, 10);
		
	}
	
	@Test
	public void updateSilenceNum(){
		service.updateSilenceNum(TestValue.orgId,TestValue.groupId, 10);
		
	}
	
	@After
	public void last(){
		logger.info("last");
		TestValue.sleep(100);
	}
}
