package com.qftx.tsm.cust.thread;

import com.alibaba.druid.util.StringUtils;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.common.filter.AppContextUtil;
import com.qftx.common.util.OperateEnum;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.cust.dao.ResCustInfoDetailMapper;
import com.qftx.tsm.cust.dto.ResCustInfoDto;
import com.qftx.tsm.cust.service.ResCustInfoService;
import com.qftx.tsm.follow.dao.CustSaleChanceMapper;
import com.qftx.log.tablestore.bean.LogBean;
import com.qftx.tsm.log.service.LogBatchInfoService;
import com.qftx.tsm.log.service.LogContactDayDataService;
import com.qftx.tsm.log.service.LogCustInfoService;
import com.qftx.tsm.log.util.ContactUtil;
import com.qftx.tsm.plan.facade.PlanFactService;
import com.qftx.tsm.plan.facade.enu.PlanResCR;
import com.qftx.tsm.plan.facade.enu.PlanWillCR;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustGiveUpThread implements Runnable {
	
	private Log log = LogFactory.getLog(CustGiveUpThread.class);
	private PlanFactService planFactService = (PlanFactService)AppContextUtil.getBean("planFactService");
	private LogCustInfoService logCustInfoService = (LogCustInfoService)AppContextUtil.getBean("logCustInfoService");
	private ResCustInfoService resCustInfoService = (ResCustInfoService)AppContextUtil.getBean("resCustInfoService");
	private LogBatchInfoService logBatchInfoService = (LogBatchInfoService) AppContextUtil.getBean("logBatchInfoService");
	private ResCustInfoDetailMapper resCustInfoDetailMapper = (ResCustInfoDetailMapper) AppContextUtil.getBean("resCustInfoDetailMapper");
	private LogContactDayDataService logContactDayDataService = (LogContactDayDataService)AppContextUtil.getBean("logContactDayDataService"); 
	private CustSaleChanceMapper custSaleChanceMapper = (CustSaleChanceMapper)AppContextUtil.getBean("custSaleChanceMapper");
	private List<String> resCustIds;
	private ShiroUser user;
	private String reason;
	private short operateType;
	private String module;
	public CustGiveUpThread(List<String> resCustIds,ShiroUser user,String reason,short operateType,String module) {
		// TODO Auto-generated constructor stub
		this.resCustIds = resCustIds;
		this.user = user;
		this.reason = reason;
		this.operateType = operateType;
		this.module = module;
	}
	
	public void run() {
		try {
			// TODO Auto-generated method stub
			Map<String, Object> logMap = new HashMap<String, Object>();
			List<String> custIds = new ArrayList<String>();
			StringBuffer context = new StringBuffer("");
			if(resCustIds.size() > 1){
				context.append(resCustIds.size()+"条");
			}
			for (String custIdStr : resCustIds) {
				String resCustId = custIdStr.split("_")[0];
				String custStatus = custIdStr.split("_")[1];
				custIds.add(resCustId);
				ResCustInfoDto dto = new ResCustInfoDto();
				dto.setItemCode(AppConstant.SALES_TYPE_ONE);
				dto.setResCustId(resCustId);
				dto.setOrgId(user.getOrgId());
				ResCustInfoDto custDto = resCustInfoService.getTeamCustByCustId(dto);
				if (custDto != null) {
					if(resCustIds.size() == 1 && !StringUtils.isEmpty(module)){
						if(user.getIsState() == 1 || StringUtils.isEmpty(custDto.getCompany())){
							context.append(StringUtils.isEmpty(custDto.getName()) ? "" : custDto.getName());
						}else{
							context.append(StringUtils.isEmpty(custDto.getCompany()) ? "" : custDto.getCompany());
						}
					}
					if (custStatus.equals("1")) {
						// resNum += 1;
						logContactDayDataService.addLogContactDayData(ContactUtil.RES_TO_SEA, user.getOrgId(), resCustId, custDto.getOwnerAcc(), null, null);
						planFactService.updateContactResult(user.getOrgId(), user.getGroupId(), user.getId(), resCustId, "res",
								PlanResCR.TURN_HIGH_SEA.getResult());
					} else if(custStatus.equals("2")) {
						// intNum += 1;
						logContactDayDataService.addLogContactDayData(ContactUtil.CUST_TO_SEA, user.getOrgId(), resCustId, custDto.getOwnerAcc(), custDto.getSaleProcessId(), null);
						planFactService.updateContactResult(user.getOrgId(), user.getGroupId(), user.getId(), resCustId, "will",
								PlanWillCR.TURN_HIGH_SEA.getResult());
					}else if(operateType == 21){
						//流失
						reason = "流失-"+custDto.getLosingId();
						ResCustInfoBean bean = new ResCustInfoBean();
						bean.setOrgId(user.getOrgId());
						bean.setResCustId(resCustId);
						bean.setRecuclingTypeDetails(reason);
						bean.setState(user.getIsState());
						bean.setLastOptionId(custDto.getLastOptionId());
						resCustInfoService.modify(bean);
					}
					//清空拨通次数
					if(custDto.getState() != null && custDto.getState() == 1){
						resCustInfoDetailMapper.cleanCallNum(user.getOrgId(), resCustId);
					}
				}
				// 插入日志
				logMap.put(resCustId, StringUtils.isEmpty(reason) ? "" : "放入公海原因："+reason);
			}
			if (resCustIds.size() > 0) {
				custSaleChanceMapper.updateIsDelByCustIds(user.getOrgId(), custIds);
				LogBean logBean = new LogBean(user.getOrgId(), user.getAccount(), user.getName(), OperateEnum.LOG_GIVE.getCode(), OperateEnum.LOG_GIVE.getDesc(), resCustIds.size(), SysBaseModelUtil.getModelId());
				logBean.setContent(context.toString());
				logBean.setOperateId(AppConstant.Operate_id7);
				logBean.setOperateName(AppConstant.Operate_Name7);
				if(!StringUtils.isEmpty(module)){
					if(module.equals("1")){
						logBean.setModuleId(AppConstant.Module_id1001);
						logBean.setModuleName(AppConstant.Module_Name1001);
					}else if(module.equals("2")){
						logBean.setModuleId(AppConstant.Module_id1000);
						logBean.setModuleName(AppConstant.Module_Name1000);
					}else if(module.equals("5")){
						logBean.setModuleId(AppConstant.Module_id101);
						logBean.setModuleName(AppConstant.Module_Name101);
					}
				}else{
					logBean.setModuleId(AppConstant.Module_id9);
					logBean.setModuleName(AppConstant.Module_Name9);
				}
				logCustInfoService.addTableStoreLog(logBean, logMap);
			}
		} catch (Exception e) {
			log.error("放弃客户线程异常，msg="+e.getMessage(),e);
		}
	}

}
