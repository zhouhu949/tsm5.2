package com.qftx.tsm.log.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.log.bean.LogCustInfoBean;

import java.util.List;

public interface LogCustInfoMapper extends BaseDao<LogCustInfoBean> {
	List<LogCustInfoBean> findCustLogInfosListPage(LogCustInfoBean logCustInfoBean);
	
}
