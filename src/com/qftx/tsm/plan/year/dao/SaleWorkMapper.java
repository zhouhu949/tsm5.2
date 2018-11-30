package com.qftx.tsm.plan.year.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.plan.year.bean.SaleWorkBean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface SaleWorkMapper extends BaseDao<SaleWorkBean>{
	/* */
	public List<SaleWorkBean> querySaleWorks(Map<String, String> params);
	
	public void insert(SaleWorkBean saleWorkBean);
}
