package com.qftx.tsm.email.service;

import com.qftx.tsm.email.bean.TsmEmailSign;
import com.qftx.tsm.email.dao.TsmEmailSignMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class TsmEmailSignService{

	@Autowired
	private TsmEmailSignMapper tsmEmailSignMapper;
	
	 
	public List<TsmEmailSign> getList() {
		return tsmEmailSignMapper.find();
	}
 
	public List<TsmEmailSign> getListByCondtion(TsmEmailSign entity) {
		return tsmEmailSignMapper.findByCondtion(entity);
	}
	 
	public TsmEmailSign getByCondtion(TsmEmailSign entity) {
		return tsmEmailSignMapper.getByCondtion(entity);
	}
	 
	public void create(TsmEmailSign entity) {
		tsmEmailSignMapper.insert(entity);
	}
 
	public void createBatch(List<TsmEmailSign> entitys) {
		tsmEmailSignMapper.insertBatch(entitys);
	}

	public void modify(TsmEmailSign entity) {
		tsmEmailSignMapper.update(entity);
	}
	 
	public void modifyTrends(TsmEmailSign entity) {
		tsmEmailSignMapper.updateTrends(entity);
	}

	public void remove(String id) {
		tsmEmailSignMapper.delete(id);
	}
 
	public void removeBatch(List<String> ids,String orgId) {
		Map<String,Object>map = new HashMap<String, Object>();
		map.put("orgId", orgId);
		map.put("list", ids);
		tsmEmailSignMapper.deleteBatchBy(map);
	}

	public void deleteBatchById(Map<String,Object>map){
		tsmEmailSignMapper.deleteBatchById(map);
	}
}
