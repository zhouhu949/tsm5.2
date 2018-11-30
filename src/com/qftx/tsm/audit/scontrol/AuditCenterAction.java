package com.qftx.tsm.audit.scontrol;

import com.qftx.common.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/audit/center")
public class AuditCenterAction {
	
	@RequestMapping()
	public String center(HttpServletRequest request,String type){
		request.setAttribute("type", StringUtils.isBlank(type) ? "1" : type);
		return "audit/audit_center";
	}
}
