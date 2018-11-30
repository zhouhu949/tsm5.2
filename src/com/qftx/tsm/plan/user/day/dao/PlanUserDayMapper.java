package com.qftx.tsm.plan.user.day.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.plan.user.day.bean.PlanUserDayBean;
import com.qftx.tsm.plan.user.day.dto.TeamDayPlanReportDto;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PlanUserDayMapper extends BaseDao<PlanUserDayBean> {
	/* 根据用户id日期查询日计划 唯一*/
	public PlanUserDayBean queryByUserAndDate(Map<String, Object> params);
	/* 根据用户id日期查询日计划 唯一*/
	public PlanUserDayBean findById(PlanUserDayBean plan);
	
	public List<PlanUserDayBean> findFromPlan(PlanUserDayBean plan);
	
	public List<PlanUserDayBean> findFromRes(PlanUserDayBean plan);
	/* 查询计划中的资源客户ID*/
	public List<String> findCustIdFromRes(PlanUserDayBean plan);
	/* 查询计划中的资源客户ID*/
	public List<String> findCustIdFromPlan(PlanUserDayBean plan);
	
	public List<PlanUserDayBean> getCalendarViewState(Map<String, Object> params);
	
	public void insert(PlanUserDayBean planUserDayBean);
	
	public List<TeamDayPlanReportDto> findTeamTomorrowPlans(@Param("orgId")String orgId,@Param("shareAcc")String shareAcc,@Param("planDate")String planDate);
	
	public List<TeamDayPlanReportDto> findTeamNotReportNums(@Param("orgId")String orgId,@Param("shareAcc")String shareAcc,@Param("planDate")String planDate);
	
	public List<String> findUsersPlanId(Map<String, Object> params);
	
	public List<String> delPlanCust(Map<String, Object> params);
}
