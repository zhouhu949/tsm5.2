package com.qftx.tsm.sys.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.sys.bean.SmsTemplate;

import java.util.List;
import java.util.Map;


public interface SmsTemplateMapper extends BaseDao<SmsTemplate> {
	List<SmsTemplate> getTemplateList(Map<String,Object>map);
	 /***************************【慧营销4.0 开始】******************************************/
		public void deleteBy(Map<String,String>map)	;
		
		public Integer getTemplateCount(String orgId);
	 /***************************【慧营销4.0 结束】******************************************/
}
