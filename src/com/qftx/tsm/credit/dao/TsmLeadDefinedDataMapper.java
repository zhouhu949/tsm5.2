package com.qftx.tsm.credit.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.credit.bean.TsmLeadDefinedData;

public interface TsmLeadDefinedDataMapper extends BaseDao<TsmLeadDefinedData> {

	List<String> findLeadIdsByDefinedData(Map<String, Object> paramMap);

	List<TsmLeadDefinedData> findCustDefinedShowDatas(Map<String, Object> map);
	
	public void deleteByCustId(@Param("orgId")String orgId,@Param("leadId") String custId);
	
	public void deleteByFieldCode(Map<String,Object> map);
}