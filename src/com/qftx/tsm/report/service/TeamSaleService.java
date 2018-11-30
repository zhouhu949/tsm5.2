package com.qftx.tsm.report.service;

import com.qftx.base.auth.bean.OrgGroup;
import com.qftx.base.util.DateUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.tsm.plan.group.month.bean.PlanGroupmonthBean;
import com.qftx.tsm.plan.group.month.dao.PlanGroupmonthMapper;
import com.qftx.tsm.plan.user.month.bean.PlanUsermonthBean;
import com.qftx.tsm.plan.user.month.dao.PlanUsermonthMapper;
import com.qftx.tsm.report.dto.FullYearGroupPlanDTO;
import com.qftx.tsm.report.dto.FullYearUserPlanDTO;
import com.qftx.tsm.report.dto.TeamSaleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
@Service
public class TeamSaleService {
	@Autowired
	private PlanGroupmonthMapper planGroupmonthMapper;
	@Autowired
	private PlanUsermonthMapper planUsermonthMapper;
	@Autowired
	private CachedService cachedService;
	
	// 通过团队id查询团队计划
	public List<PlanGroupmonthBean> findFullYearPlanByGroups(String orgId,String[] groupIds, int planYear) {
		PlanGroupmonthBean entity = new PlanGroupmonthBean();
		entity.setOrgId(orgId);
		entity.setGroupIds(groupIds);
		entity.setPlanYear(String.valueOf(planYear));
		entity.setIsDel(0);
		entity.setOrderKey("ttg.GROUP_INDEX");
		return planGroupmonthMapper.findByCondtion(entity);
	}

	/* 获取不同部门的全年计划 key1:groupId key2:month */
	public TeamSaleDTO findGroupFullYearPlanMap(String orgId, String[] groupIds, int planYear) {
		TeamSaleDTO dto = new TeamSaleDTO();
		Map<String, Map<String, PlanGroupmonthBean>> map = new LinkedHashMap<String, Map<String, PlanGroupmonthBean>>();

		Date date = new Date();
		int currnetYear = DateUtil.year(date);
		int currentMonth = DateUtil.month(date);

		List<PlanGroupmonthBean> list = findFullYearPlanByGroups(orgId,groupIds, planYear);
		for (PlanGroupmonthBean plan : list) {
			String groupId = plan.getGroupId();
			//String groupName = plan.getGroupName();
			String planMonth = plan.getPlanMonth();
			if (planYear < currnetYear || (planYear == currnetYear && Integer.valueOf(planMonth) <= currentMonth)) {
				// 合计值
				if (map.containsKey(groupId)) {
					Map<String, PlanGroupmonthBean> monthMap = map.get(groupId);
					monthMap.put(planMonth, plan);
				} else {
					Map<String, PlanGroupmonthBean> monthMap = new HashMap<String, PlanGroupmonthBean>();
					monthMap.put(planMonth, plan);
					map.put(groupId, monthMap);
				}
			} else {

			}
		}
		dto.setTotalMap(getTotal(orgId, groupIds, list, planYear, currnetYear, currentMonth));
		dto.setGroupList(groupMaps2List(orgId,map));
		return dto;
	}
	
	public Map<String, PlanGroupmonthBean> getTotal(String orgId, String[] groupIds,List<PlanGroupmonthBean> list,int planYear,int currentYear,int currentMonth){
		List<String> rejectGroupIds =new ArrayList<String>();
		Map<String, List<OrgGroup>> map = cachedService.getOrgGroupPidMap(orgId);
		for (String groupId : groupIds) {
			List<OrgGroup> sonGroups= map.get(groupId);
			if(sonGroups!=null && sonGroups.size()>0){
				for (OrgGroup orgGroup : sonGroups) {
					rejectGroupIds.add(orgGroup.getGroupId());
				}
			}
		}
		Map<String, PlanGroupmonthBean> totalMap = new HashMap<String, PlanGroupmonthBean>();
		for (PlanGroupmonthBean plan : list) {
			String groupId = plan.getGroupId();
			
			if(rejectGroupIds.contains(groupId)){
				continue;
			}
			String planMonth = plan.getPlanMonth();
			
			if (planYear < currentYear|| (planYear == currentYear && Integer.valueOf(planMonth) <= currentMonth)) {
				if (totalMap.containsKey(planMonth)) {
					PlanGroupmonthBean total = totalMap.get(planMonth);
					total.setPlanMoney(BigDecimal.valueOf(total.getPlanMoney()).add(BigDecimal.valueOf(plan.getPlanMoney())).doubleValue());
					total.setFactMoney(BigDecimal.valueOf(total.getFactMoney()).add(BigDecimal.valueOf(plan.getFactMoney())).doubleValue());
					total.setPlanSigncustnum(total.getPlanSigncustnum() + plan.getPlanSigncustnum());
					total.setPlanWillcustnum(total.getPlanWillcustnum() + plan.getPlanWillcustnum());
					total.setFactSigncustnum(total.getFactSigncustnum() + plan.getFactSigncustnum());
					total.setFactWillcustnum(total.getFactWillcustnum() + plan.getFactWillcustnum());
	
				} else {
					PlanGroupmonthBean total = new PlanGroupmonthBean();
					total.setPlanMoney(plan.getPlanMoney());
					total.setFactMoney(plan.getFactMoney());
					total.setPlanSigncustnum(plan.getPlanSigncustnum());
					total.setPlanWillcustnum(plan.getPlanWillcustnum());
					total.setFactSigncustnum(plan.getFactSigncustnum());
					total.setFactWillcustnum(plan.getFactWillcustnum());
					totalMap.put(planMonth, total);
				}
			}
		}
		
		return totalMap;
	}
	
	public List<FullYearUserPlanDTO> findUsersFullYearPlanMap(String orgId,String[] userIds,int planYear){
		List<PlanUsermonthBean> list = queryFullYearByGroup(orgId,userIds, planYear);
		
		Map<String, Map<String, PlanUsermonthBean>> map = new LinkedHashMap<String, Map<String, PlanUsermonthBean>>();
		Date date = new Date();
		int year = DateUtil.year(date);
		int month = DateUtil.month(date);
		for (PlanUsermonthBean plan : list) {
			//String userId = plan.getUserId();
			String account = plan.getUserAcc();
			//String userName = plan.getUserName();
			String planMonth = plan.getPlanMonth();
			if (planYear < year || (planYear == year && Integer.valueOf(planMonth) <= month)) {
				// 合计值
				if (map.containsKey(account)) {
					Map<String, PlanUsermonthBean> monthMap = map.get(account);
					monthMap.put(planMonth, plan);
				} else {
					Map<String, PlanUsermonthBean> monthMap = new HashMap<String, PlanUsermonthBean>();
					monthMap.put(planMonth, plan);
					map.put(account, monthMap);
				}
			} else {

			}
		}
		return userMaps2List(orgId,map);
	}
	
	//查询部门所有成员的全年月计划
	public List<PlanUsermonthBean> queryFullYearByGroup(String orgId,String[] userIds,int planYear){
		PlanUsermonthBean bean = new PlanUsermonthBean();
		bean.setOrgId(orgId);
		bean.setUserIds(userIds);
		bean.setIsDel(0);
		bean.setOrderKey("t3.USER_ACCOUNT");
		return planUsermonthMapper.findByCondtion(bean);
	}
	
	public List<FullYearUserPlanDTO> userMaps2List(String orgId,Map<String, Map<String, PlanUsermonthBean>> map){
		List<FullYearUserPlanDTO> list= new ArrayList<FullYearUserPlanDTO>();
		Map<String, String> userNamesMap = cachedService.getOrgUserNames(orgId);
		Set<String> accounts = map.keySet();
		for (String account : accounts) {
			Map<String, PlanUsermonthBean> monthMap = map.get(account);
			FullYearUserPlanDTO dto = new FullYearUserPlanDTO();
			dto.setAccount(account);
			if(userNamesMap!=null && !userNamesMap.isEmpty()){
				dto.setUserName(userNamesMap.get(account));
			}
			dto.setMonthMap(monthMap);
			list.add(dto);
		}
		return list;
	}
	
	public List<FullYearGroupPlanDTO> groupMaps2List(String orgId,Map<String, Map<String, PlanGroupmonthBean>> map){
		List<FullYearGroupPlanDTO> list= new ArrayList<FullYearGroupPlanDTO>();
		Map<String, OrgGroup> groupMap = cachedService.getOrgGroupMap(orgId);
		Set<String> groupIds = map.keySet();
		for (String groupId : groupIds) {
			Map<String, PlanGroupmonthBean> monthMap = map.get(groupId);
			FullYearGroupPlanDTO dto = new FullYearGroupPlanDTO();
			dto.setGroupId(groupId);
			dto.setMonthMap(monthMap);
			if(groupMap!=null && !groupMap.isEmpty()){
				OrgGroup group = groupMap.get(groupId);
				if(group!=null) dto.setGroupName(group.getGroupName());
			}
			list.add(dto);
		}
		return list;
	}
}


