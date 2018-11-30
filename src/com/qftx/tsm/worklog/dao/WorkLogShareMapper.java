package com.qftx.tsm.worklog.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.worklog.bean.WorkLogShareBean;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkLogShareMapper extends BaseDao<WorkLogShareBean> {
	/* 插入分享*/
	public void insert(WorkLogShareBean workLogShare);
}
