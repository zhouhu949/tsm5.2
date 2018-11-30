package com.qftx.tsm.contract.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.contract.bean.ContractOrderDetailBean;
import com.qftx.tsm.contract.dto.ContractOrderDetailDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ContractOrderDetailMapper extends BaseDao<ContractOrderDetailBean> {
	
	/** 
	 * 订单详情
	 * @param codd
	 * @return 
	 * @create  2015年12月2日 下午2:53:15 lixing
	 * @history  
	 */
	List<ContractOrderDetailDto> findContractOrderDetailListPage(ContractOrderDetailDto codd);
	
	
	/** 
	 * 产品分布图
	 * @param orgId
	 * @param ownerAcc
	 * @param adminAcc
	 * @param groupId
	 * @return 
	 * @create  2016年1月27日 下午6:23:25 lixing
	 * @history  
	 */
	List<Map<String,Object>> findProductChart(@Param("orgId")String orgId,@Param("ownerAcc")String ownerAcc,
												@Param("adminAcc")String adminAcc,@Param("groupId")String groupId);
	
	
	/** 
	 * 签约客户产品分布详情
	 * @param orgId
	 * @param ownerAcc
	 * @param adminAcc
	 * @param groupId
	 * @return 
	 * @create  2016年2月1日 下午1:59:49 lixing
	 * @history  
	 */
	List<Map<String, Object>> findProductLayout(@Param("orgId")String orgId,@Param("adminAcc")String adminAcc,@Param("groupIds")List<String> groupIds);
	
	List<Map<String, Object>> findProductMemberLayout(@Param("orgId")String orgId,@Param("adminAcc")String adminAcc,@Param("groupId")String groupId);
	
}
