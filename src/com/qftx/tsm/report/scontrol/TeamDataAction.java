package com.qftx.tsm.report.scontrol;

import com.qftx.base.auth.service.UserService;
import com.qftx.base.team.dao.TsmTeamGroupMemberMapper;
import com.qftx.base.team.service.TsmTeamGroupService;
import com.qftx.base.util.DateUtil;
import com.qftx.tsm.main.service.MainInfoService;
import com.qftx.tsm.report.service.TsmReportPlanService;
import com.qftx.tsm.rest.service.CallRecordService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Calendar;
import java.util.Date;

/**
 * User�� bxl
 * Date�� 2015/12/18
 * Time�� 15:29
 */
@Controller
@RequestMapping(value = "/report/teamData")
public class TeamDataAction {
    private static final Logger logger = Logger.getLogger(TeamDataAction.class.getName());
    @Autowired
    private UserService userService;
    @Autowired
    private CallRecordService callRecordService;
    @Autowired
    private TsmTeamGroupMemberMapper teamGroupMemberMapper;
    @Autowired
    private TsmTeamGroupService tsmTeamGroupService;
    @Autowired
    TsmReportPlanService tsmReportPlanService ;
    @Autowired
    private MainInfoService mainInfoService;

    @RequestMapping()
    public String list(ModelMap modelMap,String fromDateStr,String toDateStr,String groupId) throws Exception {
    	/*ShiroUser user = ShiroUtil.getUser();
    	Date date = new Date();
    	
    	Date fromDate= null;
    	Date toDate=null;
    	
		long startTime = System.currentTimeMillis();
    	
    	if(StringUtils.isEmpty(fromDateStr)||StringUtils.isEmpty(toDateStr)){
    		fromDateStr=null;
    		toDateStr=null;
    		fromDate= DateUtil.addDate(new Date(), 0,0, -1);
    		toDate= DateUtil.addDate(new Date(), 0, 0, -1);
    		
    	}else{
    		fromDate= parseDateFromStr(fromDateStr,"from");
    		toDate= parseDateFromStr(toDateStr,"to");;
    	}
    	//List<TeamGroupBean> groups = tsmTeamGroupService.queryManageTeamList(user.getOrgId(), user.getAccount());
    	List<TeamGroupBean> groups = teamGroupMapper.queryManageGroupList(user.getAccount());
    	
    	if(groups==null || groups.size()==0){
    		return "/error/no_root";
    	}else{
    		try {
    			String[] groupIds=null;
    	    	if(StringUtils.isBlank(groupId)){
    	    		groupIds=new String[]{groups.get(0).getGroupId()};
    	    		groupIds=new String[groups.size()];
    	    		for(int i=0;i<groups.size();i++){
    	    			groupIds[i] = groups.get(i).getGroupId();
    	    		}
    	    		
    	    	}else{
    	    		groupIds = new String[]{groupId};
    	    	}
    	    	List<TsmReportPlanBean> userList = tsmReportPlanService.findByGroup(user.getOrgId(),groupIds, fromDate, toDate);
    	    	
    	    	List<TsmReportPlanBean> teamList = tsmReportPlanService.findSumByGroup(user.getOrgId(), groupIds, fromDate, toDate);
    	    	
    	    	TsmReportPlanBean total = new TsmReportPlanBean();
    	    	total.setGroupName("全部");
    	    	
    	    	total.setResPlanNum(0);
    			total.setResNoContact(0);
    			total.setResTotalNum(0);
    			total.setResSignNum(0);
    			total.setResCustNum(0);
    			total.setResPoolNum(0);
    			total.setResNoNum(0);
    			total.setWillPlanNum(0);
    			total.setWillNoContact(0);
    			total.setWillTotalNum(0);
    			total.setWillSignNum(0);
    			total.setWillCustNum(0);
    			total.setWillPoolNum(0);
    			total.setWillNoNum(0);
    			total.setTradCustNum(0);
    			total.setCallOutTime(0);
    			total.setSignMoney(0d);
    			
    	    	for (TsmReportPlanBean bean : teamList) {
    	    		total.setResPlanNum(total.getResPlanNum()+(bean.getResPlanNum()==null?0:bean.getResPlanNum()));
    	    		total.setResNoContact(total.getResNoContact()+(bean.getResNoContact()==null?0:bean.getResNoContact()));
    	    		total.setResTotalNum(total.getResTotalNum()+(bean.getResTotalNum()==null?0:bean.getResTotalNum()));
    	    		total.setResSignNum(total.getResSignNum()+(bean.getResSignNum()==null?0:bean.getResSignNum()));
    	    		total.setResCustNum(total.getResCustNum()+(bean.getResCustNum()==null?0:bean.getResCustNum()));
    	    		total.setResPoolNum(total.getResPoolNum()+(bean.getResPoolNum()==null?0:bean.getResPoolNum()));
    	    		total.setResNoNum(total.getResNoNum()+(bean.getResNoNum()==null?0:bean.getResNoNum()));
    	    		total.setWillPlanNum(total.getWillPlanNum()+(bean.getWillPlanNum()==null?0:bean.getWillPlanNum()));
    	    		total.setWillNoContact(total.getWillNoContact()+(bean.getWillNoContact()==null?0:bean.getWillNoContact()));
    	    		total.setWillTotalNum(total.getWillTotalNum()+(bean.getWillTotalNum()==null?0:bean.getWillTotalNum()));
    	    		total.setWillSignNum(total.getWillSignNum()+(bean.getWillSignNum()==null?0:bean.getWillSignNum()));
    	    		total.setWillCustNum(total.getWillCustNum()+(bean.getWillCustNum()==null?0:bean.getWillCustNum()));
    	    		total.setWillPoolNum(total.getWillPoolNum()+(bean.getWillPoolNum()==null?0:bean.getWillPoolNum()));
    	    		total.setWillNoNum(total.getWillNoNum()+(bean.getWillNoNum()==null?0:bean.getWillNoNum()));
    	    		total.setTradCustNum(total.getTradCustNum()+(bean.getTradCustNum()==null?0:bean.getTradCustNum()));
    	    		total.setCallOutTime(total.getCallOutTime()+(bean.getCallOutTime()==null?0:bean.getCallOutTime()));
    	    		total.setSignMoney(BigDecimal.valueOf(total.getSignMoney()).add(BigDecimal.valueOf(bean.getSignMoney()==null?0:bean.getSignMoney())).doubleValue());
    	    	}
    	    	
    	    	modelMap.put("groups", groups);
    	    	modelMap.put("toWeekFrom", DateUtil.formatDate(DateUtil.getWeekBegin(date), "yyyy-MM-dd"));
    	    	modelMap.put("toWeekTo", DateUtil.formatDate(DateUtil.getWeekEnd(date), "yyyy-MM-dd"));
    	    	modelMap.put("toMonthFrom",DateUtil.formatDate(DateUtil.monthBegin(date), "yyyy-MM-dd"));
    	    	modelMap.put("toMonthTo", DateUtil.formatDate(DateUtil.monthEnd(date), "yyyy-MM-dd"));
    	    	modelMap.put("userList", userList);
    	    	modelMap.put("teamList", teamList);
    	    	modelMap.put("total", total);
    	    	
    	    	modelMap.put("fromDate", fromDate);
    	    	modelMap.put("toDate", toDate);
    	    	//modelMap.put("fromDateStr", fromDateStr);
    	    	//modelMap.put("toDateStr", toDateStr);
    	    	modelMap.put("currentDate", date);
    	    	long endTime = System.currentTimeMillis();
    	    	if(logger.isDebugEnabled()) logger.debug((endTime-startTime)+"");
    	    	
        	} catch (Exception e) {
        		logger.error(e.getMessage(),e);
        	}
    	}*/
    	return "report/teamData";
    }
    
    public Date parseDateFromStr(String str,String type){
    	Calendar calendar = Calendar.getInstance();
    	String[] array = str.split("-");
    	calendar.set(Integer.parseInt(array[0]), Integer.parseInt(array[1])-1, Integer.parseInt(array[2]));
    	if("to".equals(type)){
    		return DateUtil.dateEnd(calendar.getTime());
    	}else{
    		return DateUtil.dateBegin(calendar.getTime());
    	}
    }
}
