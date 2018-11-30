package com.qftx.tsm.cust.thread;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.druid.util.StringUtils;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.common.filter.AppContextUtil;
import com.qftx.common.util.OperateEnum;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.log.tablestore.bean.LogBean;
import com.qftx.tsm.log.service.LogCustInfoService;

public class ScreenCustGiveUpThread implements Runnable{
	private Log log = LogFactory.getLog(ScreenCustGiveUpThread.class);
	private LogCustInfoService logCustInfoService = (LogCustInfoService)AppContextUtil.getBean("logCustInfoService");
	
	private List<String> resCustIds;
	private String account;
	private String orgId;
	private String reason;
	private short operateType;
	private String module;

	

	public ScreenCustGiveUpThread(List<String> resCustIds, String account,
			String orgId, String reason, short operateType, String module) {
		super();
		this.resCustIds = resCustIds;
		this.account = account;
		this.orgId = orgId;
		this.reason = reason;
		this.operateType = operateType;
		this.module = module;
	}



	@Override
	public void run() {
		// TODO Auto-generated method stub
		Map<String, Object> logMap = new HashMap<String, Object>();
		try {
			for (String id : resCustIds ) {
				logMap.put(id, StringUtils.isEmpty(reason) ? "" : "说明："+reason);
			}
			if (resCustIds.size() > 0) {
				LogBean logBean = new LogBean(orgId, account, "系统", OperateEnum.LOG_SCREEN_TO_SEA.getCode(), OperateEnum.LOG_SCREEN_TO_SEA.getDesc(), resCustIds.size(), SysBaseModelUtil.getModelId());
				logBean.setOperateId(AppConstant.Operate_id7);
				logBean.setOperateName(AppConstant.Operate_Name7);
				logBean.setModuleId(AppConstant.Module_id12);
				logBean.setModuleName(AppConstant.Module_Name12);
				logBean.setContent(resCustIds.size()+"条,号码筛查自动转公海");
				logCustInfoService.addTableStoreLog(logBean, logMap);
			}
		} catch (Exception e) {
			log.error("号码筛查自动转公海线程异常，msg="+e.getMessage(),e);
		}
		
	}

}
