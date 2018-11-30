package com.qftx.tsm.plan.group.month.service;

import com.qftx.base.auth.service.OrgGroupUserService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.team.bean.TeamGroupBean;
import com.qftx.base.team.bean.TsmTeamGroupMemberBean;
import com.qftx.base.team.service.TsmTeamGroupMemberService;
import com.qftx.base.team.service.TsmTeamGroupService;
import com.qftx.base.util.DateUtil;
import com.qftx.base.util.GuidUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.message.service.TsmMessageService;
import com.qftx.tsm.plan.ResultDTO;
import com.qftx.tsm.plan.group.month.bean.PlanGroupmonthBean;
import com.qftx.tsm.plan.group.month.bean.PlanGroupmonthCommontBean;
import com.qftx.tsm.plan.group.month.bean.PlanStatus;
import com.qftx.tsm.plan.group.month.dao.PlanGroupmonthMapper;
import com.qftx.tsm.plan.group.month.dto.*;
import com.qftx.tsm.plan.user.month.bean.PlanUsermonthBean;
import com.qftx.tsm.plan.user.month.bean.QueryYearMonthBean;
import com.qftx.tsm.plan.user.month.service.PlanUserMonthService;
import com.qftx.tsm.plan.user.month.service.PlanUsermonthToSignLogService;
import com.qftx.tsm.plan.user.month.service.PlanUsermonthToWillLogService;
import com.qftx.tsm.plan.year.bean.PlanSaleYearBean;
import com.qftx.tsm.plan.year.service.PlanSaleYearService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
@Service
public class PlanGroupmonthService {
	@Autowired
	private PlanGroupmonthMapper planGroupmonthMapper;
	@Autowired 
	private PlanUserMonthService userService;
	@Autowired
	private PlanGroupmonthCommontService commontService;
	@Autowired
	private PlanSaleYearService planSaleYearService;
	@Autowired
	private OrgGroupUserService groupUserService;
	@Autowired
	private TsmTeamGroupService tsmTeamGroupService;
	@Autowired
	private TsmTeamGroupMemberService tsmTeamGroupMemberService;
	@Autowired
	private TsmMessageService tsmMessageService;
	@Autowired
	private PlanUsermonthToSignLogService planUsermonthToSignLogService;
	@Autowired
	private PlanUsermonthToWillLogService planUsermonthToWillLogService;
	
	
	private Logger logger= Logger.getLogger(PlanGroupmonthService.class);
	
	//通过团队id查询团队全年计划
	public List<PlanGroupmonthBean> queryPlanByYearAndGroupId(String orgId,String groupId,String planYear){
		PlanGroupmonthBean entity = new PlanGroupmonthBean();
		entity.setOrgId(orgId);
		entity.setGroupId(groupId);
		entity.setPlanYear(planYear);
		return planGroupmonthMapper.findByCondtion(entity);
	}
	
	public List<PlanGroupmonthBean> findListPage(PlanGroupmonthBean item){
		return planGroupmonthMapper.findListPage(item);
	}
	
	//通过团队id查询团队计划
	public List<PlanGroupmonthBean> findByCondtion(String orgId,String[] groupIds,int planYear,int planMonth){
		PlanGroupmonthBean entity = new PlanGroupmonthBean();
		entity.setGroupIds(groupIds);
		entity.setOrgId(orgId);
		entity.setPlanYear(String.valueOf(planYear));
		entity.setPlanMonth(String.valueOf(planMonth));
		entity.setIsDel(0);
		entity.setOrderKey("");
		return planGroupmonthMapper.findByCondtion(entity);
	}
	
	
	
	public AnalyDTO getAnalyDTO(String groupName){
		ResAnaly resAnaly= new ResAnaly();
		resAnaly.setCallTime(10000);
		resAnaly.setContracAmount(50d);
		resAnaly.setResNum(1000);
		resAnaly.setWillNum(200);
		resAnaly.setSignNum(90);
		
		CustAnaly custAnaly = new CustAnaly();
		
		custAnaly.setCurrentMonthSilenceCust(22);
		custAnaly.setLastMonthSilenceCust(33);
		custAnaly.setCurrentMonthLostCust(10);
		custAnaly.setLastMonthLostCust(20);
		
		SignAnaly signAnaly = new SignAnaly();
		
		signAnaly.setCurrentMonthIncome(100d);
		signAnaly.setNewCustIncome(20d);
		signAnaly.setOldCustIncome(80d);
		
		signAnaly.setOldCustSignCount(30);
		signAnaly.setNewCustSignCount(40);
		signAnaly.setCurrentMonthSignCount(70);
		
		signAnaly.setLastMonthIncome(80d);
		
		AnalyDTO dto= new AnalyDTO();
		dto.setCustAnaly(custAnaly);
		dto.setSignAnaly(signAnaly);
		dto.setResAnaly(resAnaly);
		dto.setGroupName(groupName);
		
		return dto;
	}
	
	/*获取全年月计划*/
	public Map<String, PlanTeamMonthDTO> getPlansFullYear(String orgId,String groupId,String year){
		Map<String, PlanTeamMonthDTO> map = new HashMap<String, PlanTeamMonthDTO>();
		
		List<PlanGroupmonthBean> plans = queryPlanByYearAndGroupId(orgId, groupId, year);
		for (PlanGroupmonthBean teamPlan : plans) {
			String month = teamPlan.getPlanMonth();
			if(month!=null && !map.containsKey(month)){
				List<PlanGroupmonthCommontBean> teamPlanCommonts = commontService.queryByPlanId(orgId, teamPlan.getId());
				
				PlanStatus planStatus = getPlanStatusByMonth(orgId, groupId, year, month,0);
				PlanTeamMonthDTO dto = new PlanTeamMonthDTO();
				
				dto.setPlanStatus(planStatus);
				dto.setTeamPlan(teamPlan);
				dto.setTeamPlanCommonts(teamPlanCommonts);
				map.put(month, dto);
			}else{
				logger.warn("月计划 planmonth 为空或重复:"+month);
			}
		}
		return map;
	}
	
	public PlanStatus getPlanStatusByMonth(String orgId,String groupId,String planYear,String planMonth,Integer groupType){
		PlanStatus planStatus = new PlanStatus();
		if(groupType==0){
			List<TsmTeamGroupMemberBean> users = tsmTeamGroupMemberService.findByGroupId(orgId, groupId);
			//未审核   上报了审核状态为待审核的 //未上报    未做计划的和上报状态为0的
			for (TsmTeamGroupMemberBean user : users) {
				String userId = user.getUserId();
				PlanUsermonthBean plan = userService.queryByUserAndMonth(orgId, userId, planYear, planMonth);
				
				if(plan!=null){
					if("0".equals(plan.getStatus())){
						//未上报
						planStatus.setNoUpNum(planStatus.getNoUpNum()+1);
						planStatus.addNoUpSb(user.getUserName());
					}else{
						//上报
						if(!"2".equals(plan.getAuthState())){
							planStatus.addNoAuthPass(user.getUserName());
							planStatus.setNoAuthNum(planStatus.getNoAuthNum()+1);
						}
					}
				}else{
					//当月无计划
					planStatus.setNoUpNum(planStatus.getNoUpNum()+1);
					planStatus.addNoUpSb(user.getUserName());
				}
			}
		}else{
			List<TeamGroupBean> groups = tsmTeamGroupService.findSonGroups(orgId, groupId);
			for (TeamGroupBean group : groups) {
				String gid = group.getGroupId();
				PlanGroupmonthBean plan = getByGroupIdAndTime(orgId, gid, planYear, planMonth);
				if(plan!=null){
					if("0".equals(plan.getStatus())){
						//未上报
						planStatus.setNoUpNum(planStatus.getNoUpNum()+1);
						planStatus.addNoUpSb(group.getGroupName());
					}else{
						//上报
						if(!"2".equals(plan.getAuthState())){
							planStatus.addNoAuthPass(group.getGroupName());
							planStatus.setNoAuthNum(planStatus.getNoAuthNum()+1);
						}
					}
				}else{
					//当月无计划
					planStatus.setNoUpNum(planStatus.getNoUpNum()+1);
					planStatus.addNoUpSb(group.getGroupName());
				}
			}
		}
		return planStatus;
	}
	
	//根据部门id查询下属团队的已上报的月计划
	public PlanGroupmonthDTO getPlanGroupmonthDTO(String orgId,PlanGroupmonthBean plan){
		//PlanGroupmonthBean plan = getById(orgId, planId);
		List<PlanGroupmonthBean> teamPlans = queryPlanByGroupIds(orgId,getGroupIds(orgId,plan.getGroupId()), plan.getPlanYear(), plan.getPlanMonth());
		PlanGroupmonthBean teamPlansSum = querySumByGroups(orgId,getGroupIds(orgId,plan.getGroupId()), plan.getPlanYear(), plan.getPlanMonth());
		PlanSaleYearBean planSaleYearBean = planSaleYearService.getPlanSaleYearBean(orgId, getGroupIds1(orgId, plan.getGroupId()), plan.getPlanYear(), plan.getPlanMonth());
		
		if(teamPlansSum == null){
			teamPlansSum = new PlanGroupmonthBean();
			teamPlansSum.setPlanWillcustnum(0);
			teamPlansSum.setPlanSigncustnum(0);
			teamPlansSum.setPlanMoney(0d);
			
			teamPlansSum.setFactSigncustnum(0);
			teamPlansSum.setFactWillcustnum(0);
			teamPlansSum.setFactMoney(0d);
		}
		
		PlanGroupmonthDTO dto= new PlanGroupmonthDTO();
		dto.setGroupPlan(plan);
		dto.setTeamPlans(teamPlans);
		dto.setTeamPlansSum(teamPlansSum);
		dto.setPlanSaleYearBean(planSaleYearBean);
		return dto;
	}
	
	//查询团队月计划 DTO
	public PlanTeamMonthDTO getPlanTeammonthDTO(String orgId,PlanGroupmonthBean plan){
		//PlanGroupmonthBean plan = getById(orgId, planId);
		List<PlanUsermonthBean> userPlans = userService.findByGroupId(orgId, plan.getGroupId(), plan.getPlanYear(), plan.getPlanMonth()); 
		PlanUsermonthBean userPlansSum = userService.querySumByGroup(orgId, plan.getGroupId(), plan.getPlanYear(), plan.getPlanMonth());
		List<PlanGroupmonthCommontBean> teamPlanCommonts = commontService.queryByPlanId(orgId, plan.getId());
		PlanSaleYearBean planSaleYearBean = planSaleYearService.getPlanSaleYearBean(orgId,plan.getGroupId(), plan.getPlanYear(), plan.getPlanMonth());
		
		PlanTeamMonthDTO dto= new PlanTeamMonthDTO();
		
		if(userPlansSum == null){
			userPlansSum = new PlanUsermonthBean();
			userPlansSum.setPlanWillcustnum(0);
			userPlansSum.setPlanSigncustnum(0);
			userPlansSum.setPlanMoney(0d);
			
			userPlansSum.setFactSigncustnum(0);
			userPlansSum.setFactWillcustnum(0);
			userPlansSum.setFactMoney(0d);
		}
		
		dto.setTeamPlan(plan);
		dto.setUserPlans(userPlans);
		dto.setTeamPlanCommonts(teamPlanCommonts);
		dto.setUserPlansSum(userPlansSum);
		dto.setPlanSaleYearBean(planSaleYearBean);
		return dto;
	}
	
	//根据部门id查询下属团队的已上报的月计划
	public List<PlanGroupmonthBean> queryPlanByGroupIds(String orgId,String[] groupIds,String planYear,String planMonth){
		PlanGroupmonthBean entity = new PlanGroupmonthBean();
		entity.setOrgId(orgId);
		entity.setGroupIds(groupIds);
		entity.setPlanYear(planYear);
		entity.setPlanMonth(planMonth);
		entity.setStatus("1");
		entity.setIsDel(0);
		
		List<PlanGroupmonthBean> list = planGroupmonthMapper.findByCondtion(entity);
		return list;
	}
	
	//根据部门id查询下属团队的已上报且审核的的月计划 总和
	public PlanGroupmonthBean querySumByGroups(String orgId,String[] groupIds,String planYear,String planMonth){
		PlanGroupmonthBean entity = new PlanGroupmonthBean();
		entity.setOrgId(orgId);
		entity.setPlanYear(planYear);
		entity.setPlanMonth(planMonth);
		entity.setStatus("1");
		entity.setIsDel(0);
		entity.setAuthState("2");
		entity.setGroupIds(groupIds);
		
		return  planGroupmonthMapper.querySumByGroups(entity);
	}
	
	//通过计划id查询月计划
	public PlanGroupmonthBean getById(String orgId,String id){
		PlanGroupmonthBean entity = new PlanGroupmonthBean();
		entity.setOrgId(orgId);
		entity.setId(id);
		return planGroupmonthMapper.getByCondtion(entity);
	}
	
	//通过计划id查询月计划
	public PlanGroupmonthBean getByGroupIdAndTime(String orgId,String groupId,Date date){
		String planYear = String.valueOf(DateUtil.year(date));
		String planMonth = String.valueOf(DateUtil.month(date));
		
		if(planYear!=null && planMonth!=null && orgId!=null &&groupId!=null){
			return getByGroupIdAndTime(orgId, groupId, planYear, planMonth);
		}else{
			logger.error("参数为空！");
			return null;
		}
		
	}
	
	//通过计划id查询月计划
	public PlanGroupmonthBean getByGroupIdAndTime(String orgId,String groupId,String planYear,String planMonth){
		PlanGroupmonthBean entity = new PlanGroupmonthBean();
		entity.setOrgId(orgId);
		entity.setGroupId(groupId);
		entity.setPlanYear(planYear);
		entity.setPlanMonth(planMonth);
		return planGroupmonthMapper.getByCondtion(entity);
	}
	
	//查询团队计划历史走势
	public List<PlanGroupmonthBean> queryHistory(String orgId,String[] groupIds,Date from,Date to){
		List<QueryYearMonthBean> querys = new ArrayList<QueryYearMonthBean>();
		int monthSub = DateUtil.monthSub(from, to);
		
		for(int i=0;i<=monthSub;i++){
			QueryYearMonthBean query = new QueryYearMonthBean();
			query.setYear(DateUtil.year(from));
			query.setMonth(DateUtil.month(from));
			querys.add(query);
			from = DateUtil.addDate(from, 0, 1, 0);
		}
		if(querys.size()>0){
			Map<String , Object> params = new HashMap<String, Object>();
			params.put("orgId", orgId);
			params.put("groupIds", groupIds);
			params.put("querys", querys);
			params.put("authState", "2");
			return planGroupmonthMapper.queryHistoryByGroupIds(params);
		}else{
			logger.debug("查询日期为空！");
			return new ArrayList<PlanGroupmonthBean>();
		}
	}
	
	//查询团队计划历史走势 111
	public List<PlanGroupmonthBean> queryHistory(String orgId,String groupId,Date from,Date to){
		List<QueryYearMonthBean> querys = new ArrayList<QueryYearMonthBean>();
		int monthSub = DateUtil.monthSub(from, to);
		
		for(int i=0;i<=monthSub;i++){
			QueryYearMonthBean query = new QueryYearMonthBean();
			query.setYear(DateUtil.year(from));
			query.setMonth(DateUtil.month(from));
			querys.add(query);
			from = DateUtil.addDate(from, 0, 1, 0);
		}
		if(querys.size()>0){
			Map<String , Object> params = new HashMap<String, Object>();
			params.put("orgId", orgId);
			params.put("groupId", groupId);
			params.put("querys", querys);
			params.put("authState", "2");
			return planGroupmonthMapper.queryHistory(params);
		}else{
			logger.debug("查询日期为空！");
			return new ArrayList<PlanGroupmonthBean>();
		}
	}
	
	public void receivePoint(String orgId,String id,String type,boolean isLast){
		PlanGroupmonthBean bean = new PlanGroupmonthBean();
		
		bean.setOrgId(orgId);
		bean.setId(id);
		if("all".equals(type)){
			bean.setPlanWillcustnumState(2);
			bean.setPlanSigncustnumState(2);
			bean.setPlanMoneyState(2);
			bean.setPlanStatus("2");
		}else if("will".equals(type)){
			bean.setPlanWillcustnumState(2);
		}else if("sign".equals(type)){
			bean.setPlanSigncustnumState(2);
		}else if("money".equals(type)){
			bean.setPlanMoneyState(2);
		}else{
			logger.error("type is undefined:"+type);
		}
		
		if(isLast){
			bean.setPlanStatus("2");
		}
		
		planGroupmonthMapper.updateTrends(bean);
		
	}
	
	//上报退回计划    0未上报，1已上报
	public void upReportPlan(String orgId,String id,String status){
		PlanGroupmonthBean bean = new PlanGroupmonthBean();
		bean.setId(id);
		bean.setOrgId(orgId);
		bean.setStatus(status);
		planGroupmonthMapper.updateTrends(bean);
	}
	
	//更新最新评论
	public void upDateLastCommontId(String orgId,String planId,String lastCommontId){
		PlanGroupmonthBean bean = new PlanGroupmonthBean();
		bean.setId(planId);
		bean.setOrgId(orgId);
		bean.setLastCommontId(lastCommontId);
		planGroupmonthMapper.updateTrends(bean);
	}
	
	public List<PlanGroupmonthBean> findNoAuthPlan(String orgId,String[] groupIds,Date date){
		PlanGroupmonthBean entity = new PlanGroupmonthBean();
		entity.setOrgId(orgId);
		entity.setAuthState("1");
		entity.setStatus("1");
		entity.setIsDel(0);
		entity.setGroupIds(groupIds);
		entity.setPlanYear(String.valueOf(DateUtil.year(date)));
		entity.setPlanMonth(String.valueOf(DateUtil.month(date)));
		return planGroupmonthMapper.findNoAuthPlan(entity);
	}
		
	//审核计划 1未审核，2通过，0未通过
	public void authPlan(String orgId,String authUserid,String authUsername,String[] ids,String authState,String authDesc){ 
		List<String> erroList= new ArrayList<String>();
		if("2".equals(authState)){
			for (String id : ids) {
				authSingle(orgId, id, authState, authUserid, authUsername, authDesc);
				
				PlanGroupmonthBean teamPlan = getById(orgId, id);
				String planYear = teamPlan.getPlanYear();
				String planMonth = teamPlan.getPlanMonth();
				
				String teamGroupId = teamPlan.getGroupId();
				TeamGroupBean group = tsmTeamGroupService.getById(orgId, teamGroupId);
				if(group!=null &&group.getPid()!=null&&planYear!=null&&planMonth!=null){
					PlanGroupmonthBean groupPlan = getByGroupIdAndTime(orgId, group.getPid(), planYear, planMonth);
					if(groupPlan!=null){
						if("1".equals(groupPlan.getStatus())){
							logger.warn("groupPlan is up report!:groupId["+group.getPid()+"]teamPlanId["+groupPlan.getId()+"]");
							erroList.add(id);
							continue;
						}else{
							updateGroupPlan(orgId, group.getPid(), planYear, planMonth,groupPlan);
						}
					}
				}
			}
		}else if("0".equals(authState)){
			authBatch(orgId, ids, authState, authUserid, authUsername, authDesc);
		}
	}
	
	/*单条审核*/
	public void authSingle(String orgId,String id,String authState,String authUserid,String authUsername,String authDesc){
		PlanGroupmonthBean bean = new PlanGroupmonthBean();
		bean.setId(id);
		bean.setOrgId(orgId);
		bean.setAuthState(authState);
		bean.setAuthUserid(authUserid);
		bean.setAuthUsername(authUsername);
		bean.setAuthTime(new Date());
		bean.setAuthDesc(authDesc);
		planGroupmonthMapper.updateTrends(bean);
	}
	/*批量审核  只适用于驳回*/
	public void authBatch(String orgId,String[] ids,String authState,String authUserid,String authUsername,String authDesc){
		PlanGroupmonthBean bean = new PlanGroupmonthBean();
		bean.setIds(ids);
		bean.setOrgId(orgId);
		bean.setAuthState(authState);
		bean.setAuthUserid(authUserid);
		bean.setAuthUsername(authUsername);
		bean.setAuthTime(new Date());
		bean.setAuthDesc(authDesc);
		planGroupmonthMapper.updateTrends(bean);
	}
	
	//更新计划内容
	public void updatePlanNum(String id,String orgId,int addPlanSigncustnum,int addPlanWillcustnum,double addPlanMoney){
		PlanGroupmonthBean bean = new PlanGroupmonthBean();
		bean.setId(id);
		bean.setOrgId(orgId);
		bean.setPlanSigncustnum(addPlanSigncustnum);
		bean.setPlanWillcustnum(addPlanWillcustnum);
		bean.setPlanMoney(addPlanMoney);
		planGroupmonthMapper.updatePlanNum(bean);
	}
	
	//更新计划实际执行数 参数为增量
	public void updateFactNum(String orgId,String groupId,int factWillcustnum,int factSigncustnum,double factMoney,Date date,int sum){
		PlanGroupmonthBean plan = getByGroupIdAndTime(orgId, groupId, date);
		if(plan!=null){
			PlanGroupmonthBean entity = new PlanGroupmonthBean();
			entity.setId(plan.getId());
			entity.setOrgId(orgId);
			entity.setFactWillcustnum(factWillcustnum);
			entity.setFactSigncustnum(factSigncustnum);
			entity.setFactMoney(factMoney);
			
			if(factWillcustnum>0 ||factSigncustnum>0||factMoney>0){
				if(plan.getPlanWillcustnumState()==0 &&
						(factWillcustnum+plan.getFactWillcustnum())>=plan.getPlanWillcustnum()){
					entity.setPlanWillcustnumState(2);
				}
				
				if(plan.getPlanSigncustnumState()==0 &&
						(factSigncustnum+plan.getFactSigncustnum())>=plan.getPlanSigncustnum()){
					entity.setPlanSigncustnumState(2);
				}
				
				if(plan.getPlanMoneyState()==0 &&
						(factMoney+plan.getFactMoney())>=plan.getPlanMoney()){
					entity.setPlanMoneyState(2);
				}
				
				if("0".equals(plan.getStatus())&&
						(factWillcustnum+plan.getFactWillcustnum())>=plan.getPlanWillcustnum() &&
						(factSigncustnum+plan.getFactSigncustnum())>=plan.getPlanSigncustnum() &&
						(factMoney+plan.getFactMoney())>=plan.getPlanMoney()){
					entity.setPlanStatus("2");
				}
			}else{
				if(plan.getPlanWillcustnumState()==2 &&
						(factWillcustnum+plan.getFactWillcustnum())<plan.getPlanWillcustnum()){
					entity.setPlanWillcustnumState(0);
				}
				
				if(plan.getPlanSigncustnumState()==2 &&
						(factSigncustnum+plan.getFactSigncustnum())<plan.getPlanSigncustnum()){
					entity.setPlanSigncustnumState(0);
				}
				
				if(plan.getPlanMoneyState()==2 &&
						(factMoney+plan.getFactMoney())>=plan.getPlanMoney()){
					entity.setPlanMoneyState(0);
				}
				
				if("2".equals(plan.getStatus())&&
						(factWillcustnum+plan.getFactWillcustnum())>=plan.getPlanWillcustnum() &&
						(factSigncustnum+plan.getFactSigncustnum())>=plan.getPlanSigncustnum() &&
						(factMoney+plan.getFactMoney())>=plan.getPlanMoney()){
					entity.setPlanStatus("0");
				}
			}
			
			planGroupmonthMapper.updateFactNum(entity);
		}else {
			if(sum==0){
				//logger.warn("无团队月计划！groupId:"+groupId+" date:"+DateUtil.formatDate(date));
			}
		}
		TeamGroupBean group = tsmTeamGroupService.getById(orgId, groupId);
		String pGroupId = group.getPid();
		if(pGroupId!=null){
			sum = sum+1;
			updateFactNum(orgId, pGroupId, factWillcustnum, factSigncustnum, factMoney, date,sum);
		}
	}
	
	//更新计划偏差值 同时更新总数量
	public int updatePlanWrapNum(String orgId,String id,int warpSigncustnum,int warpWillcustnum,double warpMoney,String warpDesc){
		PlanGroupmonthBean dbBean = getById(orgId, id);
		if(dbBean!=null){
			double dbWarpMoney = dbBean.getWarpMoney();
			int dbWarpSigncustnum = dbBean.getWarpSigncustnum();
			int dbWarpWillcustnum = dbBean.getWarpWillcustnum();
			if(dbWarpMoney!=warpMoney||dbWarpSigncustnum!=warpSigncustnum||dbWarpWillcustnum!=warpWillcustnum||warpDesc!=null){
				PlanGroupmonthBean bean = new PlanGroupmonthBean();
				bean.setId(id);
				bean.setOrgId(orgId);
				bean.setWarpMoney(warpMoney);
				bean.setWarpSigncustnum(warpSigncustnum);
				bean.setWarpWillcustnum(warpWillcustnum);
				if(warpDesc!=null) bean.setWarpDesc(warpDesc);
				planGroupmonthMapper.updatePlanAddNum(bean);
				
				double addPlanMoney = warpMoney-dbWarpMoney;
				int addPlanSigncustnum = warpSigncustnum-dbWarpSigncustnum;
				int addPlanWillcustnum =	warpWillcustnum-dbWarpWillcustnum;
				
				updatePlanNum(id, orgId, addPlanSigncustnum, addPlanWillcustnum, addPlanMoney);
				return 1;
			}else{
				return 0;
			}
		}else{
			logger.warn("数据库中不存在部门计划orgId:"+orgId+"\tid:"+id);
			return -1;
		}
	}
	
	public void updateGroupPlan(String orgId,String groupId,String planYear,String planMonth,PlanGroupmonthBean groupPlan){
		String[] groupIds = getGroupIds(orgId, groupId);
		PlanGroupmonthBean teamSum = querySumByGroups(orgId,groupIds, planYear, planMonth);
		if(teamSum!=null){
			groupPlan.setPlanMoney(BigDecimal.valueOf(teamSum.getPlanMoney()).add(BigDecimal.valueOf(groupPlan.getWarpMoney())).doubleValue());
			groupPlan.setPlanWillcustnum(teamSum.getPlanWillcustnum()+groupPlan.getWarpWillcustnum());
			groupPlan.setPlanSigncustnum(teamSum.getPlanSigncustnum()+groupPlan.getWarpSigncustnum());
			planGroupmonthMapper.updateTrends(groupPlan);
		}
	}
	
	public void updateTeamPlan(String orgId,String groupId,String planYear,String planMonth,PlanGroupmonthBean groupPlan){
		PlanUsermonthBean userSum = userService.querySumByGroup(orgId, groupId, planYear, planMonth);
		if(userSum!=null){
			groupPlan.setPlanMoney(BigDecimal.valueOf(userSum.getPlanMoney()).add(BigDecimal.valueOf(groupPlan.getWarpMoney())).doubleValue());
			groupPlan.setPlanWillcustnum(userSum.getPlanWillcustnum()+groupPlan.getWarpWillcustnum());
			groupPlan.setPlanSigncustnum(userSum.getPlanSigncustnum()+groupPlan.getWarpSigncustnum());
			planGroupmonthMapper.updateTrends(groupPlan);
		}
	}
	
	public ResultDTO save(ShiroUser user , boolean isNew,PlanGroupmonthBean bean){
		TeamGroupBean group = tsmTeamGroupService.getById(user.getOrgId(), bean.getGroupId());
		
		if("1".equals(bean.getStatus())&&group.getPid()!=null) {
			TeamGroupBean pGroup = tsmTeamGroupService.getById(user.getOrgId(), group.getPid());
			
			if(pGroup!=null && "1".equals(pGroup.getGroupType())){
				PlanGroupmonthBean groupPlan = getByGroupIdAndTime(user.getOrgId(), pGroup.getGroupId(), bean.getPlanYear(),bean.getPlanMonth());
				if(groupPlan !=null && "1".equals(groupPlan.getStatus())){
					return ResultDTO.erro("上级部门计划已经上报，无法继续上报！");
				}
			}
		}
		
		
		if(bean.getGroupType()==0){
			//团队计划
			PlanUsermonthBean userSum = userService.querySumByGroup(bean.getOrgId(), bean.getGroupId(), bean.getPlanYear(), bean.getPlanMonth());
			if(userSum == null){
				userSum = new PlanUsermonthBean();
				userSum.setPlanWillcustnum(0);
				userSum.setPlanSigncustnum(0);
				userSum.setPlanMoney(0d);
				
				userSum.setFactWillcustnum(0);
				userSum.setFactSigncustnum(0);
				userSum.setFactMoney(0d);
			}
			bean.setPlanMoney(BigDecimal.valueOf(userSum.getPlanMoney()).add(BigDecimal.valueOf(bean.getWarpMoney())).doubleValue());
			bean.setPlanWillcustnum(userSum.getPlanWillcustnum()+bean.getWarpWillcustnum());
			bean.setPlanSigncustnum(userSum.getPlanSigncustnum()+bean.getWarpSigncustnum());
			
			bean.setFactMoney(userSum.getFactMoney());
			bean.setFactWillcustnum(userSum.getFactWillcustnum());
			bean.setFactSigncustnum(userSum.getFactSigncustnum());
		}else{
			//部门计划
			String[] groupIds = getGroupIds(bean.getOrgId(), bean.getGroupId());
			PlanGroupmonthBean teamSum = querySumByGroups(bean.getOrgId(),groupIds, bean.getPlanYear(), bean.getPlanMonth());
			if(teamSum == null){
				teamSum = new PlanGroupmonthBean();
				teamSum.setPlanWillcustnum(0);
				teamSum.setPlanSigncustnum(0);
				teamSum.setPlanMoney(0d);
				
				teamSum.setFactWillcustnum(0);
				teamSum.setFactSigncustnum(0);
				teamSum.setFactMoney(0d);
			}
			bean.setPlanMoney(BigDecimal.valueOf(teamSum.getPlanMoney()).add(BigDecimal.valueOf(bean.getWarpMoney())).doubleValue());
			bean.setPlanWillcustnum(teamSum.getPlanWillcustnum()+bean.getWarpWillcustnum());
			bean.setPlanSigncustnum(teamSum.getPlanSigncustnum()+bean.getWarpSigncustnum());
			bean.setFactMoney(teamSum.getFactMoney());
			bean.setFactWillcustnum(teamSum.getFactWillcustnum());
			bean.setFactSigncustnum(teamSum.getFactSigncustnum());
		}
		
		PlanGroupmonthBean query = new PlanGroupmonthBean();
		query.setOrgId(user.getOrgId());
		query.setGroupId(bean.getGroupId());
		query.setPlanYear(bean.getPlanYear());
		query.setPlanMonth(bean.getPlanMonth());
		PlanGroupmonthBean dbPlan = planGroupmonthMapper.getByCondtion(query);
		if(dbPlan==null){
			bean = newBean(user, bean);
			planGroupmonthMapper.insert(bean);
		}else{
			if("1".equals(bean.getStatus())) bean.setAuthState("1"); 
			bean.setOrgId(user.getOrgId());
			planGroupmonthMapper.updateTrends(bean);
		}
		
		if(bean.getGroupType()==0 && group!=null && group.getPid()!=null){
			//团队计划
			if("1".equals(bean.getStatus())) tsmMessageService.createAuditDailyGroupMonthlyMessage(bean.getId(),  group.getPid(), group.getGroupName(), AppConstant.MSG_TYPE_AUDIT_GROUP_MONTHLY);
		}else{
			//部门计划
			if("1".equals(bean.getStatus())) tsmMessageService.createAuditDailyGroupMonthlyMessage(bean.getId(),  group.getPid(), group.getGroupName(), AppConstant.MSG_TYPE_AUDIT_DEPAT_MONTHLY);
		}
		return ResultDTO.Success();
	}
	
	public PlanGroupmonthBean newBean(ShiroUser user ,PlanGroupmonthBean bean){
		Date date = new Date();
		/*String planMonth =bean.getPlanMonth();
		String planyear = bean.getPlanYear();
		if(DateUtil.year(date)==Integer.parseInt(planyear)&&DateUtil.month(date)==Integer.parseInt(planMonth)){
			List<String> childGroupIds = tsmTeamGroupService.findAllSonGroupIds(user.getOrgId(), bean.getGroupId());
	    	childGroupIds.add(bean.getGroupId());
			List<TsmTeamGroupMemberBean> 	members = tsmTeamGroupMemberService.findByGroupIds(user.getOrgId(),childGroupIds.toArray(new String[childGroupIds.size()]));
    		String[] userids = tsmTeamGroupMemberService.getUserIds(members);
			
			PlanUsermonthToWillLogBean will = planUsermonthToWillLogService.findSumFact(user.getOrgId(), userids, date);
			PlanUsermonthToSignLogBean sign = planUsermonthToSignLogService.findSumFact(user.getOrgId(), userids, date);
			
			double factMoney = sign.getMoney();
			if(factMoney!=0){
				BigDecimal bigDecimal =new BigDecimal(factMoney/10000);  
				factMoney = bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();  
			}
			bean.setFactWillcustnum(will.getWillNum());
			bean.setFactSigncustnum(sign.getSignNum());
			bean.setFactMoney(factMoney);
		}else{
			bean.setFactMoney(0d);
			bean.setFactSigncustnum(0);
			bean.setFactWillcustnum(0);
		}*/
		
		//bean.setId(GuidUtil.getId());
		bean.setOrgId(user.getOrgId());
		//bean.setGroupId(groupId);
		//bean.setGroupType(groupType);
		//bean.setPlanYear(planYear);
		//bean.setPlanMonth(planMonth);
		//bean.setPlanMoney(0d);
		//bean.setPlanSigncustnum(0);
		//bean.setPlanWillcustnum(0);
		//bean.setWarpMoney(0d);
		//bean.setWarpSigncustnum(0);
		//bean.setWarpWillcustnum(0);
		bean.setPlanMoneyState(0);
		bean.setPlanSigncustnumState(0);
		bean.setPlanWillcustnumState(0);
		bean.setAuthState("1");
		bean.setPlanStatus("0");
		bean.setInputtime(date);
		bean.setUpdatetime(date);
		bean.setIsDel(0);
		
		return bean;
	}
	
	public PlanGroupmonthBean newBean(String orgId,String groupId,String planYear,String planMonth,int groupType){
		PlanGroupmonthBean bean = new PlanGroupmonthBean();
		Date date = new Date();
		
		bean.setId(GuidUtil.getId());
		bean.setOrgId(orgId);
		bean.setGroupId(groupId);
		bean.setGroupType(groupType);
		bean.setPlanYear(planYear);
		bean.setPlanMonth(planMonth);
		bean.setPlanMoney(0d);
		bean.setPlanSigncustnum(0);
		bean.setPlanWillcustnum(0);
		bean.setFactMoney(0d);
		bean.setFactSigncustnum(0);
		bean.setFactWillcustnum(0);
		bean.setWarpMoney(0d);
		bean.setWarpSigncustnum(0);
		bean.setWarpWillcustnum(0);
		bean.setPlanMoneyState(0);
		bean.setPlanSigncustnumState(0);
		bean.setPlanWillcustnumState(0);
		bean.setAuthState("1");
		bean.setStatus("0");
		bean.setPlanStatus("0");
		bean.setInputtime(date);
		bean.setUpdatetime(date);
		bean.setIsDel(0);
		return bean;
	}
	/*public String getPGroupId(String orgId,String groupId){
		OrgGroup group = groupService.getByGroupId(orgId, groupId);
		if(group!=null && group.getpId()!=null){
			return group.getpId();
		}else{
			return null;
		}
	}*/
	
	public String[] getGroupIds(String orgId,String groupId){
		List<TeamGroupBean> groups = tsmTeamGroupService.findSonGroups(orgId, groupId);
		List<String> list = new ArrayList<String>(groups.size());
		for(int i=0;i<groups.size();i++){
			String gid = groups.get(i).getGroupId();
			if(gid!=null && !list.contains(gid)) {
				list.add(gid);
			}
		}
		String [] array=new String[list.size()];
		return list.toArray(array);
	}
	
	public String[] getGroupIds1(String orgId,String groupId){
		List<TeamGroupBean> groups = tsmTeamGroupService.findSonGroups(orgId, groupId);
		List<String> list = new ArrayList<String>(groups.size());
		for(int i=0;i<groups.size();i++){
			String gid = groups.get(i).getGroupId();
			if(gid!=null && !list.contains(gid)) {
				list.add(gid);
				
				String[] sonGroupIds = getGroupIds(orgId, gid);
				if(sonGroupIds.length>0) list.addAll(Arrays.asList(sonGroupIds));
			}
		}
		String [] array=new String[list.size()];
		return list.toArray(array);
	}
	//未提交日志的部门
	public List<String> findNoReportGroups(String orgId,Date date){
		PlanGroupmonthBean entity= new PlanGroupmonthBean();
		entity.setOrgId(orgId);
		entity.setPlanMonth(String.valueOf(DateUtil.month(date)));
		entity.setPlanYear(String.valueOf(DateUtil.year(date)));
		return planGroupmonthMapper.findNoReportGroups(entity);
	}
	
	public PlanGroupmonthBean getTotal(List<PlanGroupmonthBean> list){
		PlanGroupmonthBean total = new PlanGroupmonthBean();
		total.setGroupName("合计");
		total.setPlanMoney(0d);
		total.setPlanSigncustnum(0);
		total.setPlanWillcustnum(0);
		total.setFactMoney(0d);
		total.setFactSigncustnum(0);
		total.setFactWillcustnum(0);
		total.setWarpMoney(0d);
		total.setWarpSigncustnum(0);
		total.setWarpWillcustnum(0);
		total.setPlanMoneyState(0);
		total.setPlanSigncustnumState(0);
		total.setPlanWillcustnumState(0);
		if(list!=null&& list.size()>0){
			for (PlanGroupmonthBean planGroupmonthBean : list) {
				total.setPlanMoney(BigDecimal.valueOf(total.getPlanMoney()).add(BigDecimal.valueOf(planGroupmonthBean.getPlanMoney())).doubleValue());
				total.setFactMoney(BigDecimal.valueOf(total.getFactMoney()).add(BigDecimal.valueOf(planGroupmonthBean.getFactMoney())).doubleValue());
				
				total.setPlanSigncustnum(total.getPlanSigncustnum()+planGroupmonthBean.getPlanSigncustnum());
				total.setPlanWillcustnum(total.getPlanWillcustnum()+planGroupmonthBean.getPlanWillcustnum());
				total.setFactSigncustnum(total.getFactSigncustnum()+planGroupmonthBean.getFactSigncustnum());
				total.setFactWillcustnum(total.getFactWillcustnum()+planGroupmonthBean.getFactWillcustnum());
			}
		}
		return total;
	}
}
