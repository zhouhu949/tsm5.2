package com.qftx.tsm.report.scontrol;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.team.bean.TeamGroupBean;
import com.qftx.base.team.service.TsmTeamGroupMemberService;
import com.qftx.base.team.service.TsmTeamGroupService;
import com.qftx.base.util.DateUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.common.util.ExcelUtils;
import com.qftx.tsm.report.bean.TsmReportCallInfoBean;
import com.qftx.tsm.report.service.TsmReportCallInfoService;
import com.qftx.tsm.report.service.TsmReportPlanService;
import com.qftx.tsm.report.utils.DateSubUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;
/**
 *小组历史数据
 */
@Controller
@RequestMapping(value = "/report/teamHistoryData")
public class TeamHistoryDataExportAction {
	Logger logger = Logger.getLogger(TeamHistoryDataExportAction.class);
	
	@Autowired
    private TsmReportPlanService tsmReportPlanService ;
	@Autowired
    private TsmTeamGroupService tsmTeamGroupService;
	@Autowired 
	private TsmTeamGroupMemberService tsmTeamGroupMemberService;
	@Autowired
    private TsmReportCallInfoService tsmReportCallInfoService; 
	@Autowired
	private CachedService cachedService;
	
	
	@RequestMapping("/dayDataExport")
	public void dayDataExport(String fromDateStr,String toDateStr,HttpServletRequest request,HttpServletResponse response){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			List<TeamGroupBean> groups = tsmTeamGroupService.queryManageGroupList(user.getOrgId(), user.getAccount());
	    	
			Date[] array = DateSubUtil.getDateFromStr(fromDateStr, toDateStr,"week", 0);
	    	
	    	Date fromDate = array[0];
	    	Date toDate = array[1];
	    	
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
	    	
	    	response.setContentType("application/vnd.ms-excel");
	    	fileNameResponse(request, response, "小组历史数据统计（日）.xlsx");
    		ServletOutputStream out = response.getOutputStream();
        	
        	String[] headers = new String[]{"日期","呼出已接","呼出总数","呼叫资源数（个）","有效呼叫（个）","计费时长（分钟）","呼出时长（分钟）","呼入时长（分钟）","新增意向数（个）","新增签约数（个）","回款金额（万元）","签单金额（万元）","预期签单金额（万元）"};
			String[] fiedlNames = new String[]{"dateFmt","callInNum","callOutNum","callRes","validCallOut","chargeTime","callTime","inCallTime","willNum","signNum","signMoney","saleMoney","saleChanceMoney"};
			ExcelUtils.export(TsmReportCallInfoBean.class, headers, fiedlNames, list, out);
			
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
	@RequestMapping("/dayDataGroupExport")
	public void dayDataGroup(String fromDateStr,String toDateStr,HttpServletRequest request,HttpServletResponse response){
		try {
			ShiroUser user = ShiroUtil.getUser();
			Date fromDate = DateUtil.dateBegin(DateUtil.parseDate(fromDateStr));
	    	Date toDate = DateUtil.dateBegin(DateUtil.parseDate(toDateStr));
			List<TeamGroupBean> groups = tsmTeamGroupService.queryManageGroupList(user.getOrgId(), user.getAccount());
			List<TsmReportCallInfoBean> list = tsmReportCallInfoService.findSumByGroup(user.getOrgId(), tsmTeamGroupService.getGroupIds(groups), fromDate, toDate, "desc");
			response.setContentType("application/vnd.ms-excel");
	    	fileNameResponse(request, response, "小组历史数据统计（日）-按组分析.xlsx");
    		ServletOutputStream out = response.getOutputStream();
        	
        	String[] headers = new String[]{"小组名称","呼出已接","呼出总数","呼叫资源数（个）","有效呼叫（个）","计费时长（分钟）","呼出时长（分钟）","呼入时长（分钟）","新增意向数（个）","新增签约数（个）","回款金额（万元）","签单金额（万元）","预期签单金额（万元）"};
			String[] fiedlNames = new String[]{"groupName","callInNum","callOutNum","callRes","validCallOut","chargeTime","callTime","inCallTime","willNum","signNum","signMoney","saleMoney","saleChanceMoney"};
			ExcelUtils.export(TsmReportCallInfoBean.class, headers, fiedlNames, list, out);
		} catch (Exception e) {
			logger.error("", e);
		}
	}
	
	@RequestMapping("/dayDataUserExport")
	public void dayDataUser(String fromDateStr,String toDateStr,String groupId,String column,String orderType,HttpServletRequest request,HttpServletResponse response){
		try {
			ShiroUser user = ShiroUtil.getUser();
	    	Date fromDate = DateUtil.dateBegin(DateUtil.parseDate(fromDateStr));
	    	Date toDate = DateUtil.dateBegin(DateUtil.parseDate(toDateStr));
	    	
	    	Set<String> childGroupIds = new HashSet<String>();
	    	for (String gid : groupId.split(",")) {
	    		childGroupIds.addAll(tsmTeamGroupService.findAllSonGroupIds(user.getOrgId(), gid));
	    		childGroupIds.add(gid);
	    	}
	    	
	    	String orderKey ="t1.ACCOUNT";
	    	if(StringUtils.isNotBlank(column)){
	    		orderKey =column+" "+orderType;
			}
	    	
	    	List<TsmReportCallInfoBean> list = tsmReportCallInfoService.findByGroupAndDate(user.getOrgId(), childGroupIds.toArray(new String[childGroupIds.size()]), fromDate, toDate, orderKey);
	    	
	    	Map<String, String> groupNameMap = cachedService.getOrgGroupNameMap(user.getOrgId());
	    	for (TsmReportCallInfoBean tsmReportCallInfoBean : list) {
	    		if(groupNameMap!= null && !groupNameMap.isEmpty()){
	    			tsmReportCallInfoBean.setGroupName(groupNameMap.get(tsmReportCallInfoBean.getGroupId()));
	    		}
			}
	    	
	    	response.setContentType("application/vnd.ms-excel");
	    	fileNameResponse(request, response, "小组历史数据统计（日）-个人统计.xlsx");
    		ServletOutputStream out = response.getOutputStream();
        	
        	String[] headers = new String[]{"销售姓名","所在小组","呼出已接","呼出总数","呼叫资源数（个）","有效呼叫（个）","计费时长（分钟）","呼出时长（分钟）","呼入时长（分钟）","新增意向数（个）","新增签约数（个）","回款金额（万元）","签单金额（万元）","预期签单金额（万元）"};
			String[] fiedlNames = new String[]{"userName","groupName","callInNum","callOutNum","callRes","validCallOut","chargeTime","callTime","inCallTime","willNum","signNum","signMoney","saleMoney","saleChanceMoney"};
			ExcelUtils.export(TsmReportCallInfoBean.class, headers, fiedlNames, list, out);
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
	@RequestMapping("/monthDataExport")
	public void monthData(String fromDateStr,String toDateStr,HttpServletRequest request,HttpServletResponse response){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			List<TeamGroupBean> groups = tsmTeamGroupService.queryManageGroupList(user.getOrgId(), user.getAccount());

			Date[] array = DateSubUtil.getDateFromStr(fromDateStr, toDateStr,"year", 0);
	    	
	    	Date fromDate = array[0];
	    	Date toDate = array[1];
	    	
	    	if(StringUtils.isBlank(fromDateStr)||StringUtils.isBlank(toDateStr)){
				fromDateStr = DateUtil.getDataMonth(fromDate);
				toDateStr = DateUtil.getDataMonth(toDate);
			}
	    	
	    	List<String> userIds = tsmTeamGroupMemberService.findUserIdsByGroupIds(user.getOrgId(), tsmTeamGroupService.getGroupIds(groups));
	    	List<TsmReportCallInfoBean> list = tsmReportCallInfoService.findSumByDate(user.getOrgId(), userIds.toArray(new String[userIds.size()]), fromDate, toDate,"%Y-%m", "desc");
	    	
	    	response.setContentType("application/vnd.ms-excel");
	    	fileNameResponse(request, response, "小组历史数据统计（月）.xlsx");
    		ServletOutputStream out = response.getOutputStream();
        	
        	String[] headers = new String[]{"月份","呼出已接","呼出总数","呼叫资源数（个）","有效呼叫（个）","计费时长（分钟）","呼出时长（分钟）","呼入时长（分钟）","新增意向数（个）","新增签约数（个）","回款金额（万元）","签单金额（万元）","预期签单金额（万元）"};
			String[] fiedlNames = new String[]{"dateFmt","callInNum","callOutNum","callRes","validCallOut","chargeTime","callTime","inCallTime","willNum","signNum","signMoney","saleMoney","saleChanceMoney"};
			ExcelUtils.export(TsmReportCallInfoBean.class, headers, fiedlNames, list, out);
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
	@RequestMapping("/monthDataGroupExport")
	public void monthDataGroup(String dateStr,HttpServletRequest request,HttpServletResponse response){
		try {
			ShiroUser user = ShiroUtil.getUser();
			Date date = DateSubUtil.parseDateFromStr(dateStr);
			
			Date fromDate= DateUtil.monthBegin(date);
	    	Date toDate=DateUtil.monthEnd(date);
			
			List<TeamGroupBean> groups = tsmTeamGroupService.queryManageGroupList(user.getOrgId(), user.getAccount());
			List<TsmReportCallInfoBean> list = tsmReportCallInfoService.findSumByGroup(user.getOrgId(), tsmTeamGroupService.getGroupIds(groups), fromDate, toDate, "desc");
			
			response.setContentType("application/vnd.ms-excel");
	    	fileNameResponse(request, response, "小组历史数据统计（月）-按组分析.xlsx");
    		ServletOutputStream out = response.getOutputStream();
        	
        	String[] headers = new String[]{"小组名称","呼出已接","呼出总数","呼叫资源数（个）","有效呼叫（个）","计费时长（分钟）","呼出时长（分钟）","呼入时长（分钟）","新增意向数（个）","新增签约数（个）","回款金额（万元）","签单金额（万元）","预期签单金额（万元）"};
			String[] fiedlNames = new String[]{"groupName","callInNum","callOutNum","callRes","validCallOut","chargeTime","callTime","inCallTime","willNum","signNum","signMoney","saleMoney","saleChanceMoney"};
			ExcelUtils.export(TsmReportCallInfoBean.class, headers, fiedlNames, list, out);
		} catch (Exception e) {
			logger.error("", e);
		}
	}
	
	@RequestMapping("/monthDataUserExport")
	public void monthDataUser(String dateStr,String groupId,String column,String orderType,HttpServletRequest request,HttpServletResponse response){
		try {
			ShiroUser user = ShiroUtil.getUser();
			Date date = DateSubUtil.parseDateFromStr(dateStr);
			
			Date fromDate= DateUtil.monthBegin(date);
	    	Date toDate=DateUtil.monthEnd(date);
	    	
	    	Set<String> childGroupIds = new HashSet<String>();
	    	for (String gid : groupId.split(",")) {
	    		childGroupIds.addAll(tsmTeamGroupService.findAllSonGroupIds(user.getOrgId(), gid));
	    		childGroupIds.add(gid);
	    	}
	    	
	    	List<String> userIds = tsmTeamGroupMemberService.findUserIdsByGroupIds(user.getOrgId(),childGroupIds.toArray(new String[childGroupIds.size()]));
	    	String orderKey ="t1.ACCOUNT";
	    	if(StringUtils.isNotBlank(column)){
	    		orderKey =column+" "+orderType+",t1.ACCOUNT";
			}
	    	
	    	List<TsmReportCallInfoBean> list = tsmReportCallInfoService.findSumByGroupAndDate(user.getOrgId(), userIds.toArray(new String[userIds.size()]), fromDate, toDate,orderKey);
	    	Map<String, String> userGroupMap = cachedService.getUserGroupMap(user.getOrgId());
	    	Map<String, String> groupNameMap = cachedService.getOrgGroupNameMap(user.getOrgId());
	    	for (TsmReportCallInfoBean tsmReportCallInfoBean : list) {
	    		if(userGroupMap!= null && !userGroupMap.isEmpty()
	    				&&groupNameMap!= null && !groupNameMap.isEmpty()){
	    			tsmReportCallInfoBean.setGroupName(groupNameMap.get(userGroupMap.get(tsmReportCallInfoBean.getAccount())));
	    		}
			}
	    	
	    	response.setContentType("application/vnd.ms-excel");
	    	fileNameResponse(request, response, "小组历史数据统计（月）-个人统计.xlsx");
    		ServletOutputStream out = response.getOutputStream();
        	
        	String[] headers = new String[]{"销售姓名","所在小组","销售帐号","呼出已接","呼出总数","呼叫资源数（个）","有效呼叫（个）","计费时长（分钟）","呼出时长（分钟）","呼入时长（分钟）","新增意向数（个）","新增签约数（个）","回款金额（万元）","签单金额（万元）","预期签单金额（万元）"};
			String[] fiedlNames = new String[]{"userName","groupName","account","callInNum","callOutNum","callRes","validCallOut","chargeTime","callTime","inCallTime","willNum","signNum","signMoney","saleMoney","saleChanceMoney"};
			ExcelUtils.export(TsmReportCallInfoBean.class, headers, fiedlNames, list, out);
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
	public void fileNameResponse(HttpServletRequest request,HttpServletResponse response,String fileName) throws UnsupportedEncodingException{
		String agent = request.getHeader("User-Agent");
		response.setCharacterEncoding("UTF-8");
		boolean isFireFox = (agent != null && agent.toLowerCase().indexOf("firefox") != -1);
		if (!isFireFox) {
			response.setHeader("Content-disposition", "attachment;filename="+URLEncoder.encode(fileName,"UTF-8"));
		}else{
			response.setHeader("Content-disposition", "attachment;filename="+new String(fileName.getBytes("UTF-8"),"ISO-8859-1"));
		}
	}
}
