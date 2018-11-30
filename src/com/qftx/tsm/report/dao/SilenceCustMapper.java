package com.qftx.tsm.report.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.report.dto.SilenceCustDto;

import java.util.List;
import java.util.Map;

public interface SilenceCustMapper extends BaseDao<SilenceCustDto> {

	public List<SilenceCustDto> findSilentCustList(Map<String, String> map);

	public SilenceCustDto findLastSilentCust(Map<String, String> map);

	public List<SilenceCustDto> findSilentDetailList(Map<String, String> map);

}