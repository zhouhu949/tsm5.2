package com.qftx.tsm.progress.scontrol;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.tsm.progress.dto.ProgressBarDTO;
import com.qftx.tsm.progress.service.ProgressBarService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
@RequestMapping("/progress")
@Controller
public class ProgressBarAction {
	Logger logger = Logger.getLogger(ProgressBarAction.class.getName());
	
	@Autowired	
	ProgressBarService progressBarService;
	
	/*@RequestMapping("/page")
	public String page(){
		return "/progress/page";
	}
	
	@ResponseBody
	@RequestMapping("/start")
	public String runTask(HttpServletRequest request){
		String orgId = (String) request.getParameter("orgId");
		String account = (String) request.getParameter("account");
		ShiroUser user = ShiroUtil.getUser();
		String type = (String) request.getParameter("type");
		try {
			return progressBarService.startTask(user.getOrgId(), user.getAccount(), type);
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}
		
	}*/
	
	@ResponseBody
	@RequestMapping("/query")
	public Map<String, ProgressBarDTO> query(HttpServletRequest request){
		/*String orgId = (String) request.getParameter("orgId");
		String account = (String) request.getParameter("account");*/
		ShiroUser user = ShiroUtil.getUser();
		String type = (String) request.getParameter("type");
		try {
			return  progressBarService.queryProgress(user.getOrgId(), user.getAccount(), type);
		} catch (Exception e) {
			logger.error("query progress Error:" + e.getMessage(), e);
			return new HashMap<String, ProgressBarDTO>();
		}
	}
}
