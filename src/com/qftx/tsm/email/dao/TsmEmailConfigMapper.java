package com.qftx.tsm.email.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.email.bean.TsmEmailConfig;

import java.util.Map;


public interface TsmEmailConfigMapper extends BaseDao<TsmEmailConfig> {
	public void deleteBatchBy(Map<String,Object>map);
}