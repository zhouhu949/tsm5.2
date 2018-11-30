package com.qftx.tsm.callrecord.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.callrecord.bean.MissCallRecordBean;
import com.qftx.tsm.callrecord.dto.MissCallRecordDto;

import java.util.List;
import java.util.Map;

public interface MissCallRecordMapper extends BaseDao<MissCallRecordBean> {
	
	public void deleteBy(Map<String,Object>map);
	
	public void deleteBatchBy(Map<String,Object>map);
	
	/** 分页，获取漏接记录 */
	public List<MissCallRecordBean>findMissCallListPage(MissCallRecordDto entity);
	
	/** 根据来电号码 修改处理状态 */
	public void updateTrendsByStatus(MissCallRecordBean entity);
}
