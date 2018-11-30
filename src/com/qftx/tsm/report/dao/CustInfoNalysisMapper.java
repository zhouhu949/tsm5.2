package com.qftx.tsm.report.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.report.dto.CustInfoNalysisDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CustInfoNalysisMapper extends BaseDao<CustInfoNalysisDto> {

	public List<CustInfoNalysisDto> findTotalCustInfoNalysis(Map<String, String> map);

	public List<CustInfoNalysisDto> findCustInfoNalysisList(Map<String, String> map);

	public CustInfoNalysisDto findLastRecord(@Param("orgId") String orgId, @Param("resId") String resId);
}
