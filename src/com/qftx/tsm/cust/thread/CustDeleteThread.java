package com.qftx.tsm.cust.thread;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.common.filter.AppContextUtil;
import com.qftx.common.util.OperateEnum;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.tsm.cust.dto.ResCustInfoDto;
import com.qftx.tsm.log.bean.LogCustInfoBean;
import com.qftx.tsm.log.dao.LogCustInfoMapper;
import com.qftx.tsm.log.service.LogBatchInfoService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustDeleteThread implements Runnable {
	private LogCustInfoMapper logCustInfoMapper = (LogCustInfoMapper) AppContextUtil.getBean("logCustInfoMapper");
	private LogBatchInfoService logBatchInfoService = (LogBatchInfoService) AppContextUtil.getBean("logBatchInfoService");
	
	private ResCustInfoDto custInfoDto;
	public CustDeleteThread(ResCustInfoDto custInfoDto) {
		// TODO Auto-generated constructor stub
		this.custInfoDto = custInfoDto;
	}
	
	@Override
	public void run() {
		ShiroUser user = ShiroUtil.getShiroUser();
		List<LogCustInfoBean> logs = new ArrayList<LogCustInfoBean>();
		// 记录变更记录
		for (String custId : custInfoDto.getIds()) {
//			ResCustInfoBean custInfoBean = resCustInfoMapper.getByPrimaryKey(custInfoDto.getOrgId(), custId);
			LogCustInfoBean log = new LogCustInfoBean();
			log.setId(SysBaseModelUtil.getModelId());
			log.setOrgId(custInfoDto.getOrgId());
			log.setCustId(custId);
			log.setUserId(custInfoDto.getUpdateAcc());
			log.setName(OperateEnum.LOG_DELETE.getDesc());
			log.setType(OperateEnum.LOG_DELETE.getCode());
//			log.setData(JSONObject.fromObject(custInfoBean).toString());
			log.setInputtime(new Date());
			log.setIsDel(0);
			logs.add(log);
		}
		logCustInfoMapper.insertBatch(logs);
		//logBatchInfoService.saveLogInfo(custInfoDto.getOrgId(), custInfoDto.getUpdateAcc(),user.getName(),OperateEnum.LOG_DELETE, custInfoDto.getIds(),custInfoDto.getOwnerAcc());
	}

}
