package com.qftx.tsm.worklog.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.worklog.bean.WorkLogInfoBean;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
@Repository
public interface WorkLogInfoMapper extends BaseDao<WorkLogInfoBean>{
	/* 通过日期和用户id查询个数*/
	public int queryCountByUserAndDate(Map<String, Object> params);
	/* 通过日期和用户id查询当月日志*/
	public List<Date> queryWorkLogByMonth(Map<String, Object> params);
	/* 通过日期和用户id查询当月日志*/
	public List<Date> queryManagerCalendarState(WorkLogInfoBean entity);
	/* 更新评论次数  赞次数*/
	public int updateNum(Map<String, Object> params);
	//分页查询我的日志
	public List<WorkLogInfoBean> findByPage(WorkLogInfoBean entity);
	//分页查询共享日志
	public List<WorkLogInfoBean> findShareLogByPage(WorkLogInfoBean entity);
	//查询存在已提交日志的用户ID
	public List<String> findHaveLogUsers(WorkLogInfoBean entity);
}
