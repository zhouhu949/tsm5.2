package com.qftx.tsm.worklog.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.worklog.bean.WorkLogDefaultBean;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkLogDefaultMapper extends BaseDao<WorkLogDefaultBean> {
	/* 插入分享*/
	public void insert(WorkLogDefaultBean workLogDefault);
}
