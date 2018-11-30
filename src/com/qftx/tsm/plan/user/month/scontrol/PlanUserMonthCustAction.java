package com.qftx.tsm.plan.user.month.scontrol;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.tsm.plan.user.month.bean.PlanUsermonthCustBean;
import com.qftx.tsm.plan.user.month.service.PlanUsermonthCustService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/plan/month/user/cust")
public class PlanUserMonthCustAction {
	@Autowired
	private PlanUsermonthCustService service;
	
	Logger logger = Logger.getLogger(PlanUserMonthCustAction.class);
	
	//添加客户
	@RequestMapping("/findFromRes")
	@ResponseBody
	public List<PlanUsermonthCustBean> findFromRes(String custIdsStr){
		try {
			ShiroUser user = getUser();
			return service.findFromRes(user.getOrgId(), custIdsStr.split(","));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return new ArrayList<PlanUsermonthCustBean>();
		}
		
	}
	
	public ShiroUser getUser() {
		ShiroUser user = ShiroUtil.getShiroUser();
		return user;
	}
}
