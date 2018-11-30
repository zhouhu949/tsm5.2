package com.qftx.tsm.report.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.report.bean.TsmCallReportLogBean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TsmCallReportLogMapper extends BaseDao<TsmCallReportLogBean> {
	List<TsmCallReportLogBean> query(Map<String, String> params);
}
