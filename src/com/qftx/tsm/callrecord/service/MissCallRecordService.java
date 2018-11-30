package com.qftx.tsm.callrecord.service;

import com.qftx.tsm.callrecord.bean.MissCallRecordBean;
import com.qftx.tsm.callrecord.dao.MissCallRecordMapper;
import com.qftx.tsm.callrecord.dto.MissCallRecordDto;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通信管理 漏接记录
 */
@Service
public class MissCallRecordService{
	Logger logger = Logger.getLogger(MissCallRecordService.class);
	
	@Autowired
	private MissCallRecordMapper missCallRecordMapper;
	
	public List<MissCallRecordBean> getList() {
		return missCallRecordMapper.find();
	}
 
	public List<MissCallRecordBean> getListByCondtion(MissCallRecordBean entity) {
		return missCallRecordMapper.findByCondtion(entity);
	}
	
	public MissCallRecordBean getByCondtion(MissCallRecordBean entity){
		return missCallRecordMapper.getByCondtion(entity);
	}
	 
	 
	public void create(MissCallRecordBean entity) {
		missCallRecordMapper.insert(entity);
	}
 
	public void createBatch(List<MissCallRecordBean> entitys) {
		missCallRecordMapper.insertBatch(entitys);
	}

	public void remove(String id,String orgId) {
		Map<String,Object>map = new HashMap<String, Object>();
		map.put("orgId", orgId);
		map.put("id", id);
		missCallRecordMapper.deleteBy(map);
	}
 
	public void removeBatch(List<String> ids,String orgId) {
		Map<String,Object>map = new HashMap<String, Object>();
		map.put("orgId", orgId);
		map.put("list", ids);
		missCallRecordMapper.deleteBatchBy(map);
	}
	
	/** 分页，获取漏接记录 */
	public List<MissCallRecordBean>getMissCallListPage(MissCallRecordDto entity){
		return missCallRecordMapper.findMissCallListPage(entity);
	}
	
	/** 根据来电号码 修改处理状态 */
	public void modfiyByStatus(MissCallRecordBean entity){
		missCallRecordMapper.updateTrendsByStatus(entity);
	}
}