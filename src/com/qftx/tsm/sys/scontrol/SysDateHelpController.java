package com.qftx.tsm.sys.scontrol;


import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import com.qftx.common.cached.CachedService;
import com.qftx.common.util.IContants;
import com.qftx.common.util.StringUtils;

/**
 * 帮助视频
 */
@Controller
@RequestMapping("/sysDateHelp")
public class SysDateHelpController{
	Logger logger=Logger.getLogger(SysDateHelpController.class);
	@Autowired private CachedService cachedService;
		
	@RequestMapping("/jump")
	public ModelAndView searchSetPage(HttpServletRequest request,HttpServletResponse response){
		String url = request.getParameter("url");	
		
		String redirectUrl = "http://www.qftx.com";
		try{
			if(StringUtils.isNotBlank(url)){
				url = URLDecoder.decode(url, IContants.CHAR_ENCODING);
				Map<String,String>urlMap = cachedService.getSysHelpUrl();
				for (String key : urlMap.keySet()) {
					if(StringUtils.isNotBlank(key) && url.contains(key)){
						redirectUrl = urlMap.get(key);
						break;
					}
				}
			 }				
		}catch(Exception e){
			logger.error("/sysDateHelp/jump 异常！", e);
		}	
		return  new ModelAndView(new RedirectView(redirectUrl));
	}
	
}
