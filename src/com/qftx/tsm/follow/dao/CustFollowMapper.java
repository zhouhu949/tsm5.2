package com.qftx.tsm.follow.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.follow.bean.CustFollowBean;
import com.qftx.tsm.follow.dto.CustFollowDto;
import com.qftx.tsm.follow.dto.RestDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CustFollowMapper extends BaseDao<CustFollowBean> {
	
	/** 
	 * 根据客户ID 查询客户跟进记录
	 * @param custId
	 * @return 
	 * @create  2015年11月25日 上午10:46:54 lixing
	 * @history  
	 */
	List<CustFollowDto> queryCustFollowByCustId(Map<String,Object>map);
	
	
	/** 
	 * 查询客户跟进记录 分页
	 * @param dto
	 * @return 
	 * @create  2015年11月30日 上午10:27:45 lixing
	 * @history  
	 */
	List<CustFollowDto> queryCustFollowsListPage(CustFollowDto dto);
	
	/** 根据资源IDS 查询资源信息 */
	public List<CustFollowDto> findResCustsByCustIds(CustFollowDto dto);
	
	/**分页查询 跟进警报  */
	List<CustFollowDto> findTsmCustFollowAlarmPageList(CustFollowDto dto);
	List<CustFollowDto> findTsmCustFollowAlarmPhonePageList(CustFollowDto dto);
	
	/** 跟进警报 条数*/
	public Integer findTsmCustFollowAlarmCount(CustFollowDto dto);
	public Integer findTsmCustFollowAlarmPhoneCount(CustFollowDto dto);
	
	/**分页查询 客户跟进  */
	List<CustFollowDto> findTsmCustFollowListPage(CustFollowDto dto);
	
	/**分页查询 客户跟进 根据号码查询  */
	List<CustFollowDto> findTsmCustFollowPhoneListPage(CustFollowDto dto);
	
	/**分页查询 销售进程  */
	List<CustFollowDto> findTeamSaleAndFollowListPage(CustFollowDto dto);
	
	/** 客户跟进详细页面 右侧其他待跟进列表 今日计划签约客户 */
	List<RestDto>findFollowPlanSign(RestDto restDto);
	
	/**客户跟进详细页面 右侧其他待跟进列表 今日计划意向客户 */
	List<RestDto>findFollowPlanWill(RestDto restDto);
	
	/** 客户跟进详细页面 右侧其他待跟进列表 待跟进客户 */
	List<RestDto>findFollowRightViewListPage(RestDto restDto);
	
	/**客户跟进详细页面 右侧其他待跟进列表 下次跟进时间等于当日的数据 */
	List<RestDto>findDayFollowRightViewListPage(RestDto restDto);
	
	/** 客户跟进详细页面 右侧其他待跟进列表 待跟进警报 */
	List<RestDto>findFollowRightAralmView(RestDto restDto);
	
	/** 客户跟进详细页面 右侧其他待跟进列表 签约未跟进客户 */
	List<RestDto>findSignFollowRightView(RestDto restDto);
	/**
	 * 查询待跟进记录
	 */
	public List<CustFollowDto> findNextFollowList(Map<String, Object> map);
	
	/** 查询最近一条跟进信息 */
	public CustFollowDto queryLastCustFollowByCustId(Map<String, Object> map);
	
	/** 
	 * 删除历史联系信息
	 * @param orgId
	 * @param custIds 
	 * @create  2015年12月21日 下午6:58:20 lixing
	 * @history  
	 */
	public void deleteCustFollows(@Param("orgId")String orgId,@Param("custIds")List<String> custIds);
	
	public CustFollowBean findLabelByCustId(Map<String, String> map);
	
	public String findSalesProcessByCustId(@Param("orgId")String orgId,@Param("custId")String custId);
	
	public Map<String,String> findLastConcatDay(Map<String, String> map);
	/**
	 *  弹屏行动记录  
	 *
	 * @param custFollowDto
	 * @return 
	 * @create  2016年2月22日 上午9:56:54 Administrator
	 * @history
	 */
	public List<CustFollowDto> findCustFollows4TPListPage(CustFollowDto custFollowDto);
	
	/** 根据计划id获取签约客户计划 资源集合 */
	public List<String>queryCustIdBySignPlanIds(Map<String,Object>map);
	/** 根据计划id获取意向客户计划 资源集合 */
	public List<String>queryCustIdByWillPlanIds(Map<String,Object>map);
	
	public String getSignBeforeSaleProcessId(@Param("orgId")String orgId,@Param("custId")String custId,@Param("inputDate")String inputDate);

	/*******************************客户跟进详情右侧列表********************************/
	//	客户跟进列表点击 跟进操作
	public List<RestDto>findFollowRightViewByFollowRecordListPage(RestDto restDto);
	
	//	我的客户--意向客户 点击 跟进操作
	public List<RestDto>findCustFollowRightByMyCustListPage(RestDto restDto);
	
	//	我的客户--签约客户 点击 跟进操作
	public List<RestDto>findCustFollowRightByMySignListPage(RestDto restDto);
	
	//	我的客户--沉默客户 点击 跟进操作
	public List<RestDto>findCustFollowRightByMySilentListPage(RestDto restDto);
	
	//	我的客户--流失客户 点击 跟进操作
	public List<RestDto>findCustFollowRightByMyLosListPage(RestDto restDto);
	
	//	我的客户--全部客户 点击 跟进操作
	public List<RestDto>findCustFollowRightByMyAllListPage(RestDto restDto);
	
	public List<CustFollowBean>testTest(Map<String,Object>map);

	public List<CustFollowDto> queryCustFollowsPhoneListPage(CustFollowDto dto);


	public List<RestDto> findCustFollowRightByMyComListPage(RestDto restDto);


	public List<RestDto> findCustFollowRightByMyComWithPhoneListPage(RestDto restDto);


	public List<CustFollowDto> findTeamSaleAndFollowWithPhoneListPage(CustFollowDto dto);


	public List<RestDto> findCustFollowRightByMyCustWithPhoneListPage(RestDto restDto);


	public List<RestDto> findCustFollowRightByMySignWithPhoneListPage(RestDto restDto);


	public List<RestDto> findCustFollowRightByMyAllWithPhoneListPage(RestDto restDto);
	
}
