package com.qftx.tsm.cust.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.cust.bean.ResourceGroupBean;
import com.qftx.tsm.cust.dto.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ComResourceMapper extends BaseDao<ResCustInfoBean> {

	/**
	 * 批量删除公司资源
	 * 
	 * @param map
	 */
	public void removeComResBatch(Map<String, Object> map);

	/**
	 * 批量修改资源到资源组下
	 * 
	 * @param map
	 * @create 2013-11-14 上午10:51:26 wuwei
	 * @history
	 */
	public void updateBatchResToGroup(Map<String, Object> map);

	/**
	 * 查询资源客户信息list
	 * 
	 * @param map
	 * @return
	 * @create 2013-11-11 下午02:35:57 wuwei
	 * @history
	 */
	public List<ResCustInfoBean> findResCustList(Map<String, Object> map);

	/**
	 * 取得一条个人资源客户
	 * 
	 * @param resCustDto
	 * @create 2013-10-30 上午09:21:48 haoqj
	 * @history
	 */
	public ResCustDto findOneMyRes(ResCustDto resCustDto);

	/**
	 * 取得个人资源客户分页列表
	 * 
	 * @param resCustDto
	 * @return
	 * @create 2013-10-30 上午09:22:45 haoqj
	 * @history
	 */
	public List<ResCustDto> findMyResourceListPage(ResCustDto resCustDto);

	/**
	 * 取得服务共享客户分页列表
	 * 
	 * @param resCustDto
	 * @return
	 * @create 2014-11-13 上午09:22:45 yefeng
	 * @history
	 */
	public List<ResCustInfoBean> findSvcShareListPage(ResCustDto resCustDto);

	/**
	 * 取得待回访客户分页列表
	 * 
	 * @param resCustDto
	 * @return
	 * @create 2014-11-14 上午09:22:45 yefeng
	 * @history
	 */
	public List<ResCustInfoBean> findVisitPlanListPage(ResCustDto resCustDto);

	/**
	 * 取得个人当日筛选资源总数
	 * 
	 * @param ownerAcc
	 * @return
	 * @create 2013-10-30 下午07:35:06 haoqj
	 * @history
	 */
	public int findFilterMyResSum(String ownerAcc);

	/**
	 * 取得个人剩余资源数
	 * 
	 * @param ownerAcc
	 * @return
	 * @create 2013-10-30 下午08:29:31 haoqj
	 * @history
	 */
	public int findMyResSum(String ownerAcc);

	/**
	 * 取得单位资源分组
	 * 
	 * @param orgId
	 * @return
	 * @create 2013-11-1 上午09:02:12 haoqj
	 * @history
	 */
	public List<ResourceGroupBean> findOrgResGroup(@Param("orgId") String orgId);

	/**
	 * 更新资源客户信息（状态、所有者或筛选次数--资源与客户通用 ）
	 * 
	 * @param ResCustInfoBean
	 * @create 2013-11-4 下午08:10:04 haoqj
	 * @history
	 */
	public void updateResCustInfo(ResCustInfoBean ResCustInfoBean);

	/**
	 * 取得单位下手机或固话列表
	 * 
	 * @param map
	 * @return
	 * @create 2013-11-6 下午02:49:12 haoqj
	 * @history
	 */
	public List<String> findPhoneList(Map<String, Object> map);

	/**
	 * 取得单位下同号码个数
	 * 
	 * @param orgId
	 * @param phone
	 * @return
	 * @create 2013-11-13 下午08:42:50 haoqj
	 * @history
	 */
	public int findPhoneCount(@Param("orgId") String orgId, @Param("phone") String phone);

	/**
	 * 取得单位下同关键词的个数
	 * 
	 * @param orgId
	 * @param phone
	 * @return
	 */
	public int findKeyWordCount(@Param("orgId") String orgId, @Param("keyWord") String keyWord);

	// /////////////////////////////////////////////////////////////////////////////////////
	// 上部为资源信息操作方法;下面为客户信息操作方法 >>>>>> 有些方法可通用
	// /////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 查询团队客户列表
	 * 
	 * @param entity
	 * @return
	 * @create 2013-10-29 下午04:29:21 xiangli
	 * @history
	 */
	List<CustDto> findTeamCustListPage(CustDto entity);

	/**
	 * 我的客户池
	 * 
	 * @param entity
	 * @return
	 * @create 2014-1-3 下午04:19:08 wuwei
	 * @history
	 */
	List<CustDto> findMyCustListPage(CustDto entity);

	/**
	 * 获取意向客户跟进页面其他待跟进客户列表
	 * 
	 * @param entity
	 * @return
	 * @create 2014-8-12 zwj
	 * @history
	 */
	List<CustDto> findMyCustList(CustDto entity);

	/**
	 * 根据所有者获得签约客户列表(新增交易客户联想查询)
	 * 
	 * @param ResCustInfoBean
	 * @return
	 * @create 2013-10-30 下午03:58:11 徐承恩
	 * @history
	 */
	List<CustInfo> findCustListByOwnerAcc(ResCustInfoBean entity);

	/**
	 * 放弃客户
	 * 
	 * @param status
	 *            状态
	 * @param logAct
	 *            登录人帐号
	 * @param ids
	 *            客户编号集合
	 */
	void updateBatchCust(@Param("status") Short status, @Param("logAct") String logAct, @Param("ids") List<String> ids);

	/**
	 * @param logAct
	 *            登录人帐号
	 * @param ownerAcc
	 *            交接人
	 * @param ids
	 *            客户编号集合
	 */
	void updateMoveCust(@Param("logAct") String logAct, @Param("ownerAcc") String ownerAcc, @Param("ids") List<String> ids);

	/**
	 * 针对签约客户做交接
	 * 
	 * @param logAct
	 * @param ownerAcc
	 * @param ids
	 * @create 2014-9-12 下午01:00:27 wuwei
	 * @history
	 */
	void updateMoveSignCust(@Param("logAct") String logAct, @Param("ownerAcc") String ownerAcc, @Param("ids") List<String> ids);

	/**
	 * 更具客户id获取客户详细信息
	 * 
	 * @param entity
	 * @return
	 */
	CustDto findTeamCustByCustId(CustDto entity);

	/**
	 * 收件箱短信接口调用 通话电话号码和机构id获取客户资源信息
	 * 
	 * @return
	 */
	List<ResCustInfoBean> findInterfaceCust(CustDto entity);

	/**
	 * 根据姓名和联系号码取得客户列表
	 * 
	 * @param map
	 * @return
	 * @create 2013-11-11 下午09:12:11 haoqj
	 * @history
	 */
	List<CustDto> findCustsByNameAndPhone(Map<String, Object> map);

	/**
	 * 取得个人客户数
	 * 
	 * @param ownerAcc
	 * @return
	 */
	Integer findMyCustSum(Map<String, String> map);

	/**
	 * 回收已分配资源
	 * 
	 * @param map
	 * @create 2014-1-16 上午10:49:50 Administrator
	 * @history
	 */
	public void findAssginRecyleResource(Map<String, String> map);

	/**
	 * 取得单位下同合同编号个数
	 * 
	 * @param orgId
	 * @param phone
	 * @return
	 */
	public int findContractCount(@Param("orgId") String orgId, @Param("contractNumber") String contractNumber);

	/**
	 * 修改延后呼叫的原因和待呼叫的时间
	 * 
	 * @param map
	 * @return
	 * @create 2014-3-20 下午01:35:59 Administrator
	 * @history
	 */
	public int modifyDelayCallReason(Map<String, String> map);

	/**
	 * 查询待呼资源列表
	 * 
	 * @param map
	 * @return
	 * @create 2014-3-20 下午04:57:00 wuwei
	 * @history
	 */
	public List<ResCustDto> findWaitCallResourceList(ResCustDto resCustDto);

	/**
	 * 批量修改置顶后的索引
	 * 
	 * @param resCustDto
	 * @create 2014-3-21 下午03:55:22 wuwei
	 * @history
	 */
	public void modifyBatchByCustId(ResCustDto resCustDto);

	/**
	 * 清空下次跟进时间
	 * 
	 * @作者 徐承恩
	 * @创建时间 2014年3月19日 下午2:44:18
	 * @param id
	 */
	public void updateNextFollowDateById(@Param("id") String id);

	/**
	 * 查询所有延后呼叫的列表
	 * 
	 * @return
	 * @create 2014-3-26 上午11:44:13 wuwei
	 * @history
	 */
	public List<ResCustDto> findAllDelayCallCust(@Param("resCustId") String custId);

	/**
	 * 将待呼资源置到最后
	 * 
	 * @param resCustId
	 * @create 2014-3-28 下午12:05:10 wuwei
	 * @history
	 */
	public void modifyWaitCallToEnd(Map<String, Object> map);

	/**
	 * 获取索引值
	 * 
	 * @return
	 * @create 2014-3-28 下午01:31:21 wuwei
	 * @history
	 */
	public long findFilterCount();

	/**
	 * 根据custId获取实体
	 * 
	 * @param map
	 * @return
	 * @create 2014-3-31 下午02:23:30 wuwei
	 * @history
	 */
	public ResCustDto getDelayCallCust(Map<String, Object> map);

	/**
	 * 电话去重验证
	 * 
	 * @作者 徐承恩
	 * @创建时间 2014年4月11日 上午11:32:26
	 * @param map
	 * @return
	 */
	public List<ResCustInfoBean> validatePhone(Map<String, Object> map);

	/**
	 * 客户单位名称去重
	 * 
	 * @作者 徐承恩
	 * @创建时间 2014年4月11日 下午1:18:38
	 * @param map
	 * @return
	 */
	public List<ResCustInfoBean> validateUnit(Map<String, Object> map);

	/**
	 * 客户单位名称模糊检索 -->
	 * 
	 * @param map
	 * @return zwj
	 */
	public List<ResCustInfoBean> validateUnit1(Map<String, Object> map);

	/**
	 * 客户单位主页去重
	 * 
	 * @param map
	 * @return
	 */
	public List<ResCustInfoBean> validateUnitHome(Map<String, Object> map);

	public List<ResCustInfoBean> findUnAssginResList(ResCustDto entity);

	public CustDto getRenDate(@Param("custId") String custId);

	public List<String> findAssginUnGroupResAll(ResCustInfoBean entity);

	public List<String> findAssginGroupedResAll(ResCustInfoBean entity);

	public void megResToGroup(Map<String, Object> map);

	public List<ResCustInfoBean> findUnAssginResIdsListPage(ResCustInfoBean entity);

	/**
	 * 根据资源ID查询进程名称
	 * 
	 * @param resCustId
	 * @return
	 */
	public String findOptionName(@Param("resCustId") String resCustId);

	public ResCustInfoBean findResourceInfo(String resId);

	public void updateAgainTaoCount(Map<String, String> map);

	public int getReportTaoNum(Map<String, String> map);

	public List<ResCustInfoBean> getCustInPool(Map<String, String> map);

	public List<CustDto> findSignCustListPage(CustDto custDto);

	public void modifyResCust(ResCustInfoBean ResCustInfoBean);

	/**
	 * 获取服务专员共享客户ownerAcc账号list
	 * 
	 * @param map
	 * @return
	 */
	public List<String> findShareAccs(Map<String, Object> map);

	/**
	 * 签约客户分配列表
	 * 
	 * @param custDto
	 * @return
	 */
	public List<CustDto> findSignCustForServiceListPage(CustDto custDto);

	/**
	 * 获取签约客户未分配客服专员数
	 * 
	 * @param numType
	 * @param orgId
	 * @return
	 */
	public Integer getHasSetServiceNum(@Param("numType") Integer numType, @Param("orgId") String orgId);

	public ResCustInfoBean findResCustById(String id);

	/**
	 * 批量設置客服專員
	 * 
	 * @param serviceAcc
	 * @param serviceName
	 * @param updateAcc
	 * @param ids
	 */
	public void setServiceBatch(@Param("serviceAcc") String serviceAcc, @Param("serviceName") String serviceName, @Param("updateAcc") String updateAcc,
			@Param("ids") List<String> ids);

	/**
	 * 设置签约客户服务评级
	 * 
	 * @param ids
	 * @param serviceLevelId
	 * @param updateAcc
	 * @create 2015年4月29日 下午1:18:11 lixing
	 * @history
	 */
	public void setServiceLevel(@Param("ids") List<String> ids, @Param("serviceLevelId") String serviceLevelId, @Param("updateAcc") String updateAcc);

	/**
	 * 设置签约客户标签
	 * 
	 * @param ids
	 * @param serviceLabelId
	 * @param serviceLabelName
	 * @param updateAcc
	 * @create 2015年4月29日 下午2:28:57 lixing
	 * @history
	 */
	public void setServiceLabel(@Param("ids") List<String> ids, @Param("serviceLabelId") String serviceLabelId,
			@Param("serviceLabelName") String serviceLabelName, @Param("updateAcc") String updateAcc);

	/**
	 * 清空客服专员
	 * 
	 * @param ids
	 * @param updateAcc
	 * @create 2015年4月29日 上午11:00:13 lixing
	 * @history
	 */
	public void cleanServiceBatch(@Param("ids") List<String> ids, @Param("updateAcc") String updateAcc);

	/**
	 * 批量设置优先级
	 * 
	 * @param updateAcc
	 * @param isPrecedence
	 * @param ids
	 * @create 2015-4-1 下午03:50:14 lixing
	 * @history
	 */
	public void setPrecedenceBatch(@Param("updateAcc") String updateAcc, @Param("isPrecedence") Integer isPrecedence, @Param("ids") List<String> ids);

	/**
	 * 根据id 获取最后销售进程选项ID
	 * 
	 * @param id
	 * @return
	 */
	public String getLastSaleProcessById(String id);

	public ResCustDto getResCustByResId(Map<String, String> map);

	/**
	 * 首次登陆时，查询列表
	 * 
	 * @param map
	 * @return
	 * @create 2015年12月9日 下午1:16:31 wuwei
	 * @history
	 */
	public List<ResCustInfoBean> findResCust4oneTime(Map<String, String> map);

	/**
	 * 获取该资源拨打电话的数量
	 * 
	 * @param resId
	 * @return
	 * @create 2015-3-4 上午11:49:34 wuwei
	 * @history
	 */
	public ResourceCallNumDto getResCallNum(Map<String, String> map);

	/**
	 * 获取淘客户页面延后资源的列表
	 * 
	 * @param resId
	 * @return
	 * @create 2014-8-1 上午10:20:34 WUWEI
	 * @history
	 */
	public List<ResCustInfoBean> findDelayCustList(Map<String, Object> map);

	/**
	 * 5-6分钟内延后呼叫提醒
	 * 
	 * @param map
	 * @return
	 * @create 2015-5-21 上午10:53:12 yefeng
	 * @history
	 */
	public List<ResCustInfoBean> findDelayCallAlert(Map<String, Object> map);

	/**
	 * 根据客户 ID获取客户分组
	 * 
	 * @param custId
	 * @return
	 * @create 2015-3-13 下午02:41:25 lixing
	 * @history
	 */
	public String getCustGroupByCustId(String custId);

	/**
	 * 资源客户呼叫数统计 清除
	 * 
	 * @param custIds
	 * @create 2015-3-16 下午04:47:19 lixing
	 * @history
	 */
	public void cleanCallCustCountByCustId(@Param("custIds") List<String> custIds);

	/**
	 * 我的客户 客户总池
	 * 
	 * @param dto
	 * @return
	 * @create 2015-3-31 下午07:23:17 lixing
	 * @history
	 */
	public List<CustDto> findAllTypeCustListPage(CustDto dto);

	/**
	 * 团队管理 客户总池
	 * 
	 * @param dto
	 * @return
	 * @create 2015年4月27日 下午3:02:39 Administrator
	 * @history
	 */
	public List<CustDto> findAllTypeCustTeamListPage(CustDto dto);

	public String findCurrFilterCountByAcc(Map<String, String> map);

	public String findGroupNameByResId(Map<String, String> paramMap);

	/**
	 * 查询未联系资源数
	 * 
	 * @param ownerAcc
	 * @param orgId
	 * @return
	 * @create 2015年6月23日 上午9:20:14 lixing
	 * @history
	 */
	public Integer findNotFollowResNum(@Param("ownerAcc") String ownerAcc, @Param("orgId") String orgId);

	public ResCustInfoBean findCustByPhone(Map<String, String> map);

	/***********************************************************************************
	 * **************************新增开始*******************************************
	/**
	 * 返回资源数量
	 * 
	 * @param entity
	 * @return
	 * @create 2015年11月17日 下午2:49:28 wuwei
	 * @history
	 */
	public int findresourceNum(Map<String, String> map);

	/**
	 * 根据ids 获取资源
	 * 
	 * @param ids
	 * @return
	 * @create 2015年11月17日 下午4:45:55 wuwei
	 * @history
	 */
	public List<ResCustInfoBean> findResList(@Param("list") List<String> ids, @Param("orgId") String orgId);

	/**
	 * 查询可分配列表
	 * 
	 * @param map
	 * @return
	 * @create 2015年12月2日 下午3:47:24 wuwei
	 * @history
	 */
	public List<ResCustInfoBean> findResListByMap(Map<String, Object> map);

	/**
	 * // * 查询可以回收的资源列表
	 * 
	 * @param map
	 * @return
	 * @create 2015年12月3日 上午11:06:33 wuwei
	 * @history
	 */
	public List<ResCustInfoBean> findRecyleRes(Map<String, Object> map);

	/**
	 * 获取最大可以回收的资源数
	 * 
	 * @param map
	 * @return
	 * @create 2015年12月3日 上午11:15:46 wuwei
	 * @history
	 */
	public int findMaxRecyleRes(Map<String, Object> map);

	/**
	 * 资源回收和分配操作时修改资源
	 * 
	 * @param optMap
	 * @create 2015年12月3日 上午11:28:19 wuwei
	 * @history
	 */
	public void updateOptRes(Map<String, Object> optMap);

	public ResCustDto findResById(Map<String, String> map);

	public ResCustDto findResCallByCustId(Map<String, String> map);
	
	/**
	 * 设置是否重点关注
	 * 
	 * @param map
	 * @create 2015年12月25日 下午4:39:39 WUWEI
	 * @history
	 */
	void updateMajorId(Map<String, String> map);

	/**
	 * 满足筛选条件的总数
	 * 
	 * @param map
	 * @return
	 * @create 2016年1月3日 上午10:59:46 wuwei
	 * @history
	 */
	public int findResListCount(Map<String, Object> map);

	/**
	 * 满足所有条件淘客户资源
	 * 
	 * @param map
	 * @return
	 * @create 2016年1月3日 上午11:21:44 WUWEI
	 * @history
	 */
	public List<TaoResDto> findTaoResList(Map<String, Object> map);

	public List<String> findClearResIds(ResCustDto resCustDto);

	/**
	 * 企业
	 * 
	 * @param map
	 * @return
	 * @create 2016年2月24日 下午3:55:10 Administrator
	 * @history
	 */
	public String findCustIdByPhone(Map<String, String> map);

	/**
	 * 个人的
	 * 
	 * @param map
	 * @return
	 * @create 2016年2月24日 下午3:54:58 Administrator
	 * @history
	 */
	
	public String findPCustIdByPhone(Map<String, String> map);
	
	/**
	 * 共有客户。企业
	 * 
	 * @param map
	 * @return
	 * @create 2017年6月19日 
	 * @history
	 */
	public String findComMonCustIdByPhone(Map<String, String> map);

	/**
	 * 共有客户。个人的
	 * 
	 * @param map
	 * @return
	 * @create 2017年6月19日 
	 * @history
	 */
	
	public String findComPCustIdByPhone(Map<String, String> map);

	public int findCustNum(Map<String, String> map);

	public List<ResCustInfoBean> findDPPhone(Map<String, Object> map);

	public String findOptionName(Map<String, String> map);

	public List<ResCustInfoBean> findDPUnit(Map<String, Object> map);

	public List<ResCustInfoBean> findDPUnitHome(Map<String, Object> map);

	public ResCustInfoBean getByPrimaryKey(Map<String, String> map);

	public Map<String, String> findResCustById(Map<String, String> map);

	public List<ResCustInfoBean> findResByIdsListPage(ResCustInfoDto custInfoDto);

	public void updateResByassignCust(Map<String, Object> map);

	public void updateResByRecyleCust(Map<String, Object> map);

	public List<TaoResDto> findDelayResList(Map<String, Object> map);

	public int findDelayResCountList(Map<String, Object> map);

	public void deleteBatchByOrgId(Map<String,Object>map);
	
	public List<ResCustInfoBean> findResListByIds(@Param("list") List<String> ids, @Param("orgId") String orgId);

	public void changeResImportDept(Map<String, Object> map);

	public List<ResCustDto> findUnAssginResPageList(Map<String, Object> map);
	
	public Integer findUnAssginResCount(ResCustDto entity);

	public List<ResCustDto> findUnAssginResWithPhonePageList(Map<String, Object> map);
	
	public Integer findUnAssginResWithPhoneCount(ResCustDto entity);
	
	public List<ResCustDto> findAssginResPageList(Map<String, Object> map);
	
	public Integer findAssginResCount(ResCustDto entity);

	public List<ResCustDto> findAssginResWithPhonePageList(Map<String, Object> map);
	
	public Integer findAssginResWithPhoneCount(ResCustDto entity);
	
	public List<ResCustDto> findResPageList(Map<String, Object> map);
	
	public Integer findResCount(ResCustDto entity);

	public List<ResCustDto> findResWithPhonePageList(Map<String, Object> map);

	public Integer findResWithPhoneCount(ResCustDto entity);

	public List<String> findUnAssginResIdsWithPhone(ResCustDto entity);

	public List<String> findUnAssginResIds(ResCustDto entity);

	public List<String> findAssginResIdsWithPhone(ResCustDto entity);

	public List<String> findAssginResIds(ResCustDto entity);

	public List<String> findResIdsWithPhone(ResCustDto entity);

	public List<String> findResIds(ResCustDto entity);

	public List<ResCustInfoBean> getListByGroupIds(ResCustInfoBean entity);

	public List<ResCustInfoBean> queryResByNameOrCompany(Map<String, Object> map);
	
	public void updateResByDeptId(Map<String, Object> map);
	

}
