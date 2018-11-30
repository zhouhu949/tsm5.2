package com.qftx.tsm.email.service;

import com.qftx.tsm.email.bean.TsmEmailTemp;
import com.qftx.tsm.email.dao.TsmEmailTempMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class TsmEmailTempService{

	@Autowired
	private TsmEmailTempMapper tsmEmailTempMapper;
	
	 
	public List<TsmEmailTemp> getList() {
		return tsmEmailTempMapper.find();
	}
 
	public List<TsmEmailTemp> getListByCondtion(TsmEmailTemp entity) {
		return tsmEmailTempMapper.findByCondtion(entity);
	}
	 
	 
	public void create(TsmEmailTemp entity) {
		tsmEmailTempMapper.insert(entity);
	}
 
	public void createBatch(List<TsmEmailTemp> entitys) {
		tsmEmailTempMapper.insertBatch(entitys);
	}

	public void modify(TsmEmailTemp entity) {
		tsmEmailTempMapper.update(entity);
	}
	 
	public void modifyTrends(TsmEmailTemp entity) {
		tsmEmailTempMapper.updateTrends(entity);
	}

	public void remove(String id) {
		tsmEmailTempMapper.delete(id);
	}
 
	public void removeBatch(List<String> ids,String orgId) {
		Map<String,Object>map = new HashMap<String, Object>();
		map.put("orgId", orgId);
		map.put("list", ids);
		tsmEmailTempMapper.deleteBatchBy(map);
	}

}
