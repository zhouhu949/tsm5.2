package com.qftx.tsm.sys.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.sys.bean.Points;

import java.util.List;


public interface PointsMapper extends BaseDao<Points> {
	
	/** 定义 初始化数据 */
	List<Points>getOldPointsList();
	
	public void deleteByOrgId(String orgId);
}
