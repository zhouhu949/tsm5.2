package com.qftx.tsm.sys.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.sys.bean.CustFieldSet;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 客户基本信息字段设置
 */
public interface CustFieldSetMapper extends BaseDao<CustFieldSet> {
	/**
	 * 查询所有带orgid的对象
	 */
	List<CustFieldSet> findAllWithOrgId();

	/**
	 * 获得原始定义的自定义字段数据
	 * 
	 * @作者 徐承恩
	 * @创建时间 2014年4月12日 下午1:38:43
	 * @return
	 */
	List<CustFieldSet> findOldCustFieldSetList(Map<String, Object> map);

	/**
	 * 根据机构查询字段数据
	 *
	 * @param orgId
	 * @return
	 * @create 2015-4-15 下午5:29:15 wangchao
	 * @history
	 */
	List<CustFieldSet> getAllWithOrgId(@Param("orgId") String orgId);

	/** 根据字段code 模糊查询 获取该字段个数 */
	public Integer findCountByFieldCode(Map<String, Object> map);

	/** 查询 排序最大值 */
	public Integer findMaxBySort(Map<String, Object> map);

	/** 查询 字段名称是否存在 */
	public Integer findFieldNameIsExists(Map<String, Object> map);

	/** 根据orgId 删除实体 */
	public void deleteByOrgId(String orgId);

	public String findFieldNameByCode(@Param("orgId") String orgId, @Param("fieldCode") String fieldCode, @Param("state") int state);

	/** 查询排序区间的数据 */
	public List<CustFieldSet> findAllBySortInterval(Map<String,Object>map);
	
	/**  根据 字段类型查询数据 */
	public  List<CustFieldSet>  findAllByDataType(Map<String,Object>map);
	
	/** 根据字段ID，查询排序值*/
	public List<CustFieldSet> findSortsByFieldId(Map<String,Object>map);
	
	/**
	 * 批量修改
	 * @param list
	 */
	public void batchUpdate(List<CustFieldSet>list);
	
}