package com.qftx.tsm.plan.year.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.plan.year.bean.PlanLogYearBean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface PlanLogYearMapper extends BaseDao<PlanLogYearBean>{
	/* */
	public List<PlanLogYearBean> queryPlanLogYear(Map<String, String> params);
	
	public List<PlanLogYearBean> findChartData(PlanLogYearBean entity);
	
	public void insert(PlanLogYearBean planLogYearBean);
}
