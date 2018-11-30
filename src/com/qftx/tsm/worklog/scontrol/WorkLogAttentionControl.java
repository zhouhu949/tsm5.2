package com.qftx.tsm.worklog.scontrol;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.common.util.StringUtils;
import com.qftx.tsm.plan.ResultDTO;
import com.qftx.tsm.worklog.bean.WorkLogAttentionBean;
import com.qftx.tsm.worklog.service.WorkLogAttentionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/worklog")
public class WorkLogAttentionControl {
	@Autowired
	private WorkLogAttentionService workLogAttentionService;
	Logger logger = Logger.getLogger(WorkLogAttentionControl.class);

	/* 获取关注列表*/
	@RequestMapping("/attentionList")
	@ResponseBody
	public List<WorkLogAttentionBean> attentionList() {
		ShiroUser user = ShiroUtil.getShiroUser();
		return workLogAttentionService.findList(user);
	}
	
	/* 关注   取消关注*/
	@RequestMapping("/attention")
	@ResponseBody
	public ResultDTO attention(String attentionUserId,String type) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			if(StringUtils.isNotBlank(attentionUserId)){
				//if(attentionUserId.equals(user.getId())) return ResultDTO.erro("不能对自己进行关注操作！");
				if("add".equals(type)||"cancel".equals(type)){
					return workLogAttentionService.attention(user, attentionUserId, type);
				}else{
					return ResultDTO.erro("关注类型不识别！");
				}
			}else{
				return ResultDTO.erro("关注用户ID为空！");
			}
		} catch (Exception e) {
			logger.error("",e);
			return ResultDTO.erro("系统错误！");
		}
		
	}

}
