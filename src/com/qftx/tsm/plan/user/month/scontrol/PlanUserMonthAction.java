package com.qftx.tsm.plan.user.month.scontrol;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.team.bean.TeamGroupBean;
import com.qftx.base.team.service.TsmTeamGroupService;
import com.qftx.base.util.DateUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.cust.dto.ResCustInfoDto;
import com.qftx.tsm.message.service.TsmMessageService;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.plan.CustomFieldUtils;
import com.qftx.tsm.plan.ResultDTO;
import com.qftx.tsm.plan.user.month.bean.*;
import com.qftx.tsm.plan.user.month.dto.PlanUserMonthDTO;
import com.qftx.tsm.plan.user.month.service.PlanUserMonthService;
import com.qftx.tsm.plan.user.month.service.PlanUsermonthCommontService;
import com.qftx.tsm.plan.user.month.service.PlanUsermonthCustService;
import com.qftx.tsm.plan.user.month.service.PlanUsermonthPointService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/plan/month/user")
public class PlanUserMonthAction {
	@Autowired
	private PlanUserMonthService service;
	@Autowired
	private PlanUsermonthCustService custService;
	@Autowired
	private PlanUsermonthPointService pointService;
	@Autowired
	private PlanUsermonthCommontService commontService;
	@Autowired 
	private CachedService cachedService;
	@Autowired 
	private TsmMessageService tsmMessageService;
	@Autowired 
	private TsmTeamGroupService tsmTeamGroupService;
	@Autowired
	private CustomFieldUtils customFieldUtils;
	Logger logger = Logger.getLogger(PlanUserMonthAction.class);
	
	//个人全年计划查看
	@RequestMapping("/view")
	public String yearView(ModelMap modelMap,String planYear){
		
		try {
			ShiroUser user = getUser();
			if(planYear==null){
				planYear = String.valueOf(DateUtil.year(new Date()));
			} 
			PointCache pointCache = new PointCache(user.getOrgId(), cachedService);
			Map<String, PlanUserMonthDTO> plansMap = service.getPlansFullYear(user.getOrgId(), user.getId(), planYear);
			
			Date autoAuthTime = customFieldUtils.getAutoAuthTime(user.getOrgId());
			
			modelMap.put("autoAuthTime", autoAuthTime.getTime());
			modelMap.put("currentTime", new Date().getTime());
			
			modelMap.put("pointCache", pointCache);
			modelMap.put("currentMonth", DateUtil.month(new Date()));
			modelMap.put("currentYear", DateUtil.year(new Date()));
			modelMap.put("planYear", planYear);
			modelMap.put("months", new String[]{"1","2","3","4","5","6","7","8","9","10","11","12"});
			modelMap.put("plansMap", plansMap);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		return "/plan/month/user/year_view";
	}
	
	//历年计划详情查询
	@RequestMapping("/historyView")
	public String historyView(ModelMap modelMap,PlanHistoryBean item,String userIds,String userAccs,String groupIds){
		try {
			ShiroUser user = getUser();
			item.setOrgId(user.getOrgId());
			item.setIsDel(0);
			if(StringUtils.isEmpty(item.getPlanYear())){
				item.setPlanYear(null);
			}
			
			if(StringUtils.isEmpty(item.getPlanMonth())){
				item.setPlanMonth(null);
			}
			
			if(StringUtils.isBlank(item.getQueryText())){
				item.setCustName(null);
				item.setCompany(null);
			}else{
				if("1".equals(item.getQueryType())){
					item.setCustName(item.getQueryText().trim());
				}else if("2".equals(item.getQueryType())){
					item.setCompany(item.getQueryText().trim());
				}
			}
			
			if(StringUtils.isNotBlank(userIds)){
				item.setUserIds(userIds.split(","));
			}
			
			if(StringUtils.isNotBlank(groupIds)){
				item.setGroupIds(groupIds.split(","));
			}else{
				List<TeamGroupBean> groups = tsmTeamGroupService.queryManageGroupList(user.getOrgId(), user.getAccount());
				String[] groupIdArray = new String[groups.size()];
				for(int i=0;i<groups.size();i++){
					groupIdArray[i]=groups.get(i).getGroupId();
				}
				if(groupIdArray!=null && groupIdArray.length>0){
					item.setGroupIds(groupIdArray);
				}
			}
			
			List<PlanHistoryBean> list = custService.findHistoryListPage(item);
			
			int currentYear = DateUtil.year(new Date());
			
			int[] years= new int[6];
			for(int i=0;i<years.length;i++){
				years[i]=currentYear-i;
			}
			
			modelMap.put("isState",user.getIsState());//0:个人版  1: 企业版
			modelMap.put("filedMap", customFieldUtils.getCustomFiled(user));
			modelMap.put("years", years);
			modelMap.put("months", new int[]{1,2,3,4,5,6,7,8,9,10,11,12});
			modelMap.put("currentYear", currentYear);
			modelMap.put("userIds",userIds);
			modelMap.put("userAccs", userAccs);
			modelMap.put("groupIds", groupIds);
			modelMap.put("item", item);
			modelMap.put("list", list);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		return "/plan/month/user/history_view";
	}
	
	//历年计划详情查询
	@RequestMapping("/historyView1")
	public String historyView1(ModelMap modelMap){
		ShiroUser user = getUser();
		
		int currentYear = DateUtil.year(new Date());
		int[] years= new int[6];
		for(int i=0;i<years.length;i++){
			years[i]=currentYear-i;
		}
		
		modelMap.put("isState",user.getIsState());//0:个人版  1: 企业版
		modelMap.put("filedMap", customFieldUtils.getCustomFiled(user));
		modelMap.put("years", years);
		modelMap.put("months", new int[]{1,2,3,4,5,6,7,8,9,10,11,12});
		modelMap.put("currentYear", currentYear);
		return "/plan/month/user/history_view1";
	}
	
	//历年计划详情查询
	@RequestMapping("/pageTest")
	public String pageTest(){
		
		return "/plan/month/user/pageTest";
	}
		
	
	//历年计划详情查询
	@RequestMapping("/historyViewJson")
	@ResponseBody
	public Map<String, Object> historyViewJson(PlanHistoryBean item,String userIds,String userAccs,String groupIds){
		Map<String, Object> map = new HashMap<>();
		try {
			ShiroUser user = getUser();
			item.setOrgId(user.getOrgId());
			item.setIsDel(0);
			if(StringUtils.isEmpty(item.getPlanYear())){
				item.setPlanYear(null);
			}
			
			if(StringUtils.isEmpty(item.getPlanMonth())){
				item.setPlanMonth(null);
			}
			
			if(StringUtils.isBlank(item.getQueryText())){
				item.setCustName(null);
				item.setCompany(null);
			}else{
				if("1".equals(item.getQueryType())){
					item.setCustName(item.getQueryText().trim());
				}else if("2".equals(item.getQueryType())){
					item.setCompany(item.getQueryText().trim());
				}
			}
			
			if(StringUtils.isNotBlank(userIds)){
				item.setUserIds(userIds.split(","));
			}
			
			if(StringUtils.isNotBlank(groupIds)){
				item.setGroupIds(groupIds.split(","));
			}else{
				List<TeamGroupBean> groups = tsmTeamGroupService.queryManageGroupList(user.getOrgId(), user.getAccount());
				String[] groupIdArray = new String[groups.size()];
				for(int i=0;i<groups.size();i++){
					groupIdArray[i]=groups.get(i).getGroupId();
				}
				if(groupIdArray!=null && groupIdArray.length>0){
					item.setGroupIds(groupIdArray);
				}
			}
			List<PlanHistoryBean> list = custService.findHistoryListPage(item);
			
			map.put("item", item);
			map.put("list", list);
			int currentYear = DateUtil.year(new Date());
			
			int[] years= new int[6];
			for(int i=0;i<years.length;i++){
				years[i]=currentYear-i;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return map;
	}
	
	//个人月计划查看(未执行)
	@RequestMapping("/monthView")
	public String monthView(ModelMap map,String id){
		try {
			ShiroUser user = getUser();
			PlanUsermonthBean planMonth = service.getById(user.getOrgId(), id);
			List<PlanUsermonthCustBean> custs = custService.queryByPlanUserMonthId(user.getOrgId(),id);
			PlanUsermonthCustBean custCount = custService.queryCustSum(user.getOrgId(), id);
			List<PlanUsermonthCommontBean> commonts = commontService.queryByPlanId(user.getOrgId(), id);
			
			
			PlanUserMonthDTO dto = new PlanUserMonthDTO();
			dto.setCustCount(custCount);
			dto.setPlanMonth(planMonth);
			dto.setCusts(custs);
			dto.setUserPlanCommonts(commonts);
			
			map.put("autoAuthDay",customFieldUtils.getAutoAuthDay(user.getOrgId()));
			map.put("isState",user.getIsState());//0:个人版  1: 企业版
			map.put("filedMap", customFieldUtils.getCustomFiled(user));
			map.put("dto", dto);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		return "/plan/month/user/month_view";
	}
	
	//个人月计划查看(执行)
	@RequestMapping("/monthView_p")
	public String monthView_p(ModelMap map,String id){
		try {
			ShiroUser user = getUser();
			PlanUsermonthBean planMonth = service.getById(user.getOrgId(), id);
			List<PlanUsermonthCustBean> custs = custService.queryByPlanUserMonthId(user.getOrgId(),id);
			PlanUsermonthCustBean custCount = custService.queryCustSum(user.getOrgId(), id);
			List<PlanUsermonthCommontBean> commonts = commontService.queryByPlanId(user.getOrgId(), id);
			
			PlanUserMonthDTO dto = new PlanUserMonthDTO();
			dto.setCustCount(custCount);
			dto.setPlanMonth(planMonth);
			dto.setCusts(custs);
			dto.setUserPlanCommonts(commonts);
			
			map.put("autoAuthDay",customFieldUtils.getAutoAuthDay(user.getOrgId()));
			map.put("isState",user.getIsState());//0:个人版  1: 企业版
			map.put("filedMap", customFieldUtils.getCustomFiled(user));
			map.put("dto", dto);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		return "/plan/month/user/month_view_p";
	}
	
	//个人月计划编辑
	@RequestMapping("/monthEdit")
	public String monthEdit(ModelMap map,String id,String planYear,String month){
		try {
			ShiroUser user = getUser();
			PlanUserMonthDTO dto = new PlanUserMonthDTO();
			PlanUsermonthBean planMonth = null;
			boolean isNew =false;
			if(StringUtils.isNotBlank(id)){
				planMonth = service.getById(user.getOrgId(), id);
			}else if(StringUtils.isNotBlank(planYear)&&StringUtils.isNotBlank(month)){
				planMonth = service.queryByUserAndMonth(user.getOrgId(),user.getId(),  planYear, month);
				
				if(planMonth==null){
					isNew = true;
					planMonth = service.newBean(user.getOrgId(), user.getGroupId(), user.getId(), planYear, month);
					//planMonth = service.insert(user.getOrgId(), user.getGroupId(), user.getId(), planYear, month);
				}
			}else{
				logger.error("参数为空");
			}
			
			List<PlanUsermonthCustBean> custs = custService.queryByPlanUserMonthId(user.getOrgId(),planMonth.getId());
			PlanUsermonthCustBean custCount = custService.queryCustSum(user.getOrgId(), planMonth.getId());
			
			dto.setCustCount(custCount);
			dto.setPlanMonth(planMonth);
			dto.setCusts(custs);
			
			map.put("autoAuthDay",customFieldUtils.getAutoAuthDay(user.getOrgId()));
			map.put("isState",user.getIsState());//0:个人版  1: 企业版
			map.put("filedMap", customFieldUtils.getCustomFiled(user));
			map.put("isNew", isNew);
			map.put("dto", dto);
			map.put("plan", planMonth);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		return "/plan/month/user/month_edit";
	}
	
	//添加客户弹窗
	@RequestMapping("/addCustWindow")
	public String addCustWindow(ModelMap map,String custIdsStr,ResCustInfoDto custInfoDto){
		try {
			ShiroUser user = getUser();
			
			if(StringUtils.isBlank(custInfoDto.getQueryText())){
				custInfoDto.setQueryText(null);
			}else{
				custInfoDto.setQueryText(custInfoDto.getQueryText().trim());
			}
			
			if(StringUtils.isBlank(custInfoDto.getCustTypeId())){
				custInfoDto.setCustTypeId(null);
			}
			
			if(StringUtils.isBlank(custInfoDto.getSaleProcessId())){
				custInfoDto.setSaleProcessId(null);
			}
			
			if(StringUtils.isBlank(custInfoDto.getCustType())){
				custInfoDto.setCustType("6");
			}
			
			/*if (custInfoDto.getoDateType() != null && !"".equals(custInfoDto.getoDateType()) && custInfoDto.getoDateType()!=0 && custInfoDto.getoDateType()!=5) {
				custInfoDto.setStartNextContactDate(getStartDateStr(new Integer(custInfoDto.getoDateType())));
				custInfoDto.setEndNextContactDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}*/
			
			if (custInfoDto.getlDateType() != null && !"".equals(custInfoDto.getlDateType()) && custInfoDto.getlDateType()!=0 && custInfoDto.getlDateType()!=5) {
				custInfoDto.setStartNextContactDate(getStartDateStr(new Integer(custInfoDto.getlDateType())));
				custInfoDto.setEndNextContactDate(getEndDateStr(new Integer(custInfoDto.getlDateType())));
				
				//custInfoDto.setStartContractEndDate(getStartDateStr(new Integer(custInfoDto.getlDateType())));
				//custInfoDto.setEndContractEndDate(getEndDateStr(new Integer(custInfoDto.getlDateType())));
			}
			
			List<OptionBean> salProgress = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE,user.getOrgId());
			//List<OptionBean> custTypes = cachedService.getOptionList(AppConstant.SALES_TYPE_TWO,user.getOrgId());
			List<ResCustInfoDto> list = service.getAllCusts(user.getOrgId(), user.getAccount(), custIdsStr.split(","), custInfoDto);
			
			map.put("filedMap", customFieldUtils.getCustomFiled(user));
			map.put("salProgress", salProgress);
			map.put("isState",user.getIsState());//0:个人版  1: 企业版
			//map.put("custTypes", custTypes);
			map.put("custIdsStr", custIdsStr);
			map.put("list", list);
			map.put("item", custInfoDto);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		return "/plan/month/user/add_cust_window";
	}
	
	//领取积分
	@RequestMapping("/receivePoint")
	@ResponseBody
	public Map<String, Object> showHistoryMonthPlan(String id,String type,boolean isLast){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ShiroUser user = getUser();
			if(type!=null){
				Point point = service.receivePoint(user.getOrgId(), id,user.getId(),user.getAccount(), type,isLast);
				map.put("point",point);
				map.put("status", "success");
				map.put("msg",null);
			}else{
				map.put("status", "erro");
				map.put("msg","类型为空！");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			map.put("status", "erro");
			map.put("msg","系统错误！");
		}
		return map;
	}
	//上报计划
	@RequestMapping("/reportPlan")
	@ResponseBody
	public ResultDTO reportPlan(String planId){
		try {
			ShiroUser user = getUser();
			return service.upReportPlan(user.getOrgId(), planId, "1");
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return ResultDTO.erro("系统错误!");
		}
		
	}
	
	//保存计划
	@RequestMapping("/savePlan")
	@ResponseBody
	public ResultDTO savePlan(String planJsonStr,String custsJsonStr,boolean isNew){
		try {
			ShiroUser user = getUser();
			PlanUsermonthBean plan = (PlanUsermonthBean)JsonUtil.getObjectJsonString(planJsonStr, PlanUsermonthBean.class);
			List<PlanUsermonthCustBean> custs = JsonUtil.getListJson(custsJsonStr, PlanUsermonthCustBean.class);
			
			plan.setOrgId(user.getOrgId());
			
			ResultDTO dto = service.savePlan(user,plan, custs,isNew);
			return dto;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return ResultDTO.erro("系统错误！");
		}
	}
	
	//月计划历史走势
	@RequestMapping("/historyWindow")
	public String historyWindow(ModelMap modelMap,String userId){
		try {
			modelMap.put("userId", userId);
			
			Date toDate =new Date();
			Date fromDate = DateUtil.addDate(toDate, 0, -5, 0);
			modelMap.put("fromDate",fromDate);
			modelMap.put("toDate", toDate);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	
		return "/plan/month/user/history_window";
	}
	
	//月计划历史走势
	@RequestMapping("/historyJson")
	@ResponseBody
	public List<PlanUsermonthBean> historyWindowJson(String userId,Long fromTime,Long toTime){
		try {
			//历史计划走势：历史计划走势显示指标的计划和完成对比，鼠标点击后出现一个浮层，浮层中显示指标的完成值/计划值的完成对比图；默认显示包含当前月份的前六个月份的数据对比，且选择跨度不能超过12个月。
			ShiroUser user = getUser();
			if(fromTime==null ||toTime==null){
				toTime =System.currentTimeMillis();
				fromTime = DateUtil.addDate(new Date(), 0, -5, 0).getTime();
			}
				
			if(StringUtils.isBlank(userId)){
				userId = user.getId();
			}
			return service.queryHistory(user.getOrgId(),userId, new Date(fromTime), new Date(toTime));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return new ArrayList<PlanUsermonthBean>();
		}
		
		
	}
	
	//月计划详情弹窗（团队月计划引用）
	@RequestMapping("/detailWindow")
	public String detailWindow(ModelMap modelMap,String planId){
		try {
			ShiroUser user = getUser();
			PlanUsermonthBean planMonth = service.getById(user.getOrgId(), planId);
			List<PlanUsermonthCustBean> custs = custService.queryByPlanUserMonthId(user.getOrgId(),planId);
			PlanUsermonthCustBean custCount = custService.queryCustSum(user.getOrgId(), planId);
			List<PlanUsermonthCommontBean> commonts = commontService.queryByPlanId(user.getOrgId(), planId);
			
			PlanUserMonthDTO dto = new PlanUserMonthDTO();
			dto.setCustCount(custCount);
			dto.setPlanMonth(planMonth);
			dto.setCusts(custs);
			dto.setUserPlanCommonts(commonts);
			
			modelMap.put("autoAuthDay",customFieldUtils.getAutoAuthDay(user.getOrgId()));
			modelMap.put("isState",user.getIsState());//0:个人版  1: 企业版
			modelMap.put("filedMap", customFieldUtils.getCustomFiled(user));
			modelMap.put("dto", dto);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		return "/plan/month/user/detail_window";
	}
	
	//月计划详情弹窗（团队月计划引用）
	@RequestMapping("/detailWindow_p")
	public String detailWindow_p(ModelMap modelMap,String planId){
		try {
			ShiroUser user = getUser();
			PlanUsermonthBean planMonth = service.getById(user.getOrgId(), planId);
			List<PlanUsermonthCustBean> custs = custService.queryByPlanUserMonthId(user.getOrgId(),planId);
			PlanUsermonthCustBean custCount = custService.queryCustSum(user.getOrgId(), planId);
			List<PlanUsermonthCommontBean> commonts = commontService.queryByPlanId(user.getOrgId(), planId);
			
			PlanUserMonthDTO dto = new PlanUserMonthDTO();
			dto.setCustCount(custCount);
			dto.setPlanMonth(planMonth);
			dto.setCusts(custs);
			dto.setUserPlanCommonts(commonts);
			
			modelMap.put("autoAuthDay",customFieldUtils.getAutoAuthDay(user.getOrgId()));
			modelMap.put("isState",user.getIsState());//0:个人版  1: 企业版
			modelMap.put("filedMap", customFieldUtils.getCustomFiled(user));
			modelMap.put("dto", dto);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		return "/plan/month/user/detail_window_p";
	}
	
	//月计划审核弹窗（团队月计划引用）
	@RequestMapping("/authWindow")
	public String authWindow(ModelMap modelMap,String planId,String groupPlanId){
		try {
			ShiroUser user = getUser();
			PlanUsermonthBean planMonth = service.getById(user.getOrgId(), planId);
			List<PlanUsermonthCustBean> custs = custService.queryByPlanUserMonthId(user.getOrgId(),planId);
			PlanUsermonthCustBean custCount = custService.queryCustSum(user.getOrgId(), planId);
			
			PlanUserMonthDTO dto = new PlanUserMonthDTO();
			dto.setCustCount(custCount);
			dto.setPlanMonth(planMonth);
			dto.setCusts(custs);
			
			modelMap.put("isState",user.getIsState());//0:个人版  1: 企业版
			modelMap.put("filedMap", customFieldUtils.getCustomFiled(user));
			modelMap.put("dto", dto);
			modelMap.put("groupPlanId", groupPlanId);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		return "/plan/month/user/auth_window";
	}
	
	//个人月计划查看(未执行)
	@RequestMapping("/authView")
	public String authView(ModelMap map,PlanUsermonthBean item,String userIds,String userAccs,String authUserIds,String authUserAccs,Integer oDateType){
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
			
			if(StringUtils.isNotBlank(userIds)){
				item.setUserIds(userIds.split(","));
			}else{
				List<TeamGroupBean> groups = tsmTeamGroupService.queryManageGroupList(user.getOrgId(), user.getAccount());
				if(groups!=null && groups.size()>0){
					String[] gids = new String[groups.size()];
					for(int i=0;i<groups.size();i++){
						gids[i] = groups.get(i).getGroupId();
					}
					item.setGroupIds(gids);
				} 
			}
			
			item.setStatus("1");
			
			List<PlanUsermonthBean> list = service.findListPage(item);
			
			map.put("filedMap", customFieldUtils.getCustomFiled(user));
			map.put("userIds",userIds);
			map.put("userAccs", userAccs);
			map.put("authUserIds",authUserIds);
			map.put("authUserAccs", authUserAccs);
			map.put("oDateType", oDateType);
			
			map.put("list", list);
			map.put("item", item);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		return "/plan/month/user/auth_view";
	}
	
	//月计划点评弹窗（团队月计划引用）
	@RequestMapping("/commontWindow")
	public String commontWindow(ModelMap modelMap,String planId){
		try {
			modelMap.put("planId", planId);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		return "/plan/month/user/commont_window";
	}
	
	//月计划审核（团队月计划引用）
	@RequestMapping("/auth")
	@ResponseBody
	public Map<String, String> auth(String planIdsStr ,String authState,String context){
		Map<String, String> map= new HashMap<String, String>();
		try {
			if(StringUtils.isBlank(context)) context = null;
			ShiroUser user = getUser();
			service.authPlan(user.getOrgId(), user.getId(), user.getName(), planIdsStr.split(","), authState, context);
			map.put("status", "success");
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			map.put("status", "erro");
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
			PlanUsermonthCommontBean commont = commontService.insert(user.getOrgId(), user.getGroupId(), user.getId(), planId, context);
			
			PlanUsermonthBean plan = service.getById(user.getOrgId(), planId);
			if(plan!=null){
				//计划用户ID
				String planUserId = plan.getUserId();
				// 发送消息
				tsmMessageService.createPlanReviewMessage(planUserId,commont.getContext(),commont.getId());
			}
			
			service.upDateLastCommontId(user.getOrgId(), planId, commont.getId());
			map.put("status", "success");
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			map.put("status", "erro");
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
			logger.error(e.getMessage(),e);
			map.put("status", "erro");
		}
		return map;
	}
	
	@RequestMapping("/getUserMonthPlan")
	@ResponseBody
	public PlanUsermonthBean getUserMonthPlan(String year,String month){
		ShiroUser user = getUser();
		Date autoAuthTime = customFieldUtils.getAutoAuthTime(user.getOrgId());
		
		if(new Date().after(autoAuthTime)){
			PlanUsermonthBean bean= new PlanUsermonthBean();
			bean.setTooLateEdit(true);
			return bean;
		}else{
			return service.queryByUserAndMonth(user.getOrgId(), user.getId(), year, month);
		}
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
