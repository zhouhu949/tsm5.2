package com.qftx.tsm.option.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.option.dto.OptionDto;
import com.qftx.tsm.sys.dto.TsmCustReviewDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OptionMapper extends BaseDao<OptionBean>{
	/**
	 * 查询客户放弃原因
	 */
	List<OptionBean> findOptionGiveUpListPage1(OptionBean entity);
	
	List<OptionBean>findOptionGiveUp(Map<String,Object>map);
	
	/**
	 * 根据单位ID查询该单位下选型表是否存在数据<br>
	 * 来判定是否单位已经初始化
	 * @param orgId
	 * @return 
	 * @create  2013-11-1 下午02:20:04 徐承恩
	 * @history
	 */
	Integer findIsUnitInit(@Param("orgId")String orgId);
	
	/**
	 * 获得原始定义的数据选项数据
	 * @return 
	 * @create  2013-11-1 下午03:48:00 徐承恩
	 * @history
	 */
	List<OptionBean> getOldOptionList();
	
	List<OptionBean> getOldOptionList1(String orgId);
	/**
	 * 根据单位ID查询出录音示范分类及数量
	 * @param orgId
	 * @return 
	 * @create  2013-11-7 上午10:28:26 徐承恩
	 */
	List<OptionDto> findRecordReviewTypeByOrgId(TsmCustReviewDto reviewDto);
	
	/**
	 * 根据单位ID和子项code查询出子项列表
	 * @作者 徐承恩
	 * @创建时间 2014年4月1日 上午9:43:44
	 * @param param
	 * @return
	 */
	List<OptionBean> findSubOptionList(Map<String, String> param);
	
	
	/** 
	 * 查询所有带orgid的对象
	 * @return 
	 * @create  2014-4-10 下午01:51:06 wuwei
	 * @history  
	 */
	List<OptionBean>  findAllWithOrgId();
	/**
	 * 查询所有机构id
	 *
	 * @return 
	 * @create  2015-4-15 下午4:57:50 wangchao
	 * @history
	 */
	List<String> getOrgIdList();
	/**
	 * 根据机构查询数据选项
	 * @param orgId
	 * @return 
	 * @create  2015-4-15 下午5:23:21 wangchao
	 * @history
	 */
	List<OptionBean> getAllWithOrgId(@Param("orgId") String orgId);
	
	public void deleteByBatch(Map<String,Object>map);
	
	/** 根据orgId 删除实体 */
	public void deleteByOrgId(String orgId);
	
	public List<OptionBean> findOptionList(String orgId);
	
	//判断排序是否唯一
	public Integer findSortByExists(OptionBean entity);
	
	public OptionBean getByPrimaryKeyAndOrgId(@Param("optionlistId") String optionlistId,@Param("orgId") String orgId);

	/**
	 * 获得【快话】原始定义的数据选项数据
	 * @return 
	 * @create  2013-11-1 下午03:48:00 徐承恩
	 * @history
	 */
	List<OptionBean> getQcOldOptionList();
	
	public List<String>findOptionNames(Map<String,Object>map);
	
    public List<OptionBean> findOptionListByAccount(String orgId);

	List<OptionBean> getAllOption(OptionBean optionBean);
	
	public void updateByIds(Map<String,Object>map);
	public void updateByGroupId(Map<String,Object>map);
}
