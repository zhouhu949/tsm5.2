package com.qftx.tsm.plan.facade;

import com.qftx.common.filter.AppContextUtil;
import com.qftx.tsm.plan.group.month.bean.PlanGroupmonthAnalyBean;
import com.qftx.tsm.plan.group.month.service.PlanGroupmonthAnalyService;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.List;

public class UpdateContractNumThread implements Runnable{
	private String orgId;
	private String groupId;
	private int type;
	private int contractNum;
	private Date date;
	
	private Logger logger = Logger.getLogger(UpdateContractNumThread.class);
	
	private PlanGroupmonthAnalyService analyService = (PlanGroupmonthAnalyService) AppContextUtil.getBean("planGroupmonthAnalyService");
	
	@Override
	public void run() {
		try {
			if(orgId==null ||groupId==null ){
				logger.error("param is null:"+orgId==null?"orgId":groupId==null?"groupId":"");
			}else{
				updateContractNum();
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		
	}
	public UpdateContractNumThread(String orgId, String groupId, int type,
			int contractNum,Date date) {
		super();
		this.orgId = orgId;
		this.groupId = groupId;
		this.type = type;
		this.contractNum = contractNum;
		this.date = date;
	}
	
	/*更新签约合同客户数*/
	public void updateContractNum(){
		List<PlanGroupmonthAnalyBean> analys = analyService.findFatherByDate(orgId, groupId, date);
		for (PlanGroupmonthAnalyBean analy : analys) {
			if(analy!=null && analy.getId()!=null) analyService.updateContractNum(orgId, analy.getId(), type, contractNum);
		}
	}
}
