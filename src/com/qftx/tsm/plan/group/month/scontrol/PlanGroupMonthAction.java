package com.qftx.tsm.plan.group.month.scontrol;

import com.qftx.base.auth.service.RoleResourceService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.team.bean.TeamGroupBean;
import com.qftx.base.team.service.TsmTeamGroupService;
import com.qftx.base.util.DateUtil;
import com.qftx.common.util.StringUtils;
import com.qftx.tsm.plan.CustomFieldUtils;
import com.qftx.tsm.plan.group.month.bean.PlanGroupmonthAnalyBean;
import com.qftx.tsm.plan.group.month.bean.PlanGroupmonthBean;
import com.qftx.tsm.plan.group.month.dto.AnalyDTO1;
import com.qftx.tsm.plan.group.month.dto.PlanGroupmonthDTO;
import com.qftx.tsm.plan.group.month.dto.PlanTeamMonthDTO;
import com.qftx.tsm.plan.group.month.service.PlanGroupmonthAnalyService;
import com.qftx.tsm.plan.group.month.service.PlanGroupmonthService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/plan/month/group")
public class PlanGroupMonthAction {
	@Autowired
	private PlanGroupmonthService service;
	@Autowired
	PlanGroupmonthAnalyService analyService;
	@Autowired
	private TsmTeamGroupService tsmTeamGroupService;
	@Autowired
    private RoleResourceService roleResourceService;
	@Autowired
	private CustomFieldUtils customFieldUtils;
	Logger logger= Logger.getLogger(PlanGroupMonthAction.class);
	// 全年计划查看
	@RequestMapping("/yearView")
	public String yearView(ModelMap modelMap,String groupId,String groupName,String planYear,String planMonth,boolean isNotFirst) {
		ShiroUser user = getUser();
		if(planYear==null){
			planYear = String.valueOf(DateUtil.year(new Date()));
		}
		if(planMonth==null){
			planMonth = String.valueOf(DateUtil.month(new Date()));
		}
		
		List<TeamGroupBean> groups = tsmTeamGroupService.queryManageTeamGroupList(user.getOrgId(), user.getAccount());
		if(groups.size()>0){
			try {
				if(StringUtils.isBlank(groupId)){
					TeamGroupBean group = tsmTeamGroupService.getFatherTeam(groups);
					groupId = group.getGroupId();
					groupName = group.getGroupName();
				}else{
					for (TeamGroupBean group : groups) {
						if(groupId.equals(group.getGroupId())){
							groupName = group.getGroupName();
							break;
						}
					}
				}

				Map<String, PlanTeamMonthDTO> plansMap = service.getPlansFullYear(user.getOrgId(), groupId, planYear);
				Date autoAuthTime = customFieldUtils.getAutoAuthTime(user.getOrgId());
				modelMap.put("autoAuthTime", autoAuthTime.getTime());
				modelMap.put("currentTime", new Date().getTime());
				
				modelMap.put("currentYear", DateUtil.year(new Date()));
				modelMap.put("currentMonth", DateUtil.month(new Date()));
				modelMap.put("planMonth", planMonth);
				
				modelMap.put("isNotFirst", isNotFirst);
				modelMap.put("plansMap", plansMap);
				modelMap.put("planYear", planYear);
				modelMap.put("groups", groups);
				modelMap.put("groupId", groupId);
				modelMap.put("groupName", groupName);
				modelMap.put("months", new String[]{"1","2","3","4","5","6","7","8","9","10","11","12"});
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
			return "/plan/month/group/year_view";
		}else{
			return "/error/no_root";
		}
		
	}
	
	//团队月计划分析
	@RequestMapping("/monthAnaly")
	public String monthAnaly(ModelMap map,String groupId,Integer planYear,Integer planMonth){
		
		try {
			ShiroUser user = getUser();
			
			if(planYear==null || planMonth ==null){
				planYear= DateUtil.year(new Date());
				planMonth= DateUtil.month(new Date());
			}
			
			AnalyDTO1 dto = analyService.getAnalyDTO(user.getOrgId(), groupId, planYear, planMonth);
			
			List<PlanGroupmonthAnalyBean> list = analyService.findChildAnalyByDate(user.getOrgId(), groupId, DateUtil.getDate(planYear, planMonth, 1));
			
			map.put("list", list);
			map.put("dto", dto);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		return "/plan/month/group/month_analy";
	}
		
	//团队月计划查看(未执行)
	@RequestMapping("/monthView")
	public String monthView(ModelMap map,String id){
		try {
			ShiroUser user = getUser();
			PlanGroupmonthBean plan = service.getById(user.getOrgId(), id);
			PlanGroupmonthDTO dto = service.getPlanGroupmonthDTO(user.getOrgId(), plan);
			
			TeamGroupBean group = tsmTeamGroupService.getById(user.getOrgId(), plan.getGroupId());
			String pid = group.getPid();
			if(pid!=null){
				TeamGroupBean parentGroup = tsmTeamGroupService.getById(user.getOrgId(), pid);
				if(parentGroup !=null && "1".equals(parentGroup.getGroupType())) map.put("pGroupId", pid);
			}
			
			map.put("autoAuthDay",customFieldUtils.getAutoAuthDay(user.getOrgId()));
			map.put("plan", dto.getGroupPlan());
			map.put("plans", dto.getTeamPlans());
			map.put("teamPlansSum", dto.getTeamPlansSum());
			map.put("planSaleYearBean", dto.getPlanSaleYearBean());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		return "/plan/month/group/month_view";
	}
	
	//团队月计划查看(执行)
	@RequestMapping("/monthView_p")
	public String monthView_p(ModelMap map,String id){
		try {
			ShiroUser user = getUser();
			PlanGroupmonthBean plan = service.getById(user.getOrgId(), id);
			PlanGroupmonthDTO dto = service.getPlanGroupmonthDTO(user.getOrgId(), plan);
			
			TeamGroupBean group = tsmTeamGroupService.getById(user.getOrgId(), plan.getGroupId());
			String pid = group.getPid();
			if(pid!=null){
				TeamGroupBean parentGroup = tsmTeamGroupService.getById(user.getOrgId(), pid);
				if(parentGroup !=null && "1".equals(parentGroup.getGroupType())) map.put("pGroupId", pid);
			}
			
			map.put("autoAuthDay",customFieldUtils.getAutoAuthDay(user.getOrgId()));
			map.put("plan", dto.getGroupPlan());
			map.put("plans", dto.getTeamPlans());
			map.put("teamPlansSum", dto.getTeamPlansSum());
			map.put("planSaleYearBean", dto.getPlanSaleYearBean());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return "/plan/month/group/month_view_p";
	}
	
	//团队月计划编辑
	@RequestMapping("/monthEdit")
	public String monthEdit(ModelMap map,boolean isNew,boolean isFirst,PlanGroupmonthBean item){
		try {
			ShiroUser user = getUser();
			PlanGroupmonthBean plan = null;
			
			TeamGroupBean group = tsmTeamGroupService.getById(user.getOrgId(), item.getGroupId());
			if(isNew){
				plan = service.newBean(user.getOrgId(), item.getGroupId(), item.getPlanYear(), item.getPlanMonth(),1);
				plan.setGroupName(group.getGroupName());
			}else{
				plan = service.getById(user.getOrgId(), item.getId());
			}
			PlanGroupmonthDTO dto = service.getPlanGroupmonthDTO(user.getOrgId(), plan);
			PlanGroupmonthBean teamPlansSum = dto.getTeamPlansSum();
			if(!isFirst){
				plan.setPlanWillcustnum(teamPlansSum.getPlanWillcustnum() +item.getWarpWillcustnum());
				plan.setPlanSigncustnum(teamPlansSum.getPlanSigncustnum() +item.getWarpSigncustnum());
				plan.setPlanMoney(BigDecimal.valueOf(teamPlansSum.getPlanMoney()).add(BigDecimal.valueOf(item.getWarpMoney())).doubleValue());
				
				plan.setWarpWillcustnum(item.getWarpWillcustnum());
				plan.setWarpSigncustnum(item.getWarpSigncustnum());
				plan.setWarpMoney(item.getWarpMoney());
				plan.setWarpDesc(item.getWarpDesc());
			}else{
				plan.setPlanWillcustnum(teamPlansSum.getPlanWillcustnum() +plan.getWarpWillcustnum());
				plan.setPlanSigncustnum(teamPlansSum.getPlanSigncustnum() +plan.getWarpSigncustnum());
				plan.setPlanMoney(BigDecimal.valueOf(teamPlansSum.getPlanMoney()).add(BigDecimal.valueOf(plan.getWarpMoney())).doubleValue());
			}
			
			String pid = group.getPid();
			if(pid!=null){
				TeamGroupBean parentGroup = tsmTeamGroupService.getById(user.getOrgId(), pid);
				if(parentGroup !=null && "1".equals(parentGroup.getGroupType())) map.put("pGroupId", pid);
			}
			
			map.put("autoAuthDay",customFieldUtils.getAutoAuthDay(user.getOrgId()));
			map.put("isNew", isNew);
			map.put("plan", dto.getGroupPlan());
			map.put("plans", dto.getTeamPlans());
			map.put("teamPlansSum", dto.getTeamPlansSum());
			map.put("planSaleYearBean", dto.getPlanSaleYearBean());
			//map.put("lastDay", DateUtil.day(customFieldUtils.getAutoAuthTime(user.getOrgId())));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		return "/plan/month/group/month_edit";
	}
	
	//月计划详情弹窗（团队月计划引用）
	@RequestMapping("/detailWindow")
	public String detailWindow(ModelMap map,String planId,boolean isFuture){
		try {
			ShiroUser user = getUser();
			PlanGroupmonthBean plan = service.getById(user.getOrgId(), planId);
			PlanGroupmonthDTO dto = service.getPlanGroupmonthDTO(user.getOrgId(), plan);
			
			map.put("autoAuthDay",customFieldUtils.getAutoAuthDay(user.getOrgId()));
			map.put("plan", dto.getGroupPlan());
			map.put("plans", dto.getTeamPlans());
			map.put("teamPlansSum", dto.getTeamPlansSum());
			map.put("planSaleYearBean", dto.getPlanSaleYearBean());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		if(isFuture){
			return "/plan/month/group/detail_window";
		}else{
			return "/plan/month/group/detail_window_p";
		}
	}
	
	//个人月计划查看(未执行)
	@RequestMapping("/authView")
	public String authView(ModelMap map,PlanGroupmonthBean item,String groupIds,String authUserIds,String authUserAccs,Integer oDateType){
		try {
			ShiroUser user = getUser();
			
			item.setOrgId(user.getOrgId());
			if(StringUtils.isBlank(item.getAuthState())){
				item.setAuthState(null);
			}
			
			if(StringUtils.isBlank(item.getPlanMonth())) item.setPlanMonth(null);
			
			if(StringUtils.isBlank(item.getPlanYear())) item.setPlanYear(null);
			
			if(StringUtils.isBlank(item.getStartInputtime())) item.setStartInputtime(null);
			
			if(StringUtils.isBlank(item.getEndInputtime())) item.setEndInputtime(null);
			
			if(StringUtils.isNotBlank(authUserIds)){
				item.setAuthUserIds(authUserIds.split(","));
			}
			
			if (oDateType != null && !"".equals(oDateType) && oDateType!=0 && oDateType!=5) {
				item.setStartInputtime(getStartDateStr(new Integer(oDateType)));
				item.setEndInputtime(getEndDateStr(new Integer(oDateType)));
			}
			
			if(StringUtils.isNotBlank(groupIds)){
				item.setGroupIds(groupIds.split(","));
			}else{
				List<TeamGroupBean> groups = tsmTeamGroupService.queryManageGroupList(user.getGroupId(), user.getAccount());
				if(groups!=null && groups.size()>0){
					String[] gids = new String[groups.size()];
					for(int i=0;i<groups.size();i++){
						gids[i] = groups.get(i).getGroupId();
					}
					item.setGroupIds(gids);
				} 
			}
			item.setStatus("1");
			List<PlanGroupmonthBean> list = service.findListPage(item);
			
			map.put("groupIds",groupIds);
			map.put("authUserIds",authUserIds);
			map.put("authUserAccs", authUserAccs);
			map.put("oDateType", oDateType);
			
			map.put("list", list);
			map.put("item", item);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		return "/plan/month/team/auth_view";
	}
	
	/**
	 * 获取第一天
	 * 
	 * @param type
	 *            1-当天 2-本周 3-本月 4-半年
	 * @return
	 * @create 2015年12月14日 下午3:48:05 lixing
	 * @history
	 */
	public String getStartDateStr(Integer type) {
		String str = "";
		if (type == 1) {
			str = DateUtil.formatDate(new Date(), DateUtil.DATE_DAY);
		} else if (type == 2) {
			str = DateUtil.getWeekFirstDay(new Date());
		} else if (type == 3) {
			str = DateUtil.getMonthFirstDay(new Date());
		} else if (type == 4) {
			str = DateUtil.formatDate(DateUtil.getAddDate(new Date(), -180), DateUtil.DATE_DAY);
		}
		return str;
	}
	
	/**
	 * 获取最后一天
	 * 
	 * @param type
	 *            1-当天 2-本周 3-本月 4-半年
	 * @return
	 * @create 2015年12月14日 下午3:48:05 lixing
	 * @history
	 */
	public String getEndDateStr(Integer type) {
		String str = "";
		if (type == 1 || type==4) {
			str = DateUtil.formatDate(DateUtil.dateEnd(new Date()), DateUtil.DATE_DAY);
		} else if (type == 2) {
			str = DateUtil.getWeekLastDay(new Date());
		} else if (type == 3) {
			str = DateUtil.getMonthLastDay(new Date());
		}  
		return str;
	}
	
	public ShiroUser getUser() {
		ShiroUser user = ShiroUtil.getShiroUser();
		return user;
	}
}
