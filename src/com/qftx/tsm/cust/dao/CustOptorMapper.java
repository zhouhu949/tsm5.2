package com.qftx.tsm.cust.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.cust.bean.CustOptorBean;
import com.qftx.tsm.cust.dto.CustOptorDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustOptorMapper extends BaseDao<CustOptorBean> {
	
	/** 
	 * 客户交接记录
	 * @param dto
	 * @return 
	 * @create  2015年11月30日 下午3:54:01 lixing
	 * @history  
	 */
	List<CustOptorDto> findCustInOutListPage(CustOptorDto dto);
	
	
	/** 
	 * 今日转入数量
	 * @param transferAcc
	 * @return 
	 * @create  2015年11月30日 下午4:51:38 lixing
	 * @history  
	 */
	Integer findCustIncomeNum(String transferAcc);
	
	
	/** 
	 * 所有转出数量
	 * @param ownerAcc
	 * @return 
	 * @create  2015年11月30日 下午4:52:47 Administrator
	 * @history  
	 */
	Integer findCustOutNum(String ownerAcc);
	
	void deleteCustOptors(@Param("orgId")String orgId,@Param("custIds")List<String> custIds);
}
