
package com.qftx.tsm.report.scontrol;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.DateUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.util.StringUtils;
import com.qftx.tsm.main.service.MainInfoService;
import com.qftx.tsm.report.bean.TsmReportCallInfoBean;
import com.qftx.tsm.report.bean.TsmReportPlanBean;
import com.qftx.tsm.report.service.TsmReportCallInfoService;
import com.qftx.tsm.report.service.TsmReportPlanService;
import com.qftx.tsm.report.utils.DateSubUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 *个人历史数据
 */
@Controller
@RequestMapping(value = "/report/userStatis")
public class UserHistoryDataAction{
    private static final Logger logger = Logger.getLogger(UserHistoryDataAction.class.getName());
    @Autowired
    private TsmReportCallInfoService tsmReportCallInfoService;    
    @Autowired 
    private MainInfoService mainInfoService;
    @Autowired
    private TsmReportPlanService tsmReportPlanService;

    @RequestMapping()
    public String list(HttpServletRequest request, HttpServletResponse response){
        return "report/userHistory/mainPage";
    }

    @RequestMapping(value = "/saleResult")
    public String saleResult(ModelMap modelMap,TsmReportPlanBean item,String fromDateStr,String toDateStr,String pageType,String timeType){
    	try {
    		ShiroUser user = ShiroUtil.getUser();
        	Date date = new Date();
        	
        	Date fromDate= null;
        	Date toDate=null;
        	
        	Date[] array = DateSubUtil.getDateFromStr(fromDateStr, toDateStr,"week", 0);
        	Date[] toWeekArray = DateSubUtil.toWeek();
	    	Date[] toMonthArray = DateSubUtil.toMonth();
	    	
	    	fromDate = array[0];
        	toDate = array[1];
	    	if(StringUtils.isBlank(fromDateStr)||StringUtils.isBlank(toDateStr)){
				fromDateStr = DateUtil.getDateDay(fromDate);
				toDateStr = DateUtil.getDateDay(toDate);
			}
        	
        	List<TsmReportPlanBean> list = tsmReportPlanService.findByUser(user.getOrgId(),user.getId(), fromDate, toDate);
        	if(StringUtils.isBlank(timeType)) timeType="0";
        	modelMap.put("timeType", timeType);
        	modelMap.put("toMonthArray", toMonthArray);
        	modelMap.put("toWeekArray", toWeekArray);
        	modelMap.put("fromDateStr", fromDateStr);
        	modelMap.put("toDateStr", toDateStr);
        	modelMap.put("list", list);
        	modelMap.put("currentDate", date);
        	modelMap.put("user", user);
    	} catch (Exception e) {
    		logger.error(e.getMessage(),e);
    	}
    	
    	if("res".equals(pageType)){
			return "report/userHistory/saleResult_res";
		}else{
			return "report/userHistory/saleResult_will";
		}
    }

    @RequestMapping(value = "/dayData")
    public String dayData(ModelMap modelMap,String dataIndex,String dataName,Integer index,String fromDateStr,String toDateStr,String timeType){
    	try {
    		ShiroUser user = ShiroUtil.getUser();
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
        	
        	List<TsmReportCallInfoBean> list = tsmReportCallInfoService.findByAccAndDate(user.getOrgId(), user.getAccount(), fromDate, toDate,"desc");
        	
        	tsmReportCallInfoService.formatCallTime(list);
        	tsmReportCallInfoService.processPercent(user.getOrgId(), list);
        	if(StringUtils.isBlank(timeType)) timeType="0";
        	modelMap.put("timeType", timeType);
        	modelMap.put("list", list);
        	modelMap.put("toMonthArray", toMonthArray);
        	modelMap.put("toWeekArray", toWeekArray);
        	modelMap.put("fromDateStr", fromDateStr);
        	modelMap.put("toDateStr", toDateStr);
        	
        	modelMap.put("index", index);
        	modelMap.put("dataIndex",dataIndex);
        	modelMap.put("dataName",dataName);
        	
        	modelMap.put("timeElngth", tsmReportCallInfoService.getTimeElngth(user.getOrgId()));
    	} catch (Exception e) {
    		logger.error(e.getMessage(),e);
    	}
        return "report/userHistory/userStatis_day";
    }

    @RequestMapping(value = "/dayChart")
    public String dayline(ModelMap modelMap,String type,Integer index,String fromDateStr,String toDateStr){
    	try {
    		ShiroUser user = ShiroUtil.getUser();
        	
        	Date fromDate= null;
        	Date toDate=null;
        	
        	Date[] array = DateSubUtil.getDateFromStr(fromDateStr, toDateStr,"week", 0);
        	
        	fromDate = array[0];
        	toDate = array[1];
        	
    		List<TsmReportCallInfoBean> list = tsmReportCallInfoService.findByAccAndDate(user.getOrgId(), user.getAccount(), fromDate, toDate,"asc");
    		tsmReportCallInfoService.formatCallTime(list);
    		tsmReportCallInfoService.processPercent(user.getOrgId(), list);
    		modelMap.put("list", JsonUtil.getJsonString(list));
        	modelMap.put("index", index);
    	} catch (Exception e) {
    		logger.error(e.getMessage(),e);
    	}
    	
        return "report/userHistory/chart/userStatis_day_" + type;
    }

    @RequestMapping(value = "/monthData")
    public String monthData(ModelMap modelMap,String dataIndex,String dataName,Integer index,String fromDateStr,String toDateStr,String timeType){
    	
    	try {
    		ShiroUser user = ShiroUtil.getUser();
        	
        	Date fromDate= null;
        	Date toDate=null;
        	if(StringUtils.isBlank(dataIndex)) dataIndex="bar1";
        	if(index==null) index=0;
        	Date[] array = DateSubUtil.getDateFromStr(fromDateStr, toDateStr,"year", 0);
        	
        	fromDate = array[0];
        	toDate = array[1];
        	
        	Date[] toYearArray = DateSubUtil.toYear();
        	if(StringUtils.isBlank(fromDateStr)||StringUtils.isBlank(toDateStr)){
				fromDateStr = DateUtil.getDataMonth(fromDate);
				toDateStr = DateUtil.getDataMonth(toDate);
			}
        	
        	
        	List<TsmReportCallInfoBean> list = tsmReportCallInfoService.findSumByAccAndDate(user.getOrgId(), user.getAccount(), fromDate, toDate,"desc");
        	tsmReportCallInfoService.formatCallTime(list);
        	if(StringUtils.isBlank(timeType)) timeType="1";
        	modelMap.put("timeType", timeType);
        	modelMap.put("list", list); 
        	modelMap.put("fromDateStr", fromDateStr);
        	modelMap.put("toDateStr", toDateStr);
        	modelMap.put("toYearArray", toYearArray);
        	modelMap.put("index", index);
        	modelMap.put("dataIndex",dataIndex);
        	modelMap.put("dataName",dataName);
        	modelMap.put("timeElngth", tsmReportCallInfoService.getTimeElngth(user.getOrgId()));
        	
    	} catch (Exception e) {
    		logger.error(e.getMessage(),e);
    	}
    	
        return "report/userHistory/userStatis_month";
    }

    @RequestMapping(value = "/monthChart")
    public String monthChart(ModelMap modelMap,String type,Integer index,String fromDateStr,String toDateStr){
    	try {
    		ShiroUser user = ShiroUtil.getUser();
        	Date fromDate= null;
        	Date toDate=null;
        	Date[] array = DateSubUtil.getDateFromStr(fromDateStr, toDateStr,"year", 0);
        	
        	fromDate = array[0];
        	toDate = array[1];
            
    		List<TsmReportCallInfoBean> list = tsmReportCallInfoService.findSumByAccAndDate(user.getOrgId(),user.getAccount(), fromDate, toDate,"asc");
    		tsmReportCallInfoService.formatCallTime(list);
    		
    		modelMap.put("list", JsonUtil.getJsonString(list));
    	    modelMap.put("index", index);
    	} catch (Exception e) {
    		logger.error(e.getMessage(),e);
    	}
    	
        return "report/userHistory/chart/userStatis_month_" + type;
    }
    

    
	
}
