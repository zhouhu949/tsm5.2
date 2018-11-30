package com.qftx.tsm.plan.user.month.service;

import com.qftx.base.util.DateUtil;
import com.qftx.base.util.GuidUtil;
import com.qftx.tsm.plan.user.month.bean.PlanUsermonthToSignLogBean;
import com.qftx.tsm.plan.user.month.dao.PlanUsermonthToSignLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PlanUsermonthToSignLogService {
	@Autowired PlanUsermonthToSignLogMapper planUsermonthToSignLogMapper;
	/* 查询*/
	public PlanUsermonthToSignLogBean findSumFact(String orgId,String[] userIds,Date date){
		PlanUsermonthToSignLogBean entity= new PlanUsermonthToSignLogBean();
		entity.setOrgId(orgId);
		entity.setUserIds(userIds);
		entity.setFromDate(DateUtil.monthBegin(date));
		entity.setToDate(date);
		
		return planUsermonthToSignLogMapper.findSumFact(entity);
	}
	
	/* 分页查询*/
	public List<PlanUsermonthToSignLogBean> findListPage(String orgId,PlanUsermonthToSignLogBean entity){
		entity.setOrgId(orgId);
		
		return planUsermonthToSignLogMapper.findListPage(entity);
	}
	/* 插入*/
	public PlanUsermonthToSignLogBean insert(String orgId,String userId,String groupId,int signNum,double money,Date date){
		PlanUsermonthToSignLogBean bean = new PlanUsermonthToSignLogBean();
		String id=GuidUtil.getGuid();
		bean.setId(id);
		bean.setOrgId(orgId);
		bean.setUserId(userId);
		bean.setGroupId(groupId);
		bean.setSignNum(signNum);
		bean.setMoney(money);
		bean.setIsDel(0);
		bean.setInputtime(new Date());
		bean.setUpdatetime(date);
		planUsermonthToSignLogMapper.insert(bean);
		return bean;
	}
}
