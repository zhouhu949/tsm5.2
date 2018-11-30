package com.qftx.tsm.credit.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.credit.bean.ImportLeadResultBean;
import com.qftx.tsm.credit.dto.ImportLeadResultDto;

@Repository
public interface ImportLeadResultMapper extends BaseDao<ImportLeadResultBean> {
	List<ImportLeadResultDto> query(Map<String, Object> params);
	
	public void updateByFileId(ImportLeadResultBean entity);
}
