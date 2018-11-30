package com.qftx.tsm.log.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.log.bean.LogSysOperateBean;

import java.util.Map;

public interface LogSysOperateMapper extends BaseDao<LogSysOperateBean> {
	
	public LogSysOperateBean  findSysOper(Map<String,String> map);
	
}
