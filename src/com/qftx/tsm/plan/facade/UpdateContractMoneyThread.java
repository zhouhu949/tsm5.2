package com.qftx.tsm.plan.facade;

import com.qftx.common.filter.AppContextUtil;
import com.qftx.tsm.plan.group.month.bean.PlanGroupmonthAnalyBean;
import com.qftx.tsm.plan.group.month.service.PlanGroupmonthAnalyService;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class UpdateContractMoneyThread implements Runnable{
	private String orgId;
	private String groupId;
	private int type;
	private double contractMoney;
	private Date date;
	
	private Logger logger = Logger.getLogger(UpdateContractMoneyThread.class);
	
	private PlanGroupmonthAnalyService analyService = (PlanGroupmonthAnalyService) AppContextUtil.getBean("planGroupmonthAnalyService");
	
	@Override
	public void run() {
		try {
			if(orgId==null ||groupId==null ){
				logger.error("param is null:"+orgId==null?"orgId":groupId==null?"groupId":"");
			}else{
				updateContractMoney();
			}
		} catch (Exception e) {
			logger.error("",e);
		}
		
	}
	public UpdateContractMoneyThread(String orgId, String groupId, int type,
			double contractMoney,Date date) {
		super();
		this.orgId = orgId;
		this.groupId = groupId;
		this.type = type;
		this.contractMoney = contractMoney;
		this.date = date;
	}
	
	/*更新签约金额数*/
	public void updateContractMoney(){
		if(contractMoney!=0){
			BigDecimal bigDecimal =new BigDecimal(contractMoney/10000);  
			contractMoney = bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();  
		}
		
		List<PlanGroupmonthAnalyBean> analys = analyService.findFatherByDate(orgId, groupId, date);
		for (PlanGroupmonthAnalyBean analy : analys) {
			if(analy!=null && analy.getId()!=null) analyService.updateContractMoney(orgId, analy.getId(), type, contractMoney);
		}
	}
}
