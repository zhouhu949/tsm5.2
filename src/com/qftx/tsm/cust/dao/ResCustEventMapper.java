package com.qftx.tsm.cust.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.cust.bean.ResCustEventBean;
import com.qftx.tsm.cust.dto.ResCustActionDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ResCustEventMapper extends BaseDao<ResCustEventBean> {
	List<ResCustEventBean> findHistoryListPage(ResCustEventBean bean);
	
	List<Map<String, Object>> findEventsNums(ResCustEventBean bean);
	
	void cleanActionRecords(@Param("orgId")String orgId,@Param("type")String type,@Param("custIds")List<String> custIds);
	
	List<ResCustActionDto> findResCustActionsListPage(ResCustActionDto dto);

	List<ResCustActionDto> findResCustActionsList(@Param("orgId")String orgId,@Param("custId")String custId);
}
