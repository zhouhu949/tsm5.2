package com.qftx.tsm.cust.thread;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.common.filter.AppContextUtil;
import com.qftx.common.util.OperateEnum;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.cust.dto.ResCustInfoDto;
import com.qftx.tsm.cust.service.ResCustInfoService;
import com.qftx.log.tablestore.bean.LogBean;
import com.qftx.tsm.log.service.LogCustInfoService;
import com.qftx.tsm.plan.facade.PlanFactService;
import com.qftx.tsm.plan.facade.enu.PlanSignCR;
import com.qftx.tsm.report.service.SilenceCustService;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CustSilentOrWeekThread implements Runnable {
	private Log log = LogFactory.getLog(CustSilentOrWeekThread.class);
			
	private LogCustInfoService logCustInfoService = (LogCustInfoService) AppContextUtil.getBean("logCustInfoService");
	private SilenceCustService silenceCustService = (SilenceCustService) AppContextUtil.getBean("silenceCustService");
	private PlanFactService planFactService = (PlanFactService) AppContextUtil.getBean("planFactService");
	private ResCustInfoService resCustInfoService = (ResCustInfoService) AppContextUtil.getBean("resCustInfoService");
	
	private ResCustInfoDto custInfoDto; 
	private ShiroUser user;
	public CustSilentOrWeekThread(ResCustInfoDto custInfoDto, ShiroUser user) {
		this.custInfoDto = custInfoDto;
		this.user = user;
	}
	
	@Override
	public void run() {
		try {
			// 记录变更记录
			Map<String,Object> logMap = new HashMap<String, Object>();
			for (String custId : custInfoDto.getIds()) {
				logMap.put(custId, "");
				if (custInfoDto.getStatus() == 7) {
					planFactService.updateContactResult(user.getOrgId(), user.getGroupId(), user.getId(), custId, "sign", PlanSignCR.TURN_SILENCE.getResult());
				} 
			}
			
			StringBuffer context = new StringBuffer("");
			if(custInfoDto.getIds().size() > 1){
				context.append(custInfoDto.getIds().size()).append("条");
			}else{
				ResCustInfoBean bean = resCustInfoService.getCustInfoByOrgIdAndPk(user.getOrgId(), custInfoDto.getIds().get(0));
				if(user.getIsState() == 1 || StringUtils.isEmpty(bean.getCompany())){
					context.append(StringUtils.isEmpty(bean.getName()) ? "" : bean.getName());
				}else{
					context.append(StringUtils.isEmpty(bean.getCompany()) ? "" : bean.getCompany());
				}
//				if(StringUtils.isNotBlank(bean.getMobilephone())) context.append("(").append(bean.getMobilephone()).append(")");
			}
			
			if (custInfoDto.getStatus() == 7) {
				LogBean logBean = new LogBean(user.getOrgId(), user.getAccount(), user.getName(), OperateEnum.LOG_SILENT.getCode(), OperateEnum.LOG_SILENT.getDesc(), custInfoDto.getIds().size(), SysBaseModelUtil.getModelId());
				logBean.setContent(context.toString());
				logBean.setModuleId(AppConstant.Module_id1002);
				logBean.setModuleName(AppConstant.Module_Name1002);
				logBean.setOperateId(AppConstant.Operate_Name14);
				logBean.setOperateName(AppConstant.Operate_Name14);
				logCustInfoService.addTableStoreLog(logBean, logMap);
				silenceCustService.updateSilentLossReport(null, 2, custInfoDto.getIds().size(),user);
				planFactService.updateSilenceNum(user.getOrgId(), user.getGroupId(), custInfoDto.getIds().size());
			} else {
				LogBean logBean = new LogBean(user.getOrgId(), user.getAccount(), user.getName(), OperateEnum.LOG_WEEK.getCode(), OperateEnum.LOG_WEEK.getDesc(), custInfoDto.getIds().size(), SysBaseModelUtil.getModelId());
				logBean.setContent(context.toString());
				logBean.setModuleId(AppConstant.Module_id1003);
				logBean.setModuleName(AppConstant.Module_Name1003);
				logBean.setOperateId(AppConstant.Operate_Name17);
				logBean.setOperateName(AppConstant.Operate_Name17);
				logCustInfoService.addTableStoreLog(logBean, logMap);
				silenceCustService.updateSilentLossReport(null, 3, custInfoDto.getIds().size(),user);
			}
		} catch (Exception e) {
			log.error("【沉默/唤醒】线程异常",e);
		}
	}

}
