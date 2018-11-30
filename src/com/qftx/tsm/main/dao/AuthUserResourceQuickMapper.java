package com.qftx.tsm.main.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.main.bean.AuthUserResourceQuickBean;

import java.util.List;
import java.util.Map;

public interface AuthUserResourceQuickMapper extends BaseDao<AuthUserResourceQuickBean> {
	void deleteByUserId(String userId);
	
	/** 查询某一单位下，所有资源 根据资源ID分组 */
	public List<AuthUserResourceQuickBean>findResourceByList(String orgId);
	
	/** 根据单位ID，用户ID 批量删除  */
	public void deleteByUserIdBatch(Map<String,Object>map);
	
	/** 根据单位ID，用户ID 删除  */
	public void deleteByUserIdOrgId(Map<String,Object>map);
	
	/** 根据单位ID，资源ID 批量删除  */
	void deleteByBatch(Map<String,Object>map);
	
	public void deleteByOrgId(String orgId);
}
