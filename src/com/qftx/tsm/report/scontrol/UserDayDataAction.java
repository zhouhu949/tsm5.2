package com.qftx.tsm.report.scontrol;

import com.qftx.base.auth.service.UserService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.DateUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.contract.dto.ContractOrderDto;
import com.qftx.tsm.contract.service.ContractService;
import com.qftx.tsm.follow.service.CustSaleChanceService;
import com.qftx.tsm.main.service.ContactDayDataService;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.plan.user.day.dto.ContactResult;
import com.qftx.tsm.plan.user.day.service.PlanUserDayResourceService;
import com.qftx.tsm.plan.user.day.service.PlanUserdayWillcustService;
import com.qftx.tsm.report.bean.CallReportBean;
import com.qftx.tsm.report.dto.CallReportDto;
import com.qftx.tsm.report.service.CallReportService;
import com.qftx.tsm.report.service.TsmNewWillCountService;
import com.qftx.tsm.rest.service.CallRecordService;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;

/**
 * User�� bxl
 * Date�� 2015/12/18
 * Time�� 15:29
 */
@Controller
@RequestMapping(value = "/report/userDayData")
public class UserDayDataAction {
    private static final Logger logger = Logger.getLogger(UserDayDataAction.class.getName());
    @Autowired
    private UserService userService;
    @Autowired
    private CallRecordService callRecordService;
    @Autowired
    private ContactDayDataService contactDayDataService;
    @Autowired
    private PlanUserDayResourceService planUserDayResourceService;
    @Autowired
    private PlanUserdayWillcustService planUserdayWillcustService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private CallReportService callReportService;
    @Autowired
    private CachedService cachedService;
    @Autowired
    private TsmNewWillCountService csmNewWillCountService;
    @Autowired
    private CustSaleChanceService custSaleChanceService;
    @RequestMapping()
    public String list(HttpServletRequest request, HttpServletResponse response){
    	try {
    		ShiroUser user = ShiroUtil.getShiroUser();
        	ContactResult cr = planUserDayResourceService.queryContactResult(user.getOrgId(),user.getId().split(","),new Date(),"1");
    		ContactResult cr1 = planUserdayWillcustService.queryContactResult(user.getOrgId(), user.getId().split(","),new Date(),"1");
    		ContractOrderDto orderDto = contractService.findTodayReport(user.getOrgId(), user.getAccount());
    		/**预期签单金额  根据销售计划预期签单时间 客户所有者查询*/
    		BigDecimal willSignMoney = custSaleChanceService.getTodayWillSignMoney(user.getOrgId(), user.getAccount());
    		orderDto.setWillSignMoney(willSignMoney);
    		
    		CallReportBean callReportBean = callReportService.getUserTodayCallReport(user.getOrgId(),user.getAccount());
            callReportBean.setCallinTime((int)(Math.ceil((double)callReportBean.getCallinTime()/(double)60)));
            callReportBean.setCalloutTime((int)(Math.ceil((double)callReportBean.getCalloutTime()/(double)60)));
            CallReportDto callReportDto = analy(callReportBean,user.getOrgId());
            Map<String, Integer> resMap = new HashMap<String, Integer>();
            Map<String, Integer> intMap = new HashMap<String, Integer>();
            resMap = contactDayDataService.getContactReport(user.getOrgId(), Arrays.asList(user.getAccount()), DateUtil.formatDate(new Date(),DateUtil.DATE_DAY), 1);
            intMap = contactDayDataService.getContactReport(user.getOrgId(), Arrays.asList(user.getAccount()), DateUtil.formatDate(new Date(),DateUtil.DATE_DAY), 2);
            if(cr != null){
            	resMap.put("planNum", cr.getTotalCount());
            	resMap.put("noConNum", cr.getNoContactCount());
            }
            
            if(cr1 != null){
            	intMap.put("planNum", cr1.getTotalCount());
            	intMap.put("noConNum", cr1.getNoContactCount());
            }
            int timeLength = getTimeElngth(user.getOrgId());
            request.setAttribute("timeLength", timeLength);
            request.setAttribute("resMap", resMap);
            request.setAttribute("intMap", intMap);
            request.setAttribute("orderDto", orderDto);
            request.setAttribute("callReportBean", callReportDto);
            request.setAttribute("userAccount", user.getAccount());
		} catch (Exception e) {
			e.printStackTrace();
			throw new SysRunException(e);
		}
		return "report/userDayData";
    }
    
    
    /** 
     * 计算行动完成率<br />
     * 计算公式：<b>{(每日呼出已接次数/每日呼出已接次数达标值)*接通次数指标系数+（每日通话时长/每日通话时长达标值）*通话时长指标系数}乘以100%</b>
     * @create  2017年7月11日 下午2:26:03 lixing
     * @history  
     */
    public CallReportDto analy(CallReportBean bean,String orgId){
    	//每日呼出指数达标值 默认值100
    	int callNumPoint = 100;
    	List<DataDictionaryBean> callNumPointDirs = cachedService.getDirList(AppConstant.DATA_50008, orgId);
    	if(callNumPointDirs.size() > 0 && StringUtils.isNotBlank(callNumPointDirs.get(0).getDictionaryValue())){
    		callNumPoint = Integer.parseInt(callNumPointDirs.get(0).getDictionaryValue());
    	}
    	//每日呼出已接次数达标值 默认值200
    	int callTimePoint = 200;
    	List<DataDictionaryBean> callTimePointDirs = cachedService.getDirList(AppConstant.DATA_50005, orgId);
    	if(callTimePointDirs.size() > 0 && StringUtils.isNotBlank(callTimePointDirs.get(0).getDictionaryValue())){
    		callTimePoint = Integer.parseInt(callTimePointDirs.get(0).getDictionaryValue());
    	}
    	
    	// 行动完成率基数设置 呼入时长
    	String callInTime4Point = "0";
    	List<DataDictionaryBean> callInTimePointDirs = cachedService.getDirList(AppConstant.DATA_50006, orgId);
    	if(callInTimePointDirs.size() > 0 && StringUtils.isNotBlank(callInTimePointDirs.get(0).getDictionaryValue())){
    		callInTime4Point = callInTimePointDirs.get(0).getDictionaryValue();
    	}
    	
    	// 行动完成率基数设置 呼出时长
    	String callOutTime4Point = "0";
    	List<DataDictionaryBean> callOutTimePointDirs = cachedService.getDirList(AppConstant.DATA_50007, orgId);
    	if(callOutTimePointDirs.size() > 0 && StringUtils.isNotBlank(callOutTimePointDirs.get(0).getDictionaryValue())){
    		callOutTime4Point = callOutTimePointDirs.get(0).getDictionaryValue();
    	}
    	
    	//呼出已结次数达标值
    	float callNumStandard = 1f;
    	//通话时长达标值
    	float callTimeStandard = 1f;

    	List<DataDictionaryBean> stanrdPowerDirs = cachedService.getDirList(AppConstant.DATA_50004, orgId);
    	if(stanrdPowerDirs.size() > 0 && StringUtils.isNotBlank(stanrdPowerDirs.get(0).getDictionaryValue()) && stanrdPowerDirs.get(0).getDictionaryValue().equals("1")){
    		callNumStandard = 0.5f;
    		callTimeStandard = 0.5f;
    		
    		List<DataDictionaryBean> callNumStandardDirs = cachedService.getDirList(AppConstant.DATA_50010, orgId);
    		if(callNumStandardDirs.size() > 0 && StringUtils.isNotBlank(callNumStandardDirs.get(0).getDictionaryValue())){
    			callNumStandard = Float.parseFloat(callNumStandardDirs.get(0).getDictionaryValue());
    		}
    		
    		List<DataDictionaryBean> callTimeStandardDirs = cachedService.getDirList(AppConstant.DATA_50009, orgId);
    		if(callTimeStandardDirs.size() > 0 && StringUtils.isNotBlank(callTimeStandardDirs.get(0).getDictionaryValue())){
    			callTimeStandard = Float.parseFloat(callTimeStandardDirs.get(0).getDictionaryValue());
    		}
    	}
    	
    	int callTimeLength = 0;
    	if(callInTime4Point.equals("1")){
    		callTimeLength+=bean.getCallinTime();
    	}
    	if(callOutTime4Point.equals("1")){
    		callTimeLength+=bean.getCalloutTime();
    	}
    	
    	float rate = bean.getCalloutAnswer()*1.0f/callNumPoint*callNumStandard + callTimeLength*1.0f/callTimePoint*callTimeStandard;
    	
    	NumberFormat percentFormat = NumberFormat.getPercentInstance();
    	percentFormat.setMaximumFractionDigits(2); //最大小数位数 
    	String acr = percentFormat.format(rate);
    	
    	CallReportDto callReportDto = new CallReportDto();
    	BeanUtils.copyProperties(bean, callReportDto);
    	callReportDto.setActionCompletionRate(acr);
    	
    	return callReportDto;
    }
    
    public int getTimeElngth(String orgId) {
    	int timeElngth = 0;
    	if(cachedService.getDirList(AppConstant.DATA16, orgId).size() > 0){
			String isEffect = cachedService.getDirList(AppConstant.DATA16, orgId).get(0).getDictionaryValue();
			if ("1".equals(isEffect)) {
				timeElngth = new Integer(cachedService.getDirList(AppConstant.DATA04, orgId).get(0).getDictionaryValue());
			}
    	}
		return timeElngth;
	}
}
