package com.qftx.tsm.plan.group.month.service;

import com.qftx.base.util.GuidUtil;
import com.qftx.tsm.plan.group.month.bean.PlanGroupmonthCommontBean;
import com.qftx.tsm.plan.group.month.dao.PlanGroupmonthCommontMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class PlanGroupmonthCommontService {
	@Autowired
	private PlanGroupmonthCommontMapper mapper;
	private Logger logger= Logger.getLogger(PlanGroupmonthCommontService.class);
	
	//查询评论
	public List<PlanGroupmonthCommontBean> queryByPlanId(String orgId,String planId){
		Map<String , String> params = new HashMap<String, String>();
		params.put("orgId", orgId);
		params.put("planGroupmonthId", planId);
		return mapper.queryByPlanId(params);
	}
		
	//插入点评
	public PlanGroupmonthCommontBean insert(String orgId,String groupId,String userId,String planId,String context){
		Date date = new Date();
		PlanGroupmonthCommontBean bean = new PlanGroupmonthCommontBean();
		bean.setId(GuidUtil.getGuid());
		bean.setOrgId(orgId);
		bean.setCommontUserId(userId);
		bean.setGroupId(groupId);
		bean.setPlanGroupmonthId(planId);
		bean.setContext(context);
		bean.setInputtime(date);
		bean.setUpdatetime(date);
		bean.setIsDel(0);
		mapper.insert(bean);
		return bean;
	}
	
}
