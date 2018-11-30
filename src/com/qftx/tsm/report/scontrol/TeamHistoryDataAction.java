package com.qftx.tsm.report.scontrol;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.team.bean.TeamGroupBean;
import com.qftx.base.team.bean.TsmTeamGroupMemberBean;
import com.qftx.base.team.service.TsmTeamGroupMemberService;
import com.qftx.base.team.service.TsmTeamGroupService;
import com.qftx.base.util.DateUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.tsm.report.bean.TsmReportCallInfoBean;
import com.qftx.tsm.report.bean.TsmReportPlanBean;
import com.qftx.tsm.report.service.TsmReportCallInfoService;
import com.qftx.tsm.report.service.TsmReportPlanService;
import com.qftx.tsm.report.utils.DateSubUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
/**
 *小组历史数据
 */
@Controller
@RequestMapping(value = "/report/teamHistoryData")
public class TeamHistoryDataAction {
	Logger logger = Logger.getLogger(TeamHistoryDataAction.class);
	
	@Autowired
    private TsmReportPlanService tsmReportPlanService ;
	@Autowired
    private TsmTeamGroupService tsmTeamGroupService;
	@Autowired 
	private TsmTeamGroupMemberService tsmTeamGroupMemberService;
	@Autowired
    private TsmReportCallInfoService tsmReportCallInfoService; 
	
	@RequestMapping("/mainPage")
	public String mainPage(ModelMap model){
		return "report/teamHistory/mainPage";
	}
	
	@RequestMapping("/saleResult")
	public String saleResult(ModelMap model,TsmReportPlanBean item,String fromDateStr,String toDateStr,String pageType,String timeType){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			List<TeamGroupBean> groups = tsmTeamGroupService.queryManageGroupList(user.getOrgId(), user.getAccount());
			if(groups==null ||groups.size()==0){
				return "/error/no_root";
			}
			
			Date fromDate= null;
	    	Date toDate=null;
	    	
	    	Date[] array = DateSubUtil.getDateFromStr(fromDateStr, toDateStr,"week", 0);
	    	
	    	fromDate = array[0];
	    	toDate = array[1];
	    	
	    	Date[] toWeekArray = DateSubUtil.toWeek();
	    	Date[] toMonthArray = DateSubUtil.toMonth();
	    	
	    	if(StringUtils.isBlank(fromDateStr)||StringUtils.isBlank(toDateStr)){
				fromDateStr = DateUtil.getDateDay(fromDate);
				toDateStr = DateUtil.getDateDay(toDate);
			}
	    	
	    	if(StringUtils.isBlank(item.getGroupIdsStr())){
				item.setGroupIds(tsmTeamGroupService.getGroupIds(groups));
				item.setGroupNames(tsmTeamGroupService.getGroupNamesStr(groups));
				item.setGroupIdsStr(tsmTeamGroupService.getGroupIdsStr(groups));
	    	}else{
	    		item.setGroupIds(item.getGroupIdsStr().split(","));
	    	}
	    	
	    	item.setFromDate(fromDate);
	    	item.setToDate(toDate);
	    	
	    	List<TsmReportPlanBean> list = tsmReportPlanService.findSumByGroup(user.getOrgId(), item.getGroupIds(), fromDate, toDate);
	    	//TsmReportPlanBean total = tsmReportPlanService.getTotal(list);
	    	
	    	if(StringUtils.isBlank(timeType)) timeType="0";
	    	model.put("timeType", timeType);
	    	model.put("toMonthArray", toMonthArray);
	    	model.put("toWeekArray", toWeekArray);
	    	
			model.put("fromDateStr", fromDateStr);
			model.put("toDateStr", toDateStr);
			//model.put("total", total);
			model.put("item", item);
			model.put("list", list);
			model.put("groups", groups);
		} catch (Exception e) {
			logger.error("",e);
		}
		if("res".equals(pageType)){
			return "report/teamHistory/saleResult_res";
		}else{
			return "report/teamHistory/saleResult_will";
		}
	}
	
	@RequestMapping("/saleResultUser")
	public String saleResultUser(ModelMap model,TsmReportPlanBean item,String fromDateStr,String toDateStr,String pageType){
		try {
		ShiroUser user = ShiroUtil.getShiroUser();
		Date fromDate= null;
    	Date toDate=null;
    	Date[] array = DateSubUtil.getDateFromStr(fromDateStr, toDateStr,"week", 0);
    	
    	fromDate = array[0];
    	toDate = array[1];
    	
    	List<String> childGroupIds = tsmTeamGroupService.findAllSonGroupIds(user.getOrgId(), item.getGroupId());
    	childGroupIds.add(item.getGroupId());
		List<TsmTeamGroupMemberBean> members = tsmTeamGroupMemberService.findByGroupIds(user.getOrgId(),childGroupIds.toArray(new String[childGroupIds.size()]));
    	
    	if(item.getUserIds()==null||item.getUserIds().length==0){
    		item.setUserIds(tsmTeamGroupMemberService.getUserIds(members));
    		item.setUserNames(tsmTeamGroupMemberService.getUserNames(members));
    	}
    	
    	List<TsmReportPlanBean> list = tsmReportPlanService.findSumByUser(user.getOrgId(),item.getUserIds(), fromDate, toDate);
		
    	model.put("fromDateStr", fromDateStr);
		model.put("toDateStr", toDateStr);
    	model.put("members", members);
    	model.put("item", item);
		model.put("list", list);
		} catch (Exception e) {
			logger.error("",e);
		}
		if("res".equals(pageType)){
			return "report/teamHistory/saleResult_res_user";
		}else{
			return "report/teamHistory/saleResult_will_user";
		}
		
	}
	
	@RequestMapping("/saleResultDetail")
	public String saleResultDetail(ModelMap model,TsmReportPlanBean item,String fromDateStr,String toDateStr,String pageType){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			Date fromDate= null;
	    	Date toDate=null;
	    	Date[] array = DateSubUtil.getDateFromStr(fromDateStr, toDateStr,"week", 0);
	    	fromDate = array[0];
	    	toDate = array[1];
	    	
	    	item.setFromDate(DateUtil.dateBegin(fromDate));
	    	item.setToDate(DateUtil.dateEnd(toDate));
	    	List<TsmReportPlanBean> list = tsmReportPlanService.findListByUser(user.getOrgId(), item);
	    	
	    	model.put("item", item);
	    	model.put("list", list);
		} catch (Exception e) {
			logger.error("",e);
		}
		
		if("res".equals(pageType)){
			return "report/teamHistory/saleResult_res_detail";
		}else{
			return "report/teamHistory/saleResult_will_detail";
		}
		
	}
	
	@RequestMapping("/dayData")
	public String dayData(ModelMap model,String dataIndex,String dataName,Integer index,String fromDateStr,String toDateStr,String timeType){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			List<TeamGroupBean> groups = tsmTeamGroupService.queryManageGroupList(user.getOrgId(), user.getAccount());
			if(groups==null ||groups.size()==0){
				return "/error/no_root";
			}
			
			Date fromDate= null;
	    	Date toDate=null;
	    	

        	if(StringUtils.isBlank(dataIndex)) dataIndex="line1";
        	
        	if(StringUtils.isBlank(dataName))dataName="呼出总数";
        	
        	if(index==null) index=0;
	    	
	    	Date[] array = DateSubUtil.getDateFromStr(fromDateStr, toDateStr,"week", 0);
	    	
	    	fromDate = array[0];
	    	toDate = array[1];
	    	
	    	Date[] toWeekArray = DateSubUtil.toWeek();
	    	Date[] toMonthArray = DateSubUtil.toMonth();
	    	
	    	if(StringUtils.isBlank(fromDateStr)||StringUtils.isBlank(toDateStr)){
				fromDateStr = DateUtil.getDateDay(fromDate);
				toDateStr = DateUtil.getDateDay(toDate);
			}
	    	
	    	List<String> userIds = tsmTeamGroupMemberService.findUserIdsByGroupIds(user.getOrgId(), tsmTeamGroupService.getGroupIds(groups));
	    	
	    	List<TsmReportCallInfoBean> list = tsmReportCallInfoService.findSumByDate(user.getOrgId(),userIds.toArray(new String[userIds.size()]) , fromDate, toDate,"%Y-%m-%d", "desc");
	    	TsmReportCallInfoBean total = new TsmReportCallInfoBean();
	    	if(list!=null && list.size()>0){
	    		total.setDateFmt("合计");
	    		
	    		for(TsmReportCallInfoBean info:list){
	    			total.setCallInNum((total.getCallInNum() == null?0:total.getCallInNum()) + (info.getCallInNum() == null?0:info.getCallInNum()));
	    			total.setCallOutNum((total.getCallOutNum() == null?0:total.getCallOutNum()) + (info.getCallOutNum() == null?0:info.getCallOutNum()));
	    			total.setCallRes((total.getCallRes() == null?0:total.getCallRes()) + (info.getCallRes() == null?0:info.getCallRes()));
	    			total.setValidCallOut((total.getValidCallOut() == null?0:total.getValidCallOut()) + (info.getValidCallOut() == null?0:info.getValidCallOut()));
	    			total.setChargeTime((total.getChargeTime() == null?0:total.getChargeTime()) + (info.getChargeTime() == null?0:info.getChargeTime()));
	    			total.setCallTime((total.getCallTime() == null?0:total.getCallTime()) + (info.getCallTime() == null?0:info.getCallTime()));
	    			total.setInCallTime((total.getInCallTime() == null?0:total.getInCallTime()) + (info.getInCallTime() == null?0:info.getInCallTime()));
	    			total.setWillNum((total.getWillNum() == null?0:total.getWillNum()) + (info.getWillNum() == null?0:info.getWillNum()));
	    			total.setSignNum((total.getSignNum() == null?0:total.getSignNum()) + (info.getSignNum() == null?0:info.getSignNum()));
	    			total.setSignMoney(BigDecimal.valueOf(total.getSignMoney() == null?0:total.getSignMoney()).add(BigDecimal.valueOf(info.getSignMoney() == null?0:info.getSignMoney())).doubleValue());
	    			total.setSaleChanceMoney(BigDecimal.valueOf(total.getSaleChanceMoney() == null?0:total.getSaleChanceMoney()).add(BigDecimal.valueOf(info.getSaleChanceMoney() == null?0:info.getSaleChanceMoney())).doubleValue());
	    			total.setSaleMoney(BigDecimal.valueOf(total.getSaleMoney() == null?0:total.getSaleMoney()).add(BigDecimal.valueOf(info.getSaleMoney() == null?0:info.getSaleMoney())).doubleValue());
	    		}
	    		list.add(total);
	    	}
	    	
	    	
	    	if(StringUtils.isBlank(timeType)) timeType="0";
	    	model.put("timeType", timeType);
	    	model.put("toMonthArray", toMonthArray);
	    	model.put("toWeekArray", toWeekArray);
	    	model.put("fromDateStr", fromDateStr);
			model.put("toDateStr", toDateStr);
	    	model.put("index", index);
	    	model.put("dataIndex",dataIndex);
	    	model.put("dataName",dataName);
			model.put("list", list);
			model.put("timeElngth", tsmReportCallInfoService.getTimeElngth(user.getOrgId()));
		} catch (Exception e) {
			logger.error("",e);
		}
		
		return "report/teamHistory/dayData";
	}
	
	@RequestMapping(value = "/dayChart")
    public String dayline(ModelMap modelMap,String type,Integer index,String fromDateStr,String toDateStr){
    	try {
    		ShiroUser user = ShiroUtil.getUser();
    		List<TeamGroupBean> groups = tsmTeamGroupService.queryManageGroupList(user.getOrgId(), user.getAccount());
        	Date fromDate= null;
        	Date toDate=null;
        	
        	Date[] array = DateSubUtil.getDateFromStr(fromDateStr, toDateStr,"week", 0);
        	
        	fromDate = array[0];
        	toDate = array[1];
        	
        	List<String> userIds = tsmTeamGroupMemberService.findUserIdsByGroupIds(user.getOrgId(), tsmTeamGroupService.getGroupIds(groups));
        	
        	List<TsmReportCallInfoBean> list = tsmReportCallInfoService.findSumByDate(user.getOrgId(),userIds.toArray(new String[userIds.size()]), fromDate, toDate,"%m月%d日", "asc");
	    	
    		modelMap.put("list", JsonUtil.getJsonString(list));
        	modelMap.put("index", index);
    	} catch (Exception e) {
    		logger.error(e.getMessage(),e);
    	}
    	
        return "report/teamHistory/chart/day_" + type;
    }
	
	@RequestMapping("/dayDataGroup")
	public String dayDataGroup(ModelMap model,String fromDateStr,String toDateStr){
		try {
			ShiroUser user = ShiroUtil.getUser();
			Date fromDate = DateUtil.dateBegin(DateUtil.parseDate(fromDateStr));
	    	Date toDate = DateUtil.dateBegin(DateUtil.parseDate(toDateStr));
			
			List<TeamGroupBean> groups = tsmTeamGroupService.queryManageGroupList(user.getOrgId(), user.getAccount());
			
			
			List<TsmReportCallInfoBean> list = tsmReportCallInfoService.findSumByGroup(user.getOrgId(), tsmTeamGroupService.getGroupIds(groups), fromDate, toDate, "desc");
			
			model.put("fromDateStr", fromDateStr);
			model.put("toDateStr", toDateStr);
			model.put("list", list);
		} catch (Exception e) {
			logger.error("", e);
		}
		
		return "report/teamHistory/dayData_group";
	}
	
	@RequestMapping("/dayDataUser")
	public String dayDataUser(ModelMap model,String fromDateStr,String toDateStr,String groupId,String column,String orderType){
		try {
			ShiroUser user = ShiroUtil.getUser();
	    	Date fromDate = DateUtil.dateBegin(DateUtil.parseDate(fromDateStr));
	    	Date toDate = DateUtil.dateBegin(DateUtil.parseDate(toDateStr));
	    	
	    	List<String> childGroupIds = tsmTeamGroupService.findAllSonGroupIds(user.getOrgId(), groupId);
	    	childGroupIds.add(groupId);
	    	
	    	String orderKey ="t1.ACCOUNT";
	    	if(StringUtils.isNotBlank(column)){
	    		orderKey =column+" "+orderType;
			}
	    	
	    	List<TsmReportCallInfoBean> list = tsmReportCallInfoService.findByGroupAndDate(user.getOrgId(), childGroupIds.toArray(new String[childGroupIds.size()]), fromDate, toDate, orderKey);
	    	
	    	model.put("fromDateStr", fromDateStr);
			model.put("toDateStr", toDateStr);
	    	model.put("groupId", groupId);
	    	model.put("column", column);
	    	model.put("orderType", orderType);
	    	model.put("list", list);
		} catch (Exception e) {
			logger.error("",e);
		}
		
		return "report/teamHistory/dayData_user";
	}
	
	@RequestMapping("/monthData")
	public String monthData(ModelMap model,String dataIndex,String dataName,Integer index,String fromDateStr,String toDateStr,String timeType){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			List<TeamGroupBean> groups = tsmTeamGroupService.queryManageGroupList(user.getOrgId(), user.getAccount());
			if(groups==null ||groups.size()==0){
				return "/error/no_root";
			}
			
			Date fromDate= null;
	    	Date toDate=null;
	    	

        	if(StringUtils.isBlank(dataIndex)) dataIndex="bar1";
        	
        	if(StringUtils.isBlank(dataName))dataName="呼出总数";
        	
        	if(index==null) index=0;
	    	
	    	Date[] array = DateSubUtil.getDateFromStr(fromDateStr, toDateStr,"year", 0);
	    	
	    	fromDate = array[0];
	    	toDate = array[1];
	    	
	    	Date[] toYearArray = DateSubUtil.toYear();
	    	Date[] toMonthArray = DateSubUtil.toMonth();
	    	
	    	if(StringUtils.isBlank(fromDateStr)||StringUtils.isBlank(toDateStr)){
				fromDateStr = DateUtil.getDataMonth(fromDate);
				toDateStr = DateUtil.getDataMonth(toDate);
			}
	    	
	    	List<String> userIds = tsmTeamGroupMemberService.findUserIdsByGroupIds(user.getOrgId(), tsmTeamGroupService.getGroupIds(groups));
	    	List<TsmReportCallInfoBean> list = tsmReportCallInfoService.findSumByDate(user.getOrgId(), userIds.toArray(new String[userIds.size()]), fromDate, toDate,"%Y-%m", "desc");
	    	
	    	if(StringUtils.isBlank(timeType)) timeType="1";
	    	model.put("timeType", timeType);
	    	model.put("toMonthArray", toMonthArray);
	    	model.put("toYearArray", toYearArray);
	    	model.put("fromDateStr", fromDateStr);
			model.put("toDateStr", toDateStr);
	    	model.put("index", index);
	    	model.put("dataIndex",dataIndex);
	    	model.put("dataName",dataName);
			model.put("list", list);
			model.put("timeElngth", tsmReportCallInfoService.getTimeElngth(user.getOrgId()));
		} catch (Exception e) {
			logger.error("",e);
		}
		
		
		return "report/teamHistory/monthData";
	}
	
	@RequestMapping(value = "/monthChart")
    public String monthChart(ModelMap modelMap,String type,Integer index,String fromDateStr,String toDateStr){
    	try {
    		ShiroUser user = ShiroUtil.getUser();
    		List<TeamGroupBean> groups = tsmTeamGroupService.queryManageGroupList(user.getOrgId(), user.getAccount());
        	Date fromDate= null;
        	Date toDate=null;
        	
        	Date[] array = DateSubUtil.getDateFromStr(fromDateStr, toDateStr,"month", 0);
        	
        	fromDate = array[0];
        	toDate = array[1];
        	List<String> userIds = tsmTeamGroupMemberService.findUserIdsByGroupIds(user.getOrgId(), tsmTeamGroupService.getGroupIds(groups));
        	List<TsmReportCallInfoBean> list = tsmReportCallInfoService.findSumByDate(user.getOrgId(), userIds.toArray(new String[userIds.size()]), fromDate, toDate,"%Y-%m", "asc");
	    	
    		modelMap.put("list", JsonUtil.getJsonString(list));
        	modelMap.put("index", index);
    	} catch (Exception e) {
    		logger.error(e.getMessage(),e);
    	}
    	
        return "report/teamHistory/chart/month_" + type;
    }
	
	@RequestMapping("/monthDataGroup")
	public String monthDataGroup(ModelMap model,String dateStr){
		try {
			ShiroUser user = ShiroUtil.getUser();
			Date date = DateSubUtil.parseDateFromStr(dateStr);
			
			Date fromDate= DateUtil.monthBegin(date);
	    	Date toDate=DateUtil.monthEnd(date);
			
			List<TeamGroupBean> groups = tsmTeamGroupService.queryManageGroupList(user.getOrgId(), user.getAccount());
			List<TsmReportCallInfoBean> list = tsmReportCallInfoService.findSumByGroup(user.getOrgId(), tsmTeamGroupService.getGroupIds(groups), fromDate, toDate, "desc");
			
			model.put("dateStr", dateStr);
			model.put("list", list);
		} catch (Exception e) {
			logger.error("", e);
		}
		
		return "report/teamHistory/monthData_group";
	}
	
	@RequestMapping("/monthDataUser")
	public String monthDataUser(ModelMap model,String dateStr,String groupId,String column,String orderType){
		try {
			ShiroUser user = ShiroUtil.getUser();
			Date date = DateSubUtil.parseDateFromStr(dateStr);
			
			Date fromDate= DateUtil.monthBegin(date);
	    	Date toDate=DateUtil.monthEnd(date);
	    	
	    	List<String> childGroupIds = tsmTeamGroupService.findAllSonGroupIds(user.getOrgId(), groupId);
	    	childGroupIds.add(groupId);
	    	
	    	List<String> userIds = tsmTeamGroupMemberService.findUserIdsByGroupIds(user.getOrgId(),childGroupIds.toArray(new String[childGroupIds.size()]));
	    	String orderKey ="t1.ACCOUNT";
	    	if(StringUtils.isNotBlank(column)){
	    		orderKey =column+" "+orderType+",t1.ACCOUNT";
			}
	    	
	    	List<TsmReportCallInfoBean> list = tsmReportCallInfoService.findSumByGroupAndDate(user.getOrgId(), userIds.toArray(new String[userIds.size()]), fromDate, toDate,orderKey);
	    	
	    	model.put("dateStr", dateStr);
	    	model.put("groupId", groupId);
	    	model.put("column", column);
	    	model.put("orderType", orderType);
	    	model.put("list", list);
		} catch (Exception e) {
			logger.error("",e);
		}
		
		return "report/teamHistory/monthData_user";
	}
	
	@RequestMapping("/exportDataUser")
	public String exportDataUser(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap,String type){
		/*Enumeration<String> attrs = request.getAttributeNames();
		logger.info("***************attrs*******************");
		while (attrs.hasMoreElements()) {
			String attr = attrs.nextElement();
			Object value = request.getAttribute(attr);
			
			logger.info(attr+"\t"+String.valueOf(value));
		}
		logger.info("###############attrs###################");*/
		
		
		Enumeration<String> params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String param = params.nextElement();
			String value = request.getParameter(param);
			logger.debug(param+"\t"+value);
			modelMap.put(param, value);
		}
		
		String url =null;
		if("0".equals(type)){
			url="/report/teamHistoryData/dayDataUserExport";
		}else{
			url="/report/teamHistoryData/monthDataUserExport";
		}
		
		modelMap.put("url", url);
		
		return "report/idialog/teamHistoryDataOutput";
	}
}
