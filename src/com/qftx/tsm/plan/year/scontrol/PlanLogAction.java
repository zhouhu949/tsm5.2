package com.qftx.tsm.plan.year.scontrol;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.DateUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.util.StringUtils;
import com.qftx.tsm.plan.year.bean.PlanLogYearBean;
import com.qftx.tsm.plan.year.service.PlanLogYearService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/plan/yearlog/")
public class PlanLogAction {
	@Autowired
	private PlanLogYearService planLogYearService ;
	
	Logger logger=Logger.getLogger(PlanLogAction.class);
	
	/*页面加载数据*/
	@RequestMapping("/view")
	public String view(ModelMap modelMap,String planYear){
		try {
			if(StringUtils.isBlank(planYear)){
				planYear= String.valueOf(DateUtil.year(new Date()));
			}
			ShiroUser user = getUser();
			List<PlanLogYearBean> list = planLogYearService.queryFullYearPlanHaveDContext(user.getOrgId(), planYear);
			planLogYearService.processData(list);
			
			
			List<PlanLogYearBean> list1 = planLogYearService.queryFullYearPlan(user.getOrgId(), planYear);
			int currentYear=DateUtil.year(new Date());
			ArrayList<Integer> years = new ArrayList<Integer>();
			for(int i=0;i<6;i++){
				years.add(currentYear-i);
			}
			modelMap.put("years", years);
			modelMap.put("planYear", planYear);
			modelMap.put("jsonList", JsonUtil.getJsonString(list1));
			modelMap.put("list", list);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		return "/plan/year/year_plan_log";
	}

	public ShiroUser getUser() {
		ShiroUser user = ShiroUtil.getShiroUser();
		return user;
	}
	
}
