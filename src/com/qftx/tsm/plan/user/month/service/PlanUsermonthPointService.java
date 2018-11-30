package com.qftx.tsm.plan.user.month.service;

import com.qftx.base.util.GuidUtil;
import com.qftx.tsm.plan.user.month.bean.PlanUsermonthPointBean;
import com.qftx.tsm.plan.user.month.dao.PlanUsermonthPointMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PlanUsermonthPointService {
	@Autowired
	private PlanUsermonthPointMapper mapper;
	
	public List<PlanUsermonthPointBean> queryByPlanId(String orgId,String planId){
		Map<String , Object> params = new HashMap<String, Object>();
		params.put("orgId", orgId);
		params.put("planId", planId);
		return mapper.queryByPlanId(params);
	}
	
	//TODO 积分领取
	
	//插入积分记录
	public PlanUsermonthPointBean insert(String orgId,String planId,String type,String status,String context){
		Date date = new Date();
		PlanUsermonthPointBean bean = new PlanUsermonthPointBean();
		bean.setId(GuidUtil.getGuid());
		bean.setOrgId(orgId);
		bean.setSuId(planId);
		bean.setType(type);
		bean.setStatus(status);
		bean.setContext(context);
		bean.setInputtime(date);
		bean.setUpdatetime(date);
		bean.setIsDel(0);
		mapper.insert(bean);
		return bean;
	}
}
