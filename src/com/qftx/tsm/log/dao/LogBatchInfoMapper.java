package com.qftx.tsm.log.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.cust.dto.LogBatchInfoDto;
import com.qftx.tsm.log.bean.LogBatchInfoBean;

import java.util.List;

public interface LogBatchInfoMapper extends BaseDao<LogBatchInfoBean> {

	List<LogBatchInfoDto> findLogResOperateListPage(LogBatchInfoDto logBatchInfoDto);
	
}
