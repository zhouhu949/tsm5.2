package com.qftx.tsm.option.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

 /** 
 * 数据字典表数据访问接口
 */
public interface DataDictionaryMapper extends BaseDao<DataDictionaryBean> {
	/**
	 * 批量伪删除(单条记录也可以删除)
	 * @param map ids列表,IS_DEL参数
	 */
	public void deleteFakeBatch(Map<String, Object> map);
	
	/**
	 * 获得原始定义的字典数据数据
	 * @return 
	 * @create  2013-11-1 下午03:55:19 徐承恩
	 * @history
	 */
	List<DataDictionaryBean> getOldDataList();
	

	/**
	 * 根据单位ID以及CODE查询值
	 * MAP:PUT->1.orgId 2.code
	 * @param param
	 * @return
	 */
	Integer findDataValueByOrgIdAndCode(Map<String, String> param);
	
	/** 根据单位ID以及CODE开启开关
	 * MAP:PUT->1.orgId 2.code
	 * @param param
	 * @return
	 */
	Integer findIsOpenByOrgIdAndCode(Map<String, String> param);
	
	public DataDictionaryBean  findDataDictionaryBean(Map<String, String> map);
	/** 
	 * 查询所有有机构id的字典
	 * @return 
	 * @create  2014-4-10 下午01:37:00 wuwei
	 * @history  
	 */
	List<DataDictionaryBean> findAllWithOrgId();
	
	/**
	 * 根据机构查询数据字典
	 * @param orgId
	 * @return 
	 * @create  2015-4-15 下午5:15:34 wangchao
	 * @history
	 */
	public List<DataDictionaryBean> getAllWithOrgId(@Param("orgId") String orgId);

	/**
	 * 获取状态为开启的消息发送参数
	 */
	public List<DataDictionaryBean> findOpenMsgArgs(Map<String, String> param);
	
	/** 根据orgId 删除实体 */
	public void deleteByOrgId(String orgId);
	
	/***
	 * 获得【快话】原始定义的字典数据数据
	 * @return 
	 * @create  2016-6-23 下午4:23:05 zwj
	 * @history  4.x
	 */
	List<DataDictionaryBean> getQcOldDataList();
	
	public void updateByCode(DataDictionaryBean bean);
	
	public void batchUpdate(List<DataDictionaryBean>list);
}