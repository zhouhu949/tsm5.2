package com.qftx.tsm.report.dao;

import java.util.List;
import java.util.Map;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.report.bean.CustomReportBean;
import com.qftx.tsm.report.bean.CustomReportShareBean;

public interface CustomReportMapper extends BaseDao<CustomReportBean>{

	public List<CustomReportBean> findByIds(Map<String, Object> map);

	public List<CustomReportBean> findShareToMeCustomReportListPage(CustomReportShareBean bean);
}