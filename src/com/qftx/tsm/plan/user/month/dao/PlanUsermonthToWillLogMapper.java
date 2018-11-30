package com.qftx.tsm.plan.user.month.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.plan.user.month.bean.PlanUsermonthToWillLogBean;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanUsermonthToWillLogMapper extends BaseDao<PlanUsermonthToWillLogBean> {
	
	public PlanUsermonthToWillLogBean findSumFact(PlanUsermonthToWillLogBean entity);
}
