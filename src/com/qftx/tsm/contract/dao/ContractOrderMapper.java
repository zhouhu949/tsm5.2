package com.qftx.tsm.contract.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.contract.bean.ContractOrderBean;
import com.qftx.tsm.contract.dto.ContractOrderDto;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;


 /** 
 *
 * @author: Administrator
 * @since: 2016年5月9日  上午11:38:16
 * @history:
 */
public interface ContractOrderMapper extends BaseDao<ContractOrderBean> {
	
	List<ContractOrderBean> findViewContractOrders(@Param("orgId")String orgId,@Param("caId")String caId);
	
	/** 
	 * 订单维护
	 * @param cod
	 * @return 
	 * @create  2015年12月2日 下午2:00:53 lixing
	 * @history  
	 */
	List<ContractOrderDto> findContractOrderListPage(ContractOrderDto cod);
	
	List<ContractOrderDto> findCardListPage(ContractOrderDto cod);
	
	/** 
	 * 订单维护
	 * @param cod
	 * @return 
	 * @create  2015年12月2日 下午2:00:53 lixing
	 * @history  
	 */
	List<ContractOrderDto> findContractOrderList(ContractOrderDto cod);
	
	/** 
	 * 查询订单统计	
	 * @param cod
	 * @return 
	 * @create  2016年5月9日 上午11:40:11 lixing
	 * @history  
	 */
	List<ContractOrderDto> findContractOrderCountData(ContractOrderDto cod);
	
	/** 
	 * 批量审核
	 * @param cod 
	 * @create  2015年12月2日 下午3:03:30 lixing
	 * @history  
	 */
	void checkContractOrder(ContractOrderDto cod);
	
	List<String> findCheckIds(ContractOrderDto cod);
	
	/** 
	 * 根据订单ID查询订单
	 * @param orderId
	 * @param orgId
	 * @return 
	 * @create  2016年5月9日 上午11:38:52 lixing
	 * @history  
	 */
	ContractOrderBean getOrderInfoByIdAndOrg(@Param("orderId")String orderId,@Param("orgId")String orgId);
	
	
	/** 
	 * 根据订单ID批量查询订单
	 * @param ids
	 * @param orgId
	 * @return 
	 * @create  2016年5月9日 上午11:39:25 lixing
	 * @history  
	 */
	List<ContractOrderDto> getOrderInfoByIdSAndOrg(@Param("ids")List<String> ids,@Param("orgId")String orgId);
	
	
	/** 
	 * 订单上报
	 * @param orderId
	 * @param orgId 
	 * @create  2016年5月9日 上午11:39:14 lixing
	 * @history  
	 */
	void reportOrder(@Param("orderId")String orderId,@Param("orgId")String orgId);
	
	/** 
	 * 订单撤回
	 * @param orderId
	 * @param orgId 
	 * @create  2016年5月9日 上午11:39:14 lixing
	 * @history  
	 */
	void rebackOrder(@Param("orderId")String orderId,@Param("orgId")String orgId);
	
	
	/** 
	 * 订单作废
	 * @param orderId
	 * @param orgId 
	 * @create  2016年12月14日 下午3:00:50 lixing
	 * @history  
	 */
	void cancelledOrder(@Param("orderId")String orderId,@Param("orgId")String orgId);
	
	void cancelledOrderBatch(ContractOrderDto cod);
	/** 
	 * 批量删除订单
	 * @param cod 
	 * @create  2016年5月9日 上午11:37:47 lixing
	 * @history  
	 */
	void deleteOrderBatch(ContractOrderDto cod);
	
	BigDecimal findOrderMoney(ContractOrderDto cod);
	
	List<ContractOrderDto> findContractOrders(ContractOrderDto cod);
	/** 
	 * 批量删除订单详情
	 * @param cod 
	 * @create  2016年5月9日 上午11:38:18 lixing
	 * @history  
	 */
	void deleteOrderDetailBatch(ContractOrderDto cod);
	
	ContractOrderDto findTodayReport(@Param("orgId")String orgId,@Param("userId")String userId);
	
	List<ContractOrderDto> findTeamTodayReport(@Param("orgId")String orgId,@Param("userId")String userId,@Param("groupIds")List<String> groupIds);

	List<ContractOrderDto> findTeamTodayReportListPage(ContractOrderDto contractOrderDto);
	
	List<ContractOrderBean> findTotalMoneyByCustIds(@Param("orgId")String orgId,@Param("custIds")List<String> custIds);

	List<String> findOrderExpireCustIds(@Param("orgId")String orgId,@Param("expireDate")String expireDate,@Param("ownerAcc")String ownerAcc);
}
