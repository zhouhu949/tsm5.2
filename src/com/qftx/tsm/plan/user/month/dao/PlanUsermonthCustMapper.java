package com.qftx.tsm.plan.user.month.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.plan.user.month.bean.PlanHistoryBean;
import com.qftx.tsm.plan.user.month.bean.PlanUsermonthCustBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanUsermonthCustMapper extends BaseDao<PlanUsermonthCustBean> {
	/*查询个人月计划重点客户签约客户数和签约金额*/
	public PlanUsermonthCustBean queryCustSum(PlanUsermonthCustBean bean);
	
	public List<PlanHistoryBean> findHistoryListPage(PlanHistoryBean bean);
	
	public List<PlanUsermonthCustBean> findFromRes(PlanUsermonthCustBean bean); 
	
	public void updateFactNum(PlanUsermonthCustBean bean);
	
}
