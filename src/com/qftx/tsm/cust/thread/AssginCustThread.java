package com.qftx.tsm.cust.thread;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.common.filter.AppContextUtil;
import com.qftx.common.util.OperateEnum;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.log.tablestore.bean.LogBean;
import com.qftx.tsm.cust.bean.CustOptorBean;
import com.qftx.tsm.cust.dao.CustOptorMapper;
import com.qftx.tsm.cust.dao.ResCustinfoLogMapper;
import com.qftx.tsm.cust.dao.TsmCustGuideMapper;
import com.qftx.tsm.cust.service.ResCustEventService;
import com.qftx.tsm.follow.dao.CustFollowMapper;
import com.qftx.tsm.log.service.LogBatchInfoService;
import com.qftx.tsm.log.service.LogCustInfoService;

import java.util.*;

public class AssginCustThread implements Runnable {
	private LogCustInfoService logCustInfoService = (LogCustInfoService) AppContextUtil.getBean("logCustInfoService");
	private CustOptorMapper custOptorMapper = (CustOptorMapper) AppContextUtil.getBean("custOptorMapper");
	private CustFollowMapper custFollowMapper = (CustFollowMapper) AppContextUtil.getBean("custFollowMapper");
	private ResCustinfoLogMapper resCustInfoLogMapper = (ResCustinfoLogMapper) AppContextUtil.getBean("resCustinfoLogMapper");
	private TsmCustGuideMapper custGuideMapper = (TsmCustGuideMapper)  AppContextUtil.getBean("tsmCustGuideMapper");
	private ResCustEventService resCustEventService = (ResCustEventService) AppContextUtil.getBean("resCustEventService");
	private LogBatchInfoService logBatchInfoService = (LogBatchInfoService) AppContextUtil.getBean("logBatchInfoService");
	
	private List<String> ids;
	private String transAcc;
	private String transName;
	private ShiroUser user;
	private String isClean;
	private Date upDate;
	private String module;
	private String poolType;
	public AssginCustThread(List<String> ids, String transAcc, String transName, ShiroUser user, String isClean , Date upDate,String module,String poolType){
		this.ids = ids;
		this.transAcc = transAcc;
		this.transName = transName;
		this.user = user;
		this.isClean = isClean;
		this.upDate = upDate;
		this.module = module;
		this.poolType = poolType;
	}

	@Override
	public void run() {
		List<CustOptorBean> optorBeans = new ArrayList<CustOptorBean>();
		Map<String,Object> logMap = new HashMap<String, Object>();
		for (String custId : ids) {
			CustOptorBean optorBean = new CustOptorBean();
			optorBean.setCustOptorId(SysBaseModelUtil.getModelId());
			optorBean.setCustId(custId);
			optorBean.setType((short) 13);
			optorBean.setTransferAcc(transAcc);
			optorBean.setOptorResDate(upDate);
			optorBean.setOptoerAcc(user.getAccount());
			optorBean.setOrgId(user.getOrgId());
			optorBean.setOwnerName(transName);
			optorBeans.add(optorBean);
			//重新分配日志
			logMap.put(custId, transAcc);
		}
		LogBean logBean = new LogBean(user.getOrgId(), user.getAccount(), user.getName(), OperateEnum.LOG_DIST.getCode(), OperateEnum.LOG_DIST.getDesc(), ids.size(), SysBaseModelUtil.getModelId());
		logBean.setOperateId(AppConstant.Operate_id18);
		logBean.setOperateName(AppConstant.Operate_Name18);
		if(StringUtils.isNotBlank(module)){
			if(module.equals("5")){
				logBean.setModuleId(AppConstant.Module_id101);
				logBean.setModuleName(AppConstant.Module_Name101);
				logBean.setContent(ids.size()+"条，分配对象："+transName);
			}else if(module.equals("6")){
				if(poolType.equals("0")){
					logBean.setModuleId(AppConstant.Module_id1004);
					logBean.setModuleName(AppConstant.Module_Name1004);
				}else{
					logBean.setModuleId(AppConstant.Module_id1005);
					logBean.setModuleName(AppConstant.Module_Name1005);
				}
				logBean.setContent(ids.size()+"条，分配对象："+transName);
			}
		}
		
		logCustInfoService.addTableStoreLog(logBean, logMap);
		custOptorMapper.insertBatch(optorBeans);

		if (isClean.equals("1")) {
			custFollowMapper.deleteCustFollows(user.getOrgId(), ids);
			resCustInfoLogMapper.deleteCustLogs(user.getOrgId(), ids);
			custGuideMapper.deleteCustGuids(user.getOrgId(), ids);
			resCustEventService.cleanActionRecord(user.getOrgId(), "1", ids);
		}
	}
	
}
