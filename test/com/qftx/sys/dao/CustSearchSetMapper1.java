package com.qftx.sys.dao;

import java.util.List;
import java.util.Map;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.sys.bean.CustSearchSet;
import com.qftx.tsm.sys.dto.SearchSetSortDto;

/**
 * 查询项设置
 */
public interface CustSearchSetMapper1 extends BaseDao<CustSearchSet> {

	/**
	 * 获得原始定义的数据
	 */
	List<CustSearchSet> findOldCustSearchSetList(Map<String, Object> map);

	List<CustSearchSet> findOldCustSearchSetList1(Map<String, Object> map);

	
	/**
	 * 批量修改
	 * @param list
	 */
	public void batchUpdate(List<CustSearchSet>list);
	
	/**
	 * 查询排序区间的数据
	 */
	List<CustSearchSet> findAllBySortInterval(Map<String, Object> map);
	
	public Integer findSortByDevelopCode(Map<String,Object>map);
}