package com.qftx.tsm.plan.user.day.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.plan.user.day.bean.DataLableDTO;
import com.qftx.tsm.plan.user.day.bean.PlanUserdaySigncustBean;
import com.qftx.tsm.plan.user.day.bean.PlanUserdaySigncustBean;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PlanUserdaySigncustMapper extends BaseDao<PlanUserdaySigncustBean> {

	public DataLableDTO queryDataLable(Map<String, Object> params);
	
	public List<String> queryCustIdByPlanIds(Map<String, Object> params);
	
	public List<PlanUserdaySigncustBean> findFromRes(PlanUserdaySigncustBean entity);
	
	public List<PlanUserdaySigncustBean> findFromResListPage(PlanUserdaySigncustBean entity);
	
	public List<PlanUserdaySigncustBean> processList(PlanUserdaySigncustBean entity);
	
	public void insert(PlanUserdaySigncustBean planUserdaySigncustBean);
	
	public List<String> findResId(PlanUserdaySigncustBean entity);
}
