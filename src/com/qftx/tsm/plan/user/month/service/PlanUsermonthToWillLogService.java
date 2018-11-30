package com.qftx.tsm.plan.user.month.service;

import com.qftx.base.util.DateUtil;
import com.qftx.base.util.GuidUtil;
import com.qftx.tsm.plan.user.month.bean.PlanUsermonthToWillLogBean;
import com.qftx.tsm.plan.user.month.dao.PlanUsermonthToWillLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PlanUsermonthToWillLogService {
	@Autowired PlanUsermonthToWillLogMapper planUsermonthToWillLogMapper;
	/* 查询*/
	public PlanUsermonthToWillLogBean findSumFact(String orgId,String[] userIds,Date date){
		PlanUsermonthToWillLogBean entity= new PlanUsermonthToWillLogBean();
		entity.setOrgId(orgId);
		entity.setUserIds(userIds);
		entity.setFromDate(DateUtil.monthBegin(date));
		entity.setToDate(date);
		
		return planUsermonthToWillLogMapper.findSumFact(entity);
	}
	 
	/* 插入*/
	public PlanUsermonthToWillLogBean insert(String orgId,String groupId,String userId,int willNum){
		PlanUsermonthToWillLogBean bean = new PlanUsermonthToWillLogBean();
		String id=GuidUtil.getGuid();
		bean.setId(id);
		bean.setOrgId(orgId);
		bean.setGroupId(groupId);
		bean.setUserId(userId);
		bean.setWillNum(willNum);
		bean.setIsDel(0);
		bean.setInputtime(new Date());
		planUsermonthToWillLogMapper.insert(bean);
		return bean;
	}
}
