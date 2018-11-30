package com.qftx.tsm.plan.group.month.scontrol;

import com.qftx.base.auth.service.RoleResourceService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.team.bean.TeamGroupBean;
import com.qftx.base.team.service.TsmTeamGroupService;
import com.qftx.base.util.DateUtil;
import com.qftx.common.util.StringUtils;
import com.qftx.tsm.plan.CustomFieldUtils;
import com.qftx.tsm.plan.ResultDTO;
import com.qftx.tsm.plan.group.month.bean.PlanGroupmonthBean;
import com.qftx.tsm.plan.group.month.bean.PlanGroupmonthCommontBean;
import com.qftx.tsm.plan.group.month.bean.PlanStatus;
import com.qftx.tsm.plan.group.month.dto.PlanTeamMonthDTO;
import com.qftx.tsm.plan.group.month.service.PlanGroupmonthCommontService;
import com.qftx.tsm.plan.group.month.service.PlanGroupmonthService;
import com.qftx.tsm.plan.user.month.bean.PlanUsermonthBean;
import com.qftx.tsm.plan.user.month.service.PlanUserMonthService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/plan/month/team")
public class PlanTeamMonthAction {
	@Autowired
	private PlanGroupmonthService service;
	@Autowired
	private PlanUserMonthService userService;
	@Autowired
	private PlanGroupmonthCommontService commontService;
	@Autowired
	private TsmTeamGroupService tsmTeamGroupService;
	@Autowired
    private RoleResourceService roleResourceService;
	@Autowired
	private CustomFieldUtils customFieldUtils;
	private Logger logger = Logger.getLogger(PlanTeamMonthAction.class);
	
	// 全年计划查看
	@RequestMapping("/yearView")
	public String yearView(ModelMap modelMap,String groupId,String groupName,String planYear) {
		ShiroUser user = getUser();
		if(planYear==null){
			planYear = String.valueOf(DateUtil.year(new Date()));
		}
		
		List<TeamGroupBean> groups = tsmTeamGroupService.queryManageTeamList(user.getOrgId(), user.getAccount());
		
		if(groups.size()>0){
			try {
				if(StringUtils.isBlank(groupId)){
					groupId = groups.get(0).getGroupId();
					groupName = groups.get(0).getGroupName();
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
				modelMap.put("currentMonth", DateUtil.month(new Date()));
				modelMap.put("currentYear", DateUtil.year(new Date()));
				modelMap.put("plansMap", plansMap);
				modelMap.put("planYear", planYear);
				modelMap.put("groups", groups);
				modelMap.put("groupId", groupId);
				modelMap.put("groupName", groupName);
				modelMap.put("months", new String[]{"1","2","3","4","5","6","7","8","9","10","11","12"});
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
			return "/plan/month/team/year_view";
		}else{
			return "/error/no_root";
		}
	}
	
	//团队月计划查看(未执行)
	@RequestMapping("/monthView")
	public String monthView(ModelMap map,String id){
		try {
			ShiroUser user = getUser();
			PlanGroupmonthBean plan = service.getById(user.getOrgId(), id);
			PlanTeamMonthDTO dto = service.getPlanTeammonthDTO(user.getOrgId(), plan);
			
			map.put("autoAuthDay",customFieldUtils.getAutoAuthDay(user.getOrgId()));
			map.put("planSaleYearBean", dto.getPlanSaleYearBean());
			map.put("plan", dto.getTeamPlan());
			map.put("userPlans", dto.getUserPlans());
			map.put("userPlansSum", dto.getUserPlansSum());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		return "/plan/month/team/month_view";
	}
	
	//团队月计划查看(执行)
	@RequestMapping("/monthView_p")
	public String monthView_p(ModelMap map,String id){
		try {
			ShiroUser user = getUser();
			
			PlanGroupmonthBean plan = service.getById(user.getOrgId(), id);
			PlanTeamMonthDTO dto = service.getPlanTeammonthDTO(user.getOrgId(), plan);
			map.put("autoAuthDay",customFieldUtils.getAutoAuthDay(user.getOrgId()));
			map.put("planSaleYearBean", dto.getPlanSaleYearBean());
			map.put("plan", dto.getTeamPlan());
			map.put("userPlans", dto.getUserPlans());
			map.put("userPlansSum", dto.getUserPlansSum());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		return "/plan/month/team/month_view_p";
	}
	
	//团队月计划编辑
	@RequestMapping("/monthEdit")
	public String monthEdit(ModelMap map,boolean isNew,boolean isFirst,PlanGroupmonthBean item){
		try {

			ShiroUser user = getUser();
			PlanGroupmonthBean plan = null;
			
			if(isNew){
				plan = service.newBean(user.getOrgId(), item.getGroupId(), item.getPlanYear(), item.getPlanMonth(),0);
				
				TeamGroupBean group = tsmTeamGroupService.getById(user.getOrgId(), item.getGroupId());
				plan.setGroupName(group.getGroupName());
			}else{
				plan = service.getById(user.getOrgId(), item.getId());
			}
			
			PlanTeamMonthDTO dto = service.getPlanTeammonthDTO(user.getOrgId(),plan);
			PlanUsermonthBean userPlanSum = dto.getUserPlansSum();
			
			if(!isFirst){
				plan.setPlanWillcustnum(userPlanSum.getPlanWillcustnum() +item.getWarpWillcustnum());
				plan.setPlanSigncustnum(userPlanSum.getPlanSigncustnum() +item.getWarpSigncustnum());
				plan.setPlanMoney(BigDecimal.valueOf(userPlanSum.getPlanMoney()).add(BigDecimal.valueOf(item.getWarpMoney())).doubleValue());
				
				plan.setWarpWillcustnum(item.getWarpWillcustnum());
				plan.setWarpSigncustnum(item.getWarpSigncustnum());
				plan.setWarpMoney(item.getWarpMoney());
				plan.setWarpDesc(item.getWarpDesc());
			}else{
				plan.setPlanWillcustnum(userPlanSum.getPlanWillcustnum() +plan.getWarpWillcustnum());
				plan.setPlanSigncustnum(userPlanSum.getPlanSigncustnum() +plan.getWarpSigncustnum());
				plan.setPlanMoney(BigDecimal.valueOf(userPlanSum.getPlanMoney()).add(BigDecimal.valueOf(plan.getWarpMoney())).doubleValue());
			}
			
			Date autoAuthTime = customFieldUtils.getAutoAuthTime(user.getOrgId());
			map.put("autoAuthTime", DateUtil.day(autoAuthTime));
			map.put("autoAuthDay",customFieldUtils.getAutoAuthDay(user.getOrgId()));
			map.put("isNew", isNew);
			map.put("planSaleYearBean", dto.getPlanSaleYearBean());
			map.put("plan", dto.getTeamPlan());
			map.put("userPlans", dto.getUserPlans());
			map.put("userPlansSum", dto.getUserPlansSum());
			map.put("planSaleYearBean", dto.getPlanSaleYearBean());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return "/plan/month/team/month_edit";
	}
	
	//月计划调整弹窗
	@RequestMapping("/warpWindow")
	public String warpWindow(ModelMap modelMap,PlanGroupmonthBean item){
		try {
			ShiroUser user = getUser();
			//PlanUsermonthBean userPlanCount = userService.querySumByGroup(user.getOrgId(), item.getGroupId(), item.getPlanYear(), item.getPlanMonth());
			
			//modelMap.put("userPlanCount", userPlanCount);
			modelMap.put("plan", item);
			modelMap.put("planId", item.getId());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		return "/plan/month/team/warp_window";
	}
	
	//保存团队月计划
	@RequestMapping("/saveTeamPlan")
	@ResponseBody
	public ResultDTO saveTeamPlan(boolean isNew,PlanGroupmonthBean item){
		try {
			ShiroUser user = getUser();
			if(item!=null && item.getWarpWillcustnum()!=null &&item.getWarpSigncustnum()!=null && item.getWarpMoney()!=null){
				return service.save(user, isNew, item);
			}else{
				return ResultDTO.erro("偏差值不能为空");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return ResultDTO.erro("系统错误");
		}
	}
	
	//获取团队成员计划（是否上报 是否审核）
	@RequestMapping("/getPlanStatus")
	@ResponseBody
	public PlanStatus getPlanStatus(boolean isNew,PlanGroupmonthBean item){
		ShiroUser user = getUser();
		PlanStatus planStatus = service.getPlanStatusByMonth(user.getOrgId(), item.getGroupId(), item.getPlanYear(), item.getPlanMonth(),item.getGroupType());
		return planStatus;
	}
	
	//月计划历史走势
	@RequestMapping("/historyWindow")
	public String historyWindow(ModelMap modelMap,String groupId){
		try {
			Date toDate =new Date();
			Date fromDate = DateUtil.addDate(toDate, 0, -5, 0);
			modelMap.put("groupId", groupId);
			modelMap.put("fromDate",fromDate);
			modelMap.put("toDate", toDate);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		return "/plan/month/team/history_window";
	}
	
	//月计划历史走势
	@RequestMapping("/historyJson")
	@ResponseBody
	public List<PlanGroupmonthBean> historyJson(String groupId,Long fromTime,Long toTime){
		
		try {
			ShiroUser user = getUser();
			if(fromTime==null ||toTime==null){
				toTime =System.currentTimeMillis();
				fromTime = DateUtil.addDate(new Date(), 0, -5, 0).getTime();
			}
			
			if(StringUtils.isBlank(groupId)){
				groupId = user.getGroupId();
			}
			return service.queryHistory(user.getOrgId(),groupId, new Date(fromTime), new Date(toTime));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return new ArrayList<PlanGroupmonthBean>();
		}
		
		
	}
	
	//月计划详情弹窗（团队月计划引用）
	@RequestMapping("/detailWindow")
	public String detailWindow(ModelMap map,String planId,boolean isFuture){
		try {
			ShiroUser user = getUser();
			PlanGroupmonthBean plan = service.getById(user.getOrgId(), planId);
			PlanTeamMonthDTO dto = service.getPlanTeammonthDTO(user.getOrgId(), plan);
			
			map.put("autoAuthDay",customFieldUtils.getAutoAuthDay(user.getOrgId()));
			map.put("planSaleYearBean", dto.getPlanSaleYearBean());
			map.put("plan", dto.getTeamPlan());
			map.put("userPlans", dto.getUserPlans());
			map.put("userPlansSum", dto.getUserPlansSum());
			map.put("planSaleYearBean", dto.getPlanSaleYearBean());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		if(isFuture){
			return "/plan/month/team/detail_window";
		}else{
			return "/plan/month/team/detail_window_p";
		}
	}
	
	//月计划审核弹窗（团队月计划引用）
	@RequestMapping("/authWindow")
	public String authWindow(ModelMap modelMap,String planId,String groupPlanId){
		try {
			ShiroUser user = getUser();
			PlanGroupmonthBean plan = service.getById(user.getOrgId(), planId);
			PlanUsermonthBean userPlanCount = userService.querySumByGroup(user.getOrgId(), plan.getGroupId(), plan.getPlanYear(), plan.getPlanMonth());
			
			modelMap.put("plan", plan);
			modelMap.put("userPlanCount", userPlanCount);
			modelMap.put("groupPlanId", groupPlanId);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		return "/plan/month/team/auth_window";
	}
	
	//月计划点评弹窗（团队月计划引用）
	@RequestMapping("/commontWindow")
	public String commontWindow(ModelMap modelMap,String planId){
		try {
			modelMap.put("planId", planId);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		return "/plan/month/team/commont_window";
	}
	
	//月计划审核（团队月计划引用）
	@RequestMapping("/auth")
	@ResponseBody
	public Map<String, String> auth(String planIdsStr,String authState,String context){
		Map<String, String> map= new HashMap<String, String>();
		try {
			ShiroUser user = getUser();
			if(StringUtils.isBlank(context)) context = null;
			
			service.authPlan(user.getOrgId(), user.getId(), user.getName(), planIdsStr.split(","), authState, context);
			map.put("status", "success");
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			map.put("status", "erro");
		}
		return map;
	}
	
	//领取积分
	@RequestMapping("/receivePoint")
	@ResponseBody
	public Map<String, Object> showHistoryMonthPlan(String id,String type,boolean isLast){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ShiroUser user = getUser();
			if(type!=null){
				service.receivePoint(user.getOrgId(), id, type,isLast);
				map.put("status", "success");
				map.put("msg",null);
			}else{
				map.put("status", "erro");
				map.put("msg","类型为空！");
			}
		} catch (Exception e) {
			map.put("status", "erro");
			map.put("msg", "系统异常！");
			logger.error(e.getMessage(),e);
		}
		return map;
	}
	
	//月计划点评（团队月计划引用）
	@RequestMapping("/commont")
	@ResponseBody
	public Map<String, String> commont(String planId,String context){
		Map<String, String> map= new HashMap<String, String>();
		try {
			ShiroUser user = getUser();
			PlanGroupmonthCommontBean commont = commontService.insert(user.getOrgId(), user.getGroupId(), user.getId(), planId, context);
			
			PlanGroupmonthBean groupPlan = service.getById(user.getOrgId(), planId);
			if(groupPlan!=null&& groupPlan.getGroupId()!=null){
				//计划部门ID
				String planGroupId =groupPlan.getGroupId(); 
				
				
			}
			service.upDateLastCommontId(user.getOrgId(), planId, commont.getId());
			map.put("status", "success");
		} catch (Exception e) {
			map.put("status", "erro");
			logger.error(e.getMessage(),e);
		}
		return map;
	}
	
	//退回计划（团队月计划引用，与上报相反）
	@RequestMapping("/bakReport")
	@ResponseBody
	public Map<String, String> bakReport(String planId){
		Map<String, String> map= new HashMap<String, String>();
		try {
			ShiroUser user = getUser();
			service.upReportPlan(user.getOrgId(), planId, "0");
			map.put("status", "success");
		} catch (Exception e) {
			map.put("status", "erro");
			logger.error(e.getMessage(),e);
		}
		
		return map;
	}
	
	@RequestMapping("/getGroupmonthPlan")
	@ResponseBody
	public PlanGroupmonthBean getUserMonthPlan(String groupId,String year,String month){
		ShiroUser user = getUser();
		Date autoAuthTime = customFieldUtils.getAutoAuthTime(user.getOrgId());
		
		if(new Date().after(autoAuthTime)){
			PlanGroupmonthBean bean= new PlanGroupmonthBean();
			bean.setTooLateEdit(true);
			return bean;
		}else{
			return service.getByGroupIdAndTime(user.getOrgId(), groupId, year, month);
		}
	}
	
	public ShiroUser getUser() {
		ShiroUser user = ShiroUtil.getShiroUser();
		return user;
	}
}
