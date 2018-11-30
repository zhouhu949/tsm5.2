package com.qftx.tsm.sms.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.sms.bean.TsmSendSms;
import com.qftx.tsm.sms.dto.TsmSendSmsDto;

import java.util.List;
import java.util.Map;

public interface TsmSendSmsMapper extends BaseDao<TsmSendSms> {

	public void updateBatchSms(Map<String, Object> map);

	public List<TsmSendSmsDto> getSendSmsListPage(TsmSendSmsDto entity);
	
	public List<TsmSendSmsDto> getTeamSendHookSmsListPage(TsmSendSmsDto entity);
	
	public List<TsmSendSms> findByIds(List<String> ids);

	public void deleteBatchBy(Map<String,Object>map);
	
	/** 查询 在条件内是否有短信记录 */
	public int getExistsCount(TsmSendSms entity);
}
