package com.qftx.tsm.email.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.email.bean.TsmEmailTemp;

import java.util.Map;

public interface TsmEmailTempMapper extends BaseDao<TsmEmailTemp> {
	public void deleteBatchBy(Map<String,Object>map);
}