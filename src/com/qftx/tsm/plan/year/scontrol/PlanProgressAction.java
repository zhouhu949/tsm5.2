package com.qftx.tsm.plan.year.scontrol;

import com.qftx.base.auth.bean.OrgGroup;
import com.qftx.base.auth.service.OrgGroupService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.team.bean.TsmTeamGroupMemberBean;
import com.qftx.base.team.service.TsmTeamGroupMemberService;
import com.qftx.base.util.DateUtil;
import com.qftx.common.util.StringUtils;
import com.qftx.tsm.plan.user.month.bean.PlanUsermonthBean;
import com.qftx.tsm.plan.user.month.service.PlanUserMonthService;
import com.qftx.tsm.plan.year.bean.PlanSaleTrendBean;
import com.qftx.tsm.plan.year.bean.PlanSaleYearBean;
import com.qftx.tsm.plan.year.dto.GroupChartDTO;
import com.qftx.tsm.plan.year.dto.ProgressDto;
import com.qftx.tsm.plan.year.dto.UserChartDTO;
import com.qftx.tsm.plan.year.service.PlanProgressService;
import com.qftx.tsm.plan.year.service.PlanSaleYearService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/plan/year/progress")
public class PlanProgressAction {
	@Autowired
	private PlanSaleYearService planService;
	@Autowired
	private PlanProgressService service;
	@Autowired
	private PlanUserMonthService planUserService;
	@Autowired
	private OrgGroupService orgGroupService;
	@Autowired
	private TsmTeamGroupMemberService tsmTeamGroupMemberService;
	
	Logger logger=Logger.getLogger(PlanProgressAction.class);
	
	/*页面加载数据*/
	@RequestMapping("/view")
	public String progress(ModelMap modelMap,String planYear,String planMonth){
		try {
			ShiroUser user = getUser();
			if(StringUtils.isBlank(planYear)){
				planYear =String.valueOf(DateUtil.year(new Date()));
			}
			ProgressDto dto = service.findFullYearOrderByMonth(user.getOrgId(), planYear);
			PlanSaleTrendBean progress = planService.getPlanYearProgress(user.getOrgId(), new Date());
			int currYear=DateUtil.year(new Date());
			int currMonth = DateUtil.month(new Date());
			
			List<OrgGroup> groups = orgGroupService.findLastSaleGroup(user.getOrgId());
			
			modelMap.put("currYear", String.valueOf(currYear));
			modelMap.put("currMonth", String.valueOf(currMonth<10?("0"+currMonth):currMonth));
			modelMap.put("progress", progress);
			modelMap.put("planYear", planYear);
			modelMap.put("groups", groups);
			modelMap.put("dto", dto);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		return "/plan/year/year_progress";
	}
	
	/*页面加载数据*/
	@RequestMapping("/findAllGroupByMonth")
	@ResponseBody
	public GroupChartDTO findAllGroupByMonth(String planYear,String planMonth){
		GroupChartDTO dto = new GroupChartDTO();
		try {
			ShiroUser user = getUser();
			List<OrgGroup> groups = orgGroupService.findLastSaleGroup(user.getOrgId());
			List<PlanSaleYearBean> plans = planService.findAllGroupByMonth(user.getOrgId(), planYear, planMonth);
			dto.setPlans(plans);
			dto.setGroups(groups);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return  dto;
	}
	
	/*页面加载数据*/
	@RequestMapping("/findAllUserPlanByGroupAndMonth")
	@ResponseBody
	public UserChartDTO findAllGroupByMonth(String groupId,String planYear,String planMonth){
		UserChartDTO dto = new UserChartDTO();
		try {
			ShiroUser user = getUser();
			List<TsmTeamGroupMemberBean> users = tsmTeamGroupMemberService.findByGroupId(user.getOrgId(), groupId);
			List<PlanUsermonthBean> plans = planUserService.findByGroupId(user.getOrgId(), groupId, planYear, planMonth);
			dto.setUsers(users);
			dto.setPlans(plans);
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return dto ;
	}
	
	
	public ShiroUser getUser() {
		ShiroUser user = ShiroUtil.getShiroUser();
		return user;
	}
	
}
