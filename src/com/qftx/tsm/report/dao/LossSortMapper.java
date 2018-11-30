package com.qftx.tsm.report.dao;

import com.qftx.tsm.report.dto.GroupDto;
import com.qftx.tsm.report.dto.LossSortDetial;

import java.util.List;
import java.util.Map;

public interface LossSortMapper {

	public List<GroupDto> findGroupList(Map<String, String> map);

	public List<GroupDto> findOptionByGroupId(Map<String, String> map);

	public List<GroupDto> findOptionTotal(Map<String, String> map);

	public List<LossSortDetial> findDetailList(Map<String, String> map);

}
