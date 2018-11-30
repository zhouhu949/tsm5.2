package com.qftx.tsm.email.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.email.bean.TsmEmailSend;
import com.qftx.tsm.email.dto.EmailRecordDto;

import java.util.List;
import java.util.Map;



public interface TsmEmailSendMapper extends BaseDao<TsmEmailSend> {
	public void deleteBatchBy(Map<String,Object>map);
	
	public List<EmailRecordDto>findSendLogListPage(EmailRecordDto entity);

	public List<EmailRecordDto> findSendLogByRight(EmailRecordDto entity);
	
}