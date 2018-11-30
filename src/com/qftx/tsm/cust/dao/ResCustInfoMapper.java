package com.qftx.tsm.cust.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.cust.dto.CustDto;
import com.qftx.tsm.cust.dto.ResCustInfoDto;
import com.qftx.tsm.cust.dto.ResourceGroupDto;
import com.qftx.tsm.imp.dto.ImportRepeatDto;
import com.qftx.tsm.report.dto.CustomReportDoubleDto;
import com.qftx.tsm.report.dto.CustomReportSingleDto;
import com.qftx.tsm.report.dto.ResCustReportDto;
import com.qftx.tsm.report.dto.TeamCustStatusLayoutDto;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;


 /** 
 *
 * @author: Administrator
 * @since: 2015年12月21日  上午11:05:30
 * @history:
 */

 /** 
 *
 * @author: Administrator
 * @since: 2016年2月1日  下午1:26:28
 * @history:
 */
public interface ResCustInfoMapper extends BaseDao<ResCustInfoBean> {
	
	ResCustInfoBean getByPrimaryKey(@Param("orgId")String orgId,@Param("resId")String resId);
	
	/** 
	 * 资源分页列表
	 * @param custInfoDto
	 * @return 
	 * @create  2015年11月13日 下午5:27:17 lixing
	 * @history  
	 */
	List<ResCustInfoDto> findMyResCustListPage(ResCustInfoDto custInfoDto);
	
	List<ResCustInfoDto> findMyResCustPhoneListPage(ResCustInfoDto custInfoDto);
	
	
	
	/** 
	 * 共有客户查询
	 * @param custInfoDto
	 * @return 
	 * @create  2017年6月21日 上午11:13:50 lixing
	 * @history  
	 */
	List<ResCustInfoDto> findMyCommonCustListPage(ResCustInfoDto custInfoDto);
	List<ResCustInfoDto> findMyCommonCustPhoneListPage(ResCustInfoDto custInfoDto);
	
	
	/** 
	 * 意向客户分页列表
	 * @param custInfoDto
	 * @return 
	 * @create  2015年11月13日 下午5:27:33 lixing
	 * @history  
	 */
	List<ResCustInfoDto> findMyCustListPage(ResCustInfoDto custInfoDto);
	List<ResCustInfoDto> findMyCustPhoneListPage(ResCustInfoDto custInfoDto);
	
	/** 
	 * 签约客户分页列表
	 * @param custInfoDto
	 * @return 
	 * @create  2015年11月16日 上午11:41:28 lixing
	 * @history  
	 */
	List<ResCustInfoDto> findSignCustListPage(ResCustInfoDto custInfoDto);
	
	List<ResCustInfoDto> findSignCustPhoneListPage(ResCustInfoDto custInfoDto);
	
	List<ResCustInfoDto> findQupaiSignCustListPage(ResCustInfoDto custInfoDto);
	
	List<ResCustInfoDto> findQupaiSignCustPhoneListPage(ResCustInfoDto custInfoDto);
	
	/** 
	 * 流失客户分页列表
	 * @param custInfoDto
	 * @return 
	 * @create  2015年11月17日 下午6:43:42 lixing
	 * @history  
	 */
	List<ResCustInfoDto> findLosingCustListPage(ResCustInfoDto custInfoDto);
	
	List<ResCustInfoDto> findLosingCustPhoneListPage(ResCustInfoDto custInfoDto);
	
	List<ResCustInfoDto> findManageLosingCustListPage(ResCustInfoDto custInfoDto);
	
	List<ResCustInfoDto> findManageLosingCustPhoneListPage(ResCustInfoDto custInfoDto);
	/** 
	 * 沉默客户分页列表
	 * @param custInfoDto
	 * @return 
	 * @create  2015年11月16日 下午6:07:55 lixing
	 * @history  
	 */
	List<ResCustInfoDto> findSilentCustListPage(ResCustInfoDto custInfoDto);
	
	List<ResCustInfoDto> findSilentCustPhoneListPage(ResCustInfoDto custInfoDto);
	
	/** 
	 * 全部客户分页列表
	 * @param custInfoDto
	 * @return 
	 * @create  2015年11月17日 下午6:42:19 lixing
	 * @history  
	 */
	List<ResCustInfoDto> findAllTypeCustListPage(ResCustInfoDto custInfoDto);
	
	List<ResCustInfoDto> findAllTypeCustPhoneListPage(ResCustInfoDto custInfoDto);
	/** 
	 * 公海客户池 分页列表
	 * @param ids
	 * @param orgId
	 * @return
	 * @create  2015年11月17日 下午7:36:11 lixing
	 * @history  
	 */
	List<ResCustInfoDto> findCustPoolList(@Param("ids")List<String> ids,@Param("orgId")String orgId);
//	List<ResCustInfoDto> findCustPoolPhoneListPage(ResCustInfoDto custInfoDto);
	/** 
	 * 公海客户池-共享资源 分页列表
	 * @param custInfoDto
	 * @return 
	 * @create  2015年11月17日 下午7:36:11 lixing
	 * @history  
	 */
	List<ResCustInfoDto> findCustPoolResListPage(ResCustInfoDto custInfoDto);
	List<ResCustInfoDto> findCustPoolResPhoneListPage(ResCustInfoDto custInfoDto);
	/** 
	 * 清空公海客户池 查询
	 * @param custInfoDto
	 * @return 
	 * @create  2015年11月18日 上午11:58:31 lixing
	 * @history  
	 */
	List<ResCustInfoDto> findClearPoolListPage(ResCustInfoDto custInfoDto);
	List<ResCustInfoDto> findClearPoolPhoneListPage(ResCustInfoDto custInfoDto);
	/** 
	 * 清空公海客户池 - 共享资源 查询
	 * @param custInfoDto
	 * @return 
	 * @create  2015年11月18日 上午11:58:31 lixing
	 * @history  
	 */
	List<ResCustInfoDto> findClearPoolResListPage(ResCustInfoDto custInfoDto);
	List<ResCustInfoDto> findClearPoolResPhoneListPage(ResCustInfoDto custInfoDto);
	/** 
	 * 设置优先级
	 * @param updateAcc
	 * @param isPrecedence
	 * @param ids 
	 * @create  2015年11月13日 下午7:27:41 lixing
	 * @history  
	 */
	void setPrecedenceBatch(@Param("updateAcc")String updateAcc,@Param("isPrecedence")Integer isPrecedence,@Param("ids")List<String> ids,@Param("orgId")String orgId);
	
	/**
	 * @param logAct
	 *            登录人帐号
	 * @param ownerAcc
	 *            交接人
	 * @param ids
	 *            客户编号集合
	 */
	void updateMoveCust(@Param("logAct") String logAct, @Param("ownerAcc") String ownerAcc, @Param("ids") List<String> ids,@Param("orgId")String orgId);
	
	/** 
	 * 资源客户呼叫数统计 清除
	 * @param custIds 
	 * @create  2015-3-16 下午04:47:19 lixing
	 * @history  
	 */
	public void cleanCallCustCountByCustId(@Param("custIds")List<String> custIds,@Param("orgId")String orgId);
	
	/** 
	 * 针对签约客户做交接
	 * @param logAct
	 * @param ownerAcc
	 * @param ids 
	 * @create  2014-9-12 下午01:00:27 wuwei
	 * @history  
	 */
	void updateMoveSignCust(@Param("logAct") String logAct, @Param("ownerAcc") String ownerAcc, @Param("ids") List<String> ids,@Param("orgId")String orgId);

	 /**
	  * 取得 客户放弃原因 列表
	  * @param custInfoDto
	  * @return
	  * @create  2017年5月31日 上午9:53:27 wfg
	  * @history
	  */
	 List<String> findCustGiveUpReasonList(Map<String, Object> params);

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
	void updateBatchCust(@Param("status") Short status, @Param("logAct") String logAct, @Param("ids") List<String> ids,@Param("operateType")Short operateType,@Param("orgId")String orgId,@Param("reason")String reason);

	
	/** 
	 * 批量修改
	 * @param resCustInfoDto 
	 * @create  2015年11月18日 上午10:04:40 lixing
	 * @history  
	 */
	void updateByIds(ResCustInfoDto resCustInfoDto);
	
	
	/** 
	 * 取得 销售交接 分页 ID
	 * @param custInfoDto
	 * @return 
	 * @create  2015年11月24日 上午9:41:27 lixing
	 * @history  
	 */
	List<ResCustInfoDto> findDeliveryIdsListPage(ResCustInfoDto custInfoDto);
	
	
	/** 
	 * 取得个人剩余资源数
	 * @param ownerAcc
	 * @return 
	 * @create  2015年11月26日 上午9:37:15 lixing
	 * @history  
	 */
	Integer findMyResSum(@Param("ownerAcc") String ownerAcc,@Param("orgId")String orgId);
	
	/** 
	 * 取得个人剩余意向数
	 * @param ownerAcc
	 * @return 
	 * @create  2015年11月26日 上午9:37:15 lixing
	 * @history  
	 */
	Integer findMyCustSum(@Param("ownerAcc") String ownerAcc,@Param("orgId")String orgId);
	
	ResCustInfoDto findTeamCustByCustId(ResCustInfoDto dto);
	
	
	/** 
	 * 修改计划日期
	 * @param planDate
	 * @param orgId
	 * @param custId 
	 * @create  2015年12月9日 下午1:54:05 lixing
	 * @history  
	 */
	void updatePlanDate(@Param("planDate")Date planDate,@Param("orgId")String orgId,@Param("custId")String custId);

	/** 
	 * 延后呼叫提醒
	 */
	public List<ResCustInfoBean> findDelayCallAlert(Map<String, Object> map);
	
	
	/** 
	 * 沉默客户筛选
	 * @param dto
	 * @return 
	 * @create  2015年12月16日 下午6:04:52 lixing
	 * @history  
	 */
	public List<ResCustInfoDto> silentCustsSxListPage(ResCustInfoDto dto);
	
	
	/** 
	 * 我的客户 页签 数量
	 * @param dto
	 * @return 
	 * @create  2015年12月21日 上午11:05:34 lixing
	 * @history  
	 */
	public Map<String, Integer> findCustNums(ResCustInfoDto dto);
	
	/** 
	 * 查看资源的优先级
	 * @param 
	 * @return 
	 * @create  2015年12月21日 上午11:05:34 lizhihui
	 * @history  
	 */
	public Integer findIsPrecedence(Map<String, Object> map);
	
	
	/** 
	 * 获得签约数量
	 * @param dto
	 * @return 
	 * @create  2015年12月23日 下午4:32:44 lixing
	 * @history  
	 */
	public Map<String, Integer> getSignNum(ResCustInfoDto dto);
	
	
	/** 
	 * 回退到签约客户之前状态
	 * @param custInfoBean 
	 * @create  2015年12月30日 下午5:19:51 lixing
	 * @history  
	 */
	public void rebackSignBeforeStatus(ResCustInfoBean custInfoBean);
	
	
	/** 
	 * 取回
	 * @param dto 
	 * @create  2016年1月16日 下午1:57:06 lixing
	 * @history  
	 */
	public void modifyQuHui(ResCustInfoDto dto);
	
	public List<String> findQuHuiIds(@Param("orgId")String orgId,@Param("ids")List<String> ids,@Param("isShare")String isShare);
	
	
	
	/** 
	 * 取回意向
	 * @param dto 
	 * @create  2017年11月1日 下午1:57:06 xiaoxh
	 * @history  
	 */
	public void modifyQuHuiYx(ResCustInfoDto dto);
	/**
	 * 收件箱短信接口调用 通话电话号码和机构id获取客户资源信息
	 * 
	 * @create zwj
	 */
	public List<ResCustInfoBean> findInterfaceCust(CustDto entity);
	
	
	/** 
	 * 撞单查询
	 * @param custInfoDto
	 * @return 
	 * @create  2016年1月25日 下午1:36:53 lixing
	 * @history  
	 */
	public List<ResCustInfoDto> hitSigleListPage(ResCustInfoDto custInfoDto);
	
	public List<ResCustInfoDto> hitSiglePhoneListPage(ResCustInfoDto custInfoDto);
	
	public List<ResCustInfoDto> hitSigleList(@Param("orgId")String orgId,@Param("ids")List<String> ids);
	/** 
	 * 销售进程分布图
	 * @param custInfoDto
	 * @return 
	 * @create  2016年1月27日 下午3:33:03 lixing
	 * @history  
	 */
	public List<Map<String, Object>> findSaleProcLayoutChart(ResCustInfoDto custInfoDto);
	
	
	/** 
	 * 客户类型分布图
	 * @param custInfoDto
	 * @return 
	 * @create  2016年1月27日 下午4:57:25 lixing
	 * @history  
	 */
	public List<Map<String, Object>> findCustTypeLayoutChart(ResCustInfoDto custInfoDto);
	
	
	/** 
	 * 产品类型分布图
	 * @param custInfoDto
	 * @return 
	 * @create  2016年1月27日 下午6:52:43 lixing
	 * @history  
	 */
	public List<Map<String, Object>> findProductChart(ResCustInfoDto custInfoDto);
	
	
	/** 
	 * 客户状态分布详情
	 * @param orgId
	 * @param adminAcc
	 * @param groupIds
	 * @return 
	 * @create  2016年1月28日 上午9:47:07 lixing
	 * @history  
	 */
	public List<TeamCustStatusLayoutDto> findTeamCustStatusLayout(@Param("orgId")String orgId,@Param("adminAcc")String adminAcc,@Param("groupIds")List<String> groupIds);
	
	public List<TeamCustStatusLayoutDto> findTeamGroupStatusLayout(@Param("orgId")String orgId,@Param("adminAcc")String adminAcc,@Param("groupIds")List<String> groupIds);
	
	public List<TeamCustStatusLayoutDto> findTeamMemberCustStatusLayout(@Param("orgId")String orgId,@Param("accounts")List<String> accounts,@Param("groupIds")List<String> groupIds);
	
	/** 
	 * 销售进程分布详情
	 * @param orgId
	 * @param adminAcc
	 * @param groupId
	 * @return 
	 * @create  2016年1月28日 下午1:49:17 lixing
	 * @history  
	 */
	public List<ResCustInfoDto> findSaleProcLayout(@Param("orgId")String orgId,@Param("adminAcc")String adminAcc,@Param("groupIds")List<String> groupId);

	public List<ResCustInfoDto> findSaleGroupProcLayout(@Param("orgId")String orgId,@Param("adminAcc")String adminAcc,@Param("groupIds")List<String> groupId);
	
	public List<ResCustInfoDto> findSaleProcMemberLayout(@Param("orgId")String orgId,@Param("accounts")List<String> accounts,@Param("groupIds")List<String> groupIds);
	
	/** 
	 * 客户类型分布详情
	 * @param orgId
	 * @param adminAcc
	 * @param groupIds
	 * @return 
	 * @create  2016年2月1日 上午9:33:58 lixing
	 * @history  
	 */
	public List<ResCustInfoDto> findCustTypeLayout(@Param("orgId")String orgId,@Param("adminAcc")String adminAcc,@Param("groupIds")List<String> groupIds);

	public List<ResCustInfoDto> findCustTypeMemberLayout(@Param("orgId")String orgId,@Param("accounts")List<String> accounts,@Param("groupId")String groupId);
	
	/** 
	 * 产品分布详情
	 * @param orgId
	 * @param adminAcc
	 * @param groupIds
	 * @return 
	 * @create  2016年2月1日 下午1:26:30 lixing
	 * @history  
	 */
	public List<ResCustInfoDto> findCustProductLayout(@Param("orgId")String orgId,@Param("adminAcc")String adminAcc,@Param("groupIds")List<String> groupIds);
	
	public List<ResCustInfoDto> findCustProductMemberLayout(@Param("orgId")String orgId,@Param("groupId")String groupId);
	// 【导入去重】查询号码是否存在
	public Integer getRepeatByPhone(ImportRepeatDto entity);
	
	// 【导入去重】查询单位名称是否存在
	public Integer getRepeatByName(ImportRepeatDto entity);
		
	// 【导入去重】查询单位主页是否存在
	public Integer getRepeatByUnitHome(ImportRepeatDto entity);

	List<ResourceGroupDto> findStaticGroupList(Map<String, Object> map);
	
	public List<ResCustInfoBean> findResByOrgId(@Param("orgId") String orgId,@Param("type") String type);

	// 根据资源ID集合，查询公海客户
	public List<String> findResByResId(Map<String,Object>map);
	
	public void updateCallNum(ResCustInfoBean bean);
	// 根据导入部门id查询部门下资源数量
	Integer findResCountByGroupId(ResCustInfoDto entity);
	
	void updateCustsCommonAcc(@Param("orgId")String orgId,@Param("custIds")List<String> custIds,@Param("commonAcc")String commonAcc);

	void updateCustsNextFollowDate(@Param("orgId")String orgId,@Param("custId")String custId,@Param("nextFollowDate")Date nextFollowDate);
	
	public void update2(ResCustInfoBean custInfoBean);
	
	public Integer findResExpireCount(Map<String, Object> map);
	
	public Integer findCustExpireCount(Map<String, Object> map);
	
	public List<String> findCustExpireIds(Map<String, Object> map);
	
	public List<ResCustInfoBean> findCucstFollowExpire1(Map<String, Object> map);
	
	public List<ResCustInfoBean> findNoChangeResExpire(@Param("day") Integer day, @Param("orgId") String orgId, @Param("ownerAcc") String ownerAcc);

	public List<ResCustInfoBean> findNoLinkResExpire(@Param("day") Integer day, @Param("orgId") String orgId, @Param("ownerAcc") String ownerAcc);
	
	public List<ResCustInfoBean> findSignOrderExpireCusts(@Param("orgId") String orgId, @Param("ids") List<String> ids);

	List<CustomReportDoubleDto> makeCustomReport2(ResCustReportDto dto);

	List<CustomReportSingleDto> makeCustomReport1(ResCustReportDto dto);
	
	
	List<ResCustInfoDto> findAllTypeCustByOrgId(ResCustInfoDto custInfoDto);
	
	public Integer  findAllTypeCustByCount(@Param("orgId") String orgId);
	
	List<ResCustInfoBean> findAllByCustIds(ResCustInfoDto custInfoDto);
	
	public Integer findNoChooseCount(ResCustInfoDto custInfoDto);

	public Integer findChooseCount(@Param("orgId") String orgId);

	 public void updateChooseByOrgId(@Param("orgId") String orgId);
	
	// 表单修改
	public void updateForm(ResCustInfoBean custInfoBean);

	public void deleteIsDel();

	//idList
	public void deleteDetail(Map<String, Object> map);
	//资源明细CustIds
	public List<String> selectDttxDetailIds(@Param("startVal")Integer startVal,@Param("endVal")Integer endVal);

	//主表中存在子表custId,   custIdList
	public List<String> selectDttxCustIds(Map<String, Object> map);
	
	/**
	 * 批量修改淘到客户时间
	 * 
	 * @param amoytocustomerDate
	 * @param orgId
	 * @param ids
	 */
	public void updateAmoytocustomerDate(List<ResCustInfoDto> list);

}
