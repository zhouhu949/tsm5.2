package com.qftx.tsm.plan.user.day.scontrol;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.team.service.TsmTeamGroupMemberService;
import com.qftx.base.team.service.TsmTeamGroupService;
import com.qftx.base.util.DateUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.common.domain.BaseJsonResult;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.message.service.TsmMessageService;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.plan.user.day.bean.PlanUserDayBean;
import com.qftx.tsm.plan.user.day.bean.PlanUserDayResourceBean;
import com.qftx.tsm.plan.user.day.dto.PlanIndexDTO;
import com.qftx.tsm.plan.user.day.dto.WillCustIndex;
import com.qftx.tsm.plan.user.day.service.PlanUserDayResourceService;
import com.qftx.tsm.plan.user.day.service.PlanUserDayService;
import com.qftx.tsm.plan.user.day.service.PlanUserdaySigncustService;
import com.qftx.tsm.plan.user.day.service.PlanUserdayWillcustService;
import com.qftx.tsm.plan.user.month.service.PlanUserMonthService;
import com.qftx.tsm.plan.year.scontrol.PlanAction;
import com.qftx.tsm.report.utils.DateSubUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/plan/day")
public class PlanUserDayAction{
	@Autowired
	private PlanUserDayService PlanUserDayService;
	@Autowired 
	private PlanUserMonthService monthService;
	@Autowired 
	private TsmMessageService tsmMessageService;
	@Autowired
    private TsmTeamGroupService tsmTeamGroupService;
	@Autowired 
	private TsmTeamGroupMemberService tsmTeamGroupMemberService;
	@Autowired
	private PlanUserDayResourceService planUserDayResourceService;
	@Autowired
	private PlanUserdayWillcustService planUserdayWillcustService;
	@Autowired
	private PlanUserdaySigncustService planUserdaySigncustService;
	@Autowired
	private CachedService cachedService;
	Logger logger=Logger.getLogger(PlanAction.class);
	/*页面加载数据*/
	@RequestMapping("/view")
	public String showView(ModelMap modelMap,String dateStr,Integer weekIndex){
		ShiroUser user = getUser();
		Date date = null;
		if(weekIndex!=null && weekIndex>0 && !StringUtils.isBlank(dateStr)){
			date = DateUtil.parseDate(dateStr);
		}
		
		if(date==null){
			date = new Date();
			weekIndex = DateUtil.getWeekOfMonth(date);
		}
		
		Integer num = DateUtil.getWeekNumOfMonth(date);
		
		modelMap.put("week", DateSubUtil.getWeekByMonth(date, weekIndex));
		modelMap.put("weekIndex", weekIndex);
		modelMap.put("weekNum", num);
		modelMap.put("currTime", System.currentTimeMillis());
		modelMap.put("user", user);
		return "/plan/dayplan/dayPlanOverview";
	}
	
	@RequestMapping("/viewJson")
	@ResponseBody
	public List<PlanIndexDTO> viewJson(String dateStr,Integer week,String userAcc,String userId){
		ShiroUser user = getUser();
		Date date = DateUtil.parseDate(dateStr);
		Date[] toweek = null;
		Date now = new Date();
		if(date==null||week==null){
			date= new Date();
			toweek = DateSubUtil.toWeek(0);
		}else{
			toweek = DateSubUtil.getWeekByMonth(date, week);
		}
		if(StringUtils.isBlank(userAcc)||StringUtils.isBlank(userAcc)){
			userAcc = user.getAccount();
			userId = user.getId();
		} 
		
		Date[] oldSub = null;
		Date[] newSub = null;
		
		if(now.after(toweek[1])){
			//全部是已生成
			oldSub = toweek;
		}else if(now.before(toweek[0])){
			//全部是未生成计划
			newSub = toweek;
		}else{
			//一部分已经生成计划 一部分未生成计划
			oldSub = new Date[] {new Date(toweek[0].getTime()),new Date(toweek[1].getTime())};
			newSub = new Date[] {new Date(toweek[0].getTime()),new Date(toweek[1].getTime())};
			//newSub = [new Date(toweek[0].getTime()),new Date(toweek[1].getTime())];;
			
			oldSub[1] = DateUtil.dateEnd(now);
			newSub[0] = DateUtil.dateBegin(DateUtil.addDay(now, 1));
		}
		List<PlanUserDayBean> newPlans = null;
		List<PlanUserDayBean> oldPlans = null;
		
		PlanUserDayBean query = new PlanUserDayBean();
		query.setOrgId(user.getOrgId());
		query.setUserAcc(userAcc);
		query.setUserId(userId);
		
		List<PlanIndexDTO> dtos = new ArrayList<PlanIndexDTO>();
		
		Map<String, OptionBean> optionMap = getOptionMap(user.getOrgId());
		
		if(newSub!=null){
			query.setFromDate(newSub[0]);
			query.setToDate(newSub[1]);
			newPlans = PlanUserDayService.findFromRes(query);
			
			for (PlanUserDayBean plan : newPlans) {
				query.setFromDate(DateUtil.dateBegin(plan.getPlanDate()));
				query.setToDate(DateUtil.dateEnd(plan.getPlanDate()));
				
				List<WillCustIndex> indexs = planUserdayWillcustService.findIndexFromRes(query);
				indexs = process(indexs, optionMap);
				Collections.sort(indexs);
				PlanIndexDTO dto = copyBean(plan);
				dto.setWillCustIndexlist(indexs);
				dtos.add(dto);
			}
		}
		
		if(oldSub!=null){
			oldPlans = new ArrayList<>();
			
			for(Date dd = oldSub[0] ; dd.compareTo(oldSub[1])<1 ; dd = DateUtil.addDay(dd, 1)){
				PlanUserDayBean plan = PlanUserDayService.getPlanUserDay(user.getOrgId(), userId, dd);
				
				if(plan != null){
					plan = PlanUserDayService.queryPlanById(user.getOrgId(), plan.getId());
					if(plan!=null) oldPlans.add(plan);
				}
			}
			
			for (PlanUserDayBean plan : oldPlans) {
				List<WillCustIndex> indexs = planUserdayWillcustService.findIndexFromPlan(plan);
				indexs = process(indexs, optionMap);
				Collections.sort(indexs);
				PlanIndexDTO dto = copyBean(plan);
				
				dto.setWillCustIndexlist(indexs);
				dtos.add(dto);
			}
		}
		
		
		Map<Date, PlanIndexDTO> map = new HashMap<Date, PlanIndexDTO>();
		
		for (PlanIndexDTO planIndexDTO : dtos) {
			map.put(DateUtil.dateBegin(planIndexDTO.getPlanDate()), planIndexDTO);
		}
		
		Date tempDate = new Date(toweek[0].getTime());
		
		List<PlanIndexDTO> returnDtos = new ArrayList<>();
		
		while(tempDate.before(toweek[1])){
			PlanIndexDTO dto =null;
			if(!map.containsKey(tempDate)){
				dto = new PlanIndexDTO();
				dto.setPlanDate(tempDate);
				dto.setPlanFlag(false);
			}else{
				dto = map.get(tempDate);
				dto.setPlanFlag(true);
			}
			
			dto.setMineFlag(user.getAccount().equals(query.getUserAcc())||user.getId().equals(query.getUserId()));
			returnDtos.add(dto);
			tempDate = DateUtil.addDay(tempDate, 1);
		}
		return returnDtos;
	}
	
	@RequestMapping("/detail")
	public String detail(String planId,String dateStr,String type,String custStatusId,String status,String userAcc,String userId,ModelMap modelMap){
		ShiroUser user = getUser();
		
		Date date = null;
		if(!StringUtils.isBlank(dateStr)){
			date = DateUtil.parseDate(dateStr);
		}
		
		if(date==null){
			date = new Date();
		}
		
		if(StringUtils.isBlank(userAcc)||StringUtils.isBlank(userAcc)){
			userAcc = user.getAccount();
			userId = user.getId();
		} 
		/*PlanUserDayBean query = new PlanUserDayBean();
		query.setOrgId(user.getOrgId());
		query.setUserAcc(userAcc);
		query.setUserId(userId);*/
		//List<WillCustIndex> indexs = null;
		if(DateUtil.dateBegin(date).before(new Date())){
			//已生成计划
			if(StringUtils.isBlank(planId)){
				PlanUserDayBean plan = PlanUserDayService.getPlanUserDay(user.getOrgId(), userId, date);
				if(plan!=null) planId = plan.getId();
			}
			
			/*if(!StringUtils.isBlank(planId)){
				query.setId(planId);
				indexs = planUserdayWillcustService.findIndexFromPlan(query);
				process(indexs, optionMap);
				Collections.sort(indexs);
			}*/
			
		}else{
			//未生成计划
			/*query.setFromDate(DateUtil.dateBegin(date));
			query.setToDate(DateUtil.dateEnd(date));
			
			indexs = planUserdayWillcustService.findIndexFromRes(query);
			process(indexs, optionMap);
			Collections.sort(indexs);*/
		}
		Map<String, OptionBean> optionMap = getOptionMap(user.getOrgId());
		List<WillCustIndex> indexs = new ArrayList<WillCustIndex>();
		for (OptionBean optionBean : optionMap.values()) {
			WillCustIndex index = new WillCustIndex();
			index.setCustStatusId(optionBean.getOptionlistId());
			index.setCustStatus(optionBean.getOptionName());
			index.setSort(optionBean.getSort());
			indexs.add(index);	
		}
		Collections.sort(indexs);
		modelMap.put("sudId", planId);
		modelMap.put("dateStr", dateStr);
		modelMap.put("type", type);
		modelMap.put("status", status);
		modelMap.put("custStatusId", custStatusId);
		modelMap.put("userAcc", userAcc);
		modelMap.put("userId", userId);
		modelMap.put("indexs", indexs);
		return "/plan/dayplan/dayPlanDetails";
	}
	
	//加入客户
	@RequestMapping("/addPlan")
	@ResponseBody
	public BaseJsonResult addPlan(String custIdsStr,String dateStr,String type){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			Date planDate = DateUtil.parseDate(dateStr);
			
			PlanUserDayResourceBean update = new PlanUserDayResourceBean();
			update.setOrgId(user.getOrgId());
			update.setCustIds(custIdsStr.split(","));
			update.setPlanDate(planDate);
			planUserDayResourceService.updatePlanDate(update);
			
			String planId = null;
			
			//如果是当天
			if(DateUtil.dateBegin(planDate).equals(DateUtil.dateBegin(new Date()))){
				//计划时间调整为今天
				if("res".equals(type)){
					planId = planUserDayResourceService.insertResources(user, custIdsStr.split(","), planDate);
				}else if("will".equals(type)){
					planId = planUserdayWillcustService.insertWillcusts(user, custIdsStr.split(","), planDate);
				}else if("sign".equals(type)){
					planId = planUserdaySigncustService.insertSigncusts(user, custIdsStr.split(","), planDate);
				}
			}
			return BaseJsonResult.success(planId);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return BaseJsonResult.error();
		}
	}
	
	/*时间调整*/
	@RequestMapping("/moveAnotherDayWindow")
	public String moveAnotherDayWindow(ModelMap modelMap,String custIds,String type,String planDate,String sudId){
		try {
			modelMap.put("type", type);
			modelMap.put("custIds", custIds);
			modelMap.put("planDate", planDate);
			modelMap.put("sudId", sudId);
			modelMap.put("today", DateUtil.formatDate(new Date(),DateUtil.DATE_DAY));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return "/plan/day/moveAnotherDay";
	}
	
	/*时间调整*/
	@RequestMapping("/moveAnotherDay")
	@ResponseBody
	public BaseJsonResult moveAnotherDay(String custIds,String type,String planDate,String oldSudId,String oldPlanDate){
		try {
			if(oldPlanDate!=null && oldPlanDate.equals(planDate)){
				return BaseJsonResult.error("请重新选择日期！");
			}
			
			ShiroUser user = ShiroUtil.getUser();
			Date date = DateUtil.parseDate(planDate);
			
			PlanUserDayResourceBean update = new PlanUserDayResourceBean();
			update.setOrgId(user.getOrgId());
			update.setCustIds(custIds.split(","));
			update.setPlanDate(date);
			planUserDayResourceService.updatePlanDate(update);
			
			if(DateUtil.dateBegin(date).equals(DateUtil.dateBegin(new Date()))){
				//计划时间调整为今天
				if("res".equals(type)){
					planUserDayResourceService.insertResources(user, custIds.split(","), date);
				}else if("will".equals(type)){
					planUserdayWillcustService.insertWillcusts(user, custIds.split(","), date);
				}else if("sign".equals(type)){
					planUserdaySigncustService.insertSigncusts(user, custIds.split(","), date);
				}
			}
			
			Date oldDate = DateUtil.parseDate(oldPlanDate);
			if(DateUtil.dateBegin(oldDate).equals(DateUtil.dateBegin(new Date()))&& !StringUtils.isBlank(oldSudId)){
				//旧计划时间为今天   调整到其他天
				String table = null;
				if("res".equals(type)){
					table = "plan_userday_resource";
				}else if("will".equals(type)){
					table = "plan_userday_willcust";
				}else if("sign".equals(type)){
					table = "plan_userday_signcust";
				}
				planUserDayResourceService.updateMoveDate(user.getOrgId(),oldSudId, table, custIds.split(","));
				
			}
			return BaseJsonResult.success();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return BaseJsonResult.error();
		}
	}
	
	public ShiroUser getUser() {
		ShiroUser user = ShiroUtil.getShiroUser();
		return user;
	}
	
	private Map<String, OptionBean> getOptionMap(String orgId){
		List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE,orgId);
		
		HashMap<String, OptionBean> map = new HashMap<String, OptionBean>();
		for (OptionBean optionBean : options) {
			map.put(optionBean.getOptionlistId(), optionBean);
		}
		return map;
	}
	
	private List<WillCustIndex> process(List<WillCustIndex> willCustIndexs,Map<String, OptionBean> optionMap){
		List<WillCustIndex> temp =new ArrayList<WillCustIndex>();
		if(optionMap==null ||optionMap.isEmpty()) return temp;
		
		for (WillCustIndex willCustIndex : willCustIndexs) {
			OptionBean option = optionMap.get(willCustIndex.getCustStatusId());
			if(option!=null){
				willCustIndex.setCustStatus(option.getOptionName());
				willCustIndex.setSort(option.getSort());
				temp.add(willCustIndex);
			}else{
				//willCustIndex.setCustStatus("-");
				//willCustIndex.setSort(-1);
			}
		}
		
		return temp;
	}
	
	public PlanIndexDTO copyBean(PlanUserDayBean src){
		PlanIndexDTO dest = new PlanIndexDTO();
		dest.setResourceCount(src.getResourceCount());
		dest.setResourceContactCount(src.getResourceContactCount());
		dest.setSigncustCount(src.getSigncustCount());
		dest.setSigncustContactCount(src.getSigncustContactCount());
		dest.setWillcustCount(src.getWillcustCount());
		dest.setWillcustContactCount(src.getWillcustContactCount());
		dest.setPlanDate(src.getPlanDate());
		dest.setId(src.getId());
		return dest;
	}
	
	public static void main(String[] args) {
		
		Date[] oldSub = new Date[]{DateUtil.parseDate("2018-05-25"),DateUtil.parseDate("2018-05-29")};
		for(Date dd = oldSub[0] ; dd.compareTo(oldSub[1])<1 ; dd = DateUtil.addDay(dd, 1)){
			
			System.out.println(DateUtil.formatDate(dd));
			
		}
		
	}
}
