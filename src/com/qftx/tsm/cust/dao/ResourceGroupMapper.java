package com.qftx.tsm.cust.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.cust.bean.ResourceGroupBean;
import com.qftx.tsm.cust.dto.ResourceGroupDto;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ResourceGroupMapper extends BaseDao<ResourceGroupBean> {
	/**
	 * 获取分组以及组下资源数量信息
	 * 
	 * @param orgid
	 * @return
	 */
	public List<ResourceGroupDto> getResSumList(Map<String, Object> map);

	/**
	 * 获取所有成员数
	 * 
	 * @param orgid
	 * @return
	 */
	public int getComResSumByOrgID(Map<String, Object> map);

	/**
	 * 获取未分组的成员数
	 * 
	 * @param orgid
	 * @return
	 */
	public int getComResNOTGroupSumByOrgID(Map<String, String> map);

	/**
	 * 根据分组名称模糊查询资源分组
	 * 
	 * @param custGroup
	 * @return
	 */
	public List<ResourceGroupBean> findGrpListByGrpName(ResourceGroupBean resGroup);

	/**
	 * 根据资源id删除资源
	 * 
	 * @param resGroupId
	 */
	public void updateResGroupByResId(Map<String, Object> map);

	/**
	 * 根据资源ID修改资源为未分组资源
	 * 
	 * @param resGroupId
	 */
	public void updateResourceByGroupId(Map<String, Object> map);

	/**
	 * 获取资源组
	 * 
	 * @param orgId
	 * @return
	 * @create 2013-11-14 上午10:24:29 wuwei
	 * @history
	 */
	public List<ResourceGroupBean> findResGroup(String orgId);
	
	public List<ResourceGroupBean> findResGroupForShare(String orgId);
	/**
	 * 获得我的资源分组以及数量信息
	 * 
	 * @作者 徐承恩
	 * @创建时间 2014年3月20日 下午5:10:40
	 * @param map
	 * @return
	 */
	List<ResourceGroupDto> getMyResGroupAndNum(Map<String, String> map);

	/**
	 * 获得我的未分组资源和所有资源总数
	 * 
	 * @作者 徐承恩
	 * @创建时间 2014年3月20日 下午5:36:49
	 * @param param
	 * @return
	 */
	HashMap<String, Integer> getMyResNums(@Param("account") String param);

	/**
	 * 是否存在未分组
	 * 
	 * @param orgId
	 * @return
	 * @create 2016年1月19日 上午11:11:10 wuwei
	 * @history
	 */
	public String findUnGroup(String orgId);

	public List<ResourceGroupBean> findShareGroupList(Map<String, String> map);

	public ResourceGroupBean getByPrimaryKey(Map<String, String> map);

	public List<ResourceGroupDto> findStaticGroupList(Map<String, Object> map);

	public void deleteByResGroupByResId(Map<String,Object>map);

	public List<ResourceGroupBean> findByGroupIds(Map<String, Object> map);

	public void initResGroup(ResourceGroupBean bean);

	public List<ResourceGroupBean> findResClassList(String orgId);
	
	public List<ResourceGroupBean> findResClassList2(Map<String,Object> map);
	
	public List<ResourceGroupBean> findGroupList(@Param("orgId")String orgId,@Param("pId")String pId);
	
	public List<ResourceGroupBean> getByIds(@Param("orgId")String orgId,@Param("groupIds")List<String> groupIds);

	public ResourceGroupBean findUnClassById(ResourceGroupBean bean);

	public String findUnClass(String orgId);

	public void updateGroupPid(Map<String, Object> map);
	
	public List<String> findAllGroup(String orgId);

	public List<ResourceGroupBean> findOnlyResGroup(String orgId);
	
}