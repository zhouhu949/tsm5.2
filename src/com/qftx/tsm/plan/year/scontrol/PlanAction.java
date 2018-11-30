package com.qftx.tsm.plan.year.scontrol;

import com.qftx.base.auth.service.OrgGroupService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.DateUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.util.StringUtils;
import com.qftx.tsm.plan.year.bean.PlanSaleTrendBean;
import com.qftx.tsm.plan.year.dto.AllYearPlanDTO;
import com.qftx.tsm.plan.year.dto.PlanChangeDTO;
import com.qftx.tsm.plan.year.service.PlanSaleYearService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/plan/year")
public class PlanAction {
	@Autowired
	private PlanSaleYearService planService;
	@Autowired
	OrgGroupService orgGroupService;
	Logger logger=Logger.getLogger(PlanAction.class);
	/*页面加载数据*/
	@RequestMapping("/view")
	public String view(ModelMap modelMap,Long dateTime){
		try {
			if(dateTime==null){
				dateTime= System.currentTimeMillis();
			}
			ShiroUser user = getUser();
			AllYearPlanDTO dto = planService.queryFullYearPlan(user.getOrgId(),user.getId(),new Date(dateTime));
			PlanSaleTrendBean progress = planService.getPlanYearProgress(user.getOrgId(), new Date(dateTime));
			
			modelMap.put("currentDateTime", System.currentTimeMillis());
			modelMap.put("dateTime", dateTime);
			modelMap.put("progress", progress);
			modelMap.put("dto", dto);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		return "/plan/year/year_plan";
	}
	
	/*保存计划*/
	@RequestMapping("/savePlan")
	@ResponseBody
	public String savePlan(HttpServletRequest req){
		try {
			String jsonString = req.getParameter("planChanges");
			String reason = req.getParameter("reason");
			String planYear = req.getParameter("planYear");
			ShiroUser user = getUser();
			if(jsonString!=null&&reason!=null&&planYear!=null){
				List<PlanChangeDTO> planChanges= JsonUtil.getListJson(jsonString, PlanChangeDTO.class);
				planService.savePlan(user.getId(),user.getName(),user.getOrgId(), planChanges, planYear, reason);
				return "success";
			}else{
				return "erro";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return "erro";
		}
		
	}
	
	/*计划说明弹窗*/
	@RequestMapping("/planContextWindow")
	public String planContextWindow(ModelMap model){
		return "/plan/year/year_plan_context";
	}
	
	/*页面加载数据*/
	@RequestMapping("/history")
	public String history(ModelMap modelMap){
		try {
			ShiroUser user = getUser();
			modelMap.put("groups", orgGroupService.findLastSaleGroup(user.getOrgId()));
			//1、历史规划走势中选择的年份长度范围不能超过6年，默认显示3年的走势，部门选择中的部门与年度规划列表中的部门相同。
			modelMap.put("currentYear", DateUtil.year(new Date()));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		return "/plan/year/year_plan_history";
	}
	
	/*页面加载数据*/
	@RequestMapping("/history_json")
	@ResponseBody
	public List<PlanSaleTrendBean> history_json(String groupId,String fromYear,String toYear){
		try {
			ShiroUser user = getUser();
			
			if(StringUtils.isEmpty( toYear)||StringUtils.isEmpty( fromYear)){
				Date date = new Date();
				fromYear = String.valueOf(DateUtil.year(DateUtil.addDate(new Date(), -2, 0, 0)));
				toYear = String.valueOf(DateUtil.year(date));
			}
			
			if(StringUtils.isBlank(groupId)&&groupId!=null){
				groupId=null;
			}
			return planService.queryPlanSalTrend(user.getOrgId(), fromYear, toYear, groupId);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return  new ArrayList<PlanSaleTrendBean>();
		}
	}
	
	public ShiroUser getUser() {
		ShiroUser user = ShiroUtil.getShiroUser();
		return user;
	}
	
}
