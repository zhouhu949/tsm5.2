package com.qftx.tsm.report.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.report.bean.NewWillBean;

import java.util.List;

public interface NewWillMapper extends BaseDao<NewWillBean>{
	
	public List<NewWillBean> findNewWillCountDataBydate(NewWillBean bean);
	
	public List<String> findNewWilldate(NewWillBean bean);
	
	public List<NewWillBean> findNewWilldateByGroup(NewWillBean bean);
	
	public List<NewWillBean> findNewWilldateByUser(NewWillBean bean);
	
	public List<NewWillBean> findNewWillUserByGroup(NewWillBean bean);
	
	public List<NewWillBean> findNewWillUserByGroupByDay(NewWillBean bean);
	
	public List<NewWillBean> findNewWillAllUser(String orgId);
	
	
	
	

}
