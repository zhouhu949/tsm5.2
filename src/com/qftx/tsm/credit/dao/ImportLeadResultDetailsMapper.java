package com.qftx.tsm.credit.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.credit.bean.ImportLeadResultDetailsBean;

@Repository
public interface ImportLeadResultDetailsMapper extends BaseDao<ImportLeadResultDetailsBean> {
	List<ImportLeadResultDetailsBean> query(Map<String, String> params);
	
	public void deleteByBatch(@Param("orgId")String orgId,@Param("list")List<String> list);
}
