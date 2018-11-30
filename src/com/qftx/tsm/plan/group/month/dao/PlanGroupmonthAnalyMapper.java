package com.qftx.tsm.plan.group.month.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.plan.group.month.bean.PlanGroupmonthAnalyBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanGroupmonthAnalyMapper extends BaseDao<PlanGroupmonthAnalyBean> {
	
	public List<PlanGroupmonthAnalyBean> findErroData();
	
	public List<String> getErroId(PlanGroupmonthAnalyBean entity);
	
	public void removeErroData(PlanGroupmonthAnalyBean entity);
	
	public void updateFactNum(PlanGroupmonthAnalyBean entity);
	
	public List<PlanGroupmonthAnalyBean> findByCondtion1(PlanGroupmonthAnalyBean entity);
	
}
