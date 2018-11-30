package com.qftx.export.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qftx.common.dao.BaseDao;
import com.qftx.export.bean.LogExportInfoBean;

public interface LogExportInfoMapper extends BaseDao<LogExportInfoBean> {
	
	public LogExportInfoBean getByPrimaryKeyAndOrgId(@Param("orgId")String orgId,@Param("exportId")String exportId);

	public void deleteByOrgId(@Param("orgId")String orgId,@Param("exportId")String exportId);
	
	public List<LogExportInfoBean> findLogListPage(LogExportInfoBean bean);
}
