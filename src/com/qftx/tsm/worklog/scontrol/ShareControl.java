package com.qftx.tsm.worklog.scontrol;


import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.team.bean.TeamGroupBean;
import com.qftx.base.team.service.TsmTeamGroupMemberService;
import com.qftx.base.team.service.TsmTeamGroupService;
import com.qftx.common.util.StringUtils;
import com.qftx.tsm.plan.ResultDTO;
import com.qftx.tsm.worklog.bean.WorkLogAttentionBean;
import com.qftx.tsm.worklog.bean.WorkLogDefaultBean;
import com.qftx.tsm.worklog.bean.WorkLogInfoBean;
import com.qftx.tsm.worklog.bean.WorkLogShareBean;
import com.qftx.tsm.worklog.dto.WorkLogDto;
import com.qftx.tsm.worklog.service.WorkLogAttentionService;
import com.qftx.tsm.worklog.service.WorkLogDefaultShareService;
import com.qftx.tsm.worklog.service.WorkLogInfoService;
import com.qftx.tsm.worklog.service.WorkLogShareService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/worklog/share")
public class ShareControl {
	@Autowired
	private WorkLogShareService workLogShareService;
	@Autowired
	private WorkLogDefaultShareService defservice;
	@Autowired
	private WorkLogInfoService workLogInfoService;
	@Autowired
	private WorkLogAttentionService workLogAttentionService;
	@Autowired
	private TsmTeamGroupService tsmTeamGroupService;
	@Autowired
	private TsmTeamGroupMemberService tsmTeamGroupMemberService;
	
	Logger logger = Logger.getLogger(ShareControl.class);
	/* 共享页面  */
	@RequestMapping("/window")
	public String window(ModelMap modelMap,String wliId,int type) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			
			StringBuilder shareUserAccs = new StringBuilder();
			if(type==0){
				List<WorkLogDefaultBean> list = defservice.queryByUserId(user.getOrgId(), user.getId());
				for (int i =0; i<list.size();i++) {
					if(i!=0) shareUserAccs.append(",");
					shareUserAccs.append(list.get(i).getDefaultShareUseracc());
				}
			}else{
				List<WorkLogShareBean> list = workLogShareService.findByCondtion(user.getOrgId(), wliId);
				for (int i =0; i<list.size();i++) {
					if(i!=0) shareUserAccs.append(",");
					shareUserAccs.append(list.get(i).getShareUseracc());
				}
			}
			
			modelMap.put("wliId", wliId);
			modelMap.put("shareUserAccs", shareUserAccs);
			modelMap.put("type", type);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		return "/worklog/shareWindow";
	}
	
	@RequestMapping("/shareLog")
	public String shareLog(ModelMap modelMap,WorkLogInfoBean item) {
		ShiroUser user = ShiroUtil.getShiroUser();
		/*if(StringUtils.isBlank(item.getShareUserId())) item.setShareUserId(null);
		if(StringUtils.isBlank(item.getQueryText())) item.setQueryText(null);
		
		List<TeamGroupBean> groups = tsmTeamGroupService.queryManageGroupList(user.getOrgId(), user.getAccount());
		
		if(item.getLogDate()==null){
			item.setLogDate(DateUtil.dateEnd(new Date()));
		}else{
			item.setLogDate(DateUtil.dateEnd(item.getLogDate()));
		}
		List<WorkLogInfoBean> shareLogs =null;
		if(item.getShareUserId()==null && item.getQueryText() == null){
			if((item.getIsAll() || item.getGroupIds()==null ||item.getGroupIds().length==0)&& groups!=null &&groups.size()>0) item.setGroupIds(tsmTeamGroupService.getGroupIds(groups));
			shareLogs = workLogShareService.findShareLogByPage(user,item,null);
		}else{
			shareLogs = workLogInfoService.findWorkLogByUserId(user.getOrgId(), item, null);
		}
		workLogAttentionService.processAttention(user, shareLogs);
		List<WorkLogDto> dtos = workLogInfoService.processLog(user,shareLogs);*/
		
		List<TeamGroupBean> groups = tsmTeamGroupService.queryManageGroupList(user.getOrgId(), user.getAccount());
		List<WorkLogAttentionBean> attentions = workLogAttentionService.findList(user);
		
		if(groups!=null && groups.size()>0){
			modelMap.put("isManager", true);
		}
		//modelMap.put("dtos", dtos);
		
		//modelMap.put("item", item);
		//modelMap.put("isAll", item.getIsAll());
		modelMap.put("item", item);
		modelMap.put("attentions", attentions);
		return "/worklog/shareLog";
	}
	
	@RequestMapping("/shareLogJson")
	@ResponseBody
	public Map<String, Object> shareLogJson(WorkLogInfoBean item,String type) {
		Map<String, Object> map= new HashMap<String, Object>();
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			if(StringUtils.isBlank(item.getUserId())) item.setUserId(null);
			if(StringUtils.isBlank(item.getQueryText())) item.setQueryText(null);
			
			List<WorkLogInfoBean> shareLogs =null;
			item.setRejectUserId(user.getId());
			
			if(!StringUtils.isBlank(item.getUserId())||!StringUtils.isBlank(item.getQueryText())||(item.getUserIds()!=null && item.getUserIds().length>0)){
				//1搜索
				//2选择下属成员
				//3选择关注用户
				item.setStatus(1);
				shareLogs = workLogInfoService.findWorkLogByUserId(user.getOrgId(), item, type);
			}else {
				List<TeamGroupBean> groups = tsmTeamGroupService.queryManageGroupList(user.getOrgId(), user.getAccount());
				if(groups.size()>0) item.setGroupIds(tsmTeamGroupService.getGroupIds(groups));
				shareLogs = workLogShareService.findShareLogByPage(user,item,type);
			}
			
			workLogAttentionService.processAttention(user, shareLogs);
			List<WorkLogDto> dtos = workLogInfoService.processLog(user,shareLogs);
			map.put("dtos", dtos);
			map.put("item", item);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return map;
	}
	
	/*@RequestMapping("/findUsers")
	@ResponseBody
	public List<TsmTeamGroupMemberBean> getUsers(String groupIdsStr){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			return tsmTeamGroupMemberService.findByGroupIds(user.getOrgId(), groupIdsStr.split(","));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return  new ArrayList<TsmTeamGroupMemberBean>();
		}
	}*/
	
	/* 插入分享 */
	@RequestMapping("/save")
	@ResponseBody
	public ResultDTO saveShareUser(String wliId, String accArrayStr,String idArrayStr,int type) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			
			String[] accArray = new String[]{};
			String[] idArray = new String[]{};
			
			if(StringUtils.isBlank(accArrayStr)){
				accArrayStr = null;
			}else{
				accArray = accArrayStr.split(",");
			}
			
			if(StringUtils.isBlank(idArrayStr)){
				idArrayStr = null;
			}else{
				idArray = idArrayStr.split(",");
			}
			if(type==0){
				defservice.save(user.getOrgId(),user.getId(),accArray,idArray);
				return ResultDTO.Success();
			}else{
				return workLogShareService.saveShareUser(user.getOrgId(),user.getId(), wliId, accArray,idArray);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return ResultDTO.erro("系统错误");
		}
		
	}
}
