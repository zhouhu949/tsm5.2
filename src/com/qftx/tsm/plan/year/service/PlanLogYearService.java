package com.qftx.tsm.plan.year.service;

import com.qftx.base.util.DateUtil;
import com.qftx.base.util.GuidUtil;
import com.qftx.tsm.plan.year.bean.PlanLogYearBean;
import com.qftx.tsm.plan.year.dao.PlanLogYearMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PlanLogYearService {
	@Autowired
	private PlanLogYearMapper planLogYearMapper;
	@Autowired
	private PlanSaleYearService planSaleYearService;
	
	/* 查询全年计划*/
	public List<PlanLogYearBean>  queryFullYearPlan(String orgId,String planYear){
		PlanLogYearBean entity = new PlanLogYearBean();
		entity.setOrgId(orgId);
		entity.setPlanYear(planYear);
		
		entity.setOrderKey("inputtime");
		return planLogYearMapper.findChartData(entity);
	}
	
	/* 查询全年计划*/
	public List<PlanLogYearBean>  queryFullYearPlanHaveDContext(String orgId,String planYear){
		PlanLogYearBean entity = new PlanLogYearBean();
		entity.setOrgId(orgId);
		entity.setPlanYear(planYear);
		entity.setIsHaveDcontext(1);
		
		entity.setOrderKey("inputtime desc");
		return planLogYearMapper.findByCondtion(entity);
	}
	
	public PlanLogYearBean findLogByDate(String orgId,String planYear,Date inputtime){
		PlanLogYearBean entity = new PlanLogYearBean();
		entity.setOrgId(orgId);
		entity.setPlanYear(planYear);
		return planLogYearMapper.getByCondtion(entity);
	}
	
	public void processData(List<PlanLogYearBean> list){
		if(list!=null && list.size()>0){
			for (PlanLogYearBean planLogYearBean : list) {
				Date inputtime = planLogYearBean.getInputtime();
				if(inputtime!=null){
					planLogYearBean.setInputtimeMonthDayStr(DateUtil.formatDate(inputtime, "MM月dd日"));
					planLogYearBean.setInputtimeYearStr(String.valueOf(DateUtil.year(inputtime)));
				}
			}
		}
	}
	
	public void insertPlanLogYear(String userId,String orgId,String planYear,String dcontext,Date inputtime,String reason){
		double sum = planSaleYearService.queryPlanMoneyByYear(orgId, planYear);
		PlanLogYearBean planLogYearBean = new PlanLogYearBean();
		planLogYearBean.setId(GuidUtil.getGuid());
		planLogYearBean.setOrgId(orgId);
		planLogYearBean.setUserId(userId);
		planLogYearBean.setPlanMoney(sum);
		planLogYearBean.setPlanYear(planYear);
		planLogYearBean.setDcontext(dcontext);
		planLogYearBean.setReason(reason);
		planLogYearBean.setInputtime(inputtime);
		planLogYearBean.setIsDel(0);
		planLogYearMapper.insert(planLogYearBean);
		
		//PlanLogYearBean dbLog = findLogByDate(orgId, planYear, inputtime);
		/*if(dbLog==null){
			
			PlanLogYearBean planLogYearBean = new PlanLogYearBean();
			planLogYearBean.setId(GuidUtil.getGuid());
			planLogYearBean.setOrgId(orgId);
			planLogYearBean.setUserId(userId);
			planLogYearBean.setPlanMoney(sum);
			planLogYearBean.setPlanYear(planYear);
			planLogYearBean.setDcontext(dcontext);
			planLogYearBean.setReason(reason);
			planLogYearBean.setInputtime(inputtime);
			planLogYearBean.setIsDel(0);
			planLogYearMapper.insert(planLogYearBean);
		}else{
			dbLog.setUpdatetime(inputtime);
			dbLog.setDcontext(dcontext);
			dbLog.setReason(reason);
			dbLog.setPlanMoney(sum);
			dbLog.setUserId(userId);
			planLogYearMapper.updateTrends(dbLog);
		}*/
	}
	
}
