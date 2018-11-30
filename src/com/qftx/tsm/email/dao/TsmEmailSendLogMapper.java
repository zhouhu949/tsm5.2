package com.qftx.tsm.email.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.email.bean.TsmEmailSendLog;

import java.util.Map;

public interface TsmEmailSendLogMapper extends BaseDao<TsmEmailSendLog> {
	public void deleteBatchBy(Map<String,Object>map);
}