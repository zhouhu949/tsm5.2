package com.qftx.tsm.plan.group.month.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.plan.group.month.bean.PlanAuthlogBean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PlanAuthlogMapper extends BaseDao<PlanAuthlogBean> {
	List<PlanAuthlogBean> queryByPlanId (Map<String, String> params);
}
