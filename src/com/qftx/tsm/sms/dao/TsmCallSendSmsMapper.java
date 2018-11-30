package com.qftx.tsm.sms.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.sms.bean.TsmCallSendSms;
import com.qftx.tsm.sms.dto.TsmCallSendSmsDto;

import java.util.List;
import java.util.Map;


public interface TsmCallSendSmsMapper extends BaseDao<TsmCallSendSms> {

	public void updateBatchSms(Map<String, Object> map);

	public List<TsmCallSendSmsDto> getSendSmsListPage(TsmCallSendSmsDto entity);
	
	public List<TsmCallSendSms> findByIds(List<String> ids);

	public void deleteBatchBy(Map<String,Object>map);
	
}
