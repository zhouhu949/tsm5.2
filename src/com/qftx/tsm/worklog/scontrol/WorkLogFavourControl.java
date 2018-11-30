package com.qftx.tsm.worklog.scontrol;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.common.domain.BaseJsonResult;
import com.qftx.tsm.worklog.bean.WorkLogBbsUpBean;
import com.qftx.tsm.worklog.service.WorkLogBbsService;
import com.qftx.tsm.worklog.service.WorkLogBbsUpService;
import com.qftx.tsm.worklog.service.WorkLogFavourLock;
import com.qftx.tsm.worklog.service.WorkLogShareService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/worklog")
public class WorkLogFavourControl {
	@Autowired
	private WorkLogBbsService service;

	@Autowired
	private WorkLogShareService workLogShareService;
	@Autowired
	private WorkLogBbsUpService workLogBbsUpService;
	
	Logger logger = Logger.getLogger(WorkLogFavourControl.class);

	/* 赞  、取消赞*/
	@RequestMapping("/favour")
	@ResponseBody
	public BaseJsonResult favour(String id,Integer type) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			synchronized (WorkLogFavourLock.getInstance().lock(user.getAccount())) {
				WorkLogBbsUpBean bbsUp = new WorkLogBbsUpBean();
				bbsUp.setWlbId(id);
				bbsUp.setUserId(user.getId());
				bbsUp.setOrgId(user.getOrgId());
				if(type == null || type==0 ) {
					bbsUp.setType(0);
				}else{
					bbsUp.setType(2);
				}
				return workLogBbsUpService.updateUpNum(bbsUp);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return BaseJsonResult.error();
		}
	}
	
	/* 点赞列表*/
	@RequestMapping("/favourList")
	public String favourList(ModelMap model,String id,Integer type) {
		model.put("id", id);
		model.put("type", type);
		return "/worklog/favourList";
	}
	
	/* 点赞列表*/
	@RequestMapping("/favourListJson")
	@ResponseBody
	public BaseJsonResult favourListJson(WorkLogBbsUpBean item) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			item.setOrgId(user.getOrgId());
			item.setOrderKey("inputdate desc");
			List<WorkLogBbsUpBean> list = workLogBbsUpService.findFavourList(item);
			
			BaseJsonResult result = BaseJsonResult.success();
			result.put("list", list);
			result.put("item", item);
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return BaseJsonResult.error();
		}
	}
	
	/* 赞状态*/
	/*@RequestMapping("/favourState")
	@ResponseBody
	public BaseJsonResult favourStatus(String id) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			WorkLogBbsUpBean query = new WorkLogBbsUpBean();
			query.setOrgId(user.getOrgId());
			query.setWlbId(id);
			query.setUserId(user.getId());
			WorkLogBbsUpBean up = workLogBbsUpService.getByCondtion(query);
			
			if(up!=null){
				return BaseJsonResult.success("state", true);
			}else{
				return BaseJsonResult.success("state", false);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return BaseJsonResult.error("系统错误！");
		}
	}*/
}
