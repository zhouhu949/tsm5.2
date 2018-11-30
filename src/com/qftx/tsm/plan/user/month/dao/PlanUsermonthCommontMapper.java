package com.qftx.tsm.plan.user.month.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.plan.user.month.bean.PlanUsermonthCommontBean;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanUsermonthCommontMapper extends BaseDao<PlanUsermonthCommontBean> {
	public void insert(PlanUsermonthCommontBean bean);
}
