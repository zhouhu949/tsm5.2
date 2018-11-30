package com.qftx.tsm.plan.user.month.bean;

import com.qftx.common.cached.CachedService;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PointCache {
	private int singlePoint = 100;
	private int planPoint = 500;
	private int plan3MonthPoint = 2000;
	private String orgId;
	@Autowired 
	private CachedService cachedService = null;
	
	
	public PointCache(String orgId,CachedService cachedService) {
		super();
		this.orgId = orgId;
		this.cachedService = cachedService;
	}
	public int getSinglePoint() {
		// 每月单项任务完成获得 xx 积分奖励
		List<DataDictionaryBean> dlist1 = cachedService.getDirList(AppConstant.DATA_40022,orgId);
		if(dlist1!=null && dlist1.size()>0){
			singlePoint = Integer.parseInt(dlist1.get(0).getDictionaryValue());
		}
		
		return singlePoint;
	}
	public void setSinglePoint(int singlePoint) {
		this.singlePoint = singlePoint;
	}
	public int getPlanPoint() {
		// 完成月度计划，获得xx积分奖励
		List<DataDictionaryBean> dlist2 = cachedService.getDirList(AppConstant.DATA_40023,orgId);
		if(dlist2!=null && dlist2.size()>0){
			planPoint = Integer.parseInt(dlist2.get(0).getDictionaryValue());
		}
		
		return planPoint;
	}
	public void setPlanPoint(int planPoint) {
		this.planPoint = planPoint;
	}
	public int getPlan3MonthPoint() {
		
		// 连续3个月完成月度计划，获得xx积分奖励
		List<DataDictionaryBean> dlist3 = cachedService.getDirList(AppConstant.DATA_40024,orgId);
		if(dlist3!=null && dlist3.size()>0){
			plan3MonthPoint = Integer.parseInt(dlist3.get(0).getDictionaryValue());
		}
		return plan3MonthPoint;
	}
	public void setPlan3MonthPoint(int plan3MonthPoint) {
		this.plan3MonthPoint = plan3MonthPoint;
	}
	
	/*1、	系统默认销售审核通过的签约金额每10元可获取奖励1积分，用户可修改1个积分代表的签约金额数；
	2、	系统默认每月单项指标完成获取100积分奖励，用户可修改每月单项指标完成获取的积分数值；
	3、	系统默认完成月度计划，获得500积分奖励，用户可修改完成月度计划获取的积分数值；
	4、	系统默认连续3个月完成月度计划，获得2000积分奖励，用户可修改连续三个月完成月度计划获取的积分数值；*/
	
}
