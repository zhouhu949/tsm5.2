package com.qftx.tsm.plan.data.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.plan.user.day.bean.PlanUserDayBean;

@Repository
public interface DataProcessMapper extends BaseDao<PlanUserDayBean> {
	public int countTableNum(@Param(value = "orgId")  String orgId,@Param(value = "tableName")  String tableName);

	public List<String> getSudIds(@Param(value = "orgId")  String orgId,@Param(value = "whereSql")  String whereSql);

	public List<String> getPlanUserMonthIds(@Param(value = "orgId")  String orgId,@Param(value = "whereSql")  String whereSql);

	public List<String> getGroupIds(@Param(value = "orgId")  String orgId);

}
