package com.qftx.tsm.worklog.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.worklog.bean.WorkLogBbsUpBean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface WorkLogBbsUpMapper extends BaseDao<WorkLogBbsUpBean> {
	List<WorkLogBbsUpBean> query(Map<String, String> params);
}
