package com.qftx.tsm.plan.user.month.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.plan.user.month.bean.PlanUserMonthNumSort;
import com.qftx.tsm.plan.user.month.bean.PlanUsermonthBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PlanUsermonthMapper extends BaseDao<PlanUsermonthBean> {
	
	public List<PlanUsermonthBean> queryHistory(Map<String , Object> params);
	
	public List<PlanUserMonthNumSort> queryNumSort(Map<String , String> params);
	
	public PlanUsermonthBean querySumByGroup(PlanUsermonthBean bean);
	
	public List<PlanUsermonthBean> findNoAuthPlan(PlanUsermonthBean bean);
	
	public void updatePlanAddNum(PlanUsermonthBean bean);
	
	public void updatePlanNumByDb(PlanUsermonthBean bean);
	
	public void updatePlanNum(PlanUsermonthBean bean);
	
	public void updateFactNum(PlanUsermonthBean bean);
	
	public void updateFactAddNum(PlanUsermonthBean bean);
	
	public List<String> findNoReportUsers(PlanUsermonthBean bean);
	
	public void receivePointsByType(@Param("id")String id,@Param("orgId")String orgId,@Param("ptype")String type,@Param("allState")Integer allState);
}
