package com.qftx.tsm.plan.user.month.service;

import com.qftx.base.util.GuidUtil;
import com.qftx.tsm.plan.user.month.bean.PlanUsermonthCommontBean;
import com.qftx.tsm.plan.user.month.dao.PlanUsermonthCommontMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PlanUsermonthCommontService {
	@Autowired
	private PlanUsermonthCommontMapper mapper;
	//查询评论
	public List<PlanUsermonthCommontBean> queryByPlanId(String orgId,String planId){
		PlanUsermonthCommontBean entity = new PlanUsermonthCommontBean();
		entity.setOrgId(orgId);
		entity.setPlanUsermonthId(planId);
		entity.setIsDel(0);
		return mapper.findByCondtion(entity);
	}
	
	//插入点评
	public PlanUsermonthCommontBean insert(String orgId,String groupId,String userId,String planId,String context){
		Date date = new Date();
		PlanUsermonthCommontBean bean = new PlanUsermonthCommontBean();
		bean.setId(GuidUtil.getGuid());
		bean.setOrgId(orgId);
		bean.setCommontUserId(userId);
		bean.setGroupId(groupId);
		bean.setPlanUsermonthId(planId);
		bean.setContext(context);
		bean.setInputtime(date);
		bean.setUpdatetime(date);
		bean.setIsDel(0);
		mapper.insert(bean);
		return bean;
	}
}
