package com.qftx.tsm.email.service;

import com.qftx.tsm.email.bean.TsmEmailConfig;
import com.qftx.tsm.email.dao.TsmEmailConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class TsmEmailConfigService{

	@Autowired
	private TsmEmailConfigMapper tsmEmailConfigMapper;
	
	 
	public List<TsmEmailConfig> getList() {
		return tsmEmailConfigMapper.find();
	}
 
	public List<TsmEmailConfig> getListByCondtion(TsmEmailConfig entity) {
		return tsmEmailConfigMapper.findByCondtion(entity);
	}
	
	public TsmEmailConfig getByCondtion(TsmEmailConfig entity){
		return tsmEmailConfigMapper.getByCondtion(entity);
	}
	 
	 
	public void create(TsmEmailConfig entity) {
		tsmEmailConfigMapper.insert(entity);
	}
 
	public void createBatch(List<TsmEmailConfig> entitys) {
		tsmEmailConfigMapper.insertBatch(entitys);
	}

	public void modify(TsmEmailConfig entity) {
		tsmEmailConfigMapper.update(entity);
	}
	 
	public void modifyTrends(TsmEmailConfig entity) {
		tsmEmailConfigMapper.updateTrends(entity);
	}

	public void remove(String id) {
		tsmEmailConfigMapper.delete(id);
	}
 
	public void removeBatch(List<String> ids,String orgId) {
		Map<String,Object>map = new HashMap<String, Object>();
		map.put("orgId", orgId);
		map.put("list", ids);
		tsmEmailConfigMapper.deleteBatchBy(map);
	}

}
