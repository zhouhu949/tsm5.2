package com.qftx.tsm.report.scontrol;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.team.bean.TsmTeamGroupMemberBean;
import com.qftx.base.team.service.TsmGroupShareinfoService;
import com.qftx.base.team.service.TsmTeamGroupMemberService;
import com.qftx.base.team.service.TsmTeamGroupService;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.SysRunException;
import com.qftx.tsm.log.dto.LogContactDayDataDto;
import com.qftx.tsm.log.service.LogContactDayDataService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.util.*;

@Controller
@RequestMapping(value="/report/contact")
public class ContactDetailDataAction {
	
	private Log log = LogFactory.getLog(ContactDetailDataAction.class);
	
	@Autowired
	private LogContactDayDataService logContactDayDataService;
	@Autowired
	private TsmGroupShareinfoService tsmGroupShareinfoService;
	@Autowired
	private TsmTeamGroupService tsmTeamGroupService;
	@Autowired
	private TsmTeamGroupMemberService tsmTeamGroupMemberService;
	@RequestMapping()
	public String index(LogContactDayDataDto dataDto,HttpServletRequest request){
		String view = "";
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			if(dataDto.getCustType() == 1){
				if(dataDto.getChangeType() == 1){
					view = "report/idialog/changedToInt";//资源-转意向
				}else{
					view = "report/idialog/changedToSign";//资源-转签约
				}
			}else{
				if(dataDto.getChangeType() == 1){
					view = "report/idialog/changedIntention";//意向-意向变更
				}else{
					view = "report/idialog/changedToSign";//意向-转签约
				}
			}
			//历史数据
			request.setAttribute("history", StringUtils.isEmpty(dataDto.getDateStr())||StringUtils.isEmpty(dataDto.getStartDate()));
			request.setAttribute("dataDto", dataDto);
			request.setAttribute("shiroUser", user);
		} catch (Exception e) {
			new SysRunException(e);
		}
		return view;
	}
	
	@RequestMapping(value="/data")
	@ResponseBody
	public Map<String, Object> data(LogContactDayDataDto dataDto,HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			dataDto.setOrgId(user.getOrgId());
			if(StringUtils.isBlank(dataDto.getOwnerAcc())){
				String groupIdStr = dataDto.getGroupIdStr();
				List<String> accs = new ArrayList<String>();
				if(StringUtils.isBlank(groupIdStr)){
					accs = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(), user.getAccount());
				}else{
					List<String> childGroupIds = tsmTeamGroupService.findAllSonGroupIds(user.getOrgId(), groupIdStr);
			    	childGroupIds.add(groupIdStr);
					List<TsmTeamGroupMemberBean> members = tsmTeamGroupMemberService.findByGroupIds(user.getOrgId(),childGroupIds.toArray(new String[childGroupIds.size()]));
			    	
					for (TsmTeamGroupMemberBean tsmTeamGroupMemberBean : members) {
						accs.add(tsmTeamGroupMemberBean.getMemberAcc());
					}
					//accs = tsmGroupShareinfoService.getMemberAccsByGroupIds(user.getOrgId(),Arrays.asList(groupIdStr.split(",")));
				}
				dataDto.setOwnerAccs(accs);
			}
			List<LogContactDayDataDto> list = logContactDayDataService.getLogDetailInfoListPage(dataDto);
			map.put("list", list);
			map.put("item", dataDto);
			map.put("isState", user.getIsState());
		} catch (Exception e) {
			log.error("联系结果详情查看异常！",e);
		}
		return map;
	}
	
}
