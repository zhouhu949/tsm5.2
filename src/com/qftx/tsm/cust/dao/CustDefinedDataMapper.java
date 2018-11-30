package com.qftx.tsm.cust.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.cust.bean.CustDefinedDataBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CustDefinedDataMapper extends BaseDao<CustDefinedDataBean> {
	
	public List<String> findCustIdsByDefinedData(Map<String, Object> map);
	
	public List<CustDefinedDataBean> findCustDefinedShowDatas(Map<String, Object> map);
	
	public void deleteByCustId(@Param("orgId")String orgId,@Param("custId")String custId);
	
	public void deleteByFieldCode(Map<String,Object>map);
}
