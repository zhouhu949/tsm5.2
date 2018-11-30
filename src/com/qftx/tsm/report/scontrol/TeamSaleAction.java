package com.qftx.tsm.report.scontrol;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.team.bean.TeamGroupBean;
import com.qftx.base.team.bean.TsmTeamGroupMemberBean;
import com.qftx.base.team.service.TsmTeamGroupMemberService;
import com.qftx.base.team.service.TsmTeamGroupService;
import com.qftx.base.util.DateUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.util.StringUtils;
import com.qftx.tsm.plan.group.month.bean.PlanGroupmonthBean;
import com.qftx.tsm.plan.user.month.bean.PlanUsermonthBean;
import com.qftx.tsm.report.dto.FullYearUserPlanDTO;
import com.qftx.tsm.report.dto.TeamSaleDTO;
import com.qftx.tsm.report.service.TeamSaleService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

/**
 * User�� bxl
 * Date�� 2015/12/18
 * Time�� 15:29
 */
@Controller
@RequestMapping(value = "/report/teamSale")
public class TeamSaleAction {
    private static final Logger logger = Logger.getLogger(TeamSaleAction.class.getName());
    
    @Autowired
    private TeamSaleService teamSaleService;
    @Autowired
    private TsmTeamGroupService tsmTeamGroupService;
    @Autowired 
	private TsmTeamGroupMemberService tsmTeamGroupMemberService;
    @RequestMapping()
    public String teamSale(ModelMap modelMap,PlanGroupmonthBean item,Integer planYear,String type){
    	try {
    		ShiroUser user = ShiroUtil.getUser();
        	
    		List<TeamGroupBean> groups = tsmTeamGroupService.queryManageGroupList(user.getOrgId(), user.getAccount());
        	
        	if(groups==null ||groups.size()==0){
				return "/error/no_root";
			}
        	
        	if(StringUtils.isBlank(type))type="0";
        	
        	if(planYear==null) {
        		Date date =  new Date();
        		planYear = DateUtil.year(date);
        	}      
        	
        	if(StringUtils.isBlank(item.getGroupIdsStr())){
				item.setGroupIds(tsmTeamGroupService.getGroupIds(groups));
				item.setGroupNames(tsmTeamGroupService.getGroupNamesStr(groups));
				item.setGroupIdsStr(tsmTeamGroupService.getGroupIdsStr(groups));
	    	}else{
	    		item.setGroupIds(item.getGroupIdsStr().split(","));
	    	}
        	
        	TeamSaleDTO dto = teamSaleService.findGroupFullYearPlanMap(user.getOrgId(), item.getGroupIds(), planYear);
        	
        	modelMap.put("groupPlans", dto.getGroupList());
        	modelMap.put("totalMap", dto.getTotalMap());
        	modelMap.put("totalMapJson", JsonUtil.getJsonString(dto.getTotalMap()));
        	modelMap.put("planYear", planYear);
        	modelMap.put("type", type);
        	modelMap.put("groups", groups);
        	modelMap.put("item", item);
        	modelMap.put("months", new String[]{"1","2","3","4","5","6","7","8","9","10","11","12"});
    	} catch (Exception e) {
    		logger.error(e.getMessage(),e);
    	}
        return "report/teamSaleTarget/team_sales_target_achieve";
    }
    @RequestMapping("/user")
    public String user(ModelMap modelMap,PlanUsermonthBean item,Integer planYear,String type){
    	try {
    		ShiroUser user = ShiroUtil.getUser();
        	
        	if(StringUtils.isBlank(type))type="0";
        	
        	if(planYear==null) {
        		Date date =  new Date();
        		planYear = DateUtil.year(date);
        	}
        	
        	List<String> childGroupIds = tsmTeamGroupService.findAllSonGroupIds(user.getOrgId(), item.getGroupId());
        	childGroupIds.add(item.getGroupId());
        	List<TsmTeamGroupMemberBean> members = tsmTeamGroupMemberService.findByGroupIds(user.getOrgId(),childGroupIds.toArray(new String[childGroupIds.size()]));
        	
        	if(item.getUserIds()==null||item.getUserIds().length==0){
        		item.setUserIds(tsmTeamGroupMemberService.getUserIds(members));
        		item.setUserNames(tsmTeamGroupMemberService.getUserNames(members));
        	}
        	List<FullYearUserPlanDTO> userPlans = teamSaleService.findUsersFullYearPlanMap(user.getOrgId(),item.getUserIds(), planYear);
        	
        	modelMap.put("item", item);
        	modelMap.put("members", members);
        	modelMap.put("userPlans", userPlans);
        	modelMap.put("planYear", planYear);
        	modelMap.put("type", type);
        	modelMap.put("months", new String[]{"1","2","3","4","5","6","7","8","9","10","11","12"});
    	} catch (Exception e) {
    		logger.error(e.getMessage(),e);
    	}
        return "report/teamSaleTarget/user";
    }
}
