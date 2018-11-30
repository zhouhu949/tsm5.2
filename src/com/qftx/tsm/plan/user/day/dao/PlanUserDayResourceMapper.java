package com.qftx.tsm.plan.user.day.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.plan.user.day.bean.DataLableDTO;
import com.qftx.tsm.plan.user.day.bean.PlanUserDayResourceBean;
import com.qftx.tsm.plan.user.day.bean.PlanUserdayWillcustBean;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PlanUserDayResourceMapper extends BaseDao<PlanUserDayResourceBean> {
	
	public DataLableDTO queryDataLable(Map<String, Object> params);
	
	public List<String> queryCustIdByPlanIds(Map<String, Object> params);
	
	public List<PlanUserDayResourceBean> findFromRes(PlanUserDayResourceBean entity);
	
	public List<PlanUserDayResourceBean> findFromResListPage(PlanUserDayResourceBean entity);
	
	public void updateStateToTomorrow(PlanUserDayResourceBean entity);
	
	public void updatePlanDate(PlanUserDayResourceBean entity);
	
	public void updateMoveDate(Map<String, Object> params);
	
	public List<PlanUserDayResourceBean> processList(PlanUserDayResourceBean entity);
	
	public void insert(PlanUserDayResourceBean planUserDayResourceBean);
	
}
