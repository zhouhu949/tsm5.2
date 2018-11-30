package com.qftx.tsm.imp.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.imp.bean.ImportResInfoBean;
import com.qftx.tsm.imp.dto.ImportResInfoDto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ImportResInfoMapper extends BaseDao<ImportResInfoBean> {
	List<ImportResInfoBean> query(Map<String, String> params);
	/** 查询 导入错误资源 */
	List<ImportResInfoDto> findErrorResInfos(Map<String,Object>params);
	
	List<ImportResInfoBean> findErrorResInfos1(Map<String,Object>params);
	
	
}
