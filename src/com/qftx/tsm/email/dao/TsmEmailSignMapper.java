package com.qftx.tsm.email.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.email.bean.TsmEmailSign;

import java.util.Map;



public interface TsmEmailSignMapper extends BaseDao<TsmEmailSign> {
	public void deleteBatchBy(Map<String,Object>map);
	
	public void deleteBatchById(Map<String,Object>map);
}