package com.qftx.tsm.sys.service;

import com.qftx.tsm.sys.bean.SmsTemplate;
import com.qftx.tsm.sys.dao.SmsTemplateMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SmsTemplateService{

	Logger logger = Logger.getLogger(SmsTemplateService.class);
	
	@Autowired
	private SmsTemplateMapper smsTemplateMapper;

	 /***************************【慧营销4.0 开始】******************************************/
	
	public List<SmsTemplate> getList() {
		return smsTemplateMapper.find();
	}

	public List<SmsTemplate> getListByCondtion(SmsTemplate entity) {
		return smsTemplateMapper.findByCondtion(entity);
	}
	 
	public SmsTemplate getByCondtion(SmsTemplate entity) {
		return smsTemplateMapper.getByCondtion(entity);
	}
	
	public void create(SmsTemplate entity) {
		smsTemplateMapper.insert(entity);
	}
	 
	public void modify(SmsTemplate entity) {
		smsTemplateMapper.update(entity);
	}

	 
	public void modifyTrends(SmsTemplate entity) {
		smsTemplateMapper.updateTrends(entity);
	}

	public List<SmsTemplate> getTemplateList(Map<String,Object>map) {
		return smsTemplateMapper.getTemplateList(map);
	}
	 
	public void remove(Map<String,String>map) {
		smsTemplateMapper.deleteBy(map);		
	}
	
	public Integer getTemplateCount(String orgId){
		return smsTemplateMapper.getTemplateCount(orgId);
	}
	
	 /***************************【慧营销4.0 结束】******************************************/
}
