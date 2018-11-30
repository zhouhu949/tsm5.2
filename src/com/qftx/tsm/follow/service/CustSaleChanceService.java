package com.qftx.tsm.follow.service;

import com.qftx.common.util.StringUtils;
import com.qftx.tsm.follow.bean.CustSaleChanceBean;
import com.qftx.tsm.follow.dao.CustSaleChanceMapper;
import com.qftx.tsm.follow.dto.CustSaleChanceDto;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CustSaleChanceService {
	private static Logger logger = Logger.getLogger(CustSaleChanceService.class);
	@Autowired
	private CustSaleChanceMapper custSaleChanceMapper;
	
	/** 根据资源IDS 查询资源信息 */
	public List<CustSaleChanceDto> getResCustsByCustIds(CustSaleChanceDto dto) {
		return custSaleChanceMapper.findResCustsByCustIds(dto);
	}

	/**根据客户ID查询销售机会*/
	public List<CustSaleChanceBean> getSaleChanceByCustId(String orgId,String custId){
		CustSaleChanceBean bean = new CustSaleChanceBean();
		bean.setOrgId(orgId);
		bean.setCustId(custId);
		bean.setIsFollow(0);
		bean.setIsDel(0);
		bean.setOrderKey("input_date desc");
		return custSaleChanceMapper.findByCondtion(bean);
	}
	
	public List<CustSaleChanceDto> queryCustSaleChanceListPage(CustSaleChanceDto dto) {
		List<CustSaleChanceDto> custs = new ArrayList<>();
		return custSaleChanceMapper.queryCustSaleChanceListPage(dto);
	}

	public void addSaleChance(CustSaleChanceBean custSaleChanceBean) {
		String id = StringUtils.getRandomUUIDStr();
		custSaleChanceBean.setSaleChanceId(id);
		custSaleChanceBean.setInputDate(new Date());
		custSaleChanceBean.setIsDel(0);
		custSaleChanceBean.setIsFollow(0);
		custSaleChanceMapper.insert(custSaleChanceBean);
	}

	public CustSaleChanceBean getByCondition(CustSaleChanceBean entity) {
		return custSaleChanceMapper.getByCondtion(entity);
	}

	public void editSaleChance(CustSaleChanceBean custSaleChanceBean) {
		custSaleChanceBean.setUpdateDate(new Date());
		custSaleChanceMapper.update(custSaleChanceBean);
		
	}

	public void delSaleChance(CustSaleChanceDto dto) {
		dto.setUpdateDate(new Date());
		custSaleChanceMapper.delSaleChance(dto);
	}
	
	public List<CustSaleChanceBean> findListPage(CustSaleChanceBean entity){
		return custSaleChanceMapper.findListPage(entity);
	}
	
	public BigDecimal getTodayWillSignMoney(String orgId,String userAccount){
		return custSaleChanceMapper.findTodayWillSignMoney(orgId, userAccount);
	}
	
	/**
	 *Map<String,Object> keys => ownerAcc;money
	 * */
	public List<Map<String, Object>> getTodayWillSignMoneyByAccs(String orgId,List<String> ownerAccs){
		return custSaleChanceMapper.findTodayWillSignMoneyByAccs(orgId, ownerAccs);
	}

	public CustSaleChanceDto getBySalechanceId(CustSaleChanceBean bean) {
		return custSaleChanceMapper.getBySalechanceId(bean);
	}

	public List<CustSaleChanceBean> getSaleChanceByCustId(CustSaleChanceDto dto) {
		return custSaleChanceMapper.getSaleChanceByCustId(dto);
	}
}
