package com.qftx.tsm.report.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.report.bean.CallReportBean;
import com.qftx.tsm.report.dto.CallReportDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CallReportMapper extends BaseDao<CallReportBean> {
	public List<CallReportBean> findUserDayCallReport(@Param("orgId")String orgId,@Param("account")String account,@Param("reportDate")String reportDate);
	
	public List<CallReportDto> findTeamDayCallReport(@Param("orgId")String orgId,@Param("account")String account,@Param("groupIds")List<String> groupIds);
	
	public List<CallReportDto> findTeamDayCallReportListPage(@Param("orgId")String orgId,@Param("account")String account,@Param("groupIds")List<String> groupIds);
}
