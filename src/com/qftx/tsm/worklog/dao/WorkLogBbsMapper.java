package com.qftx.tsm.worklog.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.worklog.bean.WorkLogBbsBean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface WorkLogBbsMapper extends BaseDao<WorkLogBbsBean> {
	/* 根据日志id查询评论*/
	public List<WorkLogBbsBean> queryByInfoId(Map<String, String> params);
	/* 插入评论*/
	public void insert(WorkLogBbsBean workLogBbs);
	/* 顶数+1*/
	public void updateUpNum(WorkLogBbsBean workLogBbs);
}
