package com.qftx.tsm.sys.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.sys.bean.HookSmsConfig;
import com.qftx.tsm.sys.dto.HookSmsConfigDto;

import java.util.List;
import java.util.Map;


/**
 *  挂机短信 发送规则
 */
public interface HookSmsConfigMapper extends BaseDao<HookSmsConfig>{
	
	List<HookSmsConfigDto>findHookTempListPage(HookSmsConfigDto entity);
	
	/** 查询 是否存在同一 启用模板 */
	public Integer findExistByConfig(Map<String,Object>map);
	
	/** 查询 挂机短信模板 */
	public List<HookSmsConfig>getHookSmsTemp(Map<String,Object>map);
	
	public Integer findExistByOrgId(Map<String, Object> map); 
}