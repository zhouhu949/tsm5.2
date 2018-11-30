package com.qftx.tsm.plan.group.month.service;

import com.qftx.base.util.GuidUtil;
import com.qftx.tsm.plan.group.month.bean.PlanAuthlogBean;
import com.qftx.tsm.plan.group.month.dao.PlanAuthlogMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class PlanAuthlogService {
	@Autowired
	private PlanAuthlogMapper mapper;
	private Logger logger= Logger.getLogger(PlanAuthlogService.class);
	
	//查询审核信息
	public List<PlanAuthlogBean> queryByPlanId(String orgId,String planId,String planType){
		Map<String , String> params = new HashMap<String, String>();
		params.put("orgId", orgId);
		params.put("planId", planId);
		params.put("planType", planType);
		return mapper.queryByPlanId(params);
	}
		
	//插入审核信息
	public PlanAuthlogBean insert(String orgId,String groupId,String userId,String planType,String planId,String authState,String context){
		Date date = new Date();
		PlanAuthlogBean bean = new PlanAuthlogBean();
		bean.setId(GuidUtil.getGuid());
		bean.setOrgId(orgId);
		bean.setUserId(userId);
		bean.setPlanId(planId);
		bean.setPlanType(planType);
		bean.setAuthState(authState);
		bean.setContext(context);
		bean.setInputtime(date);
		bean.setUpdatetime(date);
		bean.setIsDel(0);
		mapper.insert(bean);
		return bean;
	}
	
}
