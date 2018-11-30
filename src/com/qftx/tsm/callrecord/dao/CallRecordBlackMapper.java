package com.qftx.tsm.callrecord.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.callrecord.bean.CallRecordBlack;
import com.qftx.tsm.callrecord.dto.CallRecordBlackDto;

import java.util.List;
import java.util.Map;

public interface CallRecordBlackMapper extends BaseDao<CallRecordBlack> {

	public void deleteBy(Map<String,Object>map);
	public void deleteBatchBy(Map<String,Object>map);
	
	/** 查询黑名单  */
	public List<CallRecordBlack>findCallRecordBlackListPage(CallRecordBlackDto dto);
}
