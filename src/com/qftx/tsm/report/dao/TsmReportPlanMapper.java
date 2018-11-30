package com.qftx.tsm.report.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.report.bean.TsmReportPlanBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TsmReportPlanMapper extends BaseDao<TsmReportPlanBean> {
	
	public List<TsmReportPlanBean> findSumByGroup(TsmReportPlanBean entity);
	
	public List<TsmReportPlanBean> findSumByUser(TsmReportPlanBean entity);
	
	public void cancelSign(TsmReportPlanBean entity);
	
	public void plusSaleMoney(TsmReportPlanBean entity);
	
}
