package com.qftx.tsm.plan.year.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.plan.year.bean.PlanSaleTrendBean;
import com.qftx.tsm.plan.year.bean.PlanSaleYearBean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface PlanSaleYearMapper extends BaseDao<PlanSaleYearBean>{
	
	public void insert(PlanSaleYearBean planSaleYearBean);
	
	public List<PlanSaleYearBean> findSumByCondtion(PlanSaleYearBean entity);
	
	public PlanSaleYearBean getSumByCondtion(PlanSaleYearBean entity);
	
	public void updateFactNum(PlanSaleYearBean entity);
	
	public List<PlanSaleTrendBean> queryPlanSaleTrend(Map<String, String> params);
	
	public PlanSaleTrendBean getPlanYearProgress(Map<String, Object> params);
	
	public double queryPlanMoneyByYear(Map<String, String> params);
	
}
