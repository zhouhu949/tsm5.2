package com.qftx.tsm.plan.group.month.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.plan.group.month.bean.PlanGroupmonthBean;
import com.qftx.tsm.plan.group.month.dto.PlanGroupmonthReportDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PlanGroupmonthMapper extends BaseDao<PlanGroupmonthBean> {
	
	public PlanGroupmonthBean querySumByGroups(PlanGroupmonthBean entity);
	
	//public List<PlanGroupmonthBean> findSumOrderByGroup(PlanGroupmonthBean bean);
	
	public List<PlanGroupmonthBean> queryHistory(Map<String, Object> params);
	
	public List<PlanGroupmonthBean> queryHistoryByGroupIds(Map<String, Object> params);
	
	public List<PlanGroupmonthBean> findNoAuthPlan(PlanGroupmonthBean bean);
	
	public void updatePlanAddNum(PlanGroupmonthBean bean);
		
	public void updatePlanNum(PlanGroupmonthBean bean);
	
	public void updateFactNum(PlanGroupmonthBean bean);
	
	public List<PlanGroupmonthReportDto> queryShareTeamMonthPlan(Map<String, Object> map);
	
	public List<PlanGroupmonthBean> queryTeamMonthPlanLineReport(Map<String, String> map);
	
	public List<String> findNoReportGroups(PlanGroupmonthBean entity);
}
