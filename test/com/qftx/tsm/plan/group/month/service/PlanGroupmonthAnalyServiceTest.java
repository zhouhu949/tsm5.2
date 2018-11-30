package com.qftx.tsm.plan.group.month.service;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.util.DateUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.tsm.plan.group.month.bean.PlanGroupmonthAnalyBean;
import com.qftx.tsm.plan.group.month.dao.PlanGroupmonthAnalyMapper;
import com.qftx.tsm.plan.group.month.dto.AnalyDTO1;
import com.qftx.tsm.plan.user.day.service.TestValue;

public class PlanGroupmonthAnalyServiceTest extends BaseUtest {
	@Autowired
	private PlanGroupmonthAnalyService service;
	@Autowired
	private PlanGroupmonthAnalyMapper planGroupmonthAnalyMapper;
	
	Logger logger = Logger.getLogger(PlanGroupmonthAnalyServiceTest.class);
	@Test
	public void removeErroData(){
		List<PlanGroupmonthAnalyBean> list = service.findErroData();
		for (PlanGroupmonthAnalyBean planGroupmonthAnalyBean : list) {
			//2016/5/1 0:00:00
			List<String> ids = planGroupmonthAnalyMapper.getErroId(planGroupmonthAnalyBean);
			if(ids!=null && ids.size()>1){
				for(int i=1;i<ids.size();i++){
					String id=ids.get(i);
					
					PlanGroupmonthAnalyBean remove = new PlanGroupmonthAnalyBean();
					remove.setOrgId(planGroupmonthAnalyBean.getOrgId());
					remove.setId(id);
					planGroupmonthAnalyMapper.removeErroData(remove);
				}
			}
		}
	}
	
	@Test
	public void getAnalyDTOTest(){
		AnalyDTO1 dto = service.getAnalyDTO(TestValue.orgId,TestValue.groupId, 2016, 2);
		System.out.println(dto);
	}
	
	@Test
	public void getByDate(){
		PlanGroupmonthAnalyBean dto = service.getByDate("zthyx", "518fdc2d91134c98bb6a67b0dd4960fb", new Date());
		System.out.println(dto);
	}
	
	@Test
	public void findFatherByDateTest(){
		String orgId="yuanxw";
		String groupId="164b1061282c4f45aacf0dbdd71c618d";
		Date date = new Date();
		List<PlanGroupmonthAnalyBean> list = service.findFatherByDate(orgId, groupId, date);
		
		logger.info(JsonUtil.getJsonString(list));
	}
	
	@Test
	public void findChildAnalyByDateTest(){
		List<PlanGroupmonthAnalyBean> list = service.findChildAnalyByDate("zthyx", "1eeb35bfde994559af1f2e4658f32792", new Date());
		System.out.println(list);
	}
	 
	@Test
	public void insertTest() {
		Date date = new Date();
		for(int i=0;i<10;i++){
			service.insert(TestValue.orgId,TestValue.groupId,DateUtil.addDate(date, 0, i-5, 0));
		}
	}
	
	@Test
	public void updateTest(){
		String orgId = TestValue.orgId;
		String id = "";
		int type = 1 ;
		
		PlanGroupmonthAnalyBean analy = service.getByDate(orgId, TestValue.groupId, DateUtil.monthBegin(new Date()));
		
		if(analy !=null){
			id= analy.getId();
			
			service.updateLostNum(TestValue.orgId, id, 1);
			service.updateSilenceNum(orgId, id, 1);
		}
		
		
	}
}
