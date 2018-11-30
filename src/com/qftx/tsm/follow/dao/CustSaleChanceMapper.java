package com.qftx.tsm.follow.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.follow.bean.CustSaleChanceBean;
import com.qftx.tsm.follow.dto.CustSaleChanceDto;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CustSaleChanceMapper  extends BaseDao<CustSaleChanceBean>{
	/** 根据资源IDS 查询资源信息 */
	public List<CustSaleChanceDto> findResCustsByCustIds(CustSaleChanceDto dto);

	public List<CustSaleChanceDto> queryCustSaleChanceListPage(CustSaleChanceDto dto);

	public void delSaleChance(CustSaleChanceDto dto);
	
	public BigDecimal findTodayWillSignMoney(@Param("orgId")String orgId,@Param("userAccount")String userAccount);
	
	public List<Map<String, Object>> findTodayWillSignMoneyByAccs(@Param("orgId")String orgId,@Param("ownerAccs")List<String> ownerAccs);

	public CustSaleChanceDto getBySalechanceId(CustSaleChanceBean bean);

	public List<CustSaleChanceBean> getSaleChanceByCustId(CustSaleChanceDto dto);
	
	public void updateIsDelByCustIds(@Param("orgId")String orgId,@Param("custIds")List<String> custIds);
}