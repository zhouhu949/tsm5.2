package com.qftx.tsm.plan.user.day.scontrol;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.DateUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.common.domain.BaseJsonResult;
import com.qftx.common.util.StringUtils;
import com.qftx.tsm.plan.CustomFieldUtils;
import com.qftx.tsm.plan.user.day.bean.DataLableDTO;
import com.qftx.tsm.plan.user.day.bean.PlanUserdayWillcustBean;
import com.qftx.tsm.plan.user.day.service.PlanUserDayResourceService;
import com.qftx.tsm.plan.user.day.service.PlanUserDayService;
import com.qftx.tsm.plan.user.day.service.PlanUserdayWillcustService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/plan/day/willcust")
public class PlanUserDayWillcustAction  extends PlanUserDayBaseAction{
	@Autowired
	private PlanUserdayWillcustService planUserdayWillcustService;
	@Autowired 
	private PlanUserDayService planService;
	@Autowired 
	private CachedService cachedService;
	@Autowired
	private CustomFieldUtils customFieldUtils;
	@Autowired
	private PlanUserDayResourceService planUserDayResourceService;
	Logger logger=Logger.getLogger(PlanUserDayWillcustAction.class);
	/*页面加载数据*/
	@RequestMapping("/view")
	public String getResources(HttpServletRequest httpServletRequest,ModelMap model){
		processRequest(httpServletRequest, model);
		return "/plan/dayplan/right/dayPlanDetails_will_right";
	}
	
	@RequestMapping("/willDataJson")
	@ResponseBody
	public BaseJsonResult willDataJson(PlanUserdayWillcustBean query,DataLableDTO dto,String dateStr){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			query.setOrgId(user.getOrgId());
			
			Date planDate = null;
			if(!StringUtils.isBlank(dateStr)){
				planDate = DateUtil.parseDate(dateStr);
			}
			
			if(planDate==null){
				planDate = new Date();
			}
			
			Date nowDate = new Date();
			
			if(StringUtils.isBlank(query.getStatus())){
				query.setStatus(null);
			}
			
			if(StringUtils.isBlank(query.getContactResult())){
				query.setContactResult(null);
			}else{
				query.setContactResult(query.getContactResult().trim());
			}
			
			if(StringUtils.isBlank(query.getQueryStr())){
				query.setQueryStr(null);
			}else{
				query.setQueryStr(query.getQueryStr().trim());
			}
			
			if(StringUtils.isBlank(query.getCustStatusId())){
				query.setCustStatusId(null);
			}
			
			if(StringUtils.isBlank(query.getUserAcc())){
				query.setUserAcc(user.getAccount());
			}
			
			List<PlanUserdayWillcustBean> list = null;
			
			if(DateUtil.dateBegin(nowDate).before(DateUtil.dateBegin(planDate))){
				//未来的计划
				query.setPlanDateFrom(DateUtil.dateBegin(planDate));
				query.setPlanDateTo(DateUtil.dateEnd(planDate));
				if(query.getStatus()==null || "0".equals(query.getStatus())||query.getIsMajor()!=null){
					list = planUserdayWillcustService.findFromResListPage(query);
				}
			}else{
				//当日或者旧的计划
				list = planUserdayWillcustService.queryPageBySudId(query);
			}
				
			BaseJsonResult succ = BaseJsonResult.success("item", query);
			succ.put("list", list);
			return succ;
		} catch (Exception e) {
			logger.error("",e);
			return  BaseJsonResult.error();
		}
	}
}
