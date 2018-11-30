package com.qftx.tsm.cust.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.cust.dto.ResourceGroupDto;
import com.qftx.tsm.cust.dto.StaffDto;

import java.util.List;
import java.util.Map;

public interface StaffResourceMgMapper extends BaseDao<StaffDto> {
	/**
	 * 获取团队成员数
	 * 
	 * @param map
	 * @return
	 */
	public List<StaffDto> findTeamMemberSum(Map<String, String> map);

	/**
	 * 获取团队成员所有信息
	 * 
	 * @param staffDto
	 * @return
	 */
	public List<StaffDto> findStaffListPage(StaffDto staffDto);
	
	/** 
	 * 获取所有单位成员下未分组的信息
	 * @param staffDto
	 * @return 
	 * @create  2013-11-19 下午05:30:40 wuwei
	 * @history  
	 */
	public List<StaffDto> findStaffUnGroupListPage(StaffDto staffDto);

	/**
	 * 获取资源分组
	 * 
	 * @param map
	 * @return
	 */
	public List<ResourceGroupDto> findResGroupList(Map<String, String> map);

	/**
	 * 当前可分配的资源数
	 * 
	 * @param map
	 * @return
	 */
	public int findResourcemaxSum(Map<String, Object> map);

	/**
	 * 分配资源
	 * 
	 * @param map
	 * @return
	 * @create 2013-10-29 上午10:33:15 wuwei
	 * @history
	 */
	public Map<String, String> findAssginResource(Map<String, String> map);

	/**
	 * 获取多个用户下拥有资源数量最小的资源数
	 * 
	 * @param map
	 * @return
	 * @create 2013-10-30 下午01:30:30 wuwei
	 * @history
	 */
	public int findRecyleMinSum(Map<String, Object> map);

	/**
	 * 资源回收
	 * 
	 * @param map
	 * @return
	 * @create 2013-10-30 下午08:25:00 wuwei
	 * @history
	 */
	public Map<String, String> findRecycleResource(Map<String, String> map);

	/**
	 * 资源回收分配列表
	 * 
	 * @param map
	 * @return
	 * @create 2013-10-31 下午01:26:21 wuwei
	 * @history
	 */
	public List<StaffDto> getResAssinRecycleListPage(StaffDto staffDto);
	
	/**
	 * limin  增加日志操作实例
	 *
	 * @param delmap 
	 * @create  2014-5-22 下午03:53:59 haoqj
	 * @history
	 */
	public void insertOption(StaffDto staffDto);
}
