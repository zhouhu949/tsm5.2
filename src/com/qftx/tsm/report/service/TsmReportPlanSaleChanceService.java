package com.qftx.tsm.report.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qftx.base.util.GuidUtil;
import com.qftx.tsm.report.bean.TsmReportCallInfoBean;
import com.qftx.tsm.report.bean.TsmReportPlanSaleChanceBean;
import com.qftx.tsm.report.dao.TsmReportCallInfoMapper;
import com.qftx.tsm.report.dao.TsmReportPlanSaleChanceMapper;
import com.qftx.tsm.report.dto.TsmReportCallInfoDTO;

@Service
public class TsmReportPlanSaleChanceService {
	@Autowired TsmReportPlanSaleChanceMapper tsmReportPlanSaleChanceMapper;
	@Autowired TsmReportCallInfoMapper tsmReportCallInfoMapper;
	
	
	/* 查询*/
	public List<TsmReportPlanSaleChanceBean> find(String orgId){
		TsmReportPlanSaleChanceBean entity= new TsmReportPlanSaleChanceBean();
		entity.setOrgId(orgId);
		
		return tsmReportPlanSaleChanceMapper.findByCondtion(entity);
	}
	
	/* 查询*/
	public void processByDate(List<TsmReportCallInfoBean> list,TsmReportCallInfoDTO entity){
		List<TsmReportCallInfoBean> sales = tsmReportCallInfoMapper.findMonthSaleChance(entity);
		HashMap<String, TsmReportCallInfoBean> salesmap = new HashMap<>();
		for (TsmReportCallInfoBean tsmReportCallInfoBean : sales) {
			salesmap.put(tsmReportCallInfoBean.getDateFmt(), tsmReportCallInfoBean);
		}
		
		for (TsmReportCallInfoBean tsmReportCallInfoBean : list) {
			if(salesmap.containsKey(tsmReportCallInfoBean.getDateFmt())){
				TsmReportCallInfoBean sale = salesmap.get(tsmReportCallInfoBean.getDateFmt());
				tsmReportCallInfoBean.setSaleChanceMoney(sale.getSaleChanceMoney());
			}
		}
	}
	
	/* 查询*/
	public void processByGroup(List<TsmReportCallInfoBean> list,TsmReportCallInfoDTO entity){
		List<TsmReportCallInfoBean> sales = tsmReportCallInfoMapper.findMonthSaleChance(entity);
		HashMap<String, TsmReportCallInfoBean> salesmap = new HashMap<>();
		for (TsmReportCallInfoBean tsmReportCallInfoBean : sales) {
			salesmap.put(tsmReportCallInfoBean.getGroupId(), tsmReportCallInfoBean);
		}
		
		for (TsmReportCallInfoBean tsmReportCallInfoBean : list) {
			if(salesmap.containsKey(tsmReportCallInfoBean.getGroupId())){
				TsmReportCallInfoBean sale = salesmap.get(tsmReportCallInfoBean.getGroupId());
				tsmReportCallInfoBean.setSaleChanceMoney(sale.getSaleChanceMoney());
			}
		}
	}
	
	/* 查询*/
	public void processByAccount(List<TsmReportCallInfoBean> list,TsmReportCallInfoDTO entity){
		List<TsmReportCallInfoBean> sales = tsmReportCallInfoMapper.findMonthSaleChance(entity);
		HashMap<String, TsmReportCallInfoBean> salesmap = new HashMap<>();
		for (TsmReportCallInfoBean tsmReportCallInfoBean : sales) {
			salesmap.put(tsmReportCallInfoBean.getAccount(), tsmReportCallInfoBean);
		}
		
		for (TsmReportCallInfoBean tsmReportCallInfoBean : list) {
			if(salesmap.containsKey(tsmReportCallInfoBean.getAccount())){
				TsmReportCallInfoBean sale = salesmap.get(tsmReportCallInfoBean.getAccount());
				tsmReportCallInfoBean.setSaleChanceMoney(sale.getSaleChanceMoney());
			}
		}
	}
	
	/* 分页查询*/
	public List<TsmReportPlanSaleChanceBean> findListPage(String orgId,TsmReportPlanSaleChanceBean entity){
		entity.setOrgId(orgId);
		
		return tsmReportPlanSaleChanceMapper.findListPage(entity);
	}
	/* 插入*/
	public TsmReportPlanSaleChanceBean insert(String orgId){
		TsmReportPlanSaleChanceBean bean = new TsmReportPlanSaleChanceBean();
		String id=GuidUtil.getGuid();
		bean.setId(id);
		bean.setOrgId(orgId);
		
		bean.setSaleChanceMoney(0d);
		
		tsmReportPlanSaleChanceMapper.insert(bean);
		return bean;
	}
}
