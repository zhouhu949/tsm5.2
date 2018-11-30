package com.qftx.tsm.plan.user.month.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.plan.user.month.bean.PlanUsermonthPointBean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PlanUsermonthPointMapper extends BaseDao<PlanUsermonthPointBean> {
	public List<PlanUsermonthPointBean> queryByPlanId(Map<String, Object> params);
	
	public void insert(PlanUsermonthPointBean bean);
}
