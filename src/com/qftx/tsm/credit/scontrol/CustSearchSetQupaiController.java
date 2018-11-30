package com.qftx.tsm.credit.scontrol;


import com.qftx.base.auth.service.UserService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.cached.CachedNames;
import com.qftx.common.cached.CachedUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.credit.service.CustSearchSetQupaiService;
import com.qftx.tsm.sys.bean.CustSearchSet;
import com.qftx.tsm.sys.enums.SysEnum;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.List;

/**
 * qupai查询项配置
 */
@Controller
@RequestMapping("/quPaiSearchManager")
public class CustSearchSetQupaiController{
	Logger logger=Logger.getLogger(CustSearchSetQupaiController.class);
	@Autowired private CustSearchSetQupaiService custSearchSetQupaiService;
	@Autowired private UserService userService;
	
	@RequestMapping("/searchSetPage")
	public String searchSetPage(HttpServletRequest request){
		try{
			String module = request.getParameter("module"); // 模块类型值
			ShiroUser user = ShiroUtil.getShiroUser();
			CustSearchSet searchSet = new CustSearchSet();
			searchSet.setOrgId(user.getOrgId());
			searchSet.setEnable(1);
			searchSet.setSearchModule(StringUtils.isNotBlank(module)?module:SysEnum.QUPAI_SEARCH_SET_MODULE_1.getState());
			searchSet.setOrderKey("sort asc");
			List<CustSearchSet> entitys = custSearchSetQupaiService.getListByCondtion(searchSet);
			request.setAttribute("entitys", entitys);
			request.setAttribute("entitysJson", JsonUtil.getJsonString(entitys));
			request.setAttribute("module", searchSet.getSearchModule());
		}catch(Exception e){
			logger.error("searchSetPage 异常！", e);
		}	
		return "/qupai/sys/searchManager/queryManagement";
	}
	
	
	/** 修改 */
	@RequestMapping("/modfiy") 
    @ResponseBody  
    public String modfiy(HttpServletRequest request) { 
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			String jsonDate = request.getParameter("jsonDate");
			List<CustSearchSet> custSearchSets = JsonUtil.getListJson(jsonDate, CustSearchSet.class);
			if(custSearchSets != null && custSearchSets.size() > 0){
				for(CustSearchSet custSearchSet : custSearchSets){
					custSearchSet.setModifierAcc(user.getAccount());
					custSearchSet.setModifydate(new Date());
				}
				custSearchSetQupaiService.batchUpdate(custSearchSets);
			}
			
		
			// 删除缓存
			CachedUtil.getInstance().delete(user.getOrgId() + CachedNames.SEPARATOR + CachedNames.QUPAI_SEARCH_SET);
			// 删除高级查询缓存
		
			CachedUtil.getInstance().delete(user.getOrgId() + CachedNames.SEPARATOR +"0"+CachedNames.QUPAi_HIGH_SEARCH_+custSearchSets.get(0).getSearchModule());
			CachedUtil.getInstance().delete(user.getOrgId() + CachedNames.SEPARATOR +"1"+CachedNames.QUPAi_HIGH_SEARCH_+custSearchSets.get(0).getSearchModule());
			
//			List<String> accounts = userService.getUserAccounts(user.getOrgId());
//			for(String account : accounts){
//				CachedUtil.getInstance().delete(user.getOrgId() + CachedNames.SEPARATOR +account+CachedNames.QUPAi_HIGH_SEARCH_+custSearchSets.get(0).getSearchModule());
//			}
			return AppConstant.RESULT_SUCCESS;
		}catch(Exception e){
			logger.error("查询项管理保存异常！", e);
			return AppConstant.RESULT_EXCEPTION;
		}
    }
	
}
