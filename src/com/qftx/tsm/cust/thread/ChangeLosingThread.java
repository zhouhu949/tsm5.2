package com.qftx.tsm.cust.thread;


import com.qftx.base.shiro.ShiroUser;
import com.qftx.common.cached.CachedService;
import com.qftx.common.filter.AppContextUtil;
import com.qftx.common.util.OperateEnum;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.cust.dto.ResCustInfoDto;
import com.qftx.tsm.cust.service.ResCustInfoService;
import com.qftx.log.tablestore.bean.LogBean;
import com.qftx.tsm.log.service.LogBatchInfoService;
import com.qftx.tsm.log.service.LogCustInfoService;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.plan.facade.PlanFactService;
import com.qftx.tsm.report.service.SilenceCustService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ChangeLosingThread implements Runnable {
	private Log log = LogFactory.getLog(ChangeLosingThread.class);
	private LogCustInfoService logCustInfoService = (LogCustInfoService) AppContextUtil.getBean("logCustInfoService");
	private SilenceCustService silenceCustService = (SilenceCustService) AppContextUtil.getBean("silenceCustService");
	private PlanFactService planFactService = (PlanFactService) AppContextUtil.getBean("planFactService");
	private ResCustInfoService resCustInfoService = (ResCustInfoService) AppContextUtil.getBean("resCustInfoService");
	private CachedService cachedService = (CachedService) AppContextUtil.getBean("cachedService");
	
	private ResCustInfoDto custInfoDto;
	private ShiroUser user;
	private String module;
	public ChangeLosingThread(ResCustInfoDto custInfoDto, ShiroUser user,String module) {
		// TODO Auto-generated constructor stub
		this.custInfoDto = custInfoDto;
		this.user = user;
		this.module = module;
	}
	
	@Override
	public void run() {
		try {
			Map<String, Object> logMap = new HashMap<String, Object>();
			for (String custId : custInfoDto.getIds()) {
				logMap.put(custId, "");
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
			List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_WAST, user.getOrgId());
			Map<String, String> dataMap = cachedService.changeOptionListToMap(options);
			context.append("，流失原因：").append(dataMap.get(custInfoDto.getLosingId()));
			LogBean logBean = new LogBean(custInfoDto.getOrgId(), user.getAccount(), user.getName(), OperateEnum.LOG_LOSING.getCode(), OperateEnum.LOG_LOSING.getDesc(), custInfoDto.getIds().size(), SysBaseModelUtil.getModelId());
			logBean.setContent(context.toString());
			if(module.equals("3")){
				logBean.setModuleId(AppConstant.Module_id1002);
				logBean.setModuleName(AppConstant.Module_Name1002);
				logBean.setOperateId(AppConstant.Operate_id13);
				logBean.setOperateName(AppConstant.Operate_Name13);
			}else if(module.equals("4")){
				logBean.setModuleId(AppConstant.Module_id1003);
				logBean.setModuleName(AppConstant.Module_Name1003);
				logBean.setOperateId(AppConstant.Operate_id13);
				logBean.setOperateName(AppConstant.Operate_Name13);
			}
			logCustInfoService.addTableStoreLog(logBean, logMap);
			silenceCustService.updateSilentLossReport(null, 5, custInfoDto.getIds().size(),user);
			planFactService.updateLostNum(custInfoDto.getOrgId(), user.getGroupId(), custInfoDto.getIds().size());
		} catch (Exception e) {
			log.error("转流失异常！",e);
		}
	}

}
