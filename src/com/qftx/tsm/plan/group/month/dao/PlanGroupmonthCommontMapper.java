package com.qftx.tsm.plan.group.month.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.plan.group.month.bean.PlanGroupmonthCommontBean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PlanGroupmonthCommontMapper extends BaseDao<PlanGroupmonthCommontBean> {
	List<PlanGroupmonthCommontBean> queryByPlanId (Map<String, String> params);
}
