package com.qftx.tsm.contract.scontrol;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.tsm.contract.service.ContractOrderExpressInfoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/courier")
public class ExpInfoAction {
	private Log log = LogFactory.getLog(ExpInfoAction.class);
	
	@Autowired
	private ContractOrderExpressInfoService contractOrderExpressInfoService;
	
	@RequestMapping("/view")
	public String view(HttpServletRequest request,String courierNumber,String orderId){
		request.setAttribute("courierNumber", courierNumber);
		request.setAttribute("orderId", orderId);
		return "contract/courier_query";
	}
	
	@RequestMapping("/data")
	@ResponseBody
	public Map<String, Object> data(HttpServletRequest request,String courierNumber,String orderId){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			Map<String, Object> express_info = contractOrderExpressInfoService.searchExpressInfo(user.getOrgId(), orderId, courierNumber);
			map.put("data", express_info.get("data"));
			map.put("courierNumber", courierNumber);
			map.put("expTextName", express_info.get("expTextName"));
			map.put("state", "1");
		} catch (Exception e) {
			log.error("查询物流信息异常！",e);
			map.put("state", "-1");
			map.put("msg", "查询物流信息异常，错误信息："+e.getMessage());
		}
		return map;
	}
}
