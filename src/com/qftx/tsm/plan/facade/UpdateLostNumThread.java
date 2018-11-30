package com.qftx.tsm.plan.facade;

import com.qftx.common.filter.AppContextUtil;
import com.qftx.tsm.plan.group.month.bean.PlanGroupmonthAnalyBean;
import com.qftx.tsm.plan.group.month.service.PlanGroupmonthAnalyService;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.List;

public class UpdateLostNumThread implements Runnable{
	private String orgId;
	private String groupId;
	private int lostNum;
	
	private Logger logger = Logger.getLogger(UpdateLostNumThread.class);
	
	private PlanGroupmonthAnalyService analyService = (PlanGroupmonthAnalyService) AppContextUtil.getBean("planGroupmonthAnalyService");
	@Override
	public void run() {
		try {
			if(orgId==null ||groupId==null ){
				logger.error("param is null:"+orgId==null?"orgId":groupId==null?"groupId":"");
			}else{
				updateLostNum();
			}
		} catch (Exception e) {
			logger.error("",e);
		}
		
	}
	public UpdateLostNumThread(String orgId,String groupId,int lostNum) {
		super();
		this.orgId = orgId;
		this.groupId = groupId;
		this.lostNum = lostNum;
	}
	
	/*更新流失客户数*/
	public void updateLostNum(){
		Date date = new Date();
		
		List<PlanGroupmonthAnalyBean> analys = analyService.findFatherByDate(orgId, groupId, date);
		for (PlanGroupmonthAnalyBean analy : analys) {
			if(analy!=null && analy.getId()!=null) analyService.updateLostNum(orgId, analy.getId(), lostNum);
		}
	}
 
}
