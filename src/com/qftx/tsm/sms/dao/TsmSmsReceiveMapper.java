package com.qftx.tsm.sms.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.sms.bean.TsmSmsReceive;
import com.qftx.tsm.sms.dto.TsmSmsReceiveDto;

import java.util.List;
import java.util.Map;

 /** 
 * 短信接收记录
 */
public interface TsmSmsReceiveMapper extends BaseDao<TsmSmsReceive>{
   
	/** 
	 * 短信收件箱列表页面
	 */
	public List<TsmSmsReceiveDto> findSmsReceiveListPage(TsmSmsReceiveDto entity);
	
	public void modifyBatchSmsByIds(Map<String, Object> map);
	
	public List<TsmSmsReceive> findByIds(Map<String, Object> map);
	
	public void deleteBatchBy(Map<String,Object>map);
}