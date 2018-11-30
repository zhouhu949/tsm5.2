package com.qftx.tsm.cust.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.cust.bean.ResDetailCallBean;
import org.apache.ibatis.annotations.Param;

public interface ResDetailCallMapper extends BaseDao<ResDetailCallBean> {

	ResDetailCallBean getByPrimaryKeyAndOrgId(@Param("orgId") String orgId, @Param("id") String id);

	void deleteByOrgId(@Param("orgId") String orgId, @Param("id") String id);

	ResDetailCallBean getByDetailIdAndOrgId(@Param("orgId") String orgId, @Param("id") String id);

}
