package com.qftx.tsm.follow.scontrol;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.DateUtil;
import com.qftx.tsm.sys.dto.ServiceVisitDto;
import com.qftx.tsm.sys.service.ServiceVisitService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/***
 * 服务记录
 * @author: zwj
 * @since: 2015-12-4  下午2:14:09
 * @history: 4.x
 */
@Controller
@RequestMapping(value = "/cust/service/visit")
public class ServiceVisitControl {
	private static Logger logger = Logger.getLogger(ServiceVisitControl.class);

	@Autowired
	private ServiceVisitService serviceVisitService;
	
	/** 
	 * 服务记录
	 */
	@RequestMapping("/serviceVisitListPage")
	public String serviceVisitListPage(HttpServletRequest request,ServiceVisitDto dto){
		try {
				ShiroUser user = ShiroUtil.getShiroUser();
				if (StringUtils.isNotEmpty(dto.getAccs())) {
					String[] ownerAccs = dto.getAccs().split(",");
					List<String> owaList =Arrays.asList(ownerAccs);
					dto.setOwnerAccs(owaList);
				}else{
					dto.setOwnerAcc(user.getAccount());
				}
				String  allLabels=request.getParameter("lables");				
				// 组装选中标签
				if(StringUtils.isNotBlank(allLabels)){
					dto.setLables(allLabels.split(","));
					request.setAttribute("allLabels", allLabels);
				}	
				
				String today=DateUtil.format(new Date(), DateUtil.defaultPattern);		
				//今日服务记录
				if (StringUtils.isBlank(dto.getVstartDate())){
					dto.setVstartDate(today);
				}
				dto.setOrgId(user.getOrgId());
				List<ServiceVisitDto> serviceVisitList = serviceVisitService.queryTeamTodayVisitListPage(dto);
				request.setAttribute("serviceVisitList", serviceVisitList);
				request.setAttribute("item", dto);
			} catch (Exception e) {
				throw new SysRunException(e);
			}
		return "newQueryTeamTodayVisitList";
	}	
}
