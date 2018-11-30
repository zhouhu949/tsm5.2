package com.qftx.tsm.sms.service;

import com.qftx.tsm.sms.bean.TsmSendSmsDetail;
import com.qftx.tsm.sms.dao.TsmSendSmsDetailMapper;
import com.qftx.tsm.sms.dto.TsmSendSmsDetailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class TsmSendSmsDetailService{

	@Autowired
	private TsmSendSmsDetailMapper tsmSendSmsDetailMapper;

	public void createBatch(List<TsmSendSmsDetail> entitys) {
		 tsmSendSmsDetailMapper.insertBatch(entitys);
	}
	 
	public void removeBatch(List<String> ids,String orgId) {
		Map<String,Object>map = new HashMap<String, Object>();
		map.put("orgId", orgId);
		map.put("list", ids);
		tsmSendSmsDetailMapper.deleteBatchBy(map);
	}

	public void removeByMajorId(String id) {
		tsmSendSmsDetailMapper.removeByMajorId(id);
		
	}

	public List<TsmSendSmsDetailDto> getSendSmsDetailListPage(
			TsmSendSmsDetailDto entity) {
		return tsmSendSmsDetailMapper.getSendSmsDetailListPage(entity);
	}

	 
	public void modifyBatchSmsDetail(List<TsmSendSmsDetail> tsmSendSmsDetailList) {
		for(TsmSendSmsDetail tsmSendSmsDetail : tsmSendSmsDetailList){
			tsmSendSmsDetailMapper.update(tsmSendSmsDetail);
		}		
	}
	
	public List<TsmSendSmsDetailDto> findHookSendSmsDetailListPage(TsmSendSmsDetailDto entity){
		return tsmSendSmsDetailMapper.getHookSendSmsDetailListPage(entity);
	}
}
