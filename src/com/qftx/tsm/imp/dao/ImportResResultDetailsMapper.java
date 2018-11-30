package com.qftx.tsm.imp.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.imp.bean.ImportResResultDetailsBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ImportResResultDetailsMapper extends BaseDao<ImportResResultDetailsBean> {
	List<ImportResResultDetailsBean> query(Map<String, String> params);
	
	public void deleteByBatch(@Param("orgId")String orgId,@Param("list")List<String> list);
}
