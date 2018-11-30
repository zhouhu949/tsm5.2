package com.qftx.tsm.credit.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.credit.bean.ImportLeadInfoBean;
import com.qftx.tsm.credit.dto.ImportLeadInfoDto;

@Repository
public interface ImportLeadInfoMapper extends BaseDao<ImportLeadInfoBean> {
	List<ImportLeadInfoBean> query(Map<String, String> params);
	/** 查询 导入错误信息 */
	List<ImportLeadInfoDto> findErrorLeadInfos(Map<String,Object>params);
	
	List<ImportLeadInfoBean> findErrorLeadInfos1(Map<String,Object>params);
	
	
}
