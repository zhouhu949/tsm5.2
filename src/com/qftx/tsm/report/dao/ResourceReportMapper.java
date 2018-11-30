package com.qftx.tsm.report.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.report.bean.ResourceReportBean;

public interface ResourceReportMapper extends BaseDao<ResourceReportBean> {
	
	public void insertTsmReportResource(ResourceReportBean bean);
	
	public void updateResource(ResourceReportBean bean);
	
	public ResourceReportBean selectResource(ResourceReportBean bean);

}
