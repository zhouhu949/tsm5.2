package com.qftx.tsm.report.dao;

import java.util.List;
import java.util.Map;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.report.bean.CustomReportShareBean;

public interface CustomReportShareMapper extends BaseDao<CustomReportShareBean>{

	public List<String> findReportIdsByShareAcc(CustomReportShareBean bean);

	public List<String> getShareAccsByReportId(Map<String, String> map);

	public void deleteShare(CustomReportShareBean share);

	public void deleteByReportId(CustomReportShareBean share);

	public void deleteById(Map<String, Object> map);

	
}