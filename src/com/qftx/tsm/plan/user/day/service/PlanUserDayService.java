package com.qftx.tsm.plan.user.day.service;

import com.qftx.base.auth.bean.User;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.util.DateUtil;
import com.qftx.base.util.GuidUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.common.util.StringUtils;
import com.qftx.tsm.plan.user.day.bean.PlanUserDayBean;
import com.qftx.tsm.plan.user.day.dao.PlanUserDayMapper;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PlanUserDayService {
	@Autowired
	private PlanUserDayMapper planUserDayMapper;
	@Autowired 
	private CachedService cachedService;
	private Logger logger = Logger.getLogger(CachedService.class);
	
	//按日期查询计划
	public PlanUserDayBean getPlanUserDay(String orgId,String userId,Date date){
		PlanUserDayBean entity = new PlanUserDayBean();
		entity.setOrgId(orgId);
		entity.setUserId(userId);
		entity.setFromDate(DateUtil.dateBegin(date));
		entity.setToDate(DateUtil.dateEnd(date));
		return planUserDayMapper.getByCondtion(entity);
	}
	
	//按日期查询计划
	public List<PlanUserDayBean> findFromRes(PlanUserDayBean query){
		List<PlanUserDayBean> list = planUserDayMapper.findFromRes(query);
		return list;
	}
	
	public List<PlanUserDayBean> findFromPlan(PlanUserDayBean query){
		return planUserDayMapper.findFromPlan(query);
	}
	
	public List<String> taoGetPlanCustId(String orgId,String account){
		Map<String, User> userMap = cachedService.getOrgUserMapByAccount(orgId);
		User user = userMap.get(account);
		if(user==null) return new ArrayList<String>();
		PlanUserDayBean plan = getPlanUserDay(orgId, user.getUserId(), new Date());
		if(plan == null ||plan.getId()==null){
			return new ArrayList<String>();
		}else{
			PlanUserDayBean query =new PlanUserDayBean();
			query.setOrgId(orgId);
			query.setUserId(user.getUserId());
			query.setId(plan.getId());
			query.setType("res");
			
			return planUserDayMapper.findCustIdFromPlan(query);
		}
	}
	
	public List<String> findCustIdFromPlan(PlanUserDayBean query){
		if(StringUtils.isBlank(query.getId())){
			query.setFromDate(DateUtil.dateBegin(query.getPlanDate()));
			query.setToDate(DateUtil.dateEnd(query.getPlanDate()));
			return planUserDayMapper.findCustIdFromRes(query);
		}else{
			return planUserDayMapper.findCustIdFromPlan(query);
		}
	}
	
	public PlanUserDayBean queryPlanById(String orgId,String id){
		PlanUserDayBean query = new PlanUserDayBean();
		query.setOrgId(orgId);
		query.setId(id);
		return planUserDayMapper.findById(query);
	}
	
	public PlanUserDayBean insertIfNotExist(ShiroUser shiroUser,Date date){
		synchronized (PlanLock.getInstance().getLock(shiroUser.getOrgId())) {
			PlanUserDayBean plan = getPlanUserDay(shiroUser.getOrgId(), shiroUser.getId(), date);
			if(plan==null){
				plan = insert(shiroUser, date);
			}
			return plan;
		}
	}
	
	//插入日计划
	public PlanUserDayBean newBean(ShiroUser shiroUser,Date date){
		Date time = new Date();
		PlanUserDayBean bean = new PlanUserDayBean();
		String id=GuidUtil.getGuid();
		bean.setId(id);
		bean.setOrgId(shiroUser.getOrgId());
		bean.setGroupId(shiroUser.getGroupId()); 
		bean.setUserId(shiroUser.getId());
		bean.setStatus("1");
		bean.setPlanDate(DateUtil.dateBegin(date));
		bean.setAuthState("1");
		bean.setInputtime(time);
		bean.setUpdatetime(time);
		bean.setIsDel(0);
		return bean;
	}
	//插入日计划
	public PlanUserDayBean insert(ShiroUser shiroUser,Date date){
		PlanUserDayBean bean = newBean(shiroUser, date);
		planUserDayMapper.insert(bean);
		return bean;
	}
	
	public List<String> delPlanCust(String orgId,String sudId,String custId,String type){
		Map<String, Object> params = new HashMap<>();
		params.put("orgId", orgId);
		params.put("sudId", sudId);
		params.put("custId", custId);
		params.put("type", type);
		params.put("updatetime", new Date());
		return planUserDayMapper.delPlanCust(params);
	}
	
	public List<String> findCustIdByPlanId(String orgId,String sudId,String type){
		PlanUserDayBean query =new PlanUserDayBean();
		query.setOrgId(orgId);
		query.setId(sudId);
		query.setType(type);
		
		return planUserDayMapper.findCustIdFromPlan(query);
	}
}
