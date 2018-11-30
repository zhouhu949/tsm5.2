package com.qftx.tsm.imp.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.imp.bean.ImportResResultBean;
import com.qftx.tsm.imp.dto.ImportResResultDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ImportResResultMapper extends BaseDao<ImportResResultBean> {
	List<ImportResResultDto> query(Map<String, Object> params);
	
	public void updateByFileId(ImportResResultBean entity);
}
