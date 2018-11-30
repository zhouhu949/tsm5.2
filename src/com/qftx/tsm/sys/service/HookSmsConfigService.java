package com.qftx.tsm.sys.service;

import com.qftx.tsm.sys.bean.HookSmsConfig;
import com.qftx.tsm.sys.dao.HookSmsConfigMapper;
import com.qftx.tsm.sys.dto.HookSmsConfigDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HookSmsConfigService {
	@Autowired
	private  HookSmsConfigMapper  smsConfigMapper;
	 
	public List<HookSmsConfig> getList() {
		return smsConfigMapper.find();
	}

	public List<HookSmsConfig> getListByCondtion(HookSmsConfig entity) {
		return smsConfigMapper.findByCondtion(entity);
	}

	public List<HookSmsConfig> getListPage(HookSmsConfig entity) {
		return smsConfigMapper.findListPage(entity);
	}

	public HookSmsConfig getByPrimaryKey(String id) {
		return smsConfigMapper.getByPrimaryKey(id);
	}

	public void create(HookSmsConfig entity) {
		smsConfigMapper.insert(entity);
	}

	public void createBatch(List<HookSmsConfig> entitys) {
		smsConfigMapper.insertBatch(entitys);
	}

	public void modifyTrends(HookSmsConfig entity) {
		smsConfigMapper.updateTrends(entity);		
	}

	public void modifyTrendsBatch(List<HookSmsConfig> entitys) {
		for (HookSmsConfig entity : entitys) {
			smsConfigMapper.updateTrends(entity);
		}
	}

	public void remove(String id) {
		smsConfigMapper.delete(id);
	}
	
	public HookSmsConfig getByCondtion(HookSmsConfig entity){
		return smsConfigMapper.getByCondtion(entity);
	}

	public List<HookSmsConfigDto>getHookTempListPage(HookSmsConfigDto entity){
		return smsConfigMapper.findHookTempListPage(entity);
	}
	
	/** 查询 是否存在同一 启用模板 */
	public Integer getExistByConfig(Map<String,Object>map){
		return smsConfigMapper.findExistByConfig(map);
	}

	public Integer findExistByOrgId(String orgId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orgId", orgId);
		return smsConfigMapper.findExistByOrgId(map);
	}
}
