package com.qftx.tsm.sms.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.sms.bean.TsmSendSmsDetail;
import com.qftx.tsm.sms.dto.TsmSendSmsDetailDto;

import java.util.List;
import java.util.Map;

/**
 * 短信发送明细
 * @author: zwj
 * @since: 2015-12-9  下午2:38:01
 * @history: 4.x
 */
public interface TsmSendSmsDetailMapper extends BaseDao<TsmSendSmsDetail> {
	
	public void removeByMajorId(String id);	
	
	/** 
	 *发送短信明细列表
	 */
	public List<TsmSendSmsDetailDto> getSendSmsDetailListPage(TsmSendSmsDetailDto entity);

	public void deleteBatchBy(Map<String,Object>map);
	
	/** 
	 * 挂机短信 发送记录列表
	 */
	public List<TsmSendSmsDetailDto> getHookSendSmsDetailListPage(TsmSendSmsDetailDto entity);	
}
