package com.qftx.tsm.callrecord.service;

import com.qftx.tsm.callrecord.bean.CallRecordBlack;
import com.qftx.tsm.callrecord.dao.CallRecordBlackMapper;
import com.qftx.tsm.callrecord.dto.CallRecordBlackDto;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * 通信管理 黑名单
 */
@Service
public class CallRecordBlackService{
	Logger logger = Logger.getLogger(CallRecordBlackService.class);
	
	@Autowired
	private CallRecordBlackMapper callRecordBlackMapper;	
	
	public void removeBy(Map<String,Object>map){
		callRecordBlackMapper.deleteBy(map);
	}
	
	public void removeBatchBy(Map<String,Object>map){
		callRecordBlackMapper.deleteBatchBy(map);
	}
	
	public void create(CallRecordBlack entity) {
		callRecordBlackMapper.insert(entity);

	}
	 
	public void createBatch(List<CallRecordBlack> entitys) {
		callRecordBlackMapper.insertBatch(entitys);

	}
	 
	public void modify(CallRecordBlack entity) {
		callRecordBlackMapper.update(entity);
	}
 
	public void modifyTrends(CallRecordBlack entity) {
		callRecordBlackMapper.updateTrends(entity);
	}
	
	/** 查询黑名单  */
	public List<CallRecordBlack> getCallRecordBlackListPage(CallRecordBlackDto dto){
		return callRecordBlackMapper.findCallRecordBlackListPage(dto);
	}
}