package com.qftx.tsm.log.service;

import com.qftx.tsm.log.bean.LogSysOperateBean;
import com.qftx.tsm.log.dao.LogSysOperateMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LogSysOperateService {
    protected static Log logger = LogFactory.getLog(LogSysOperateService.class);
    @Autowired
    private LogSysOperateMapper logSysOperateMapper;
 
	public void saveLogInfo(LogSysOperateBean bean){
		logSysOperateMapper.insert(bean);
	}
	
	public void saveBatchLogInfo(List<LogSysOperateBean> beans){
		logSysOperateMapper.insertBatch(beans);
	}
	
	public LogSysOperateBean  findSysOper(Map<String,String> map){
		return logSysOperateMapper.findSysOper(map);
	}
}
