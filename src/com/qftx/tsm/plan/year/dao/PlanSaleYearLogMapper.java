package com.qftx.tsm.plan.year.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.plan.year.bean.PlanSaleYearLogBean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface PlanSaleYearLogMapper extends BaseDao<PlanSaleYearLogBean>{
	/* */
	public List<PlanSaleYearLogBean> queryByOrgId(Map<String, String> params);
	
	public void insert(PlanSaleYearLogBean planSaleYearLogBean);
}
