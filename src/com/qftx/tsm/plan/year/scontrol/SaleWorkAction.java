package com.qftx.tsm.plan.year.scontrol;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.tsm.plan.year.service.SaleWorkService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/salWork")
public class SaleWorkAction {
	@Autowired
	private SaleWorkService saleWorkService ;
	
	Logger logger=Logger.getLogger(SaleWorkAction.class);
	
	/*页面加载数据*/
	@RequestMapping("/view")
	public String view(ModelMap map){
		try {
			ShiroUser user = getUser();
			map.put("date", System.currentTimeMillis());
			map.put("data", JsonUtil.getJsonString(saleWorkService.querySaleWorks(user.getOrgId())));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		return "plan/saleWork";
	}

	public ShiroUser getUser() {
		ShiroUser user = ShiroUtil.getShiroUser();
		return user;
	}
	
}
