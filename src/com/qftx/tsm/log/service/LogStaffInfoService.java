package com.qftx.tsm.log.service;

import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.tsm.cust.scontrol.StaffResourceMgAction;
import com.qftx.tsm.log.bean.LogStaffInfoBean;
import com.qftx.tsm.log.dao.LogStaffInfoMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LogStaffInfoService {
	private static Logger logger = Logger.getLogger(LogStaffInfoService.class);
	@Autowired
	private LogStaffInfoMapper logStaffInfoMapper;

	public void saveLogInfo(String orgId, String userAcc, String context, String data, String reqId) {
		String log_staff = StaffResourceMgAction.LOG_STAFF;
		if ("0".equals(log_staff)) {
			return;
		}
		LogStaffInfoBean bean = new LogStaffInfoBean();
		bean.setId(SysBaseModelUtil.getModelId());
		bean.setContext(context);
		bean.setOperateAcc(userAcc);
		bean.setOrgId(orgId);
		bean.setData(data);
		bean.setReqId(reqId);
		bean.setOperateDate(new Date());
		try {
			logStaffInfoMapper.insert(bean);
		} catch (Exception e) {
			logger.error("saveLogInfo" + e.getMessage(), e);
		}

	}
}
