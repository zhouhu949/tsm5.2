package com.qftx.tsm.worklog.scontrol;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.common.domain.BaseJsonResult;
import com.qftx.common.util.StringUtils;
import com.qftx.tsm.plan.ResultDTO;
import com.qftx.tsm.worklog.bean.WorkLogBbsBean;
import com.qftx.tsm.worklog.bean.WorkLogInfoBean;
import com.qftx.tsm.worklog.service.WorkLogBbsService;
import com.qftx.tsm.worklog.service.WorkLogBbsUpService;
import com.qftx.tsm.worklog.service.WorkLogInfoService;
import com.qftx.tsm.worklog.service.WorkLogShareService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/worklog/bbs")
public class BbsControl {
	@Autowired
	private WorkLogBbsService workLogBbsService;
	@Autowired
	private WorkLogShareService workLogShareService;
	@Autowired
	private WorkLogBbsUpService workLogBbsUpService;
	@Autowired
	private WorkLogInfoService workLogInfoService;
	
	Logger logger = Logger.getLogger(BbsControl.class);
	
	/* 评论页面*/
	@RequestMapping("/bbsWindow")
	public String commontWindow(ModelMap modelMap,WorkLogBbsBean item) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			List<WorkLogBbsBean> list = workLogBbsService.findListPage(user,item);
			
			WorkLogInfoBean log = new WorkLogInfoBean();
			log.setOrgId(user.getOrgId());
			log.setWliId(item.getWliId());
			
			log = workLogInfoService.getByCondtion(log);
			
			modelMap.put("log", log);
			modelMap.put("list", list);
			modelMap.put("wliId", item.getWliId());
			modelMap.put("item", item);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		return "/worklog/bbsWindow";
	}
	
	/* 评论页面*/
	@RequestMapping("/bbsJson")
	@ResponseBody
	public BaseJsonResult bbsJson(WorkLogBbsBean item) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			item.setOrgId(user.getOrgId());
			List<WorkLogBbsBean> list = workLogBbsService.findListPage(user,item);
			
			return BaseJsonResult.success("data", list);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return BaseJsonResult.error();
		}
		
	}

	/* 插入评论数据 */
	@RequestMapping("/insertCommont")
	@ResponseBody
	public ResultDTO insertCommont(WorkLogBbsBean workLogBbs) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			if(StringUtils.isBlank(workLogBbs.getReplyWlbId())) workLogBbs.setReplyWlbId(null);
			workLogBbsService.insertBbs(user, workLogBbs);
			return ResultDTO.Success();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return ResultDTO.erro("系统错误！");
		}
	}
	
	/* 删除评论数据 */
	@RequestMapping("/del")
	@ResponseBody
	public BaseJsonResult del(String id) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			
			WorkLogBbsBean del  =new WorkLogBbsBean();
			del.setOrgId(user.getOrgId());
			del.setWlbId(id);
			return workLogBbsService.delBbs(user,del);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return BaseJsonResult.error("系统错误！");
		}
	}

}
