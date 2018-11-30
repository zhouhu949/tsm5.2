package com.qftx.tsm.worklog.scontrol;

import com.qftx.tsm.worklog.service.WorkLogDefaultShareService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/worklog/defShare")
public class DefaultShareControl {
	@Autowired
	private WorkLogDefaultShareService service;
	
	Logger logger = Logger.getLogger(DefaultShareControl.class);

	/* 查询默认分享用户 
	@RequestMapping("/queryDefaultShareUser")
	@ResponseBody
	public List<WorkLogDefaultBean> queryDefaultShareUser() {
		try {
			ShiroUser user = getUser();
			return service.queryByUserId(user.getOrgId(),user.getId());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return new ArrayList<WorkLogDefaultBean>();
		}
		
	}
	
	public ShiroUser getUser() {
		return ShiroUtil.getShiroUser();
	}*/

}
