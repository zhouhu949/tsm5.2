package com.qftx.tsm.sys.service;

import com.qftx.tsm.sys.bean.SmsTemplateGroup;
import com.qftx.tsm.sys.dao.SmsTemplateGroupMapper;
import com.qftx.tsm.sys.dto.SmsTemplateGroupDto;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SmsTemplateGroupService{

	Logger logger = Logger.getLogger(SmsTemplateGroupService.class);
	
	@Autowired
	private SmsTemplateGroupMapper smsTemplateGroupMapper;

	 /***************************【慧营销4.0 开始】******************************************/
	
	public List<SmsTemplateGroup> getList() {
		return smsTemplateGroupMapper.find();
	}

	public List<SmsTemplateGroup> getListByCondtion(SmsTemplateGroup entity) {
		return smsTemplateGroupMapper.findByCondtion(entity);
	}
	 
	public SmsTemplateGroup getByCondtion(SmsTemplateGroup entity) {
		return smsTemplateGroupMapper.getByCondtion(entity);
	}
	
	public SmsTemplateGroup getByPrimaryKey(Map<String,String>map) {
		return smsTemplateGroupMapper.getByMapPrimaryKey(map);
	}
	 
	public void create(SmsTemplateGroup entity) {
		smsTemplateGroupMapper.insert(entity);
	}
	 
	public void modifyTrends(SmsTemplateGroup entity) {
		smsTemplateGroupMapper.updateTrends(entity);
	}

	
	public List<SmsTemplateGroupDto> getTemplateGroupList(String orgId) {
		return smsTemplateGroupMapper.getTemplateGroupList(orgId);
	}
	 
	public void remove(Map<String,String>map) {
		smsTemplateGroupMapper.deleteBy(map);	
	}
	/** 查询最大顺序值 */
	public Integer getMaxByIndex(Map<String,Object>map){
		return smsTemplateGroupMapper.getMaxByIndex(map);
	}
	
   /***************************【慧营销4.0 结束】******************************************/
}
