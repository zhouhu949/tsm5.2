package com.qftx.tsm.plan.user.month.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.plan.user.month.bean.PlanUsermonthToSignLogBean;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanUsermonthToSignLogMapper extends BaseDao<PlanUsermonthToSignLogBean> {
	public PlanUsermonthToSignLogBean findSumFact(PlanUsermonthToSignLogBean entity);
}
