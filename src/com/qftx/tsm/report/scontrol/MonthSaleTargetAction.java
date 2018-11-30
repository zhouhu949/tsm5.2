package com.qftx.tsm.report.scontrol;

import com.qftx.base.auth.service.OrgGroupService;
import com.qftx.base.auth.service.OrgGroupUserService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.team.bean.TeamGroupBean;
import com.qftx.base.team.bean.TsmTeamGroupMemberBean;
import com.qftx.base.team.service.TsmTeamGroupMemberService;
import com.qftx.base.team.service.TsmTeamGroupService;
import com.qftx.base.util.DateUtil;
import com.qftx.common.util.StringUtils;
import com.qftx.tsm.plan.group.month.bean.PlanGroupmonthBean;
import com.qftx.tsm.plan.group.month.service.PlanGroupmonthService;
import com.qftx.tsm.plan.user.month.bean.PlanUsermonthBean;
import com.qftx.tsm.plan.user.month.service.PlanUserMonthService;
import com.qftx.tsm.report.utils.DateSubUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

/**
 *  月计划执行结果
 */
@Controller
@RequestMapping(value = "/report")
public class MonthSaleTargetAction {
    private static final Logger logger = Logger.getLogger(MonthSaleTargetAction.class);

    @Autowired
    private PlanUserMonthService userService;
    @Autowired
    private PlanGroupmonthService groupService;
    @Autowired
    private OrgGroupService orgGroupService;
    @Autowired
    private OrgGroupUserService orgGroupUserService;
    @Autowired 
    private TsmTeamGroupService tsmTeamGroupService;
    @Autowired
    private TsmTeamGroupMemberService tsmTeamGroupMemberService;
    
    @RequestMapping("/monthSaleTarget")
    public String list(ModelMap modelMap,PlanGroupmonthBean item,String dateStr){
    	try {
    		ShiroUser user = ShiroUtil.getUser();
        	
        	List<TeamGroupBean> groups = tsmTeamGroupService.queryManageGroupList(user.getOrgId(), user.getAccount());
        	
        	if(groups==null ||groups.size()==0){
				return "/error/no_root";
			}
        	Date date = null;
        	if(StringUtils.isNotEmpty(dateStr)){
        		date = DateSubUtil.parseDateFromStr(dateStr);  
        	}else{
        		date = new Date();
        		dateStr = DateUtil.getDataMonth(date);
        	}
        	
        	if(StringUtils.isBlank(item.getGroupIdsStr())){
				item.setGroupIds(tsmTeamGroupService.getGroupIds(groups));
				item.setGroupNames(tsmTeamGroupService.getGroupNamesStr(groups));
				item.setGroupIdsStr(tsmTeamGroupService.getGroupIdsStr(groups));
	    	}else{
	    		item.setGroupIds(item.getGroupIdsStr().split(","));
	    	}
        	
        	List<PlanGroupmonthBean> list = groupService.findByCondtion(user.getOrgId(),item.getGroupIds(), DateUtil.year(date), DateUtil.month(date));
        	PlanGroupmonthBean total = groupService.getTotal(list);
        	
        	modelMap.put("total", total);
        	modelMap.put("item", item);
        	modelMap.put("groups", groups);
        	modelMap.put("list", list);
        	modelMap.put("dateStr",dateStr);
        	
    	} catch (Exception e) {
    		logger.error(e.getMessage(),e);
    	}
    	
    	return "report/monthSaleTarget/group";
    }
    
    
    @RequestMapping("/monthSaleTarget/user")
    public String user(ModelMap modelMap,PlanUsermonthBean item,String groupId,String dateStr){
    	try {
    		ShiroUser user = ShiroUtil.getUser();
        	Date date = null;
        	if(StringUtils.isNotEmpty(dateStr)){
        		date = DateSubUtil.parseDateFromStr(dateStr);  
        	}else{
        		date = new Date();
        		dateStr = DateUtil.getDataMonth(date);
        	}
        	
        	List<String> childGroupIds = tsmTeamGroupService.findAllSonGroupIds(user.getOrgId(), item.getGroupId());
        	childGroupIds.add(item.getGroupId());
    		List<TsmTeamGroupMemberBean> 	members = tsmTeamGroupMemberService.findByGroupIds(user.getOrgId(),childGroupIds.toArray(new String[childGroupIds.size()]));
        	
        	if(item.getUserIds()==null||item.getUserIds().length==0){
        		item.setUserIds(tsmTeamGroupMemberService.getUserIds(members));
        		item.setUserNames(tsmTeamGroupMemberService.getUserNames(members));
        	}
        	List<PlanUsermonthBean> list = userService.findByCondtion(user.getOrgId(), item.getUserIds(),  DateUtil.year(date), DateUtil.month(date));
        	
        	modelMap.put("members", members);
        	modelMap.put("item", item);
        	modelMap.put("list", list);
        	modelMap.put("dateStr",dateStr);
    	} catch (Exception e) {
    		logger.error(e.getMessage(),e);
    	}
    	
    	return "report/monthSaleTarget/user";
    }
}
