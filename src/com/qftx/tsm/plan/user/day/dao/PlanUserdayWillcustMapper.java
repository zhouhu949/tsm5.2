package com.qftx.tsm.plan.user.day.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.plan.user.day.bean.DataLableDTO;
import com.qftx.tsm.plan.user.day.bean.PlanUserDayBean;
import com.qftx.tsm.plan.user.day.bean.PlanUserdayWillcustBean;
import com.qftx.tsm.plan.user.day.dto.WillCustIndex;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PlanUserdayWillcustMapper extends BaseDao<PlanUserdayWillcustBean> {
	
	public DataLableDTO queryDataLable(Map<String, Object> params);
	
	public List<String> queryCustIdByPlanIds(Map<String, Object> params);
	
	public List<WillCustIndex> findIndexFromPlan(PlanUserDayBean plan);
	
	public List<WillCustIndex> findIndexFromRes(PlanUserDayBean plan);
	
	public List<PlanUserdayWillcustBean> findFromRes(PlanUserdayWillcustBean entity);
	
	public List<PlanUserdayWillcustBean> processList(PlanUserdayWillcustBean entity);
	
	public List<PlanUserdayWillcustBean> findFromResListPage(PlanUserdayWillcustBean entity);
	
	public void insert(PlanUserdayWillcustBean planUserdayWillcustBean);
}
