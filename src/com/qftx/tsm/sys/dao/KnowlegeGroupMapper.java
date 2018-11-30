package com.qftx.tsm.sys.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.sys.bean.KnowlegeGroup;
import com.qftx.tsm.sys.dto.KnowlegeGroupDto;

import java.util.List;
import java.util.Map;

/**
 * 知识库分组表数据访问接口
 * 
 * @author: 徐承恩
 * @since: 2013-10-29 上午10:16:02
 * @history:
 */
public interface KnowlegeGroupMapper extends BaseDao<KnowlegeGroup> {

	/**
	 * 批量伪删除(单条记录也可以删除)
	 * 
	 * @param map
	 *            ids列表,IS_DEL参数
	 */
	public void deleteFakeBatch(Map<String, Object> map);

	/**
	 * 查找知识库组和组下知识
	 * 
	 * @param map
	 * @return
	 * @create 2013-11-5 下午05:25:14 wuwei
	 * @history
	 */
	public List<KnowlegeGroupDto> findKnowlegeGroup(Map<String, String> map);

	
	/**
	 * 查找知识库组和组下知识
	 * 
	 * @param map
	 * @return
	 * @create 2013-11-5 下午05:25:14 wuwei
	 * @history
	 */
	public List<KnowlegeGroupDto> findKnowlegeGroup_new(Map<String, String> map);

	/**
	 * 单位下所有知识數
	 * 
	 * @param map
	 * @return
	 * @create 2013-11-6 下午02:48:56 wuwei
	 * @history
	 */
	public int findAllKnowleges(Map<String, String> map);

	/**
	 * 单位下未分组的知识数
	 * 
	 * @param map
	 * @return
	 * @create 2013-11-6 下午02:49:53 wuwei
	 * @history
	 */
	public int findKnowlegeUnGroup(Map<String, String> map);

	/**
	 * 查找知识库
	 * 
	 * @param map
	 * @return
	 * @create 2013-11-6 下午05:10:27 wuwei
	 * @history
	 */
	public KnowlegeGroup findKnowlegeByGroupId(Map<String, String> map);

	/**
	 * 伪删除
	 * 
	 * @param map
	 * @create 2013-11-6 下午05:57:04 wuwei
	 * @history
	 */
	public void updateKnowlegeGroup(Map<String, String> map);

	
	/** 
	 *  根据知识库id伪删除知识
	 * @param map 
	 * @create  2013-11-20 上午09:34:57 wuwei
	 * @history  
	 */
	public void updateKnowleges(Map<String, String> map);

	/**
	 * 
	 * @param knowlegeGroup
	 * @return
	 * @create 2013-11-7 下午07:51:08 wuwei
	 * @history
	 */
	public List<KnowlegeGroup> findGrpListByGrpName(KnowlegeGroup knowlegeGroup);
	
	/**
	 * 跟新排序码groupsort
	 * @param knowlegeGroup
	 * @return
	 * @create 2013-11-7 下午07:51:08 xiaoxh
	 * @history
	 */
	public void update_new (KnowlegeGroup knowlegeGroup);
}