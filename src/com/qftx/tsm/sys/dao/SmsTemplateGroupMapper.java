package com.qftx.tsm.sys.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.sys.bean.SmsTemplateGroup;
import com.qftx.tsm.sys.dto.SmsTemplateGroupDto;

import java.util.List;
import java.util.Map;


public interface SmsTemplateGroupMapper extends BaseDao<SmsTemplateGroup> {
	 /***************************【慧营销4.0 开始】******************************************/
		List<SmsTemplateGroupDto> getTemplateGroupList(String orgId);
		
		public SmsTemplateGroup getByMapPrimaryKey(Map<String,String>map);
		
		/**
		 * 删除实体对象
		 */
		public void deleteBy(Map<String,String>map);
		
		/** 查询最大顺序值 */
		public Integer getMaxByIndex(Map<String,Object>map);
	 /***************************【慧营销4.0 结束】******************************************/
}
