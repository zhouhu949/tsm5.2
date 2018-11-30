package com.qftx.tsm.plan.facade;

import com.qftx.common.filter.AppContextUtil;
import com.qftx.tsm.plan.group.month.bean.PlanGroupmonthAnalyBean;
import com.qftx.tsm.plan.group.month.service.PlanGroupmonthAnalyService;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.List;

public class UpdateSilenceNumThread implements Runnable{
	private String orgId;
	private String groupId;
	private int silenceNum;
	
	private Logger logger = Logger.getLogger(UpdateSilenceNumThread.class);
	
	private PlanGroupmonthAnalyService analyService = (PlanGroupmonthAnalyService) AppContextUtil.getBean("planGroupmonthAnalyService");
	
	@Override
	public void run() {
		
		try {
			if(orgId==null ||groupId==null ){
				logger.error("param is null:"+orgId==null?"orgId":groupId==null?"groupId":"");
			}else{
				updateSilenceNum();
			}
		} catch (Exception e) {
			logger.error("",e);
		}
		
	}
	
	public UpdateSilenceNumThread(String orgId,String groupId,int silenceNum) {
		super();
		this.orgId = orgId;
		this.groupId = groupId;
		this.silenceNum = silenceNum;
	}
	
	/*更新沉默客户数*/
	public void updateSilenceNum(){
		Date date = new Date();
		
		List<PlanGroupmonthAnalyBean> analys = analyService.findFatherByDate(orgId, groupId, date);
		for (PlanGroupmonthAnalyBean analy : analys) {
			if(analy!=null && analy.getId()!=null) analyService.updateSilenceNum(orgId, analy.getId(), silenceNum);
		}
	}
 
}
