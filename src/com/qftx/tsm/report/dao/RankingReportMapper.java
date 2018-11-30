package com.qftx.tsm.report.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.main.dto.RankReportDto;
import com.qftx.tsm.report.bean.RankingReportBean;
import com.qftx.tsm.report.dto.RankingReportDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
public interface RankingReportMapper extends BaseDao<RankingReportBean> {
	List<RankingReportDto> findRankingList(@Param("orgId")String orgId,@Param("groupIds")List<String> groupIds,@Param("year")String year,@Param("month")String month,@Param("adminAcc")String adminAcc);
	
	List<RankingReportDto> findCalloutRankingList(@Param("orgId")String orgId,@Param("groupIds")List<String> groupIds,@Param("startDate")String startDate,@Param("endDate")String endDate);

	List<RankReportDto> findCallRankingList(Map<String, Object> map);
}
