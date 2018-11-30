package com.qftx.tsm.sms.service;

import com.qftx.tsm.sms.bean.TsmSmsReceive;
import com.qftx.tsm.sms.dao.TsmSmsReceiveMapper;
import com.qftx.tsm.sms.dto.TsmSmsReceiveDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


 /** 
 *短信 接收记录
 */
@Service
public class TsmSmsReceiveService {
	
	@Autowired
	private TsmSmsReceiveMapper tsmSmsReceiveMapper;
	 
	public void create(TsmSmsReceive entity){
		tsmSmsReceiveMapper.insert(entity);
	}
	 
	public void createBatch(List<TsmSmsReceive> entitys) {
		tsmSmsReceiveMapper.insertBatch(entitys);
	}
	 
	public TsmSmsReceive getByPrimaryKey(String id) {
		return tsmSmsReceiveMapper.getByPrimaryKey(id);
	}
	
	public List<TsmSmsReceive> getListByCondtion(TsmSmsReceive entity) {
		return tsmSmsReceiveMapper.findByCondtion(entity);
	}
 
	public List<TsmSmsReceive> getList(){
		return tsmSmsReceiveMapper.find();
	}
	 
	public List<TsmSmsReceive> getListPage(TsmSmsReceive entity) {
		return tsmSmsReceiveMapper.findListPage(entity);
	}
	 
	public void modify(TsmSmsReceive entity) {
		tsmSmsReceiveMapper.update(entity);
	}
	 
	public void modifyTrends(TsmSmsReceive entity) {
		tsmSmsReceiveMapper.updateTrends(entity);		
	}
	 
	public void modifyBatch(List<TsmSmsReceive> entitys) {
		for(TsmSmsReceive dataDictionary : entitys){
			tsmSmsReceiveMapper.update(dataDictionary);
		}
	}
	 
	public void modifyTrendsBatch(List<TsmSmsReceive> entitys) {
		for(TsmSmsReceive dataDictionary : entitys){
			tsmSmsReceiveMapper.updateTrends(dataDictionary);
		}

	}
	 
	public void remove(String id) {
		tsmSmsReceiveMapper.delete(id);
	}
 
	public void removeBatch(List<String> ids,String orgId) {
		Map<String,Object>map = new HashMap<String, Object>();
		map.put("orgId", orgId);
		map.put("list", ids);
		tsmSmsReceiveMapper.deleteBatchBy(map);
	}
	 
	public List<TsmSmsReceiveDto> getSmsReceiveListPage(TsmSmsReceiveDto entity) {
		return tsmSmsReceiveMapper.findSmsReceiveListPage(entity);
	}

	public void updateBatchSms(Map<String, Object> map) {
		tsmSmsReceiveMapper.modifyBatchSmsByIds(map);
	}

	public List<TsmSmsReceive> getByIds(String orgId,List<String> list) {
		Map<String,Object>map = new HashMap<String, Object>();
		map.put("orgId", orgId);
		map.put("list", list);
		return tsmSmsReceiveMapper.findByIds(map);
	}


}
