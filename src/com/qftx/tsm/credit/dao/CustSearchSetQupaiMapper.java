package com.qftx.tsm.credit.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.sys.bean.CustSearchSet;
import com.qftx.tsm.sys.dto.SearchSetSortDto;

import java.util.List;
import java.util.Map;

/**
 * 查询项设置
 */
public interface CustSearchSetQupaiMapper extends BaseDao<CustSearchSet> {
	/**
	 * 查询所有带orgid的对象
	 */
	List<CustSearchSet> findAllWithOrgId();

	/**
	 * 获得原始定义的数据
	 */
	List<CustSearchSet> findOldCustSearchSetList(Map<String, Object> map);

	/**
	 * 查询最大排序值按模块分组
	 */
	List<SearchSetSortDto>findMaxSortByModuleGroup(Map<String,Object>map);
	
	/**
	 * 查询 非文本项 查询项COUNT
	 */
	List<SearchSetSortDto>findQueryCountByModuleGroup(Map<String,Object>map);
	
	/**
	 * 批量修改
	 * @param list
	 */
	public void batchUpdate(List<CustSearchSet>list);
	
	/**
	 * 查询排序区间的数据
	 */
	List<CustSearchSet> findAllBySortInterval(Map<String, Object> map);
	
	public void deleteByDevelopCode(Map<String,Object>map);
	
	/** 根据orgId 删除实体 */
	public void deleteByOrgId(String orgId);
	
	public List<CustSearchSet> findSjdz();
}