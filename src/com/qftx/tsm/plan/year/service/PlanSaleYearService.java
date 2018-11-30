package com.qftx.tsm.plan.year.service;

import com.qftx.base.auth.bean.OrgGroup;
import com.qftx.base.auth.service.OrgGroupService;
import com.qftx.base.util.DateUtil;
import com.qftx.base.util.GuidUtil;
import com.qftx.tsm.plan.year.bean.PlanSaleTrendBean;
import com.qftx.tsm.plan.year.bean.PlanSaleYearBean;
import com.qftx.tsm.plan.year.bean.PlanSaleYearLogBean;
import com.qftx.tsm.plan.year.dao.PlanSaleYearLogMapper;
import com.qftx.tsm.plan.year.dao.PlanSaleYearMapper;
import com.qftx.tsm.plan.year.dto.AllYearPlanDTO;
import com.qftx.tsm.plan.year.dto.GroupYearPlanDTO;
import com.qftx.tsm.plan.year.dto.PlanChangeDTO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class PlanSaleYearService {
	@Autowired
	private PlanSaleYearMapper planSaleYearMapper;
	@Autowired
	private PlanSaleYearLogMapper planSaleYearLogMapper;
	@Autowired 
	private OrgGroupService orgGroupService;
	@Autowired
	private PlanLogYearService planLogYearService;
	
	Logger logger= Logger.getLogger(PlanSaleYearService.class);
	
	/* 查询当年全年计划*/
	public AllYearPlanDTO  queryFullYearPlan(String orgId,String userId,Date date){
		String planYear = String.valueOf(DateUtil.year(date));
		return queryFullYearPlan(orgId,userId,planYear);
	}
	
	/* 查询全年计划*/
	public AllYearPlanDTO queryFullYearPlan(String orgId,String userId,String planYear){
		AllYearPlanDTO dto= new AllYearPlanDTO();
		//按月统计
		Map<String,Double> totalByMonth = new HashMap<String, Double>();		
		List<OrgGroup> groups = orgGroupService.findLastSaleGroup(orgId);
		for (OrgGroup group : groups) {
			
			List<PlanSaleYearBean> planSaleYearBeans =findFullYearPlanByGroupId(orgId, group.getGroupId(), planYear);
			if(planSaleYearBeans==null||planSaleYearBeans.size()==0){
				//插入数值为0的年规划
				planSaleYearBeans=initYearPlan(orgId, group.getGroupId(), userId, planYear);
			}
			Map<String, String> idMap= new HashMap<String, String>();
			Map<String, Double> map = list2Map(planSaleYearBeans,totalByMonth,idMap);
			
			GroupYearPlanDTO gydto = new GroupYearPlanDTO();
			
			gydto.setGroup(group);
			gydto.setMap(map);
			gydto.setIdMap(idMap);
			dto.add(gydto);
		}
		dto.setTotalByMonth(totalByMonth);
		dto.setIfInit(ifInit(orgId, planYear));
		return dto;
	}
	/* 截止目前月份计划于执行情况*/
	public PlanSaleTrendBean getPlanYearProgress(String orgId,Date date){
		int planYear = DateUtil.year(date);
		int planMonth = DateUtil.month(date);
		
		int currentYear =DateUtil.year(new Date());
		if(planYear!=currentYear){
			planMonth =12;
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orgId", orgId);
		params.put("planYear", planYear);
		params.put("planMonth", planMonth);
		
		return planSaleYearMapper.getPlanYearProgress(params);
	}
	
	/*按年月部门ID查询规划*/
	public PlanSaleYearBean getPlanSaleYearBean(String orgId,String groupId,Date date){
		String planYear =String.valueOf(DateUtil.year(date));
		String planMonth =String.valueOf(DateUtil.month(date));
		return getPlanSaleYearBean(orgId, groupId, planYear, planMonth);
	}
	
	/*按年月部门ID查询规划*/
	public PlanSaleYearBean getPlanSaleYearBean(String orgId,String groupId,String planYear,String planMonth){
		PlanSaleYearBean entity = new PlanSaleYearBean();
		entity.setOrgId(orgId);
		entity.setGroupId(groupId);
		entity.setPlanYear(planYear);
		entity.setPlanMonth(planMonth);
		entity.setIsDel(0);
		return planSaleYearMapper.getByCondtion(entity);
	}
	
	public boolean ifInit(String orgId,String planYear){
		PlanSaleYearBean entity = new PlanSaleYearBean();
		entity.setOrgId(orgId);
		entity.setPlanYear(planYear);
		entity.setIsDel(0);
		
		List<PlanSaleYearBean> list = planSaleYearMapper.findByCondtion(entity);
		
		if(list!=null && list.size()>0){
			boolean flag = true;
			for (PlanSaleYearBean bean : list) {
				if(1==bean.getInit()){
					flag = false;
				}
			}
			return flag;
		}else{
			return true;
		}
	}

	/*获取部门年规划 ：团队年规划的合计 */
	public PlanSaleYearBean getPlanSaleYearBean(String orgId,String[] groupIds,String planYear,String planMonth){
		PlanSaleYearBean entity = new PlanSaleYearBean();
		entity.setOrgId(orgId);
		entity.setGroupIds(groupIds);
		entity.setPlanYear(planYear);
		entity.setPlanMonth(planMonth);
		
		PlanSaleYearBean item = planSaleYearMapper.getSumByCondtion(entity);
		if(item==null){
			item = new PlanSaleYearBean();
			item.setOrgId(orgId);
			item.setGroupIds(groupIds);
			item.setPlanYear(planYear);
			item.setPlanMonth(planMonth);
			item.setPlanMoney(0d);
		}
		return item;
	}
	
	/*查询全年计划*/
	public List<PlanSaleYearBean> findFullYearPlan(String orgId,String planYear){
		PlanSaleYearBean entity = new PlanSaleYearBean();
		entity.setOrgId(orgId);
		entity.setPlanYear(planYear);
		entity.setIsDel(0);
		return planSaleYearMapper.findByCondtion(entity);
	}
	
	/*按年月部门ID查询规划*/
	public List<PlanSaleYearBean> findFullYearPlanByGroupId(String orgId,String groupId,String planYear){
		PlanSaleYearBean entity = new PlanSaleYearBean();
		entity.setOrgId(orgId);
		entity.setGroupId(groupId);
		entity.setPlanYear(planYear);
		entity.setIsDel(0);
		return planSaleYearMapper.findByCondtion(entity);
	}
	
	/*按年月查询所有部门规划*/
	public List<PlanSaleYearBean> findAllGroupByMonth(String orgId,String planYear,String planMonth){
		PlanSaleYearBean entity = new PlanSaleYearBean();
		entity.setOrgId(orgId);
		entity.setPlanYear(planYear);
		entity.setPlanMonth(planMonth);
		entity.setIsDel(0);
		return planSaleYearMapper.findByCondtion(entity);
	}
	
	public Map<String, Double> list2Map(List<PlanSaleYearBean> planSaleYearBeans,Map<String, Double> totalByMonth,Map<String, String> idMap){
		double total=0L;
		Map<String, Double> map= new HashMap<String, Double>();
		if(planSaleYearBeans!=null&&planSaleYearBeans.size()>0){
			for (PlanSaleYearBean planSaleYearBean : planSaleYearBeans) {
				if(!map.containsKey(planSaleYearBean.getPlanMonth())){
					//total+=planSaleYearBean.getPlanMoney();
					total = BigDecimal.valueOf(total).add(BigDecimal.valueOf(planSaleYearBean.getPlanMoney())).doubleValue();
					map.put(planSaleYearBean.getPlanMonth(),planSaleYearBean.getPlanMoney());
					idMap.put(planSaleYearBean.getPlanMonth(), planSaleYearBean.getId());
					//所有团队月份累加
					if(totalByMonth.containsKey(planSaleYearBean.getPlanMonth())){
						//totalByMonth.put(planSaleYearBean.getPlanMonth(), totalByMonth.get(planSaleYearBean.getPlanMonth())+planSaleYearBean.getPlanMoney());
						totalByMonth.put(planSaleYearBean.getPlanMonth(),BigDecimal.valueOf(totalByMonth.get(planSaleYearBean.getPlanMonth())).add(BigDecimal.valueOf(planSaleYearBean.getPlanMoney())).doubleValue());
					}else{
						totalByMonth.put(planSaleYearBean.getPlanMonth(), planSaleYearBean.getPlanMoney());
					}
				}else{
					logger.error("年规划团队月份重复！");
				}
			}
			map.put("total",total);
			//所有团队全年累加
			if(totalByMonth.containsKey("total")){
				totalByMonth.put("total", totalByMonth.get("total")+total);
			}else{
				totalByMonth.put("total", total);
			}
		}else{
			logger.error("团队年规划为空！");
		}
		return map;
	}

	public List<PlanSaleTrendBean> queryPlanSalTrend(String orgId,String fromYear,String toYear,String groupId){
		
		Map<String, String> params =  new HashMap<String, String>(3);
		params.put("orgId", orgId);
		params.put("fromYear", fromYear);
		params.put("toYear", toYear);
		params.put("groupId", groupId);
		
		return planSaleYearMapper.queryPlanSaleTrend(params);
	}
	/* 查询年计划总额*/
	public double queryPlanMoneyByYear(String orgId,String year){
		Map<String, String> params =  new HashMap<String, String>(3);
		params.put("orgId", orgId);
		params.put("planYear", year);
		return planSaleYearMapper.queryPlanMoneyByYear(params);
	}
	
	//初始化插入全年为0的计划
	public List<PlanSaleYearBean> initYearPlan(String orgId,String groupId,String userId,String planYear){
		logger.info("初始化年规划数据orgId:"+orgId+" groupId:"+groupId+" planYear"+planYear);
		//TODO 插入日志
		Date date = new Date();
		List<PlanSaleYearBean> list = new ArrayList<PlanSaleYearBean>();
		for(int i=1;i<=12;i++){
			PlanSaleYearBean bean = new PlanSaleYearBean();
			bean.setId(GuidUtil.getGuid());
			bean.setOrgId(orgId);
			bean.setUserId(userId);
			bean.setPlanMoney(0d);
			bean.setFactMoney(0d);
			bean.setPlanYear(planYear);
			bean.setPlanMonth(String.valueOf(i));
			bean.setGroupId(groupId);
			bean.setInputtime(date);
			bean.setUpdatetime(date);
			bean.setIsDel(0);
			bean.setInit(0);
			list.add(bean);
		}
		return insertBatch(list);
	}
	
	//批量插入
	public List<PlanSaleYearBean> insertBatch(List<PlanSaleYearBean> list){
		planSaleYearMapper.insertBatch(list);
		return list;
	}
	
	public void updatePlanFactNumFacade(String orgId,String groupId,double factMoney,Date date){
		PlanSaleYearBean salePlan = getPlanSaleYearBean(orgId, groupId, date);
		if(salePlan!=null && factMoney!=0){
			updatePlanFactNum(orgId, salePlan.getId(), factMoney);
		}else{
			logger.warn("无团队年规划！groupId:"+groupId+" date:"+DateUtil.formatDate(new Date()));
		}
	}
	
	
	/*更新规划实际执行值*/
	public void updatePlanFactNum(String orgId,String id,double factMoney){
		PlanSaleYearBean entity = new PlanSaleYearBean();
		entity.setIsDel(0);
		entity.setOrgId(orgId);
		entity.setId(id);
		entity.setFactMoney(factMoney);
		entity.setUpdatetime(new Date());
		planSaleYearMapper.updateFactNum(entity);
	}
	
	/* 保存计划*/
	public void savePlan(String userId,String userName,String orgId,List<PlanChangeDTO> planChanges,String planYear,String reason){
		Date time = new Date();
		StringBuilder sb =  new StringBuilder();
		
		boolean init = ifInit(orgId, planYear);
		
		for (PlanChangeDTO planChangeDTO : planChanges) {
			String id = planChangeDTO.getId();
			String groupName = planChangeDTO.getGroupName();
			String groupId = planChangeDTO.getGroupId();
			double planMoney =planChangeDTO.getMoney();
			String planMonth = planChangeDTO.getMonth();
			
			//PlanSaleYearBean dbPlan = getPlanSaleYearBean(orgId, groupId, planYear, planMonth);
			
			if(!init ){
				if(sb.length()!=0) sb.append(",");
				sb.append(userName+"修改"+groupName+"的"+planMonth+"月份计划为"+planMoney+"万元");
			}
			
			PlanSaleYearBean planBean = new PlanSaleYearBean();
			if(id==null||"".equals(id)){
				//新增
				planBean.setId(GuidUtil.getGuid());
				planBean.setOrgId(orgId);
				planBean.setUserId(userId);
				planBean.setPlanMoney(planMoney);
				planBean.setPlanMonth(planMonth);
				planBean.setPlanYear(planYear);
				planBean.setGroupId(groupId);
				planBean.setUpdatetime(time);
				planBean.setInputtime(time);
				planSaleYearMapper.insert(planBean);
			}else{
				//更新
				planBean.setId(id);
				planBean.setPlanMoney(planMoney);
				planBean.setUpdatetime(time);
				planBean.setInit(1);
				planSaleYearMapper.updateTrends(planBean);
			}
			
			PlanSaleYearLogBean logBean = new PlanSaleYearLogBean();
			logBean.setId(GuidUtil.getGuid());
			logBean.setOrgId(orgId);
			logBean.setUserId(userId);
			logBean.setPlanMoney(planMoney);
			logBean.setPlanMonth(planMonth);
			logBean.setPlanYear(planYear);
			logBean.setGroupId(groupId);
			logBean.setUpdateReason(reason);
			logBean.setUpdatetime(time);
			logBean.setInputtime(time);
			logBean.setIsDel(0);
			
			planSaleYearLogMapper.insert(logBean);
		}
		String dcontext = null;
		if(sb.length()>0) dcontext = sb.toString();
		planLogYearService.insertPlanLogYear( userId, orgId, planYear, dcontext, time, reason);
	}
}
