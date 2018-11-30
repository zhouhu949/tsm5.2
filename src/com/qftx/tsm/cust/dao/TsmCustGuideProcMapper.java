package com.qftx.tsm.cust.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.cust.bean.TsmCustGuideProc;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TsmCustGuideProcMapper extends BaseDao<TsmCustGuideProc> {

	public void deleteBy(Map<String, String> map);

	public List<String> findProcIdsByCustId(@Param("orgId") String orgId, @Param("custId") String custId);
}